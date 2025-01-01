package darwinWorld.model;

import darwinWorld.enums.MoveRotation;
import darwinWorld.utils.NoPositonException;
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

    public Vector2d randomUnoccupiedPosition(WorldMap map) throws NoPositonException {
        // tries sqrt(area) times
        int approachLimit = (int) Math.sqrt(area()) * 10;
        int approach = 0;

        Vector2d potenitalPosition = randomPosition();

        while(map.isOccupied(potenitalPosition) && ++approach <= approachLimit)
            potenitalPosition = randomAdherentPosition(potenitalPosition);

        if(map.isOccupied(potenitalPosition)) throw new NoPositonException("No unoccupied position found");

        return potenitalPosition;
    }

    public int area(){
        int x = upperRight().getX() - lowerLeft().getX() + 1;
        int y = upperRight().getY() - lowerLeft().getY() + 1;
        return x * y;
    }
}
