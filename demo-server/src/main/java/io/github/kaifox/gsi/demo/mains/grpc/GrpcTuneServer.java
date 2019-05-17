package io.github.kaifox.gsi.demo.mains.grpc;

import io.github.kaifox.gsi.demo.mains.restws.config.TuneBaseConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({TuneBaseConfiguration.class})
public class GrpcTuneServer {

    public static void main(String... args) {
        SpringApplication.run(GrpcTuneServer.class, args);
    }

}
