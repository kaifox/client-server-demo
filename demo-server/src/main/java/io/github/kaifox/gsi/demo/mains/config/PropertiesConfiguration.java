package io.github.kaifox.gsi.demo.mains.config;


import cern.lhc.commons.web.property.Properties;
import cern.lhc.commons.web.property.RestPropertyMapping;
import cern.lhc.commons.web.property.SimpleProperty;
import io.github.kaifox.gsi.demo.calc.chroma.simulate.ChromaSimulator;
import io.github.ossgang.properties.core.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static cern.lhc.commons.web.property.RestPropertyMapping.mappingFor;

@Configuration
public class PropertiesConfiguration {

    @Autowired
    private ChromaSimulator chromaSimulator;

    @Bean
    public RestPropertyMapping<Double> standardDeviationPropertyMapping() {
        Property<Double> stdDevProperty = Properties.doubleProperty(chromaSimulator.getNoiseStandardDev());
        stdDevProperty.getSource().asStream().subscribe(v -> chromaSimulator.setNoiseStandardDev(v));
        return mappingFor(stdDevProperty).withPath("stdDev").withDefaultSerializationAs(Double.class);
    }
}
