package io.github.kaifox.gsi.demo.client.views;

import io.github.kaifox.gsi.demo.commons.domain.Tune;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static io.github.ossgang.properties.fx.util.JavaFxFlux.fxScheduler;

public class FluxTunesView extends BorderPane {

    private final Flux<Tune> inFlux;
    private final AtomicBoolean update = new AtomicBoolean(true);

    public FluxTunesView(Flux<Tune> inFlux) {
        this.inFlux = Objects.requireNonNull(inFlux, "inFlux must not be null.");
    }

    @PostConstruct
    public void init() {
        TextArea textArea = new TextArea();
        setCenter(textArea);

        CheckBox updateCheckBox = new CheckBox("Update");
        updateCheckBox.setSelected(update.get());
        updateCheckBox.selectedProperty().addListener((p, o, n) -> update.set(updateCheckBox.isSelected()));
        setBottom(updateCheckBox);

        inFlux.filter(v -> update.get())
                .map(t -> "received " + t + ".\n")
                .publishOn(fxScheduler())
                .subscribe(textArea::appendText);
    }
}
