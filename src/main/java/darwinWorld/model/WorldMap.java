package darwinWorld.model;

import darwinWorld.enums.MoveRotation;
import darwinWorld.model.simulation.parameters.SimulationParameters;
import darwinWorld.model.worldElements.Grass;
import darwinWorld.model.worldElements.IWorldElement;
import darwinWorld.model.worldElements.animals.Animal;
import darwinWorld.model.worldElements.animals.OwlBear;
import darwinWorld.model.worldElements.animals.AnimalReproduction;
import darwinWorld.model.worldElements.animals.OwlBearTerritory;
import darwinWorld.model.worldElements.animals.geneSelectionStrategies.CrazyGeneSelectionStrategy;
import darwinWorld.model.worldElements.animals.geneSelectionStrategies.IGeneSelectionStrategy;
import darwinWorld.model.worldElements.animals.geneSelectionStrategies.SequentialGeneSelectionStrategy;
import darwinWorld.utils.GeneGenerator;
import darwinWorld.utils.MapVisualizer;
import darwinWorld.utils.NoPositonException;

import java.util.*;

public class WorldMap implements ILocationProvider {

    private final SimulationParameters simulationParameters;
    private Map<Vector2d, List<Animal>> animals = new HashMap<>();
    private final Map<Vector2d, Grass> grass = new HashMap<>();
    private final Earth earth;
    private OwlBear owlBear;
    private final MapVisualizer visualizer = new MapVisualizer(this);

    private final IGeneSelectionStrategy geneSelectionStrategy;
    //TODO: MapChangeListener


    public WorldMap(SimulationParameters sp) {
        //TODO add equador span to parameters

        this.simulationParameters = sp;

        if(sp.crazyMutation()) geneSelectionStrategy = new CrazyGeneSelectionStrategy();
        else geneSelectionStrategy = new SequentialGeneSelectionStrategy();

        this.earth = new Earth(sp.mapHeight(), sp.mapWidth(), sp.mapHeight() / 6);

        GeneGenerator geneGenerator = new GeneGenerator();

        if(sp.withOwlBear()){
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
        this.placeGrass(sp.initialGrassAmount());

        for(int i=0; i<sp.initialAnimalAmount(); i++){
            Animal animal = new Animal(
                    earth.randomPosition(),
                    MoveRotation.randomMoveRotation(),
                    geneGenerator.forLength(sp.genesLength()),
                    0,
                    sp.initialAnimalEnergy());

            placeAnimal(animal);
        }
    }
    public void placeAnimal(Animal animal){
        List<Animal> animalList = animals.get(animal.getPosition());
        if(animalList != null) animalList.add(animal);
        animalList = new ArrayList<>();
        animalList.add(animal);
        animals.put(animal.getPosition(), animalList);
    }

    public void moveOwlBear(){ owlBear.move(geneSelectionStrategy,this);}

    public void moveAnimals() {
        //ugly but works
        owlBear.move(geneSelectionStrategy,this);
        List<Animal> changedPositon = new ArrayList<>();
        List<Vector2d> oldPositionList = new ArrayList<>();

        for (List<Animal> animals : animals.values())
            for (Animal animal : animals) {
                Vector2d oldPosition = animal.getPosition();
                animal.move(geneSelectionStrategy, this);
                Vector2d newPosition = animal.getPosition();
                if(oldPosition.equals(newPosition)) continue;
                changedPositon.add(animal);
                oldPositionList.add(oldPosition);
            }

        for(int i=0; i<changedPositon.size(); i++){
            Animal animal = changedPositon.get(i);
            Vector2d oldPosition = oldPositionList.get(i);

            List <Animal> animalList = animals.get(oldPosition);
            animalList.remove(animal);
            if(animalList.isEmpty()) animals.remove(oldPosition);

            animals.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>());

            animals.get(animal.getPosition()).add(animal);
        }

    }

    public void eatCycle(){
        //Owl bear eats all animals on his tile
        if(simulationParameters.withOwlBear() &&  animals.get(owlBear.getPosition()) != null){
            for(Animal animal : animals.get(owlBear.getPosition()))
                animal.setEnergy(-1); //placeholder for being eaten by an owl bear
            animals.remove(owlBear.getPosition());
        }

        for(var entry : animals.entrySet()) {
            Vector2d position = entry.getKey();
            Grass grass = this.grass.get(position);
            if(grass == null) continue;

            List<Animal> animalList = entry.getValue();
            Optional<Animal> strongestOptionalAnimal = animalList.stream()
                    .max(Comparator.comparingInt(Animal::getEnergy)
                    .thenComparing(Animal::getDaysOfLife)
                    .thenComparing(Animal::getNumberOfChildren));

            Animal strongestAnimal = strongestOptionalAnimal.orElse(null);
            if(strongestAnimal == null) continue;

            strongestAnimal.setEnergy(strongestAnimal.getEnergy() + this.simulationParameters.grassEnergy());
            this.grass.remove(position);
        }

    }

    public void reproduceAnimals(){
        int requiredEnergy = simulationParameters.minimalEnergyToReproduce();


        for(List<Animal> animalList : animals.values()) {
            if(animalList.size() <= 1) continue;

            List<Animal> animalsToReproduce = animalList.stream()
                    .filter((animal -> animal.getEnergy() >= requiredEnergy))
                    .sorted(Comparator.comparingInt(Animal::getEnergy).reversed()
                            .thenComparing(Animal::getDaysOfLife, Comparator.reverseOrder())
                            .thenComparing(Animal::getNumberOfChildren, Comparator.reverseOrder()))
                            .limit(2)
                            .toList();

            //No idea how to elegantly select animals on random when there are more than 2 with comparison parameters equal
            if(animalsToReproduce.size() <= 1) continue;

            Animal child = AnimalReproduction.reproduce(
                    animalsToReproduce.get(0),
                    animalsToReproduce.get(1),
                    simulationParameters.energyUsedToReproduce(),
                    simulationParameters.maxMutations(),
                    simulationParameters.minMutations()
            );

            animalList.add(child);
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
            Vector2d position = null;
            try {
                position = earth.randomUnoccupiedPositionWithEquatorFavored(this);
            } catch (NoPositonException e) {
                continue;
            }
            grass.put(position, new Grass(position));
        }
    }

    private int amountOfObjectsAt(Vector2d position){
        int amount = 0;

        List<Animal> animalList = animals.get(position);
        if(animalList != null) amount = animalList.size();

        Grass grass = this.grass.get(position);
        if(grass != null) amount += 1;

        if(owlBear != null && owlBear.getPosition().equals(position)) amount += 1;

        return amount;
    }

    public String representationAt(Vector2d position){
        //just so map can be looked at

        int amount = amountOfObjectsAt(position);

        if(amount > 1){
            if(amount > 9) return "^";
            return String.valueOf(amountOfObjectsAt(position));
        }
        if(grass.get(position) != null) return grass.get(position).toString();
        if(owlBear != null && owlBear.getPosition().equals(position)) return owlBear.toString();
        List<Animal> animalList = animals.get(position);
        return animalList.getFirst().toString();
    }

    public List<IWorldElement> objectsAt(Vector2d position){
        ArrayList<IWorldElement> objects = new ArrayList<>();

        if(animals.get(position) != null) objects.addAll(animals.get(position));
        if(grass.get(position) != null) objects.add(grass.get(position));
        if(owlBear != null && owlBear.getPosition().equals(position))objects.add(owlBear);

        if(objects.isEmpty()) return null;
        return objects;
    }

    public List<IWorldElement> getElements() {

        List<IWorldElement> elements = new ArrayList<>(grass.values());
        for(List<Animal> animalList: animals.values())
            elements.addAll(animalList);
        if(owlBear != null) elements.add(owlBear);

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

    public String toString(){
        Boundary mapBoundary = earth.getBoundary();
        return visualizer.draw(mapBoundary.lowerLeft(),mapBoundary.upperRight());
    }
}
