package io.github.kaifox.gsi.demo.client.mains.all;

import io.github.kaifox.gsi.demo.client.conf.CommonGuiConfiguration;
import io.github.kaifox.gsi.demo.client.mains.grpc.GrpcClientConfiguration;
import io.github.kaifox.gsi.demo.client.mains.rest.WebfluxClientConfiguration;
import io.github.kaifox.gsi.demo.client.mains.ws.WebsocketClientConfiguration;
import org.minifx.workbench.MiniFx;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

public class AllClientMain {

    public static void main(String... args) {
        MiniFx.launcher(GrpcClientConfiguration.class, WebfluxClientConfiguration.class, WebsocketClientConfiguration.class, CommonGuiConfiguration.class).launch(args);
    }
}
