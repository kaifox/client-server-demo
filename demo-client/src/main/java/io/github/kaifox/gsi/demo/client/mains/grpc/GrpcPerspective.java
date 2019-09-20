package io.github.kaifox.gsi.demo.client.mains.grpc;

import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.domain.Perspective;
import org.springframework.core.annotation.Order;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

@Name("gRPC")
@Icon(value = FontAwesomeIcon.BUS, color = "blue")
@Order(3)
public interface GrpcPerspective extends Perspective {
}
