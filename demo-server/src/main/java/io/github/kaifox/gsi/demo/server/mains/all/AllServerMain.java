package io.github.kaifox.gsi.demo.server.mains.all;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import io.github.kaifox.gsi.demo.server.conf.TuneBaseConfiguration;
import io.github.kaifox.gsi.demo.server.conf.grpc.GrpcConfiguration;
import io.github.kaifox.gsi.demo.server.conf.rest.RestConfiguration;
import io.github.kaifox.gsi.demo.server.conf.testing.TestingConfiguration;
import io.github.kaifox.gsi.demo.server.conf.ws.WebSocketConfiguration;

@SpringBootApplication
@Import({TuneBaseConfiguration.class, RestConfiguration.class, WebSocketConfiguration.class, GrpcConfiguration.class, TestingConfiguration.class})
public class AllServerMain {

    public static void main(String... args) {
        SpringApplication.run(AllServerMain.class, args);
    }

}
