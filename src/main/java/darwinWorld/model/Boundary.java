package darwinWorld.model;

import darwinWorld.enums.MoveRotation;
import darwinWorld.utils.RandomNumberGenerator;

public record Boundary(
        Vector2d lowerLeft,
        Vector2d upperRight
) {
    public Vector2d randomPosition(){
        int x = RandomNumberGenerator.integerFromRange(lowerLeft.getX(), upperRight.getX());
        int y = RandomNumberGenerator.integerFromRange(lowerLeft.getY(), upperRight.getY());
        return new Vector2d(x, y);
    }

    private Vector2d randomAdherentPosition(Vector2d oldPosition) {
        MoveRotation randomRotation = MoveRotation.randomMoveRotation();

        Vector2d potentialNewPosition = oldPosition.add(randomRotation.toVector());

        while(!contains(potentialNewPosition)) {
            randomRotation = MoveRotation.randomMoveRotation();
            potentialNewPosition = oldPosition.add(randomRotation.toVector());
        }
        return potentialNewPosition;
    }

    public boolean contains( Vector2d position){
        return position.precedes(upperRight) && position.follows(lowerLeft);
    }

    public Vector2d randomUnoccupiedPosition(WorldMap map){
        Vector2d potenitalPosition = randomPosition();

        while(map.isOccupied(potenitalPosition))
            potenitalPosition = randomAdherentPosition(potenitalPosition);

        return potenitalPosition;
    }
}
