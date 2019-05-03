package io.github.kaifox.gsi.demo.mains.restws;

import io.github.kaifox.gsi.demo.commons.domain.Tune;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;
import reactor.core.scheduler.Schedulers;

import static io.github.ossgang.properties.core.JsonConversions.defaultDeserialization;

public class WebfluxTuneClient implements TuneClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebfluxTuneClient.class);

    private static final String BASE_URI = "localhost:8080";

    private final WebClient client = WebClient.create("http://" + BASE_URI);
    private final WebSocketClient wsClient = new StandardWebSocketClient();

    @Override
    public Tune measuredTune() {
        return client.get()
                .uri("/api/measuredTune")
                .retrieve()
                .bodyToMono(Tune.class)
                .block();
    }

    @Override
    public Flux<Tune> measuredTunes() {
        return client.get()
                .uri("/api/measuredTunes")
                .retrieve()
                .bodyToFlux(Tune.class);
    }

    @Override
    public Flux<Tune> wsMeasuredTunes() {
        StringFluxWsHandler handler = new StringFluxWsHandler();
        wsClient.doHandshake(handler, "ws://" + BASE_URI + "/ws/measuredTunes");
        // set message size limits to 1 MB ?
        return handler.flux()
                .map(v -> defaultDeserialization(v, Tune.class));
    }


    @Override
    public void setStandardDev(double standardDev) {
        client.post()
                .uri("/api/standardDev/" + standardDev)
                .exchange()
                .block();
    }

    @Override
    public double getStandardDev() {
        return client.get()
                .uri("/api/standardDev")
                .retrieve()
                .bodyToMono(Double.class)
                .block();
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
