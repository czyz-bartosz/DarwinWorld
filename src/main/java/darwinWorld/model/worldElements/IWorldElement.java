package darwinWorld.model.worldElements;

import darwinWorld.model.map.Vector2d;
import javafx.scene.Node;

public interface IWorldElement {
    Vector2d getPosition();
    Node getGraphicalRepresentation();
}
