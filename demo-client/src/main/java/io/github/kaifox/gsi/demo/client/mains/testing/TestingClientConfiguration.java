package io.github.kaifox.gsi.demo.client.mains.testing;

import static org.minifx.workbench.domain.PerspectivePos.CENTER;
import static org.minifx.workbench.domain.PerspectivePos.RIGHT;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.annotations.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.github.kaifox.gsi.demo.client.api.TuneReceiver;
import io.github.kaifox.gsi.demo.client.conf.ConfigValues;
import io.github.kaifox.gsi.demo.client.views.BurstSpeedView;
import io.github.kaifox.gsi.demo.client.views.ReceptionSpeedView;
import io.github.kaifox.gsi.demo.client.views.TestingSettingsView;
import reactor.core.publisher.Flux;

@Configuration
@Profile("testing")
public class TestingClientConfiguration {

    @Autowired
    private List<TuneReceiver> receivers;

    private TestControlClient testControlClient = WebfluxTestControlClient.fromLocation(ConfigValues.host(), ConfigValues.httpPort());

    @View(in = TestControlPerspective.class, at = RIGHT, enforceTab = true)
    @Name("settings")
    @Bean
    public TestingSettingsView testSettingsView() {
        return new TestingSettingsView(testControlClient);
    }

    @View(in = TestControlPerspective.class, at = CENTER, enforceTab = true)
    @Name("periodic speed")
    @Bean
    public ReceptionSpeedView receptionSpeedView() {
        return new ReceptionSpeedView(fluxes());
    }

    private Map<String, Flux<?>> fluxes() {
        return receivers.stream().collect(Collectors.toMap(r -> r.name(), r -> r.measuredTunes()));
    }

    @View(in = TestControlPerspective.class, at = CENTER, enforceTab = true)
    @Name("burst speed")
    @Bean
    public BurstSpeedView burstSpeedView() {
        return new BurstSpeedView(testControlClient.burstStartSizes(), fluxes());
    }


}
