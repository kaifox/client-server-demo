package io.github.kaifox.gsi.demo.server.conf.testing;

import io.github.kaifox.gsi.demo.calc.chroma.simulate.PayloadSimulator;
import io.github.kaifox.gsi.demo.calc.chroma.simulate.PublicationSimulator;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/periodicPublicationEnabled")
    public boolean getPeriodicPublicationEnabled() {
        return publicationSimulator.getPeriodicPublicationEnabled();
    }

    @PostMapping("/periodicPublicationEnabled/{enabled}")
    public void setPeriodicPublicationEnabled(@PathVariable("enabled") boolean enabled) {
        publicationSimulator.setPeriodicPublicationEnabled(enabled);
    }


    @PostMapping("/triggerBurst/{numberOfPublications}")
    public void triggerBurst(@PathVariable("numberOfPublications") int numberOfPublications) {
        publicationSimulator.triggerBurst(numberOfPublications);
    }

}
