package io.github.kaifox.gsi.demo.server.conf;

import io.github.kaifox.gsi.demo.calc.chroma.simulate.ChromaSimulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class PrintTuneConfiguration {

    @Autowired
    private ChromaSimulator chromaSimulator;

    @PostConstruct
    public void init() {
        Flux.interval(Duration.of(1, ChronoUnit.SECONDS))
                .map(d -> chromaSimulator.actualTune())
                .subscribe(tune -> System.out.println("actualTune=" + tune));
    }
}
