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
import org.minifx.workbench.annotations.View;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfiguration {

    private final Channel grpcChannel = ManagedChannelBuilder.forAddress(Constants.HOST, Constants.GRPC_PORT)
            .usePlaintext()
            .build();

    private final TuneControlClient tuneClient = GrpcTuneControlClient.fromChannel(grpcChannel);
    private final TuneReceiver tuneReceiver = GrpcTuneReceiver.fromChannel(grpcChannel);


    @View
    @Bean
    public Node tuneView() {
        return new PollingTuneView(tuneClient);
    }

    @View
    @Bean
    public Node settingsView() {
        return new SettingsView(tuneClient);
    }

    @View
    @Bean
    public Node tuneFluxView() {
        return new FluxTunesView(tuneReceiver.measuredTunes());
    }


}
