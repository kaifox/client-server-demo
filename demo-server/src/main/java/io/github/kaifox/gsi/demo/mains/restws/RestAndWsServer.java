package io.github.kaifox.gsi.demo.mains.restws;

import io.github.kaifox.gsi.demo.mains.config.PrintTuneConfiguration;
import io.github.kaifox.gsi.demo.mains.config.TuneBaseConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({TuneBaseConfiguration.class, PrintTuneConfiguration.class})
public class RestAndWsServer {

    public static void main(String... args) {
        SpringApplication.run(RestAndWsServer.class, args);
    }

}
