package io.github.kaifox.gsi.demo.mains.grpc;

import de.gsi.demoservice.grpc.MeasuredTuneReply;
import de.gsi.demoservice.grpc.MeasuredTuneRequest;
import de.gsi.demoservice.grpc.StandardDevRequest;
import de.gsi.demoservice.grpc.TuneServiceGrpc;
import de.gsi.demoservice.grpc.TuneServiceGrpc.TuneServiceBlockingStub;
import de.gsi.demoservice.grpc.TuneServiceGrpc.TuneServiceStub;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class GrpcTuneClient implements TuneClient {

    private final static int PORT = 5252;

    private final Channel channel = ManagedChannelBuilder.forAddress("localhost", PORT)
            .usePlaintext()
            .build();

    private final TuneServiceStub async = TuneServiceGrpc.newStub(channel);
    private final TuneServiceBlockingStub blocking = TuneServiceGrpc.newBlockingStub(channel);

    @Override
    public Tune measuredTune() {
        MeasuredTuneRequest request = MeasuredTuneRequest.getDefaultInstance();
        MeasuredTuneReply reply = blocking.getMeasuredTune(request);
        return toTune(reply);
    }


    @Override
    public Flux<Tune> measuredTunes() {
        MeasuredTuneRequest request = MeasuredTuneRequest.getDefaultInstance();
        StreamAdapter<MeasuredTuneReply> observer = new StreamAdapter<>();
        async.getMeasuredTunes(request, observer);

        return observer.asFlux()
                .subscribeOn(Schedulers.elastic())
                .map(GrpcTuneClient::toTune);
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

    private static final Tune toTune(MeasuredTuneReply reply) {
        return new Tune(reply.getValue(), reply.getError());
    }


    private static class StreamAdapter<T> implements StreamObserver<T> {

        private final EmitterProcessor<T> emitter = EmitterProcessor.create();

        @Override
        public void onNext(T value) {
            emitter.onNext(value);
        }

        @Override
        public void onError(Throwable t) {
            emitter.onError(t);
        }

        @Override
        public void onCompleted() {
            emitter.onComplete();
        }

        public Flux<T> asFlux() {
            return this.emitter;
        }
    }
}
