package io.github.kaifox.gsi.demo.client.mains.restws.components;

import io.github.kaifox.gsi.demo.calc.chroma.simulate.ChromaSimulator;
import io.github.kaifox.gsi.demo.calc.chroma.simulate.PublicationSimulator;
import io.github.kaifox.gsi.demo.commons.domain.Tune;
import io.github.ossgang.properties.core.JsonConversions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Autowired
    private PublicationSimulator<Tune> publicationSimulator;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new TuneWebSocketHandler(), "/ws/measuredTunes");
    }

    private class TuneWebSocketHandler extends TextWebSocketHandler {
        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            publicationSimulator.flux()
                    .map(JsonConversions::defaultSerialization)
                    .subscribe(v -> sendMessage(session, v));
        }
    }

    private static final void sendMessage(WebSocketSession session, String value) {
        try {
            session.sendMessage(new TextMessage(value));
        } catch (IOException e) {
            throw new RuntimeException("unable to send message", e);
        }
    }

}
