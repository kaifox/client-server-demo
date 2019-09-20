package io.github.kaifox.gsi.demo.server.conf.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.kaifox.gsi.demo.calc.chroma.simulate.ChromaSimulator;
import io.github.kaifox.gsi.demo.calc.chroma.simulate.PublicationSimulator;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import reactor.core.publisher.Flux;

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
