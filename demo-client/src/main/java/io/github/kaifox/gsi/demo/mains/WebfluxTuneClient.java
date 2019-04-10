package io.github.kaifox.gsi.demo.mains;

import io.github.kaifox.gsi.demo.commons.domain.Tune;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;

public class WebfluxTuneClient implements TuneClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebfluxTuneClient.class);

    private final WebClient client = WebClient.create("http://localhost:8080");

    @Override
    public Tune measuredTune() {
        return client.get()
                .uri("/api/measuredTune")
                .retrieve()
                .bodyToMono(Tune.class)
                .block();
    }

    @Override
    public Flux<Tune> measuredTunes() {
        return client.get()
                .uri("/api/measuredTunes")
                .retrieve()
                .bodyToFlux(Tune.class);
    }

    @Override
    public void setStandardDev(double standardDev) {
        client.post().uri("/api/set/standardDev/" + standardDev).exchange().block();
    }



}
