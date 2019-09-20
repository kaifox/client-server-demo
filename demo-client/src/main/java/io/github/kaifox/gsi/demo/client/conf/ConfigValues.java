package io.github.kaifox.gsi.demo.client.conf;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConfigValues {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigValues.class);

    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_HTTP_PORT = 8080;
    private static final int DEFAULT_GRPC_PORT = 5252;
    public static final int ONE_MEGABYTE = 1 << 20;
    public static final int ONE_GIGABYTE = 1 << 30;

    private ConfigValues() {
        /* only static constants */
    }

    public static int httpPort() {
        return propertyValueOrDefault("server.port", Integer::valueOf, DEFAULT_HTTP_PORT);
    }


    public static String host() {
        return propertyValueOrDefault("server.host", Function.identity(), DEFAULT_HOST);
    }

    public static int grpcPort() {
        return propertyValueOrDefault("grpc.port", Integer::valueOf, DEFAULT_GRPC_PORT);
    }

    private static <T> T propertyValueOrDefault(String propertyName, Function<String, T> conversion, T defaultValue) {
        String propertyValue = System.getProperty(propertyName);
        if (propertyValue == null) {
            LOGGER.info("Property '" + propertyName + "' not set. Returning default propertyValue (" + defaultValue + ")");
            return defaultValue;
        } else {
            T value = conversion.apply(propertyValue);
            LOGGER.info("Property '" + propertyName + "' was set. Using the value '" + value + "'");
            return value;
        }
    }

}
