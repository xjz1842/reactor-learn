package com.reactor.learn.web.tcp;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import reactor.netty.tcp.TcpServer;


public class EchoTcpServer {

        static final boolean SECURE = System.getProperty("secure") != null;
        static final int PORT = Integer.parseInt(System.getProperty("port", SECURE ? "8443" : "8080"));
        static final boolean WIRETAP = System.getProperty("wiretap") != null;

        public static void main(String[] args) throws Exception { TcpServer server =
                     TcpServer.create()
                            .port(PORT)
                            .wiretap(WIRETAP)
                            .handle((in, out) -> out.send(in.receive().retain()));

            if (SECURE) {
                SelfSignedCertificate ssc = new SelfSignedCertificate();
                server = server.secure(
                        spec -> spec.sslContext(SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey())));
            }


            server.bindNow()
                    .onDispose()
                    .block();

    }

}
