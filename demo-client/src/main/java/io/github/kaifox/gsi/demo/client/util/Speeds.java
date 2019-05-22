package io.github.kaifox.gsi.demo.client.util;

import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public final class Speeds {

    private Speeds() {
        /* only static methods */
    }

    public static final Flux<Double> emissionSpeedInHz(Flux fluxToMeasure) {
        Flux<List<Instant>> buffer = fluxToMeasure
                .map(v -> Instant.now())
                .buffer(10);
        return buffer.map(inst -> averageSpeedInHz(inst));
    }

    private static double averageSpeedInHz(List<Instant> instants) {
        List<Long> delays = new ArrayList<>();

        Instant last = instants.get(0);
        for (int i = 1; i < instants.size(); i++) {
            Instant instant = instants.get(i);
            delays.add(instant.toEpochMilli() - last.toEpochMilli());
            last = instant;
        }

        return delays.stream()
                .mapToDouble(Speeds::frequInHz)
                .average()
                .getAsDouble();
    }

    public static double frequInHz(long delayInMillis) {
        return 1000.0 / delayInMillis;
    }


}
