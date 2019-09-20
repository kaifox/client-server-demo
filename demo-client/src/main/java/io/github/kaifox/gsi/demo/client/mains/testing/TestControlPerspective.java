package io.github.kaifox.gsi.demo.client.mains.testing;

import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.domain.Perspective;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

@Name("Testing")
@Icon(value = FontAwesomeIcon.BATTERY_HALF, color = "green")
public interface TestControlPerspective extends Perspective {
}
