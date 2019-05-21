package io.github.kaifox.gsi.demo.server.mains.properties;

import cern.lhc.commons.web.PropertiesServiceConfiguration;
import io.github.kaifox.gsi.demo.server.conf.TuneBaseConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({TuneBaseConfiguration.class, PropertiesServiceConfiguration.class})
public class RestPropertiesServerMain {

    public static void main(String... args) {
        SpringApplication.run(RestPropertiesServerMain.class, args);
    }

}
