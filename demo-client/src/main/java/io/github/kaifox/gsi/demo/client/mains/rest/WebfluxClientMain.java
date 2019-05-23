package io.github.kaifox.gsi.demo.client.mains.rest;

import io.github.kaifox.gsi.demo.client.conf.CommonGuiConfiguration;
import io.github.kaifox.gsi.demo.client.mains.testing.TestingClientConfiguration;
import org.minifx.workbench.MiniFx;

public class WebfluxClientMain {

    public static void main(String... args) {
        MiniFx.launcher(WebfluxClientConfiguration.class, CommonGuiConfiguration.class, TestingClientConfiguration.class).launch(args);
    }

}
