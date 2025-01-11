package darwinWorld.model.map;

import darwinWorld.enums.MoveRotation;

public interface ILocationProvider {
    Vector2d getPosition(Vector2d position);
    MoveRotation getRotation(Vector2d position, MoveRotation moveRotation);
}
