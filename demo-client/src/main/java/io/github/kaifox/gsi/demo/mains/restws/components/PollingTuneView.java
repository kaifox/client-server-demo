package io.github.kaifox.gsi.demo.mains.restws.components;

import io.github.kaifox.gsi.demo.mains.restws.TuneClient;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class PollingTuneView extends BorderPane {

    @Autowired
    private TuneClient tuneClient;

    @PostConstruct
    public void init() {
        TextArea textArea = new TextArea();
        setCenter(textArea);

        Button pollButton = new Button("Get");
        pollButton.setOnAction(evt -> textArea.appendText("Got " + tuneClient.measuredTune() + ".\n"));
        setTop(pollButton);
    }
}
