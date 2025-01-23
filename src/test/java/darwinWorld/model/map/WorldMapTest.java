package darwinWorld.model.map;

import darwinWorld.enums.MoveRotation;
import darwinWorld.model.simulation.Simulation;
import darwinWorld.model.worldElements.Grass;
import darwinWorld.model.worldElements.animals.Animal;
import darwinWorld.model.worldElements.animals.geneSelectionStrategies.SequentialGeneSelectionStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WorldMapTest {

//    @ParameterizedTest
//    @CsvSource({
//            "6, 6, 1, '0,0,6,1', '0,2,6,4', '0,5,6,6'",
//            "10, 10, 0, '0,0,10,4', '0,5,10,5', '0,6,10,10'",
//            "9, 15, 3, '0,0,15,0', '0,1,15,7', '0,8,15,9'",
//            "8, 10, 0, '0,0,10,3', '0,4,10,4', '0,5,10,8'"
//    })
    @Test
    void initializer_WithDefaultSimulation_PlacesRightAmountOfGrass(){
        Simulation simulation = new Simulation();

        WorldMap map = simulation.getMap();
        Map<Vector2d, Grass> grassMap = map.getGrass();

        assertEquals(grassMap.size(), 20);
    }

    @Test
    void initializer_WithDefaultSimulation_PlacesRightAmountOfAnimals(){
        Simulation simulation = new Simulation();

        WorldMap map = simulation.getMap();
        Map<Vector2d, HashSet<Animal>> animalMap = map.getAnimals();
        int count = 0;
        for (HashSet<Animal> animals : animalMap.values()) { count += animals.size(); }

        assertEquals(count, 6);
    }

    @Test
    void getPosition_WithCornerPosition_ReturnsValidPosition(){
        WorldMap map = new WorldMap(new Simulation());

        Boundary eb = map.getEarth().getBoundary();

        Vector2d topLeft = map.getPosition(new Vector2d(eb.lowerLeft().getX()-1,eb.upperRight().getY()+1));
        Vector2d topRight = map.getPosition( new Vector2d(eb.upperRight().getX()+1,eb.upperRight().getY()+1));
        Vector2d bottomLeft = map.getPosition( new Vector2d(eb.upperRight().getX()+1,eb.lowerLeft().getY()-1));
        Vector2d bottomRight = map.getPosition( new Vector2d(eb.lowerLeft().getX()-1,eb.lowerLeft().getY()-1));

        assertEquals(topLeft, new Vector2d(eb.lowerLeft().getX(),eb.upperRight().getY()));
        assertEquals(topRight, new Vector2d(eb.upperRight().getX(),eb.upperRight().getY()));
        assertEquals(bottomLeft, new Vector2d(eb.upperRight().getX(),eb.lowerLeft().getY()));
        assertEquals(bottomRight, new Vector2d(eb.lowerLeft().getX(),eb.lowerLeft().getY()));

    }


    @Test
    void getPosition_WithEdgePosition_ReturnsOppositePosition(){
        WorldMap map = new WorldMap(new Simulation());

        Boundary eb = map.getEarth().getBoundary();

        Vector2d right = map.getPosition( new Vector2d(eb.upperRight().getX() + 1,eb.upperRight().getY()));
        Vector2d left = map.getPosition( new Vector2d(eb.lowerLeft().getX()-1,eb.lowerLeft().getY()));

        assertEquals(right, new Vector2d(eb.lowerLeft().getX(),eb.upperRight().getY()));
        assertEquals(left, new Vector2d(eb.upperRight().getX(),eb.lowerLeft().getY()));

    }

    @ParameterizedTest
    @CsvSource({
            "0,180",
            "315, 135",
            "45, 225"
    })
    void getRotation_WithTopEdge_ReturnsOppositeRotation(int rotation,int correctRotation){
        MoveRotation moveRotation = MoveRotation.getMoveRotation(rotation);
        WorldMap map = new WorldMap(new Simulation());
        Boundary eb = map.getEarth().getBoundary();

        Vector2d top = new Vector2d((eb.upperRight().getX()-eb.lowerLeft().getX())/2, eb.upperRight().getY() + 1);

        MoveRotation newRotation = map.getRotation(top,moveRotation);


        assertEquals(MoveRotation.getMoveRotation(correctRotation), newRotation);
    }

    @ParameterizedTest
    @CsvSource({
            "180, 0",
            "90, 270",
            "135, 315"
    })
    void getRotation_WithBottomEdge_ReturnsOppositeRotation(int rotation, int correctRotation){
        MoveRotation moveRotation = MoveRotation.getMoveRotation(rotation);
        WorldMap map = new WorldMap(new Simulation());
        Boundary eb = map.getEarth().getBoundary();

        Vector2d bottom = new Vector2d((eb.upperRight().getX()-eb.lowerLeft().getX())/2, eb.lowerLeft().getY() - 1);

        MoveRotation newRotation = map.getRotation(bottom, moveRotation);


        assertEquals(MoveRotation.getMoveRotation(correctRotation), newRotation);
    }
}