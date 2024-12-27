package darwinWorld.model;

import darwinWorld.enums.MoveRotation;
import darwinWorld.model.simulation.parameters.SimulationParameters;
import darwinWorld.model.worldElements.Grass;
import darwinWorld.model.worldElements.IWorldElement;
import darwinWorld.model.worldElements.animals.Animal;
import darwinWorld.model.worldElements.animals.OwlBear;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class WorldMap implements ILocationProvider {

    private final SimulationParameters simulationParameters;
    private Map<Vector2d, List<Animal> > animals;
    private Map<Vector2d, Grass> grass;
    private Earth earth;
    private OwlBear owlBear;
    //TODO: MapVisualizer, MapChangeListener


    public WorldMap(SimulationParameters simulationParameters) {
        //TODO
        this.simulationParameters = simulationParameters;
    }

    public void reproduceAnimals(){
        int requiredEnergy = simulationParameters.minimalEnergyToReproduce();

        for(var entry : animals.entrySet()) {
            Vector2d position = entry.getKey();
            List<Animal> animalList = entry.getValue();

            if(animalList.size() == 1) continue;

            List<Animal> animalsToReproduce = animalList.stream()
                    .filter((animal -> animal.getEnergy() >= requiredEnergy))
                    .sorted(Comparator.comparingInt(Animal::getEnergy)
                            .thenComparing(Animal::getDaysOfLife)
                            .thenComparing(Animal::getNumberOfChildren))
                            .limit(2)
                            .toList();

            if(animalsToReproduce.size() == 1) continue;

            //TODO: Reproduce animalsToReproduce[0] with animalsToReproduce[1] on position
        }
    }

    public void removeDeadAnimals(){

        ArrayList<Vector2d> emptyArraysToBeRemoved = new ArrayList<>();

        for(var entry : animals.entrySet()) {
            Vector2d position = entry.getKey();
            List<Animal> animalList = entry.getValue();

            List<Animal> toBeRemoved = new ArrayList<>();

            for (Animal animal : animalList) {
                if (animal.getEnergy() <= 0)
                    toBeRemoved.add(animal);
            }
            animalList.removeAll(toBeRemoved);

            if(animalList.isEmpty())
                emptyArraysToBeRemoved.add(position);
        }

        for(Vector2d position : emptyArraysToBeRemoved)
            animals.remove(position);
    }

    public void placeGrass(int amount){

        for(int i = 0; i < amount; i++){
            Vector2d position = earth.randomUnoccupiedPositionWithEquatorFavored(this);
            grass.put(position, new Grass(position));
        }
    }

    public List<IWorldElement> objectsAt(Vector2d position){
        ArrayList<IWorldElement> objects = new ArrayList<>();

        if(animals.get(position) != null) objects.addAll(animals.get(position));
        if(grass.get(position) != null) objects.add(grass.get(position));
        if(owlBear.getPosition().equals(position))objects.add(owlBear);

        if(objects.isEmpty()) return null;
        return objects;
    }

    public List<IWorldElement> getElements() {

        List<IWorldElement> elements = new ArrayList<>(grass.values());

        for(List<Animal> animalList: animals.values())
            elements.addAll(animalList);

        elements.add(owlBear);

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
        if(oldPosition.getX() + 1 == mapBoundary.upperRight().getX() && oldPosition.getY() + 1 == mapBoundary.upperRight().getY())
            return new Vector2d(oldPosition.getX() - 1, oldPosition.getY() - 1);

        if(oldPosition.getX() + 1 == mapBoundary.upperRight().getX() && oldPosition.getY() ==  -1)
            return new Vector2d(oldPosition.getX() - 1, 0);

        if(oldPosition.getX() == -1 && oldPosition.getY() + 1 == mapBoundary.upperRight().getY() + 1)
            return new Vector2d(0, oldPosition.getY() - 1);

        if(oldPosition.getX()  == -1 && oldPosition.getY() == -1)
            return new Vector2d(0, 0);

        //Left or right edge, gets assigned to an opposite side
        if(oldPosition.getX() > mapBoundary.upperRight().getX())
            return new Vector2d(0, oldPosition.getY());

        if(oldPosition.getX() == -1)
            return new Vector2d(mapBoundary.upperRight().getX(), oldPosition.getY());

        //Top or bottom, position remains the same
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
}
