package io.github.kaifox.gsi.demo.client.mains.rest;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.domain.Perspective;
import org.springframework.core.annotation.Order;

@Name("WebFlux")
@Icon(value = FontAwesomeIcon.CAB, color = "brown")
@Order(1)
public interface WebfluxPerspective extends Perspective {
}
