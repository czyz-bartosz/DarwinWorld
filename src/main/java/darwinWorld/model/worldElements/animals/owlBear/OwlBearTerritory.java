package darwinWorld.model.worldElements.animals.owlBear;

import darwinWorld.model.map.Boundary;
import darwinWorld.model.map.Vector2d;
import darwinWorld.utils.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.List;

public class OwlBearTerritory {

    public List<Boundary> territory(Boundary earthBoundary) {
        List<Boundary> territory = new ArrayList<Boundary>();

        int size = (int) (earthBoundary.area()* 0.2);

        int sideLength = (int)Math.sqrt(size);

        Boundary lowerLeftPositionBoundary = new Boundary(
                earthBoundary.lowerLeft(),
                new Vector2d(earthBoundary.upperRight().getX(),earthBoundary.upperRight().getY() - sideLength));

        Vector2d lowerLeftPosition = lowerLeftPositionBoundary.randomPosition();

        Vector2d upperRightPosition = new Vector2d (lowerLeftPosition.getX() + sideLength, lowerLeftPosition.getY() + sideLength);

        if(earthBoundary.contains(upperRightPosition)){
            territory.add(new Boundary(lowerLeftPosition,upperRightPosition));
            return territory;
        }

        Vector2d cutUpperRightPosition = new Vector2d (earthBoundary.upperRight().getX(), lowerLeftPosition.getY() + sideLength);
        Boundary territoryFragment1 = new Boundary(lowerLeftPosition,cutUpperRightPosition);

        Vector2d lowerLeftPosition1 = new Vector2d (0,lowerLeftPosition.getY());
        Vector2d upperRightPosition1 = new Vector2d (
                upperRightPosition.getX() % (earthBoundary.upperRight().getX() - earthBoundary.lowerLeft().getX() + 1),
                upperRightPosition.getY() + sideLength);
        Boundary territoryFragment2 = new Boundary(lowerLeftPosition1,upperRightPosition1);

        territory.add(territoryFragment1);
        territory.add(territoryFragment2);
        return territory;
    }

    public Vector2d randomPosition(List<Boundary> territory){
        if(territory.size() == 1){ return territory.getFirst().randomPosition();}

        int area0 = territory.getFirst().area();
        int area1 = territory.get(1).area();

        double random = RandomNumberGenerator.randomDouble();

        if(random < ((double) area0)/(area0 + area1))
            return territory.getFirst().randomPosition();

        return territory.get(1).randomPosition();
    }
}
