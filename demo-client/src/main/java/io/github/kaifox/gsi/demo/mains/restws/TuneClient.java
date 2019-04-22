package io.github.kaifox.gsi.demo.mains.restws;

import io.github.kaifox.gsi.demo.commons.domain.Tune;
import reactor.core.publisher.Flux;

public interface TuneClient {

    Tune measuredTune();

    Flux<Tune> measuredTunes();

    Flux<Tune> wsMeasuredTunes();

    void setStandardDev(double standardDev);

    double getStandardDev();

}
