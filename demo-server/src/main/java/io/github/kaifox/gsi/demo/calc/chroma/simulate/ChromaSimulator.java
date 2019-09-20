package io.github.kaifox.gsi.demo.calc.chroma.simulate;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import com.google.common.util.concurrent.AtomicDouble;

/**
 * This is a modified version of the simulator used in the LHC chroma app. However, this one is not relying on tensors
 * and just takes one beam and plane for calculation. Further, it does not take care of any units. This class is mainly
 * for demo purposes.
 * <p>
 * Can simulate LHC tunes and chroma values. The simulated values can be influenced by
 * <ul>
 * <li>Trimming tune and chroma
 * <li>changing the momentum offset (deltap)
 * <li>changing a random noise level
 * </ul>
 * <p>
 * This class is threadsafe. However, the actualTune method will simply use always the lates values set.
 *
 * @author kfuchsbe
 */
public class ChromaSimulator {

    private static final double DEFAULT_NOISE_STD_DEV = 0.001;
    private static final Random RANDOM = new Random();

    private final AtomicDouble noiseStandardDev = new AtomicDouble(DEFAULT_NOISE_STD_DEV);

    private final AtomicReference<Double> actualOnMomentumTunes = new AtomicReference<>(0.28);
    private final AtomicReference<Double> actualChromas = new AtomicReference<>(15.0);
    private final AtomicReference<Double> actualSecondOrder = new AtomicReference<>(0.0);
    private final AtomicDouble momentumOffset = new AtomicDouble(0);


    public double actualTune() {
        return noisy(chromaticTune());
    }

    private double noisy(double idealTune) {
        double noise = RANDOM.nextGaussian() * noiseStandardDev.get();
        return idealTune + noise;
    }

    private double chromaticTune() {
        double deltap = getDeltap();
        double offset = actualChromas.get() * deltap;
        double secondOrderOffset = actualSecondOrder.get() * deltap * deltap;

        return onMomentumTune() + offset + secondOrderOffset;
    }

    private double onMomentumTune() {
        return actualOnMomentumTunes.get();
    }

    private double getDeltap() {
        return momentumOffset.get();
    }

    public void setMomentumOffset(double newMomentumOffset) {
        this.momentumOffset.set(newMomentumOffset);
    }

    public void setNoiseStandardDev(double noiseStandardDev) {
        this.noiseStandardDev.set(noiseStandardDev);
    }

    public double getNoiseStandardDev() {
        return noiseStandardDev.get();
    }

    public void trimTuneDelta(double deltaTune) {
        actualOnMomentumTunes.getAndAccumulate(deltaTune, (actual, delta) -> actual + delta);
    }

    public void trimChromaDelta(double deltaChroma) {
        actualChromas.getAndAccumulate(deltaChroma, (actual, delta) -> actual + delta);
    }

}
