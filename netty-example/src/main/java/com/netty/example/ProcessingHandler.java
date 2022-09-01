package com.netty.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicBoolean;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class ProcessingHandler extends SimpleChannelInboundHandler {

    private HttpRequest request;
    StringBuilder responseData = new StringBuilder();
    AtomicBoolean failed = new AtomicBoolean(false);

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpRequest) {
            HttpRequest request = this.request = (HttpRequest) msg;
            if (!HttpMethod.GET.equals(request.method())){
                failed.set(true);
                return;
            }
            String[] paths = request.uri().split("/");
            if (paths.length != 3 || !paths[1].equalsIgnoreCase("hello")){
                failed.set(true);
                return;
            }

            if (HttpUtil.is100ContinueExpected(request)) {
                writeResponse(ctx);
            }
            responseData.setLength(0);
            responseData.append("Hello " + paths[2]);
        }

        if (msg instanceof LastHttpContent) {
            LastHttpContent trailer = (LastHttpContent) msg;
            if (failed.get()){
                writeErrorResponse(ctx);
            } else {
                writeResponse(ctx, trailer, responseData);
            }
        }
    }

    private void writeErrorResponse(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN, Unpooled.EMPTY_BUFFER);
        ctx.write(response);
    }

    private void writeResponse(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE, Unpooled.EMPTY_BUFFER);
        ctx.write(response);
    }

    private void writeResponse(ChannelHandlerContext ctx, LastHttpContent trailer,
                               StringBuilder responseData) {
        boolean keepAlive = HttpUtil.isKeepAlive(request);
        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HTTP_1_1,
                ((HttpObject) trailer).decoderResult().isSuccess() ? OK : BAD_REQUEST,
                Unpooled.copiedBuffer(responseData.toString(), CharsetUtil.UTF_8));

        httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");

        if (keepAlive) {
            httpResponse.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,
                    httpResponse.content().readableBytes());
            httpResponse.headers().set(HttpHeaderNames.CONNECTION,
                    HttpHeaderValues.KEEP_ALIVE);
        }
        ctx.write(httpResponse);

        if (!keepAlive) {
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
