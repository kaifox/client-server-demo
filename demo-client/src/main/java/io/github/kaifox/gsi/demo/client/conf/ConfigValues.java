package io.github.kaifox.gsi.demo.client.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConfigValues {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigValues.class);

    public static final String HOST = "localhost";
    private static final int HTTP_PORT = 8080;
    public final static int GRPC_PORT = 5252;
    public static final int ONE_MEGABYTE = 1 << 20;
    public static final int ONE_GIGABYTE = 1 << 30;

    private ConfigValues() {
        /* only static constants */
    }

    public static int httpPort() {
        String port = System.getProperty("server.port");
        if (port == null) {
            LOGGER.info("Property 'server.port' not set. Returning default port (" + HTTP_PORT + ")");
            return HTTP_PORT;
        } else {
            return Integer.valueOf(port);
        }
    }
}
