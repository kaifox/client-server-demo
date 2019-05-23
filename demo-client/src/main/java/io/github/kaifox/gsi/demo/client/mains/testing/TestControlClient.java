package io.github.kaifox.gsi.demo.client.mains.testing;

public interface TestControlClient {
    long getDelayInMillis();

    void setDelayInMillis(long delayInMillis);

    int getPayloadLength();

    void setPayloadLength(int length);
}
