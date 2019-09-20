package io.github.kaifox.gsi.demo.client.mains.ws;

import static java.util.Objects.requireNonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import io.github.kaifox.gsi.demo.client.api.TuneReceiver;
import io.github.kaifox.gsi.demo.client.conf.ConfigValues;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import io.github.kaifox.gsi.demo.commons.util.JsonConversions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;
import reactor.core.scheduler.Schedulers;

public class WebsocketTuneReceiver implements TuneReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketTuneReceiver.class);

    private final WebSocketClient wsClient = new StandardWebSocketClient();
    private final StringFluxWsHandler handler = new StringFluxWsHandler();
    private final Flux<Tune> flux;

    private final String location;

    private WebsocketTuneReceiver(String host, int port) {
        requireNonNull(host, "host must not be null");
        location = host + ":" + port;
        connect();
        Flux<Tune> map = handler.flux().publishOn(Schedulers.elastic()).map(v -> JsonConversions.defaultDeserialization(v, Tune.class));
        this.flux = map.share();
    }

    public static WebsocketTuneReceiver fromLocation(String host, int port) {
        return new WebsocketTuneReceiver(host, port);
    }

    @Override
    public String name() {
        return "websocket";
    }

    @Override
    public Flux<Tune> measuredTunes() {
        return this.flux.onBackpressureLatest().publishOn(Schedulers.elastic());
    }

    private void connect() {
        String uri = "ws://" + location + "/ws/measuredTunes";
        ListenableFuture<WebSocketSession> a = wsClient.doHandshake(handler, uri);
        try {
            WebSocketSession session = a.get();
            LOGGER.info("Successfully connected to websocket {} with id {}", uri, session.getId());
            session.setBinaryMessageSizeLimit(ConfigValues.ONE_GIGABYTE);
            session.setTextMessageSizeLimit(ConfigValues.ONE_GIGABYTE);
        } catch (Exception e) {
            LOGGER.error("Cannot reconnect to {}: {}", uri, e.getMessage());
            throw new IllegalStateException("Cannot connect to " + uri, e);
        }
    }


    private class StringFluxWsHandler extends TextWebSocketHandler {
        private final ReplayProcessor<String> sink = ReplayProcessor.cacheLast();
        private final Flux<String> stream = sink.publishOn(Schedulers.elastic());

        public Flux<String> flux() {
            return this.stream;
        }

        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            sink.onNext(message.getPayload());
        }
    }

}
