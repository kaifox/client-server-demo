package io.github.kaifox.gsi.demo.mains;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.minifx.workbench.MiniFx;
import org.minifx.workbench.annotations.View;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class PureRestDemo {

    public static void main(String ...args) {
        MiniFx.launcher(PureRestDemo.class).launch(args);
    }

    @View
    @Bean
    public HBox tuneView() {
        Label label = new Label("never got tune");

        AtomicLong count = new AtomicLong(0);
        Button getButton = new Button("Get");
        getButton.setOnAction(evt -> label.setText("Got " + count.incrementAndGet() + " times."));
        return new HBox( getButton, label);
    }
}
