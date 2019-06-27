package io.github.kaifox.gsi.demo.calc.chroma.simulate;

import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * Very simple helper class to simulate periodic publications. It simply calls periodically a
 * provided supplier and publishes regularly. In addition, the latest published value is kept.
 *
 * @param <T> the type of the published values
 */
public class PublicationSimulator<T> {

    private final Supplier<T> supplier;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final ExecutorService publishExecutor = Executors.newCachedThreadPool();
    private final AtomicReference<T> actualValue = new AtomicReference<>();
    private final AtomicLong sleepTimeInMillis = new AtomicLong(1000);
    private final AtomicBoolean periodicPublicationEnabled = new AtomicBoolean(true);

    private final ReplayProcessor<T> sink = ReplayProcessor.cacheLast();
    private final Flux<T> stream = sink.publishOn(Schedulers.elastic()).share();

    private PublicationSimulator(Supplier<T> supplier) {
        this.supplier = Objects.requireNonNull(supplier, "supplier must not be null");
        executor.submit(this::periodicallyPublish);
    }

    public Flux<T> flux() {
        return this.stream.onBackpressureDrop().publishOn(Schedulers.elastic());
    }

    public T latest() {
        return actualValue.get();
    }

    public long delayInMillis() {
        return sleepTimeInMillis.get();
    }

    public void setDelayInMillis(long millis) {
        this.sleepTimeInMillis.set(millis);
    }

    public static <T> PublicationSimulator<T> generatedBy(Supplier<T> supplier) {
        return new PublicationSimulator<T>(supplier);
    }

    private void periodicallyPublish() {
        while (true) {
            if (periodicPublicationEnabled.get()) {
                T value = updateValue();
                publishExecutor.submit(() -> publish(value));
                sleepUnchecked(sleepTimeInMillis.get());
            }
        }
    }

    private T updateValue() {
        T value = supplier.get();
        actualValue.set(value);
        return value;
    }

    public void triggerBurst(int numberOfPublications) {
        publishExecutor.submit(() -> {
            for (int i = 0; i < numberOfPublications; i++) {
                publishExecutor.submit(() -> {
                    T value = updateValue();
                    publish(value);
                });
            }
        });
    }

    private void publish(T value) {
        sink.onNext(value);
    }

    private void sleepUnchecked(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getPeriodicPublicationEnabled() {
        return this.periodicPublicationEnabled.get();
    }

    public void setPeriodicPublicationEnabled(boolean enabled) {
        this.periodicPublicationEnabled.set(enabled);
    }

}
