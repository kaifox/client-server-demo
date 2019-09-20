package io.github.kaifox.gsi.demo.client.conf;

import org.minifx.fxcommons.MiniFxSceneBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonGuiConfiguration {

    @Bean
    public MiniFxSceneBuilder miniFxSceneBuilder() {
        return MiniFxSceneBuilder.miniFxSceneBuilder().withSize(640, 450);
    }

}
