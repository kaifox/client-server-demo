package io.github.kaifox.gsi.demo.client.views;

import io.github.kaifox.gsi.demo.client.mains.testing.TestControlClient;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TestingSettingsView extends BorderPane {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestingSettingsView.class);

    private final TestControlClient testControlClient;

    public TestingSettingsView(TestControlClient tuneControlClient) {
        this.testControlClient = Objects.requireNonNull(tuneControlClient);
    }

    @PostConstruct
    public void init() {
        setCenter(new VBox(publicationDelayPane(), payloadLengthPane(), burstPane()));
    }

    private VBox publicationDelayPane() {
        Supplier<String> supplier = () -> new Long(testControlClient.getDelayInMillis()).toString();
        Consumer<String> consumer = s -> {
            try {
                long val = Long.parseLong(s);
                testControlClient.setDelayInMillis(val);
            } catch (NumberFormatException e) {
                LOGGER.error("Cannot parse long '" + s + "'", e);
            }
        };


        CheckBox periodicEnabled = new CheckBox("Periodic publication");
        testControlClient.periodicPublicationEnabled()
                .subscribe(b -> Platform.runLater(() -> periodicEnabled.setSelected(b)));

        periodicEnabled.selectedProperty().addListener((observable, oldValue, newValue) -> {
            testControlClient.setPeriodicPublicationEnabled(newValue);
        });


        TitledPane periodicDelayPane = new TitledPane("publication delay in millis", getSetContent(supplier, consumer));

        testControlClient.periodicPublicationEnabled().subscribe(e -> Platform.runLater(() -> periodicDelayPane.setDisable(!e)));

        VBox content = new VBox(periodicEnabled, periodicDelayPane);
        return content;
    }


    private TitledPane payloadLengthPane() {
        Supplier<String> supplier = () -> new Integer(testControlClient.getPayloadLength()).toString();
        Consumer<String> consumer = s -> {
            try {
                int val = Integer.parseInt(s);
                testControlClient.setPayloadLength(val);
            } catch (NumberFormatException e) {
                LOGGER.error("Cannot parse int '" + s + "'", e);
            }
        };

        VBox content = getSetContent(supplier, consumer);
        return new TitledPane("payload length", content);
    }

    private TitledPane burstPane() {
        TextField textField = new TextField();
        textField.setText("1");

        Button setButton = new Button("trigger");
        setButton.setOnAction(evt -> {
            int iterations = Integer.parseInt(textField.getText());
            testControlClient.triggerBurst(iterations);
        });

        TitledPane burstPane = new TitledPane("Burst", new VBox(textField, setButton));
        testControlClient.periodicPublicationEnabled().subscribe(e -> Platform.runLater(() -> burstPane.setDisable(e)));
        return burstPane;
    }


    private VBox getSetContent(Supplier<String> supplier, Consumer<String> consumer) {
        TextField textField = new TextField();
        textField.setText(supplier.get());

        Button setButton = new Button("Set");
        setButton.setOnAction(evt -> consumer.accept(textField.getText()));

        Button getButton = new Button("Get");
        getButton.setOnAction(evt -> textField.setText(supplier.get()));

        return new VBox(textField, new HBox(getButton, setButton));
    }


}
