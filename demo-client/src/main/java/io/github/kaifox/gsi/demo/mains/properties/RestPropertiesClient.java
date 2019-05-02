package io.github.kaifox.gsi.demo.mains.properties;

import io.gihub.ossgang.properties.web.client.RestClient;
import io.gihub.ossgang.properties.web.client.RestRemoteProperty;
import io.gihub.ossgang.properties.web.client.WebSocketFlux;
import io.gihub.ossgang.properties.web.client.rbac.DemoRbacTokenProvider;
import io.gihub.ossgang.properties.web.client.rbac.RbacTokenProvider;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import io.github.kaifox.gsi.demo.mains.properties.components.FluxTunesView;
import io.github.kaifox.gsi.demo.mains.properties.components.SettingsView;
import io.github.ossgang.properties.core.JsonConversions;
import org.minifx.fxcommons.MiniFxSceneBuilder;
import org.minifx.workbench.MiniFx;
import org.minifx.workbench.annotations.View;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Configuration
public class RestPropertiesClient {


    public static void main(String... args) {
        MiniFx.launcher(RestPropertiesClient.class).launch(args);
    }


    @View
    @Bean
    public SettingsView settingsView() {
        return new SettingsView();
    }

    @View
    @Bean
    public FluxTunesView fluxTunesView() {
        return new FluxTunesView();
    }

    @Bean
    public RestRemoteProperty<Double> standardDevProperty(RestClient restClient) {
        return new RestRemoteProperty<>(restClient, "ws://localhost:8080", Double.class, "standardDev");
    }

    @Bean
    public Flux<Tune> measuredTunes() {
        return WebSocketFlux.fluxFrom("ws://localhost:8080/measuredTunes")
                .map(s -> JsonConversions.defaultDeserialization(s, Tune.class));
    }

    @Bean
    public RestClient restClient() {
        return new RestClient("http://localhost:8080");
    }

    @Bean
    public RbacTokenProvider rbacTokenProvider() {
        return new DemoRbacTokenProvider();
    }

    @Bean
    public MiniFxSceneBuilder miniFxSceneBuilder() {
        return MiniFxSceneBuilder.miniFxSceneBuilder().withSize(640, 380);
    }

}
