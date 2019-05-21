package io.github.kaifox.gsi.demo.server.mains.ws;

import io.github.kaifox.gsi.demo.server.conf.TuneBaseConfiguration;
import io.github.kaifox.gsi.demo.server.conf.ws.WebSocketConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({TuneBaseConfiguration.class, WebSocketConfiguration.class})
public class WebsocketServerMain {

    public static void main(String... args) {
        SpringApplication.run(WebsocketServerMain.class, args);
    }

}
