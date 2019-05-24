package io.github.kaifox.gsi.demo.client.mains.rest;

import io.github.kaifox.gsi.demo.client.api.TuneReceiver;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import static java.util.Objects.requireNonNull;

public class WebfluxTuneReceiver implements TuneReceiver {

    private final WebClient client;
    private final Flux<Tune> flux;


    private WebfluxTuneReceiver(String host, int port) {
        requireNonNull(host, "host must not be null.");
        String location = host + ":" + port;
        this.client = WebClient.create("http://" + location);
        this.flux = connect().publishOn(Schedulers.elastic()).share();
    }

    public static WebfluxTuneReceiver fromLocation(String host, int port) {
        return new WebfluxTuneReceiver(host, port);
    }

    @Override
    public String name() {
        return "webflux";
    }

    @Override
    public Flux<Tune> measuredTunes() {
        return flux.onBackpressureLatest().publishOn(Schedulers.elastic());
    }

    private Flux<Tune> connect() {
        return client.get()
                .uri("/api/measuredTunes")
                .retrieve()
                .bodyToFlux(Tune.class);
    }
}