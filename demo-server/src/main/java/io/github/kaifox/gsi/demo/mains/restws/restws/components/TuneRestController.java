package io.github.kaifox.gsi.demo.mains.restws.restws.components;

import io.github.kaifox.gsi.demo.calc.chroma.simulate.ChromaSimulator;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.SECONDS;

@RestController
@RequestMapping("/api")
public class TuneRestController {

    @Autowired
    private ChromaSimulator chromaSimulator;

    @GetMapping("/measuredTune")
    public Tune measuredTune() {
        return newTune();
    }


    @GetMapping(value = "/measuredTunes", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Tune> measuredTunes() {
        return Flux.interval(Duration.of(1, SECONDS))
                .map(l -> newTune());
    }

    @GetMapping("/get/standardDev")
    public double getTuneStandardDev() {
        return chromaSimulator.getNoiseStandardDev();
    }

    @PostMapping("/set/standardDev/{stdDev}")
    public void setTuneStandardDev(@PathVariable("stdDev") double stdDev) {
        chromaSimulator.setNoiseStandardDev(stdDev);
    }

    private Tune newTune() {
        return new Tune(chromaSimulator.actualTune(), chromaSimulator.getNoiseStandardDev());
    }


}
