package io.github.kaifox.gsi.demo.client.mains.ws;

import io.github.kaifox.gsi.demo.client.conf.CommonGuiConfiguration;
import io.github.kaifox.gsi.demo.client.mains.restws.WebfluxClientConfiguration;
import org.minifx.workbench.MiniFx;

public class WebsocketClientMain {

    public static void main(String... args) {
        MiniFx.launcher(WebsocketClientConfiguration.class, CommonGuiConfiguration.class).launch(args);
    }

}
