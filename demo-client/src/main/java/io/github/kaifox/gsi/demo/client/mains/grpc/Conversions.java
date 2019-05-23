package io.github.kaifox.gsi.demo.client.mains.grpc;

import de.gsi.demoservice.grpc.MeasuredTuneReply;
import io.github.kaifox.gsi.demo.commons.domain.Tune;

public final class Conversions {

    private Conversions() {
        /* only static methods */
    }

    public static final Tune toTune(MeasuredTuneReply reply) {
        return new Tune(reply.getValue(), reply.getError(), reply.getPayloadList());
    }
}
