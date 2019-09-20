package io.github.kaifox.gsi.demo.client.mains.rest;

import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.domain.Perspective;
import org.springframework.core.annotation.Order;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

@Name("WebFlux")
@Icon(value = FontAwesomeIcon.CAB, color = "brown")
@Order(1)
public interface WebfluxPerspective extends Perspective {
}
