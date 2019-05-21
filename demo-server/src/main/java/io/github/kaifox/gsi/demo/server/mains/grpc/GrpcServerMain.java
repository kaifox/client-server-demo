package io.github.kaifox.gsi.demo.server.mains.grpc;

import io.github.kaifox.gsi.demo.server.conf.TuneBaseConfiguration;
import io.github.kaifox.gsi.demo.server.conf.grpc.GrpcConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({TuneBaseConfiguration.class, GrpcConfiguration.class})
public class GrpcServerMain {

    public static void main(String... args) {
        SpringApplication.run(GrpcServerMain.class, args);
    }

}
