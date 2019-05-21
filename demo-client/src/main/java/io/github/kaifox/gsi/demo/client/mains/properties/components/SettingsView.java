package io.github.kaifox.gsi.demo.client.mains.properties.components;

import io.github.ossgang.properties.core.Property;
import io.github.ossgang.properties.fx.util.JavaFxProperties;
import javafx.scene.control.Slider;
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
    private Property<Double> standardDevProperty;

    @PostConstruct
    public void init() {

        Slider slider = new Slider();
        slider.setMin(0.0);
        slider.setMax(1.0);

        slider.valueProperty().bindBidirectional(JavaFxProperties.doubleProperty(standardDevProperty));

        VBox content = new VBox(slider);
        setCenter(new TitledPane("Standard Dev", content));
    }
}
