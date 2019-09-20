package io.github.kaifox.gsi.demo.client.mains.testing;

import static java.util.Objects.requireNonNull;

import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class WebfluxTestControlClient implements TestControlClient {
    private final WebClient client;
    private final Flux<Boolean> periodicPublicationEnabled;
    private final Flux<Integer> burstStartSizes;

    private WebfluxTestControlClient(String host, int port) {
        requireNonNull(host, "host must not be null.");
        String location = host + ":" + port;
        this.client = WebClient.create("http://" + location);
        this.periodicPublicationEnabled = client.get()
                .uri("/testing/periodicPublicationEnableds")
                .retrieve()
                .bodyToFlux(Boolean.class)
                .publishOn(Schedulers.elastic())
                .share();
        this.burstStartSizes = client.get()
                .uri("/testing/burstStartSizes")
                .retrieve()
                .bodyToFlux(Integer.class)
                .publishOn(Schedulers.elastic())
                .share();
    }

    public static WebfluxTestControlClient fromLocation(String host, int port) {
        return new WebfluxTestControlClient(host, port);
    }

    @Override
    public long getDelayInMillis() {
        return blockingGet("/testing/delayInMillis", Long.class);
    }

    @Override
    public void setDelayInMillis(long delayInMillis) {
        blockingPathPost("/testing/delayInMillis", delayInMillis);
    }

    @Override
    public int getPayloadLength() {
        return blockingGet("/testing/payloadLength", Integer.class);
    }

    @Override
    public void setPayloadLength(int length) {
        blockingPathPost("/testing/payloadLength", length);
    }

    @Override
    public Flux<Boolean> periodicPublicationEnabled() {
        return this.periodicPublicationEnabled;
    }

    @Override
    public void setPeriodicPublicationEnabled(boolean enabled) {
        blockingPathPost("/testing/periodicPublicationEnabled", enabled);
    }

    @Override
    public void triggerBurst(int burstLength) {
        blockingPathPost("/testing/triggerBurst", burstLength);
    }

    @Override
    public Flux<Integer> burstStartSizes() {
        return this.burstStartSizes;
    }

    private <T> T blockingGet(String uri, Class<T> bodyType) {
        return client.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(bodyType)
                .block();
    }

    private <T> void blockingPathPost(String uri, T value) {
        client.post()
                .uri(uri + "/" + value)
                .exchange()
                .block();
    }


}
