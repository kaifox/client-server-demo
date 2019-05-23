package io.github.kaifox.gsi.demo.client.conf;

public final class Constants {

    public static final String HOST = "localhost";
    public static final int HTTP_PORT = 8080;
    public final static int GRPC_PORT = 5252;
    public static final int ONE_MEGABYTE = 1 << 20;
    public static final int ONE_GIGABYTE = 1 << 30;

    private Constants() {
        /* only static constants */
    }
}
