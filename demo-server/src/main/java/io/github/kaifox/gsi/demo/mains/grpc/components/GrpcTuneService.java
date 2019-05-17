package io.github.kaifox.gsi.demo.mains.grpc.components;

import de.gsi.demoservice.grpc.*;
import de.gsi.demoservice.grpc.TuneServiceGrpc.TuneServiceImplBase;
import io.github.kaifox.gsi.demo.calc.chroma.simulate.ChromaSimulator;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GrpcTuneService extends TuneServiceImplBase {

    @Autowired
    private ChromaSimulator chromaSimulator;

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @Override
    public void getMeasuredTune(final MeasuredTuneRequest request,
                                final StreamObserver<MeasuredTuneReply> responseObserver) {
        newTune(responseObserver);
        responseObserver.onCompleted();
    }

    @Override
    public void getMeasuredTunes(final MeasuredTuneRequest request,
                                 final StreamObserver<MeasuredTuneReply> responseObserver) {
        executor.scheduleAtFixedRate(() -> newTune(responseObserver), 0, 1000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void getStandardDev(final StandardDevRequest request,
                               final StreamObserver<StandardDevReply> responseObserver) {
        final StandardDevReply.Builder statusReplyBuilder = StandardDevReply.newBuilder();
        statusReplyBuilder.setStdDev(chromaSimulator.getNoiseStandardDev());
        responseObserver.onNext(statusReplyBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void setStandardDev(final StandardDevRequest request,
                               final StreamObserver<StandardDevReply> responseObserver) {
        chromaSimulator.setNoiseStandardDev(request.getStdDev());
        final StandardDevReply.Builder statusReplyBuilder = StandardDevReply.newBuilder();
        statusReplyBuilder.setStdDev(chromaSimulator.getNoiseStandardDev());
        responseObserver.onNext(null);
        responseObserver.onCompleted();
    }

    private void newTune(final StreamObserver<MeasuredTuneReply> responseObserver) {
        final Tune tune = new Tune(chromaSimulator.actualTune(), chromaSimulator.getNoiseStandardDev());
        final MeasuredTuneReply.Builder reply = MeasuredTuneReply.newBuilder();
        reply.setValue(tune.getValue());
        reply.setError(tune.getError());
        responseObserver.onNext(reply.build());
    }
}
