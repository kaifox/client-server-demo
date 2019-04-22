package io.github.kaifox.gsi.demo.mains.restws.config;

import io.github.kaifox.gsi.demo.calc.chroma.simulate.ChromaSimulator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TuneBaseConfiguration {

    @Bean
    public ChromaSimulator chromaSimulator() {
        return new ChromaSimulator();
    }



}
