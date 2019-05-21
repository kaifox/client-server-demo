package io.github.kaifox.gsi.demo.client.mains.grpc;

import io.github.kaifox.gsi.demo.client.conf.CommonGuiConfiguration;
import org.minifx.workbench.MiniFx;

public class GrpcClientMain {

    public static void main(String... args) {
        MiniFx.launcher(GrpcClientConfiguration.class, CommonGuiConfiguration.class).launch(args);
    }


}
