package io.github.kaifox.gsi.demo.server.mains.rest;

import io.github.kaifox.gsi.demo.server.conf.TuneBaseConfiguration;
import io.github.kaifox.gsi.demo.server.conf.rest.RestConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({TuneBaseConfiguration.class, RestConfiguration.class})
public class WebfluxServerMain {

    public static void main(String... args) {
        SpringApplication.run(WebfluxServerMain.class, args);
    }

}
