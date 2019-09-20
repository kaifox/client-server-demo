package io.github.kaifox.gsi.demo;

import org.junit.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class FluxMapManagementTest {

    @Test
    public void nonsharedFluxGetsCancelNotificationEachTime() throws InterruptedException {
        Flux<Long> flux = createWithCancelMessage();
        subscribeTwiceAndDisposeDelayed(flux);
    }

    @Test
    public void sharedFluxOnlyCancelledIfAllSubscribersCancel() throws InterruptedException {
        Flux<Long> flux = createWithCancelMessage().share();
        subscribeTwiceAndDisposeDelayed(flux);
    }

    private void subscribeTwiceAndDisposeDelayed(Flux<Long> flux) throws InterruptedException {
        Disposable a = flux.subscribe(l -> System.out.println("subscriber a: " + l));
        Disposable b = flux.subscribe(l -> System.out.println("subscriber b: " + l));

        Thread.sleep(3000);
        a.dispose();

        Thread.sleep(3000);
        b.dispose();
    }

    private Flux<Long> createWithCancelMessage() {
        return Flux.interval(Duration.of(1, ChronoUnit.SECONDS))
                .doOnCancel(() -> System.out.println("Source cancelled"));
    }

}
