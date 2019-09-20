package io.github.kaifox.gsi.demo.server.conf;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import io.github.kaifox.gsi.demo.calc.chroma.simulate.ChromaSimulator;
import reactor.core.publisher.Flux;

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
