package com.example.demo;

import reactor.core.publisher.Mono;

public class MessageService {
    private final String message;

    public MessageService(String message) {
        this.message = message;
    }

    public Mono<String> sayHello(String name) {
        var text = message + " " + name;
        return Mono.just(text);
    }
}
