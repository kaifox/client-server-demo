package io.github.kaifox.gsi.demo.client.mains.testing;

import io.github.kaifox.gsi.demo.client.api.TuneReceiver;
import io.github.kaifox.gsi.demo.client.views.ReceptionSpeedView;
import io.github.kaifox.gsi.demo.client.views.TestSettingsView;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.annotations.View;
import org.minifx.workbench.domain.PerspectivePos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.github.kaifox.gsi.demo.client.conf.Constants.HOST;
import static io.github.kaifox.gsi.demo.client.conf.Constants.HTTP_PORT;
import static org.minifx.workbench.domain.PerspectivePos.CENTER;
import static org.minifx.workbench.domain.PerspectivePos.RIGHT;

@Configuration
public class TestingClientConfiguration {

    @Autowired
    private List<TuneReceiver> receivers;

    private TestControlClient testControlClient = WebfluxTestControlClient.fromLocation(HOST, HTTP_PORT);

    @View(in = TestControlPerspective.class, at = RIGHT, enforceTab = true)
    @Name("settings")
    @Bean
    public TestSettingsView testSettingsView() {
        return new TestSettingsView(testControlClient);
    }

    @View(in = TestControlPerspective.class, at = CENTER, enforceTab = true)
    @Name("speed")
    @Bean
    public ReceptionSpeedView receptionSpeedView() {
        Map<String, Flux<?>> fluxes = receivers.stream().collect(Collectors.toMap(r -> r.name(), r -> r.measuredTunes()));
        return new ReceptionSpeedView(fluxes);
    }


}