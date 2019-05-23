package io.github.kaifox.gsi.demo.client.views;

import io.github.kaifox.gsi.demo.client.mains.testing.TestControlClient;
import javafx.scene.control.Button;
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

public class TestSettingsView extends BorderPane {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestSettingsView.class);

    private final TestControlClient testControlClient;

    public TestSettingsView(TestControlClient tuneControlClient) {
        this.testControlClient = Objects.requireNonNull(tuneControlClient);
    }

    @PostConstruct
    public void init() {
        setCenter(new VBox(publicationDelayPane(), payloadLengthPane()));
    }

    private TitledPane publicationDelayPane() {
        Supplier<String> supplier = () -> new Long(testControlClient.getDelayInMillis()).toString();
        Consumer<String> consumer = s -> {
            try {
                long val = Long.parseLong(s);
                testControlClient.setDelayInMillis(val);
            } catch (NumberFormatException e) {
                LOGGER.error("Cannot parse long '" + s + "'", e);
            }
        };

        VBox content = getSetContent(supplier, consumer);
        return new TitledPane("publication delay in millis", content);
    }


    private TitledPane payloadLengthPane() {
        Supplier<String> supplier = () -> new Long(testControlClient.getPayloadLength()).toString();
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
