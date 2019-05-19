package io.github.kaifox.gsi.demo.mains.grpc.components;

import io.github.kaifox.gsi.demo.mains.grpc.TuneClient;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import static io.github.ossgang.properties.fx.util.JavaFxFlux.fxScheduler;

public class FluxTunesView extends BorderPane {

    @Autowired
    private TuneClient tuneClient;

    @PostConstruct
    public void init() {
        TextArea textArea = new TextArea();
        setCenter(textArea);

        tuneClient.measuredTunes()
                .map(t -> "received " + t + ".\n")
                .publishOn(fxScheduler())
                .subscribe(textArea::appendText);
    }
}
