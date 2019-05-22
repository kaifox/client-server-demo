package io.github.kaifox.gsi.demo.client.mains.rest;

import io.github.kaifox.gsi.demo.client.api.TuneControlClient;
import io.github.kaifox.gsi.demo.client.api.TuneReceiver;
import io.github.kaifox.gsi.demo.client.conf.Constants;
import io.github.kaifox.gsi.demo.client.views.FluxTunesView;
import io.github.kaifox.gsi.demo.client.views.PollingTuneView;
import io.github.kaifox.gsi.demo.client.views.SettingsView;
import javafx.scene.Node;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.annotations.View;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class WebfluxClientConfiguration {

    private final TuneControlClient tuneControlClient = WebfluxTuneControlClient.fromLocation(Constants.HOST, Constants.HTTP_PORT);

    @Bean
    public TuneReceiver webfluxTuneReceiver() {
        return WebfluxTuneReceiver.fromLocation(Constants.HOST, Constants.HTTP_PORT);
    }

    @View(in = WebfluxPerspective.class)
    @Name("tuneFlux")
    @Order(1)
    @Bean
    public Node webfluxTuneFluxView(TuneReceiver webfluxTuneReceiver) {
        return new FluxTunesView(webfluxTuneReceiver.measuredTunes());
    }

    @View(in = WebfluxPerspective.class)
    @Name("tune")
    @Order(2)
    @Bean
    public Node webfluxTuneView() {
        return new PollingTuneView(tuneControlClient);
    }

    @View(in = WebfluxPerspective.class)
    @Name("settings")
    @Order(3)
    @Bean
    public Node webfluxSettingsView() {
        return new SettingsView(tuneControlClient);
    }


}
