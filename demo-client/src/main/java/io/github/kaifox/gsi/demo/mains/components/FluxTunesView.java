package io.github.kaifox.gsi.demo.mains.components;

import io.github.kaifox.gsi.demo.mains.TuneClient;
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
                .publishOn(fxScheduler())
                .subscribe(t -> textArea.appendText("received " + t + ".\n"));
    }
}
