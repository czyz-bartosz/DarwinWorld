package darwinWorld.model.worldElements;

import darwinWorld.model.map.Vector2d;

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
}
