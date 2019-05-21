package io.github.kaifox.gsi.demo.client.views;

import io.github.kaifox.gsi.demo.commons.domain.Tune;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;

import java.util.Objects;

import static io.github.ossgang.properties.fx.util.JavaFxFlux.fxScheduler;

public class FluxTunesView extends BorderPane {

    private final Flux<Tune> inFlux;

    public FluxTunesView(Flux<Tune> inFlux) {
        this.inFlux = Objects.requireNonNull(inFlux, "inFlux must not be null.");
    }

    @PostConstruct
    public void init() {
        TextArea textArea = new TextArea();
        setCenter(textArea);

        inFlux.map(t -> "received " + t + ".\n")
              .publishOn(fxScheduler())
              .subscribe(textArea::appendText);
    }
}
