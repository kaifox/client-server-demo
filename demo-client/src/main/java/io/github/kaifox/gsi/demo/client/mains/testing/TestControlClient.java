package io.github.kaifox.gsi.demo.client.mains.testing;

import reactor.core.publisher.Flux;

public interface TestControlClient {
    long getDelayInMillis();

    void setDelayInMillis(long delayInMillis);

    int getPayloadLength();

    void setPayloadLength(int length);

    Flux<Boolean> periodicPublicationEnabled();

    void setPeriodicPublicationEnabled(boolean enabled);

    void triggerBurst(int burstLength);

    Flux<Integer> burstStartSizes();
}
