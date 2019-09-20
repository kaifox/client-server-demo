package io.github.kaifox.gsi.demo.commons.domain;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Tune {

    private final double value;
    private final double error;
    private final double[] payload;

    public Tune() {
        this(0.0, 0.0);
    }

    public Tune(double value, double error) {
        this(value, error, Collections.emptyList());
    }

    public Tune(double value, double error, List<Double> payload) {
        this.value = value;
        this.error = error;
        this.payload = payload.stream().mapToDouble(v -> v).toArray();
    }


    public double getValue() {
        return value;
    }

    public double getError() {
        return error;
    }

    public List<Double> getPayload() {
        return Arrays.stream(this.payload).mapToObj(v -> v).collect(toList());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tune tune = (Tune) o;
        return Double.compare(tune.value, value) == 0 &&
                Double.compare(tune.error, error) == 0 &&
                payload.equals(tune.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, error, payload);
    }

    @Override
    public String toString() {
        return "Tune{" +
                "value=" + value +
                ", error=" + error +
                ", payloadSize=" + payload.length + " doubles" +
                '}';
    }
}
