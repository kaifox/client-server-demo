package io.github.kaifox.gsi.demo.client.mains.grpc.components;

import de.gsi.demoservice.grpc.MeasuredTuneReply;
import de.gsi.demoservice.grpc.MeasuredTuneRequest;
import de.gsi.demoservice.grpc.StandardDevReply;
import de.gsi.demoservice.grpc.StandardDevRequest;
import de.gsi.demoservice.grpc.TuneServiceGrpc.TuneServiceImplBase;
import io.github.kaifox.gsi.demo.calc.chroma.simulate.ChromaSimulator;
import io.github.kaifox.gsi.demo.calc.chroma.simulate.PublicationSimulator;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;

public class GrpcTuneService extends TuneServiceImplBase {

    @Autowired
    private ChromaSimulator chromaSimulator;

    @Autowired
    private PublicationSimulator<Tune> publicationSimulator;

    @Override
    public void getMeasuredTune(final MeasuredTuneRequest request,
                                final StreamObserver<MeasuredTuneReply> responseObserver) {
        responseObserver.onNext(responseFor(publicationSimulator.latest()));
        responseObserver.onCompleted();
    }

    @Override
    public void getMeasuredTunes(final MeasuredTuneRequest request,
                                 final StreamObserver<MeasuredTuneReply> responseObserver) {
        publicationSimulator.flux().subscribe(tune -> responseObserver.onNext(responseFor(tune)));
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

    private MeasuredTuneReply responseFor(Tune tune) {
        return MeasuredTuneReply.newBuilder().setValue(tune.getValue()).setError(tune.getError()).build();
    }
}
