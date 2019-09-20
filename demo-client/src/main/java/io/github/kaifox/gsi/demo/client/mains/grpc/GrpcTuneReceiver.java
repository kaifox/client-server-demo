package io.github.kaifox.gsi.demo.client.mains.grpc;

import java.util.Objects;

import de.gsi.demoservice.grpc.MeasuredTuneReply;
import de.gsi.demoservice.grpc.MeasuredTuneRequest;
import de.gsi.demoservice.grpc.TuneServiceGrpc;
import io.github.kaifox.gsi.demo.client.api.TuneReceiver;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class GrpcTuneReceiver implements TuneReceiver {

    private final TuneServiceGrpc.TuneServiceStub async;
    private final Flux<Tune> flux;

    private GrpcTuneReceiver(Channel channel) {
        Objects.requireNonNull(channel, "channel must not be null.");
        this.async = TuneServiceGrpc.newStub(channel);
        this.flux = connect().share();
    }

    public static GrpcTuneReceiver fromChannel(Channel channel) {
        return new GrpcTuneReceiver(channel);
    }

    @Override
    public String name() {
        return "grpc";
    }

    @Override
    public Flux<Tune> measuredTunes() {
        return this.flux.onBackpressureLatest().publishOn(Schedulers.elastic());
    }

    private Flux<Tune> connect() {
        MeasuredTuneRequest request = MeasuredTuneRequest.getDefaultInstance();
        StreamAdapter<MeasuredTuneReply> observer = new StreamAdapter<>();
        async.getMeasuredTunes(request, observer);

        return observer.asFlux()
                .publishOn(Schedulers.elastic())
                .map(Conversions::toTune);
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
