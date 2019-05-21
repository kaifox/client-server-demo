package io.github.kaifox.gsi.demo.client.mains.properties;

import cern.lhc.commons.web.property.PropertyRestController;
import cern.lhc.commons.web.property.PropertyWebsocketConfiguration;
import io.github.kaifox.gsi.demo.client.mains.conf.TuneBaseConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({TuneBaseConfiguration.class, PropertyRestController.class, PropertyWebsocketConfiguration.class})
public class RestPropertiesServer {

    public static void main(String... args) {
        SpringApplication.run(RestPropertiesServer.class, args);
    }

}
