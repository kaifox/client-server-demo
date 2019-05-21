package io.github.kaifox.gsi.demo.client.mains.ws;

import io.github.kaifox.gsi.demo.client.api.TuneReceiver;
import io.github.kaifox.gsi.demo.client.conf.Constants;
import io.github.kaifox.gsi.demo.client.views.FluxTunesView;
import javafx.scene.Node;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.annotations.View;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebsocketClientConfiguration {

    private final TuneReceiver websocketTuneReceiver = WebsocketTuneReceiver.fromLocation(Constants.HOST, Constants.HTTP_PORT);

    @View(in=WebsocketPerspective.class, enforceTab = true)
    @Name("tuneFlux")
    @Bean
    public Node websocketTuneFluxView() {
        return new FluxTunesView(websocketTuneReceiver.measuredTunes());
    }


}
