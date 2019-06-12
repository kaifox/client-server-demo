package io.github.kaifox.gsi.demo.server.conf.grpc;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GrpcConfiguration {

    private static final int SERVER_PORT = 5252;

    @Bean
    public GrpcTuneService grpcTuneService() {
        return new GrpcTuneService();
    }

    @Bean
    public Server grpcServer(BindableService grpcTuneService) throws IOException {

        return ServerBuilder.forPort(SERVER_PORT)
                .addService(grpcTuneService)
                .build()
                .start();
    }

}
