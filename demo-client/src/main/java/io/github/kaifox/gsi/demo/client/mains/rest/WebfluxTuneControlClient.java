package io.github.kaifox.gsi.demo.client.mains.rest;

import io.github.kaifox.gsi.demo.client.api.TuneControlClient;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;

import static java.util.Objects.requireNonNull;

public class WebfluxTuneControlClient implements TuneControlClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebfluxTuneControlClient.class);

    private final WebClient client;

    private WebfluxTuneControlClient(String host, int port) {
        requireNonNull(host, "host must not be null.");
        String location = host + ":" + port;
        this.client = WebClient.create("http://" + location);
    }

    public static WebfluxTuneControlClient fromLocation(String host, int port) {
        return new WebfluxTuneControlClient(host, port);
    }

    @Override
    public Tune measuredTune() {
        return client.get()
                .uri("/api/measuredTune")
                .retrieve()
                .bodyToMono(Tune.class)
                .block();
    }

    @Override
    public void setStandardDev(double standardDev) {
        client.post()
                .uri("/api/standardDev/" + standardDev)
                .exchange()
                .block();
    }

    @Override
    public double getStandardDev() {
        return client.get()
                .uri("/api/standardDev")
                .retrieve()
                .bodyToMono(Double.class)
                .block();
    }


}
