package com.reactor.learn.web.http;

public class HttpServer {

    public static void main(String[] args) {
        reactor.netty.http.server.HttpServer.create()   // Prepares an HTTP server ready for configuration
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
    }
}
