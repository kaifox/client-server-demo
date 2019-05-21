package io.github.kaifox.gsi.demo.calc.chroma.simulate;

import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    private final ReplayProcessor<T> sink = ReplayProcessor.cacheLast();
    private final Flux<T> stream = sink.publishOn(Schedulers.elastic()).share();

    private PublicationSimulator(Supplier<T> supplier) {
        this.supplier = Objects.requireNonNull(supplier, "supplier must not be null");
        executor.submit(this::periodicallyPublish);
    }

    public Flux<T> flux() {
        return this.stream;
    }

    public T latest() {
        return actualValue.get();
    }

    public long delayInMillis() {
        return sleepTimeInMillis.get();
    }

    public void setDelay(long millis) {
        this.sleepTimeInMillis.set(millis);
    }

    public static <T> PublicationSimulator<T> generatedBy(Supplier<T> supplier) {
        return new PublicationSimulator<T>(supplier);
    }

    private void periodicallyPublish() {
        while (true) {
            T value = supplier.get();
            actualValue.set(value);
            publishExecutor.submit(() -> publish(value));
            sleepUnchecked(sleepTimeInMillis.get());
        }
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

}
