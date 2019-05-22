package io.github.kaifox.gsi.demo.client.api;

import io.github.kaifox.gsi.demo.commons.domain.Tune;
import reactor.core.publisher.Flux;

public interface TuneReceiver  {
    String name();

    Flux<Tune> measuredTunes();
}
