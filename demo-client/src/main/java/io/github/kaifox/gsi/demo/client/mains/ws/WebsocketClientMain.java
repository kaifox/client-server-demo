package io.github.kaifox.gsi.demo.client.mains.ws;

import org.minifx.workbench.MiniFx;

import io.github.kaifox.gsi.demo.client.conf.CommonGuiConfiguration;
import io.github.kaifox.gsi.demo.client.mains.testing.TestingClientConfiguration;

public class WebsocketClientMain {

    public static void main(String... args) {
        MiniFx.launcher(WebsocketClientConfiguration.class, CommonGuiConfiguration.class, TestingClientConfiguration.class).launch(args);
    }

}
