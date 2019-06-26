package io.github.kaifox.gsi.demo.client.mains.grpc;

import io.github.kaifox.gsi.demo.client.api.TuneControlClient;
import io.github.kaifox.gsi.demo.client.api.TuneReceiver;
import io.github.kaifox.gsi.demo.client.conf.ConfigValues;
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

import static io.github.kaifox.gsi.demo.client.conf.ConfigValues.ONE_GIGABYTE;

@Configuration
public class GrpcClientConfiguration {

    private final Channel grpcChannel = ManagedChannelBuilder.forAddress(ConfigValues.host(), ConfigValues.grpcPort())
            .usePlaintext()
            .maxInboundMessageSize(ONE_GIGABYTE)
            .build();

    private final TuneControlClient tuneClient = GrpcTuneControlClient.fromChannel(grpcChannel);

    @Bean
    public TuneReceiver grpcTuneReceiver() {
        return GrpcTuneReceiver.fromChannel(grpcChannel);
    }

    @View(in = GrpcPerspective.class)
    @Name("tuneFlux")
    @Order(1)
    @Bean
    public Node grpcTuneFluxView(TuneReceiver grpcTuneReceiver) {
        return new FluxTunesView(grpcTuneReceiver.measuredTunes());
    }

    @View(in = GrpcPerspective.class)
    @Name("tune")
    @Order(2)
    @Bean
    public Node grpcTuneView() {
        return new PollingTuneView(tuneClient);
    }

    @View(in = GrpcPerspective.class)
    @Name("settings")
    @Order(3)
    @Bean
    public Node grpcSettingsView() {
        return new SettingsView(tuneClient);
    }


}
