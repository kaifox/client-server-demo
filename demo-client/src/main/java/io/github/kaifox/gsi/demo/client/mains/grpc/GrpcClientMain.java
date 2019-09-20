package io.github.kaifox.gsi.demo.client.mains.grpc;

import org.minifx.workbench.MiniFx;

import io.github.kaifox.gsi.demo.client.conf.CommonGuiConfiguration;
import io.github.kaifox.gsi.demo.client.mains.testing.TestingClientConfiguration;

public class GrpcClientMain {

    public static void main(String... args) {
        MiniFx.launcher(GrpcClientConfiguration.class, CommonGuiConfiguration.class, TestingClientConfiguration.class).launch(args);
    }


}
