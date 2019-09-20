package io.github.kaifox.gsi.demo.client.mains.rest;

import org.minifx.workbench.MiniFx;

import io.github.kaifox.gsi.demo.client.conf.CommonGuiConfiguration;
import io.github.kaifox.gsi.demo.client.mains.testing.TestingClientConfiguration;

public class WebfluxClientMain {

    public static void main(String... args) {
        MiniFx.launcher(WebfluxClientConfiguration.class, CommonGuiConfiguration.class, TestingClientConfiguration.class).launch(args);
    }

}
