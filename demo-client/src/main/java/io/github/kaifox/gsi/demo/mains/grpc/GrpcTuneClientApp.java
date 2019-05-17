package io.github.kaifox.gsi.demo.mains.grpc;

import io.github.kaifox.gsi.demo.mains.grpc.components.FluxTunesView;
import io.github.kaifox.gsi.demo.mains.grpc.components.PollingTuneView;
import io.github.kaifox.gsi.demo.mains.grpc.components.SettingsView;
import io.github.kaifox.gsi.demo.mains.restws.components.WsFluxTunesView;
import javafx.scene.Node;
import org.minifx.fxcommons.MiniFxSceneBuilder;
import org.minifx.workbench.MiniFx;
import org.minifx.workbench.annotations.View;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcTuneClientApp {

    public static void main(String... args) {
        MiniFx.launcher(GrpcTuneClientApp.class).launch(args);
    }

    @View
    @Bean
    public Node tuneView() {
        return new PollingTuneView();
    }

    @View
    @Bean
    public Node settingsView() {
        return new SettingsView();
    }

    @View
    @Bean
    public Node tuneFluxView() {
        return new FluxTunesView();
    }


    @Bean
    public TuneClient tuneClient() {
        return new GrpcTuneClient();
    }

    @Bean
    public MiniFxSceneBuilder miniFxSceneBuilder() {
        return MiniFxSceneBuilder.miniFxSceneBuilder().withSize(640, 380);
    }
}
