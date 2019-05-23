package io.github.kaifox.gsi.demo.client.mains.testing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import static java.util.Objects.requireNonNull;

public class WebfluxTestControlClient implements TestControlClient {
    private final WebClient client;

    private WebfluxTestControlClient(String host, int port) {
        requireNonNull(host, "host must not be null.");
        String location = host + ":" + port;
        this.client = WebClient.create("http://" + location);
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
