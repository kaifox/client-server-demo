package io.github.kaifox.gsi.demo.client.mains.properties;

import io.gihub.ossgang.properties.web.client.RestClient;
import io.gihub.ossgang.properties.web.client.RestRemoteProperty;
import io.gihub.ossgang.properties.web.client.WebSocketFlux;
import io.gihub.ossgang.properties.web.client.rbac.DemoRbacTokenProvider;
import io.gihub.ossgang.properties.web.client.rbac.RbacTokenProvider;
import io.github.kaifox.gsi.demo.client.conf.Constants;
import io.github.kaifox.gsi.demo.client.mains.properties.components.FluxTunesView;
import io.github.kaifox.gsi.demo.client.mains.properties.components.SettingsView;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import io.github.ossgang.properties.core.JsonConversions;
import org.minifx.workbench.annotations.View;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Configuration
public class RestPropertiesClient {

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
        return new RestRemoteProperty<>(restClient, "ws://" + Constants.HOST + ":" + Constants.HTTP_PORT, Double.class, "standardDev");
    }

    @Bean
    public Flux<Tune> measuredTunes() {
        return WebSocketFlux.fluxFrom("ws://" + Constants.HOST + ":" + Constants.HTTP_PORT + "/measuredTunes")
                .map(s -> JsonConversions.defaultDeserialization(s, Tune.class));
    }

    @Bean
    public RestClient restClient() {
        return new RestClient("http://" + Constants.HOST + ":" + Constants.HTTP_PORT);
    }

    @Bean
    public RbacTokenProvider rbacTokenProvider() {
        return new DemoRbacTokenProvider();
    }

}
