package io.github.kaifox.gsi.demo.commons.domain;

import java.util.Objects;

public class Tune {

    private final double value;
    private final double error;

    public Tune() {
        this(0.0, 0.0);
    }

    public Tune(double value, double error) {
        this.value = value;
        this.error = error;
    }

    public double getValue() {
        return value;
    }

    public double getError() {
        return error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tune tune = (Tune) o;
        return Double.compare(tune.value, value) == 0 &&
                Double.compare(tune.error, error) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, error);
    }

    @Override
    public String toString() {
        return "Tune{" +
                "value=" + value +
                ", error=" + error +
                '}';
    }
}
