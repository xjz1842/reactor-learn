package com.reactor.learn.web;


import reactor.core.publisher.Flux;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.server.HttpServer;

public class RouteTest {

    public static void main(String[] args) {

        HttpServer.create()   // Prepares an HTTP server ready for configuration
                .port(8080)    // Configures the port number as zero, this will let the system pick up
                // an ephemeral port when binding the server
                .route(routes ->
                        // The server will respond only on POST requests
                        // where the path starts with /test and then there is path parameter
                        routes.post("/test/{param}", (request, response) ->
                                response.sendString(request.receive()
                                        .asString()
                                        .map(s -> s + ' ' + request.param("param") + '!')
                                        .log("http-server"))))
                .bindNow(); // Starts the server in a blocking fashion, and waits for it to finish its initialization


        HttpClient.create()             // Prepares an HTTP client ready for configuration
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
