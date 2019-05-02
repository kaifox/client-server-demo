package io.github.kaifox.gsi.demo.mains.properties.components;


import io.github.ossgang.properties.core.Properties;
import cern.lhc.commons.web.property.RestPropertyMapping;
import cern.lhc.commons.web.property.StreamWebsocketMapping;
import io.github.kaifox.gsi.demo.calc.chroma.simulate.ChromaSimulator;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import io.github.ossgang.properties.core.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.time.Duration;

import static cern.lhc.commons.web.property.RestPropertyMapping.mappingFor;
import static java.time.temporal.ChronoUnit.SECONDS;

@Configuration
public class PropertiesConfiguration {

    @Autowired
    private ChromaSimulator chromaSimulator;

    @Bean
    public RestPropertyMapping<Double> standardDeviationPropertyMapping() {
        Property<Double> stdDevProperty = Properties.doubleProperty(chromaSimulator.getNoiseStandardDev());
        stdDevProperty.getSource().asStream().subscribe(v -> chromaSimulator.setNoiseStandardDev(v));
        return mappingFor(stdDevProperty).withPath("standardDev").withDefaultSerializationAs(Double.class);
    }

    @Bean
    public StreamWebsocketMapping<Tune> tunes() {
        Flux<Tune> tunes = Flux.interval(Duration.of(1, SECONDS))
                .map(t -> newTune());
        return StreamWebsocketMapping.websocketMappingFrom(tunes).withPath("measuredTunes").withDefaultSerialization();
    }


    private Tune newTune() {
        return new Tune(chromaSimulator.actualTune(), chromaSimulator.getNoiseStandardDev());
    }


}
