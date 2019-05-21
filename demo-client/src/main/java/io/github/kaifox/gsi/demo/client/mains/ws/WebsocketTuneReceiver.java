package io.github.kaifox.gsi.demo.client.mains.ws;

import io.github.kaifox.gsi.demo.client.api.TuneReceiver;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;
import reactor.core.scheduler.Schedulers;

import static io.github.ossgang.properties.core.JsonConversions.defaultDeserialization;
import static java.util.Objects.requireNonNull;

public class WebsocketTuneReceiver implements TuneReceiver {

    private final WebSocketClient wsClient = new StandardWebSocketClient();

    private final String location;

    private WebsocketTuneReceiver(String host, int port) {
        requireNonNull(host, "host must not be null");
        location = host + ":" + port;
    }

    public static WebsocketTuneReceiver fromLocation(String host, int port) {
        return new WebsocketTuneReceiver(host, port);
    }

    @Override
    public Flux<Tune> measuredTunes() {
        StringFluxWsHandler handler = new StringFluxWsHandler();
        wsClient.doHandshake(handler, "ws://" + location + "/ws/measuredTunes");
        // set message size limits to 1 MB ?
        return handler.flux()
                .map(v -> defaultDeserialization(v, Tune.class));
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
