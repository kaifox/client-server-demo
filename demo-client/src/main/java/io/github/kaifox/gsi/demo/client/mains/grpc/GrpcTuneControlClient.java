package io.github.kaifox.gsi.demo.client.mains.grpc;

import de.gsi.demoservice.grpc.MeasuredTuneReply;
import de.gsi.demoservice.grpc.MeasuredTuneRequest;
import de.gsi.demoservice.grpc.StandardDevRequest;
import de.gsi.demoservice.grpc.TuneServiceGrpc;
import de.gsi.demoservice.grpc.TuneServiceGrpc.TuneServiceBlockingStub;
import de.gsi.demoservice.grpc.TuneServiceGrpc.TuneServiceStub;
import io.github.kaifox.gsi.demo.client.api.TuneControlClient;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import io.grpc.Channel;

import java.util.Objects;

public class GrpcTuneControlClient implements TuneControlClient {

    private final TuneServiceBlockingStub blocking;

    private GrpcTuneControlClient(Channel channel) {
        Objects.requireNonNull(channel, "channel must not be null");
        this.blocking = TuneServiceGrpc.newBlockingStub(channel);
    }

    public static GrpcTuneControlClient fromChannel(Channel channel) {
        return new GrpcTuneControlClient(channel);
    }

    @Override
    public Tune measuredTune() {
        MeasuredTuneRequest request = MeasuredTuneRequest.getDefaultInstance();
        MeasuredTuneReply reply = blocking.getMeasuredTune(request);
        return Conversions.toTune(reply);
    }


    @Override
    public void setStandardDev(double standardDev) {
        StandardDevRequest request = StandardDevRequest.newBuilder().setStdDev(standardDev).build();
        blocking.setStandardDev(request);
    }

    @Override
    public double getStandardDev() {
        StandardDevRequest request = StandardDevRequest.getDefaultInstance();
        return blocking.getStandardDev(request).getStdDev();
    }


}
