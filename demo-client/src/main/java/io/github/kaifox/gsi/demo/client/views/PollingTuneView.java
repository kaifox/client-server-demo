package io.github.kaifox.gsi.demo.client.views;

import static java.util.Objects.requireNonNull;

import javax.annotation.PostConstruct;

import io.github.kaifox.gsi.demo.client.api.TuneControlClient;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class PollingTuneView extends BorderPane {

    private final TuneControlClient tuneControlClient;

    public PollingTuneView(TuneControlClient tuneControlClient) {
        this.tuneControlClient = requireNonNull(tuneControlClient, "tuneControlClient must not be null");
    }

    @PostConstruct
    public void init() {
        TextArea textArea = new TextArea();
        setCenter(textArea);

        Button pollButton = new Button("Get");
        pollButton.setOnAction(evt -> textArea.appendText("Got " + tuneControlClient.measuredTune() + ".\n"));
        setTop(pollButton);
    }
}
