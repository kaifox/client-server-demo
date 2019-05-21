package io.github.kaifox.gsi.demo.client.mains.restws.components;

import io.github.kaifox.gsi.demo.calc.chroma.simulate.ChromaSimulator;
import io.github.kaifox.gsi.demo.calc.chroma.simulate.PublicationSimulator;
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

    @Autowired
    private PublicationSimulator<Tune> publicationSimulator;

    @GetMapping("/measuredTune")
    public Tune measuredTune() {
        return publicationSimulator.latest();
    }

    @GetMapping(value = "/measuredTunes", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tune> measuredTunes() {
        return publicationSimulator.flux();
    }

    @GetMapping("/standardDev")
    public double getTuneStandardDev() {
        return chromaSimulator.getNoiseStandardDev();
    }

    @PostMapping("/standardDev/{stdDev}")
    public void setTuneStandardDev(@PathVariable("stdDev") double stdDev) {
        chromaSimulator.setNoiseStandardDev(stdDev);
    }

}
