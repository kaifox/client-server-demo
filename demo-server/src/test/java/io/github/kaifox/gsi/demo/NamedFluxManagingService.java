package io.github.kaifox.gsi.demo;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.concurrent.GuardedBy;

import reactor.core.publisher.Flux;

/**
 * This is just a conceptual try, to see how one could  share streams if possible, but also clean up whenever none of
 * them is needed anymore. The idea is that heavy work is ony processed once.
 */
public class NamedFluxManagingService {

    private Object monitor = new Object();

    /**
     * In this map we will keep all the streams. On purpose we will use ordinal synchronization for the moment,
     * as we are not sure yet how to cleanly implement this lock-free....
     */
    @GuardedBy("monitor")
    private Map<String, Flux<Data>> dataStreams = new HashMap<>();


    /**
     * Will return a shared flux, corresponding to the given name. It will reuse an existing one if available, or create a new one.
     *
     * @param name the name of the flux
     * @return a shared flux
     */
    public Flux<Data> dataFluxFor(String name) {
        synchronized (monitor) {
            Flux<Data> flux = dataStreams.get(name);
            if (flux != null) {
                return flux;
            } else {
                flux = create(name).doOnCancel(() -> this.remove(name)).share();
                dataStreams.put(name, flux);
                return flux;
            }
        }
    }

    private void remove(String name) {
        /*
        XXX will not properly work ... nothing guarantees that in between returning from dataFluxFor() and subscribing,
        another subscriber cancels and thus removes it from the list ... then the next time, a new stream would be
        created ...
         */
        synchronized (monitor) {
            dataStreams.remove(name);
        }
    }


    private Flux<Data> create(String name) {
        return Flux.interval(Duration.of(1, ChronoUnit.SECONDS))
                .map(Data::new);
    }

    public static class Data {

        private final Long value;

        public Data(Long value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data data = (Data) o;
            return Objects.equals(value, data.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "Data{" +
                    "value=" + value +
                    '}';
        }


    }
}
