package com.reactor.learn.web.tcp;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;

public class EchoTcpClient {

    static final boolean SECURE = System.getProperty("secure") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", SECURE ? "8443" : "8080"));
    static final boolean WIRETAP = System.getProperty("wiretap") != null;


    public static void main(String[] args) {
        TcpClient client =
                TcpClient.create()
                        .port(PORT)
                        .wiretap(WIRETAP);

        if (SECURE) {
            client = client.secure(
                    spec -> spec.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)));
        }

        Connection connection =
                client.handle((in, out) -> out.send(Flux.concat(ByteBufFlux.fromString(Mono.just("echo")),
                        in.receive().retain())))
                        .connectNow();

        connection.onDispose()
                .block();
    }
}
