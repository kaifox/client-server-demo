package io.github.kaifox.gsi.demo.commons.domain;

import java.util.Objects;

public class BurstPacket {

    private static final BurstPacket NO_BURST = new BurstPacket();

    private final long burstId;
    private final int indexInBurst;
    private final int burstLength;

    public BurstPacket(long burstId, int indexInBurst, int burstLength) {
        this.burstId = burstId;
        this.indexInBurst = indexInBurst;
        this.burstLength = burstLength;
    }

    public static BurstPacket noBurst() {
        return NO_BURST;
    }

    @Override
    public String toString() {
        return "BurstPacket{" +
                "burstId=" + burstId +
                ", indexInBurst=" + indexInBurst +
                ", burstLength=" + burstLength +
                '}';
    }

    public BurstPacket() {
        this(0, -1, 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BurstPacket that = (BurstPacket) o;
        return burstId == that.burstId &&
                indexInBurst == that.indexInBurst &&
                burstLength == that.burstLength;
    }

    @Override
    public int hashCode() {
        return Objects.hash(burstId, indexInBurst, burstLength);
    }
}
