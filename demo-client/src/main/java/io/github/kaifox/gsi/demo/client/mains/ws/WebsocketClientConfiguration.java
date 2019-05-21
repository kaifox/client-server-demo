package io.github.kaifox.gsi.demo.client.mains.ws;

import io.github.kaifox.gsi.demo.client.api.TuneReceiver;
import io.github.kaifox.gsi.demo.client.conf.Constants;
import io.github.kaifox.gsi.demo.client.views.FluxTunesView;
import javafx.scene.Node;
import org.minifx.workbench.annotations.View;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebsocketClientConfiguration {

    private final TuneReceiver websocketTuneReceiver = WebsocketTuneReceiver.fromLocation(Constants.HOST, Constants.HTTP_PORT);

    @View
    @Bean
    public Node wsTuneFluxView() {
        return new FluxTunesView(websocketTuneReceiver.measuredTunes());
    }


}
