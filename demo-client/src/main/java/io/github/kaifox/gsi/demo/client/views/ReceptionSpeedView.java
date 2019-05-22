package io.github.kaifox.gsi.demo.client.views;

import io.github.kaifox.gsi.demo.client.util.Speeds;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

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
        Label label = new Label("Not yet calculated");
        Speeds.emissionSpeedInHz(flux).subscribe(v -> Platform.runLater(() -> label.setText(String.format("%.2f Hz", v))));
        return new TitledPane(name, label);
    }
}
