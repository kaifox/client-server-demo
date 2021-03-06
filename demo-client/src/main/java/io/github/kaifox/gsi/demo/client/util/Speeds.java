package io.github.kaifox.gsi.demo.client.util;

import static java.util.stream.Collectors.toList;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openjdk.jol.info.GraphLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Iterables;

import io.github.kaifox.gsi.demo.client.conf.ConfigValues;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public final class Speeds {

    private static final Logger LOGGER = LoggerFactory.getLogger(Speeds.class);

    private Speeds() {
        /* only static methods */
    }

    public static final Flux<Double> emissionSpeedInHz(Flux<?> fluxToMeasure) {
        Flux<List<Atom>> buffer = bufferedAtoms(fluxToMeasure);
        return buffer.map(atoms -> emissionSpeedInHz(atoms));
    }

    public static double emissionSpeedInHz(List<Atom> atoms) {
        return averageSpeedInHz(arrivalTimes(atoms));
    }

    private static List<Instant> arrivalTimes(List<Atom> inst) {
        return inst.stream().map(a -> a.arrivalTime).collect(toList());
    }

    public static final Flux<Double> megabytePerSecond(Flux<?> fluxToMeasure) {
        return bufferedAtoms(fluxToMeasure).map(Speeds::megabytePerSecond);
    }

    private static Flux<List<Atom>> bufferedAtoms(Flux<?> fluxToMeasure) {
        return bufferedAtoms(fluxToMeasure, 10);
    }

    private static Flux<List<Atom>> bufferedAtoms(Flux fluxToMeasure, int windowSize) {
        return (Flux<List<Atom>>) atoms(fluxToMeasure)
                .buffer(windowSize).onBackpressureLatest();
    }

    public static Flux<Atom> atoms(Flux fluxToMeasure) {
        return fluxToMeasure
                .publishOn(Schedulers.elastic()).onBackpressureDrop()
                .map(v -> new Atom(sizeInMB(v), Instant.now()));
    }


    public static double megabytePerSecond(List<Atom> atoms) {
        if (atoms.size() < 2) {
            LOGGER.warn("Not enough atoms available (min 2) to calc rate. Returning 0 MB/s.");
            return 0;
        }
        double sum = sumMegabytes(atoms);

        return sum / totalDelayInSeconds(atoms);
    }

    public static double totalDelayInSeconds(List<Atom> atoms) {
        return totalDelayInMillis(arrivalTimes(atoms)) / 1000.0;
    }

    public static double sumMegabytes(List<Atom> atoms) {
        return atoms.stream()
                .mapToDouble(a -> a.sizeInMB)
                .sum();
    }

    private static double averageSpeedInHz(List<Instant> instants) {
        if (instants.size() < 2) {
            LOGGER.warn("Not enough timestamps available (min 2) to calc frequency. Returning 0 Hz.");
            return 0;
        }
        long totalDelayInMillis = totalDelayInMillis(instants);
        double averageDelay = (1.0 * totalDelayInMillis) / (instants.size() - 1);
        return 1000.0 / averageDelay;
    }

    private static long totalDelayInMillis(List<Instant> instants) {
        List<Instant> sortedInstants = sorted(instants);
        return Iterables.getLast(sortedInstants).toEpochMilli() - sortedInstants.get(0).toEpochMilli();
    }

    private static List<Instant> sorted(List<Instant> instants) {
        List<Instant> sortedInstants = new ArrayList<>(instants);
        Collections.sort(sortedInstants);
        return sortedInstants;
    }


    public static class Atom {
        private final double sizeInMB;
        private final Instant arrivalTime;

        public Atom(double sizeInMB, Instant arrivalTime) {
            this.sizeInMB = sizeInMB;
            this.arrivalTime = arrivalTime;
        }
    }

    private static final double sizeInMB(Object object) {
        return 1.0 * GraphLayout.parseInstance(object).totalSize() / ConfigValues.ONE_MEGABYTE;
    }


}
