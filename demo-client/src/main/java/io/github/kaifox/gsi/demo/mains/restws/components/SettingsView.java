package io.github.kaifox.gsi.demo.mains.restws.components;

import io.github.kaifox.gsi.demo.mains.restws.TuneClient;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class SettingsView extends BorderPane {

    private final static Logger LOGGER = LoggerFactory.getLogger(SettingsView.class);

    @Autowired
    private TuneClient tuneClient;

    @PostConstruct
    public void init() {
        TextField textField = new TextField();
        textField.setText("0.0");

        Button setButton = new Button("Set");
        setButton.setOnAction(evt -> {
            try {
                Double val = Double.parseDouble(textField.getText());
                tuneClient.setStandardDev(val);
            } catch (NumberFormatException e) {
                LOGGER.error("Cannot parse double '" + textField.getText() + "'", e);
            }
        });

        Button getButton = new Button("Get");
        getButton.setOnAction(evt -> {
            double val = tuneClient.getStandardDev();
            textField.setText(new Double(val).toString());
        });

        VBox content = new VBox(textField, getButton, setButton);
        setCenter(new TitledPane("Standard Dev", content));
    }
}
