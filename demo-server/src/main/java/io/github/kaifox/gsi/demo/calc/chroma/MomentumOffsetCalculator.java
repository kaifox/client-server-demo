package io.github.kaifox.gsi.demo.calc.chroma;

public class MomentumOffsetCalculator {

    private static final double DEFAULT_LHC_MOMENTUM_COMPACTION_FACTOR = 3.22e-4;
    private static final double DEFAULT_LHC_RELATIVISTIC_GAMMA = 479.6;
    public static final double DEFAULT_LHC_RF_FREQUENCY = 400788871;

    private final double eta;
    private final double centerFrequencyInHz;
    private final double relativisticGamma;
    private final double momentumCompactionFactor;

    private MomentumOffsetCalculator(double center, double relativisticGamma, double momentumCompactionFactor) {
        super();
        this.centerFrequencyInHz = center;
        this.relativisticGamma = relativisticGamma;
        this.momentumCompactionFactor = momentumCompactionFactor;
        this.eta = (1 / Math.pow(relativisticGamma, 2)) - momentumCompactionFactor;
    }

    public static MomentumOffsetCalculator defaultForLhc() {
        return fromCenterFrequency(DEFAULT_LHC_RF_FREQUENCY);
    }

    public static MomentumOffsetCalculator fromCenterFrequency(double center) {
        return new MomentumOffsetCalculator(center, DEFAULT_LHC_RELATIVISTIC_GAMMA,
                DEFAULT_LHC_MOMENTUM_COMPACTION_FACTOR);
    }

    public MomentumOffsetCalculator withRelativisticGamma(double newRelativisticGamma) {
        return new MomentumOffsetCalculator(centerFrequencyInHz, newRelativisticGamma, momentumCompactionFactor);
    }

    public MomentumOffsetCalculator withMomentumCompactionFactor(double newMomentumCompactionFactor) {
        return new MomentumOffsetCalculator(centerFrequencyInHz, relativisticGamma, newMomentumCompactionFactor);
    }

    public double momentumOffsetFor(double actualFrequencyInHz) {
        double deltaFreqInHz = actualFrequencyInHz - centerFrequencyInHz;
        double deltafOverf = deltaFreqInHz / centerFrequencyInHz;
        return deltafOverf / eta;
    }

}
