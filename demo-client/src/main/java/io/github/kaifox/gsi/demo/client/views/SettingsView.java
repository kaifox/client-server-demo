package io.github.kaifox.gsi.demo.client.views;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.kaifox.gsi.demo.client.api.TuneControlClient;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SettingsView extends BorderPane {

    private final static Logger LOGGER = LoggerFactory.getLogger(SettingsView.class);

    private final TuneControlClient tuneControlClient;

    public SettingsView(TuneControlClient tuneControlClient) {
        this.tuneControlClient = Objects.requireNonNull(tuneControlClient);
    }

    @PostConstruct
    public void init() {
        TextField textField = new TextField();
        textField.setText("0.0");

        Button setButton = new Button("Set");
        setButton.setOnAction(evt -> {
            try {
                Double val = Double.parseDouble(textField.getText());
                tuneControlClient.setStandardDev(val);
            } catch (NumberFormatException e) {
                LOGGER.error("Cannot parse double '" + textField.getText() + "'", e);
            }
        });

        Button getButton = new Button("Get");
        getButton.setOnAction(evt -> {
            double val = tuneControlClient.getStandardDev();
            textField.setText(new Double(val).toString());
        });

        VBox content = new VBox(textField, getButton, setButton);
        setCenter(new TitledPane("Standard Dev", content));
    }
}
