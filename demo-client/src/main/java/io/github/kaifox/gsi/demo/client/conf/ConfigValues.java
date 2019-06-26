package io.github.kaifox.gsi.demo.client.conf;

public final class ConfigValues {

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
            return HTTP_PORT;
        } else {
            return Integer.valueOf(port);
        }
    }
}
