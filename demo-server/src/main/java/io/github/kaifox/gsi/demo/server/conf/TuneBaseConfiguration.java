package io.github.kaifox.gsi.demo.server.conf;

import io.github.kaifox.gsi.demo.calc.chroma.simulate.ChromaSimulator;
import io.github.kaifox.gsi.demo.calc.chroma.simulate.PayloadSimulator;
import io.github.kaifox.gsi.demo.calc.chroma.simulate.PublicationSimulator;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TuneBaseConfiguration {

    @Bean
    public ChromaSimulator chromaSimulator() {
        return new ChromaSimulator();
    }

    @Bean
    public PayloadSimulator payloadSimulator() {
        return new PayloadSimulator();
    }

    @Bean
    public PublicationSimulator<Tune> tunePublicationSimulator(ChromaSimulator chromaSimulator, PayloadSimulator payloadSimulator) {
        return PublicationSimulator.generatedBy(() -> new Tune(chromaSimulator.actualTune(), chromaSimulator.getNoiseStandardDev(), payloadSimulator.nextPayload()));
    }


}
