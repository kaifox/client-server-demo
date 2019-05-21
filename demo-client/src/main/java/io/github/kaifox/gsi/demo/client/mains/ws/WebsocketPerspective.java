package io.github.kaifox.gsi.demo.client.mains.ws;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.domain.Perspective;
import org.springframework.core.annotation.Order;

@Name("WebSocket")
@Icon(value = FontAwesomeIcon.TRAIN, color = "orange")
@Order(2)
public interface WebsocketPerspective extends Perspective {
}
