package io.github.kaifox.gsi.demo.mains;

import io.github.kaifox.gsi.demo.mains.components.FluxTunesView;
import io.github.kaifox.gsi.demo.mains.components.PollingTuneView;
import io.github.kaifox.gsi.demo.mains.components.SettingsView;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.minifx.workbench.MiniFx;
import org.minifx.workbench.annotations.View;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class PureRestDemo {

    public static void main(String... args) {
        MiniFx.launcher(PureRestDemo.class).launch(args);
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
        return  new WebfluxTuneClient();
    }
}
