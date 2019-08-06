package io.github.kaifox.gsi.demo.client.views;

import io.github.kaifox.gsi.demo.client.util.Speeds;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BurstSpeedView extends HBox {


    private final Flux<Integer> burstStartSizes;

    private final Map<String, Flux<?>> fluxes;
    private final Map<String, MeasureNode> nodes = new HashMap<>();

    public BurstSpeedView(Flux<Integer> burstStartSizes, Map<String, Flux<?>> fluxes) {
        this.burstStartSizes = burstStartSizes;
        this.fluxes = new LinkedHashMap(fluxes);
    }

    private void measure(int length) {
        for (String name : fluxes.keySet()) {
            Speeds.atoms(fluxes.get(name))
                    .take(length)
                    .buffer()
                    .subscribe(atoms -> Platform.runLater(() -> nodes.get(name).update(atoms)));
        }
    }

    @PostConstruct
    public void init() {
        fluxes.entrySet().stream()
                .map(e -> measureNodeFor(e.getKey()))
                .forEach(this::add);
        burstStartSizes.subscribe(this::measure);
    }

    private void add(MeasureNode measureNode) {
        getChildren().add(measureNode);
        nodes.put(measureNode.name, measureNode);
    }

    private static MeasureNode measureNodeFor(String name) {
        return new MeasureNode(name);
    }

    private static Label newLabel() {
        return new Label("Not yet calculated");
    }

    private static class MeasureNode extends TitledPane {

        private Label packageAmountLabel = newLabel();
        private Label frequencyLabel = newLabel();
        private Label megabyteLabel = newLabel();
        private Label totalSizeLabel = newLabel();
        private Label timeDelta = newLabel();

        private final String name;

        private MeasureNode(String name) {
            super();
            this.name = name;
            setText(name);
            setContent(new VBox(packageAmountLabel, totalSizeLabel, timeDelta, frequencyLabel, megabyteLabel));
        }

        private void update(List<Speeds.Atom> atoms) {
            packageAmountLabel.setText(String.format("%d packages", atoms.size()));
            frequencyLabel.setText(String.format("%.2f Hz", Speeds.emissionSpeedInHz(atoms)));
            megabyteLabel.setText(String.format("%.2f MB/s", Speeds.megabytePerSecond(atoms)));
            totalSizeLabel.setText(String.format("%.2f MB", Speeds.sumMegabytes(atoms)));
            timeDelta.setText(String.format("%.2f seconds", Speeds.totalDelayInSeconds(atoms)));
        }
    }
}
