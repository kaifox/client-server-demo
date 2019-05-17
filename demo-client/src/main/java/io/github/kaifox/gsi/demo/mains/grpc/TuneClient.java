package io.github.kaifox.gsi.demo.mains.grpc;

import io.github.kaifox.gsi.demo.commons.domain.Tune;
import reactor.core.publisher.Flux;

public interface TuneClient {

    Tune measuredTune();

    Flux<Tune> measuredTunes();

    void setStandardDev(double standardDev);

    double getStandardDev();

}
