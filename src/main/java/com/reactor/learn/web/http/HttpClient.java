package com.reactor.learn.web.http;


import reactor.core.publisher.Flux;
import reactor.netty.ByteBufFlux;

public class HttpClient {

    public static void main(String[] args) {

        reactor.netty.http.client.HttpClient.create()             // Prepares an HTTP client ready for configuration
                .port(8080)  // Obtains the server's port and provides it as a port to which this
                // client should connect
                .post()               // Specifies that POST method will be used
                .uri("/test/World")   // Specifies the path
                .send(ByteBufFlux.fromString(Flux.just("Hello")))  // Sends the request body
                .responseContent()    // Receives the response body
                .aggregate()
                .asString()
                .log("http-client")
                .block();
    }

}
