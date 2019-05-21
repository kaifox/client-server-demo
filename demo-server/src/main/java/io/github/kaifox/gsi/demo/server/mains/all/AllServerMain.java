package io.github.kaifox.gsi.demo.server.mains.all;

import io.github.kaifox.gsi.demo.server.conf.TuneBaseConfiguration;
import io.github.kaifox.gsi.demo.server.conf.grpc.GrpcConfiguration;
import io.github.kaifox.gsi.demo.server.conf.rest.RestConfiguration;
import io.github.kaifox.gsi.demo.server.conf.ws.WebSocketConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({TuneBaseConfiguration.class, RestConfiguration.class, WebSocketConfiguration.class, GrpcConfiguration.class})
public class AllServerMain {

    public static void main(String... args) {
        SpringApplication.run(AllServerMain.class, args);
    }

}
