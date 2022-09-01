package com.netty.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class ExampleApplication{

    private int port;
//    private static Logger logger = LoggerFactory.getLogger(ExampleApplication.class);

    private static final AtomicLong serverStart = new AtomicLong(0);
    public ExampleApplication(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        serverStart.set(System.currentTimeMillis());
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;

        new ExampleApplication(port).run();
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new HttpRequestDecoder(),
                                    new HttpResponseEncoder(),
                                    new ProcessingHandler());
                        }
                    })
            .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();
            System.out.println("Netty started in " + (System.currentTimeMillis() - serverStart.get()) + " ms.");
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}