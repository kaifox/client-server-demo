package io.github.kaifox.gsi.demo.client.mains.properties;

import io.github.kaifox.gsi.demo.client.conf.CommonGuiConfiguration;
import org.minifx.workbench.MiniFx;

public class RestPropertiesClientMain {

    public static void main(String... args) {
        MiniFx.launcher(RestPropertiesClient.class, CommonGuiConfiguration.class).launch(args);
    }

}
