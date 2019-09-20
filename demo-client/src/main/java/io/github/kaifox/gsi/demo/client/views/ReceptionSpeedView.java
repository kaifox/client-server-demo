package io.github.kaifox.gsi.demo.client.views;

import static io.github.kaifox.gsi.demo.client.util.Speeds.emissionSpeedInHz;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import io.github.kaifox.gsi.demo.client.util.Speeds;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import reactor.core.publisher.Flux;

public class ReceptionSpeedView extends HBox {

    private final Map<String, Flux<?>> fluxes;

    public ReceptionSpeedView(Map<String, Flux<?>> fluxes) {
        this.fluxes = new LinkedHashMap(fluxes);
    }

    @PostConstruct
    public void init() {
        fluxes.entrySet().stream()
                .map(e -> measureNodeFor(e.getKey(), e.getValue()))
                .forEach(getChildren()::add);
    }

    private static Node measureNodeFor(String name, Flux<?> flux) {
        Label frequencyLabel = doubleLabel(emissionSpeedInHz(flux), "%.2f Hz");
        Label megabyteLabel = doubleLabel(Speeds.megabytePerSecond(flux), "%.2f MB/s");

        VBox vBox = new VBox(frequencyLabel, megabyteLabel);
        return new TitledPane(name, vBox);
    }

    private static Label doubleLabel(Flux<Double> doubleFlux, String formatString) {
        Label frequencyLabel = new Label("Not yet calculated");
        doubleFlux.subscribe(v -> Platform.runLater(() -> frequencyLabel.setText(String.format(formatString, v))));
        return frequencyLabel;
    }
}
