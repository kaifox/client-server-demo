package io.github.kaifox.gsi.demo.client.mains.restws;

import io.github.kaifox.gsi.demo.client.api.TuneControlClient;
import io.github.kaifox.gsi.demo.client.api.TuneReceiver;
import io.github.kaifox.gsi.demo.client.conf.Constants;
import io.github.kaifox.gsi.demo.client.mains.ws.WebsocketTuneReceiver;
import io.github.kaifox.gsi.demo.client.views.FluxTunesView;
import io.github.kaifox.gsi.demo.client.views.PollingTuneView;
import io.github.kaifox.gsi.demo.client.views.SettingsView;
import javafx.scene.Node;
import org.minifx.workbench.MiniFx;
import org.minifx.workbench.annotations.View;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebfluxClientConfiguration {

    private final TuneControlClient tuneControlClient = WebfluxTuneControlClient.fromLocation(Constants.HOST, Constants.HTTP_PORT);
    private final TuneReceiver webfluxTuneReceiver = WebfluxTuneReceiver.fromLocation(Constants.HOST, Constants.HTTP_PORT);

    @View
    @Bean
    public Node tuneView() {
        return new PollingTuneView(tuneControlClient);
    }

    @View
    @Bean
    public Node settingsView() {
        return new SettingsView(tuneControlClient);
    }

    @View
    @Bean
    public Node tuneFluxView() {
        return new FluxTunesView(webfluxTuneReceiver.measuredTunes());
    }

}
