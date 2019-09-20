package io.github.kaifox.gsi.demo.server.conf.grpc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.ForwardingServerCall.SimpleForwardingServerCall;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;


/**
 * A interceptor to handle server header.
 */
public class HeaderServerInterceptor implements ServerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(HeaderServerInterceptor.class.getName());

    static final Metadata.Key<String> ACCESS_CONTROL_ALLOW_ORIGIN_KEY =
            Metadata.Key.of("Access-Control-Allow-Origin", Metadata.ASCII_STRING_MARSHALLER);


    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            final Metadata requestHeaders,
            ServerCallHandler<ReqT, RespT> next) {
        logger.info("header received from client:" + requestHeaders);
        return next.startCall(new SimpleForwardingServerCall<ReqT, RespT>(call) {
            @Override
            public void sendHeaders(Metadata responseHeaders) {
                responseHeaders.put(ACCESS_CONTROL_ALLOW_ORIGIN_KEY, "*");
                super.sendHeaders(responseHeaders);
            }
        }, requestHeaders);
    }
}

