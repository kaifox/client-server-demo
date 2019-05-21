package io.github.kaifox.gsi.demo.client.mains.grpc;

import io.github.kaifox.gsi.demo.client.api.TuneControlClient;
import io.github.kaifox.gsi.demo.client.api.TuneReceiver;
import io.github.kaifox.gsi.demo.client.conf.Constants;
import io.github.kaifox.gsi.demo.client.views.FluxTunesView;
import io.github.kaifox.gsi.demo.client.views.PollingTuneView;
import io.github.kaifox.gsi.demo.client.views.SettingsView;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import javafx.scene.Node;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.annotations.View;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class GrpcClientConfiguration {

    private final Channel grpcChannel = ManagedChannelBuilder.forAddress(Constants.HOST, Constants.GRPC_PORT)
            .usePlaintext()
            .build();

    private final TuneControlClient tuneClient = GrpcTuneControlClient.fromChannel(grpcChannel);
    private final TuneReceiver tuneReceiver = GrpcTuneReceiver.fromChannel(grpcChannel);


    @View(in=GrpcPerspective.class)
    @Name("tuneFlux")
    @Order(1)
    @Bean
    public Node grpcTuneFluxView() {
        return new FluxTunesView(tuneReceiver.measuredTunes());
    }

    @View(in=GrpcPerspective.class)
    @Name("tune")
    @Order(2)
    @Bean
    public Node grpcTuneView() {
        return new PollingTuneView(tuneClient);
    }

    @View(in=GrpcPerspective.class)
    @Name("settings")
    @Order(3)
    @Bean
    public Node grpcSettingsView() {
        return new SettingsView(tuneClient);
    }



}
