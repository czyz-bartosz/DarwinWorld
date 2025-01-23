package darwinWorld.model.map;

import darwinWorld.enums.MoveRotation;
import darwinWorld.model.simulation.Simulation;
import darwinWorld.model.simulation.parameters.SimulationParameters;
import darwinWorld.model.worldElements.Grass;
import darwinWorld.model.worldElements.IWorldElement;
import darwinWorld.model.worldElements.animals.Animal;
import darwinWorld.model.worldElements.animals.owlBear.OwlBear;
import darwinWorld.model.worldElements.animals.owlBear.OwlBearTerritory;
import darwinWorld.model.worldElements.animals.geneSelectionStrategies.CrazyGeneSelectionStrategy;
import darwinWorld.model.worldElements.animals.geneSelectionStrategies.IGeneSelectionStrategy;
import darwinWorld.model.worldElements.animals.geneSelectionStrategies.SequentialGeneSelectionStrategy;
import darwinWorld.utils.GeneGenerator;
import darwinWorld.utils.MapVisualizer;
import darwinWorld.utils.RandomNumberGenerator;

import java.util.*;

public class WorldMap implements ILocationProvider {

    private final Map<Vector2d, HashSet<Animal>> animals = new HashMap<>();
    private final Map<Vector2d, Grass> grass = new HashMap<>();
    private OwlBear owlBear;
    private final IGeneSelectionStrategy geneSelectionStrategy;
    private final Simulation simulation;

    private final Earth earth;
    private final MapActions mapActions;

    private final MapVisualizer visualizer = new MapVisualizer(this);

    public WorldMap(Simulation simulation) {
        this.simulation = simulation;
        SimulationParameters sp = simulation.getParameters();
        mapActions = new MapActions(this, simulation);

        if (sp.crazyMutation()) geneSelectionStrategy = new CrazyGeneSelectionStrategy();
        else geneSelectionStrategy = new SequentialGeneSelectionStrategy();

        this.earth = new Earth(sp.mapHeight(), sp.mapWidth(), sp.equatorSpan());

        GeneGenerator geneGenerator = new GeneGenerator();

        if (sp.withOwlBear()) {
            OwlBearTerritory generator = new OwlBearTerritory();
            List<Boundary> territory = generator.territory(earth.getBoundary());

            owlBear = new OwlBear(
                    generator.randomPosition(territory),
                    MoveRotation.randomMoveRotation(),
                    geneGenerator.forLength(sp.genesLength()),
                    0,
                    territory
            );
        }
        mapActions.placeGrass(sp.initialGrassAmount());

        for (int i = 0; i < sp.initialAnimalAmount(); i++) {
            Animal animal = new Animal(
                    earth.randomPosition(),
                    MoveRotation.randomMoveRotation(),
                    geneGenerator.forLength(sp.genesLength()),
                    RandomNumberGenerator.integerFromRange(0, sp.genesLength()-1),
                    sp.initialAnimalEnergy());

            mapActions.placeAnimal(animal);
        }
    }
    public Map<Vector2d, HashSet<Animal>> getAnimals() { return animals; }
    public IGeneSelectionStrategy getGeneSelectionStrategy() { return geneSelectionStrategy; }
    public Map<Vector2d, Grass> getGrass() { return grass; }
    public Optional<OwlBear> getOwlBear() { return Optional.ofNullable(owlBear); }
    public Earth getEarth() { return earth; }

    public void step(){
        mapActions.removeDeadAnimals();
        mapActions.moveAnimals();
        mapActions.eatCycle();
        mapActions.reproduceAnimals();
        mapActions.placeGrass();
    }

    private int amountOfObjectsAt(Vector2d position){
        int amount = 0;

        HashSet<Animal> animalSet = animals.get(position);
        if(animalSet != null) amount = animalSet.size();

        Grass grass = this.grass.get(position);
        if(grass != null) amount += 1;

        if(owlBear != null && owlBear.getPosition().equals(position)) amount += 1;

        return amount;
    }

    public String representationAt(Vector2d position) {

        int amount = amountOfObjectsAt(position);

        if(amount > 1){
            if(amount > 9) return "^";
            return String.valueOf(amountOfObjectsAt(position));
        }
        if(grass.get(position) != null) return grass.get(position).toString();
        if(owlBear != null && owlBear.getPosition().equals(position)) return owlBear.toString();
        Animal animal = animals.get(position).iterator().next();
        return animal.toString();
    }

    public Collection<IWorldElement> objectsAt(Vector2d position){
        ArrayList<IWorldElement> objects = new ArrayList<>();

        if(owlBear != null && owlBear.getPosition().equals(position))objects.add(owlBear);
        if(grass.get(position) != null) objects.add(grass.get(position));
        if(animals.get(position) != null) objects.addAll(animals.get(position));

        if(objects.isEmpty()) return null;
        return objects;
    }

    public Collection<IWorldElement> getElements() {
        //Might be useful later

        List<IWorldElement> elements = new ArrayList<>(grass.values());
        for (HashSet<Animal> animalHash : animals.values())
            elements.addAll(animalHash);
        if (owlBear != null) elements.add(owlBear);

        return elements;
    }

    public boolean isOccupied(Vector2d position){
        return objectsAt(position) != null;
    }

    public Vector2d getPosition(Vector2d oldPosition) {
        Boundary mapBoundary = earth.getBoundary();

        //Contains within boundaries, no changes necessary
        if(mapBoundary.contains(oldPosition)) return oldPosition;

        //Corners, position gets assigned to a corner
        if(oldPosition.getX() - 1 == mapBoundary.upperRight().getX() && oldPosition.getY() - 1 == mapBoundary.upperRight().getY())
            return new Vector2d(oldPosition.getX() - 1, oldPosition.getY() - 1);

        if(oldPosition.getX() - 1 == mapBoundary.upperRight().getX() && oldPosition.getY() ==  -1)
            return new Vector2d(oldPosition.getX() - 1, 0);

        if(oldPosition.getX() == -1 && oldPosition.getY() - 1 == mapBoundary.upperRight().getY())
            return new Vector2d(0, oldPosition.getY() - 1);

        if(oldPosition.getX()  == -1 && oldPosition.getY() == -1)
            return new Vector2d(0, 0);

        //Left or right edge, gets assigned to an opposite side
        if(oldPosition.getX() > mapBoundary.upperRight().getX())
            return new Vector2d(0, oldPosition.getY());

        if(oldPosition.getX() == -1)
            return new Vector2d(mapBoundary.upperRight().getX(), oldPosition.getY());

        if(oldPosition.getY() > mapBoundary.upperRight().getY()) {
            return new Vector2d(oldPosition.getX(), mapBoundary.upperRight().getY());
        }

        if(oldPosition.getY() < mapBoundary.lowerLeft().getY()) {
            return new Vector2d(oldPosition.getX(), mapBoundary.lowerLeft().getY());
        }

        return oldPosition;
    }

    public MoveRotation getRotation(Vector2d oldPosition, MoveRotation rotation) {
        Boundary mapBoundary = earth.getBoundary();

        //Contains within boundaries, no changes necessary
        if (mapBoundary.contains(oldPosition)) return rotation;

        //Corners, position gets assigned opposite rotation
        if (oldPosition.getX() + 1 == mapBoundary.upperRight().getX() && oldPosition.getY() + 1 == mapBoundary.upperRight().getY())
            return MoveRotation.DEG_225;

        if (oldPosition.getX() + 1 == mapBoundary.upperRight().getX() && oldPosition.getY() == -1)
            return MoveRotation.DEG_315;

        if (oldPosition.getX() == -1 && oldPosition.getY() + 1 == mapBoundary.upperRight().getY() + 1)
            return MoveRotation.DEG_135;

        if (oldPosition.getX() == -1 && oldPosition.getY() == -1)
            return MoveRotation.DEG_45;

        //Top or Bottom, gets assigned opposite rotation
        if (oldPosition.getY() > mapBoundary.upperRight().getY())
            return MoveRotation.DEG_90;
        if (oldPosition.getX() < 0)
            return MoveRotation.DEG_0;

        //Left or Right side, rotation remains the same
        return rotation;

    }

    public String toString(){
        Boundary mapBoundary = earth.getBoundary();
        return visualizer.draw(mapBoundary.lowerLeft(),mapBoundary.upperRight());
    }
}
