package io.github.kaifox.gsi.demo.server.conf.properties;


import cern.lhc.commons.web.property.PropertyRestController;
import cern.lhc.commons.web.property.PropertyWebsocketConfiguration;
import cern.lhc.commons.web.property.RestPropertyMapping;
import cern.lhc.commons.web.property.StreamWebsocketMapping;
import io.github.kaifox.gsi.demo.calc.chroma.simulate.ChromaSimulator;
import io.github.kaifox.gsi.demo.calc.chroma.simulate.PublicationSimulator;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import io.github.ossgang.properties.core.Properties;
import io.github.ossgang.properties.core.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static cern.lhc.commons.web.property.RestPropertyMapping.mappingFor;

@Configuration
@Import({PropertyRestController.class, PropertyWebsocketConfiguration.class})
public class PropertiesConfiguration {

    @Autowired
    private ChromaSimulator chromaSimulator;

    @Autowired
    private PublicationSimulator<Tune> publicationSimulator;

    @Bean
    public RestPropertyMapping<Double> standardDeviationPropertyMapping() {
        Property<Double> stdDevProperty = Properties.doubleProperty(chromaSimulator.getNoiseStandardDev());
        stdDevProperty.getSource().asStream().subscribe(v -> chromaSimulator.setNoiseStandardDev(v));
        return mappingFor(stdDevProperty).withPath("standardDev").withDefaultSerializationAs(Double.class);
    }

    @Bean
    public StreamWebsocketMapping<Tune> tunes() {
        return StreamWebsocketMapping.websocketMappingFrom(publicationSimulator.flux()).withPath("measuredTunes").withDefaultSerialization();
    }

}
