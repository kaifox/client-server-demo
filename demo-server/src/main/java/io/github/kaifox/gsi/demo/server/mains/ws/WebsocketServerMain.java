package io.github.kaifox.gsi.demo.server.mains.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import io.github.kaifox.gsi.demo.server.conf.TuneBaseConfiguration;
import io.github.kaifox.gsi.demo.server.conf.testing.TestingConfiguration;
import io.github.kaifox.gsi.demo.server.conf.ws.WebSocketConfiguration;

@SpringBootApplication
@Import({TuneBaseConfiguration.class, WebSocketConfiguration.class, TestingConfiguration.class})
public class WebsocketServerMain {

    public static void main(String... args) {
        SpringApplication.run(WebsocketServerMain.class, args);
    }

}
