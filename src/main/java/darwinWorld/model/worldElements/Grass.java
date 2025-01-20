package darwinWorld.model.worldElements;

import darwinWorld.model.map.Vector2d;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;

public class Grass implements IWorldElement{
    private final Vector2d position;
    public Grass(Vector2d position) {
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public Pane getGraphicalRepresentation() {
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: green");
        return pane;
    }
}
