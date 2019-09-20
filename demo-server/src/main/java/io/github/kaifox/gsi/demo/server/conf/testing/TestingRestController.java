package io.github.kaifox.gsi.demo.server.conf.testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.kaifox.gsi.demo.calc.chroma.simulate.PayloadSimulator;
import io.github.kaifox.gsi.demo.calc.chroma.simulate.PublicationSimulator;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/testing")
public class TestingRestController {

    @Autowired
    private PublicationSimulator<Tune> publicationSimulator;

    @Autowired
    private PayloadSimulator payloadSimulator;

    @GetMapping("/delayInMillis")
    public long getDelayInMillis() {
        return publicationSimulator.delayInMillis();
    }

    @PostMapping("/delayInMillis/{delayInMillis}")
    public void setDelayInMillis(@PathVariable("delayInMillis") long delayInMillis) {
        publicationSimulator.setDelayInMillis(delayInMillis);
    }

    @GetMapping("/payloadLength")
    public int getPayloadLength() {
        return payloadSimulator.getLength();
    }

    @PostMapping("/payloadLength/{length}")
    public void setPayloadLength(@PathVariable("length") int payloadLength) {
        payloadSimulator.setLength(payloadLength);
    }


    @GetMapping(value = "/periodicPublicationEnableds", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Boolean> getPeriodicPublicationEnabled() {
        return publicationSimulator.periodicPublicationEnabled();
    }

    @PostMapping("/periodicPublicationEnabled/{enabled}")
    public void setPeriodicPublicationEnabled(@PathVariable("enabled") boolean enabled) {
        publicationSimulator.setPeriodicPublicationEnabled(enabled);
    }


    @GetMapping(value = "/burstStartSizes", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> burstStartSizes() {
        return publicationSimulator.burstStartSizes();
    }


    @PostMapping("/triggerBurst/{numberOfPublications}")
    public void triggerBurst(@PathVariable("numberOfPublications") int numberOfPublications) {
        publicationSimulator.triggerBurst(numberOfPublications);
    }

}
