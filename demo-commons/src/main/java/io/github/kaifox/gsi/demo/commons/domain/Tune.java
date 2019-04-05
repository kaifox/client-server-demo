package io.github.kaifox.gsi.demo.commons.domain;

public class Tune {

    private final double value;
    private final double error;

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
}
