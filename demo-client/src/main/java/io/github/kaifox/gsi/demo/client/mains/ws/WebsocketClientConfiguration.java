package io.github.kaifox.gsi.demo.client.mains.ws;

import io.github.kaifox.gsi.demo.client.api.TuneReceiver;
import io.github.kaifox.gsi.demo.client.conf.ConfigValues;
import io.github.kaifox.gsi.demo.client.views.FluxTunesView;
import javafx.scene.Node;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.annotations.View;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebsocketClientConfiguration {

    @Bean
    public TuneReceiver websocketTuneReceiver() {
        return WebsocketTuneReceiver.fromLocation(ConfigValues.HOST, ConfigValues.httpPort());
    }

    @View(in = WebsocketPerspective.class, enforceTab = true)
    @Name("tuneFlux")
    @Bean
    public Node websocketTuneFluxView(TuneReceiver websocketTuneReceiver) {
        return new FluxTunesView(websocketTuneReceiver.measuredTunes());
    }


}
