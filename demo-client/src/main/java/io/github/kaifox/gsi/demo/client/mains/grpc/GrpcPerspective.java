package io.github.kaifox.gsi.demo.client.mains.grpc;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.domain.Perspective;
import org.springframework.core.annotation.Order;

@Name("gRPC")
@Icon(value = FontAwesomeIcon.BUS, color = "blue")
@Order(3)
public interface GrpcPerspective extends Perspective {
}
