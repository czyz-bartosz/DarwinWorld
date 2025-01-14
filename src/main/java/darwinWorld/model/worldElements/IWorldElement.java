package darwinWorld.model.worldElements;

import darwinWorld.model.map.Vector2d;
import javafx.scene.layout.Pane;

public interface IWorldElement {
    Vector2d getPosition();

    Pane getGraphicalRepresentation();
}
