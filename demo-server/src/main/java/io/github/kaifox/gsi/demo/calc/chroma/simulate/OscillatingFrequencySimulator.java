package io.github.kaifox.gsi.demo.calc.chroma.simulate;

import java.util.concurrent.TimeUnit;

public class OscillatingFrequencySimulator {

    private static final int DEFAULT_FREQUENCY_AMPLITUDE_IN_HZ = 80;
    private static final double DEFAULT_PERIOD_IN_SECONDS = 20.0;

    private double frequencyAmplitudeinHz = DEFAULT_FREQUENCY_AMPLITUDE_IN_HZ;
    private double centerFrequencyInHz = 400788871;

    public double frequencyInHzAfter(long timespan, TimeUnit unit) {
        double timespanInSeconds = unit.toSeconds(timespan);
        double actualFreq = Math.sin(2 * Math.PI * (timespanInSeconds / DEFAULT_PERIOD_IN_SECONDS)) * frequencyAmplitudeinHz;
        return actualFreq + centerFrequencyInHz;
    }
}
