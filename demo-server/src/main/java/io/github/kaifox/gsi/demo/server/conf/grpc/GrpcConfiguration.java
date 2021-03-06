package io.github.kaifox.gsi.demo.server.conf.grpc;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

@Configuration
public class GrpcConfiguration {

    @Value("${grpc.port:5252}")
    private int grpcPort;

    @Bean
    public GrpcTuneService grpcTuneService() {
        return new GrpcTuneService();
    }

    @Bean
    public Server grpcServer(BindableService grpcTuneService) throws IOException {

        return ServerBuilder.forPort(grpcPort)
                .addService(grpcTuneService)
                .build()
                .start();
    }

}
