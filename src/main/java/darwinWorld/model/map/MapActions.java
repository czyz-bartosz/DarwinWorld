package darwinWorld.model.map;

import darwinWorld.model.simulation.parameters.SimulationParameters;
import darwinWorld.model.worldElements.Grass;
import darwinWorld.model.worldElements.animals.Animal;
import darwinWorld.model.worldElements.animals.AnimalReproduction;
import darwinWorld.model.worldElements.animals.geneSelectionStrategies.IGeneSelectionStrategy;
import darwinWorld.utils.NoPositonException;

import java.util.*;

public class MapActions {

    private final SimulationParameters simulationParameters;
    private final WorldMap map;


    public MapActions(WorldMap map, SimulationParameters simulationParameters) {
        this.map = map;
        this.simulationParameters = simulationParameters;
    }

    public void placeAnimal(Animal animal){
        HashSet<Animal> animalSet = map.getAnimals().get(animal.getPosition());
        if (animalSet == null) animalSet = new HashSet<>();
        animalSet.add(animal);
        map.getAnimals().put(animal.getPosition(), animalSet);
    }

    public void placeGrass(){
        placeGrass(simulationParameters.grassGrowthPerDay());
    }

    public void placeGrass(int amount){

        for(int i = 0; i < amount; i++){
            Vector2d position = null;
            try {
                position = map.getEarth().randomUnoccupiedPositionWithEquatorFavored(map);
            } catch (NoPositonException e) {
                continue;
            }
            map.getGrass().put(position, new Grass(position));
        }
    }

    public void eatCycle(){

        //Owl bear eats all animals on his tile
        if(simulationParameters.withOwlBear() &&  map.getAnimals().get(map.getOwlBear().getPosition()) != null){
            for(Animal animal : map.getAnimals().get(map.getOwlBear().getPosition()))
                animal.setEnergy(-1); //placeholder for being eaten by an owl bear
            map.getAnimals().remove(map.getOwlBear().getPosition());
        }

        for(var entry : map.getAnimals().entrySet()) {
            Vector2d position = entry.getKey();
            Grass grass = this.map.getGrass().get(position);
            if(grass == null) continue;

            HashSet<Animal> animalSet = map.getAnimals().get(position);
            Optional<Animal> strongestOptionalAnimal = animalSet.stream()
                    .max(Comparator.comparingInt(Animal::getEnergy)
                            .thenComparing(Animal::getDaysOfLife)
                            .thenComparing(Animal::getNumberOfChildren));

            Animal strongestAnimal = strongestOptionalAnimal.orElse(null);
            if(strongestAnimal == null) continue;

            strongestAnimal.setEnergy(strongestAnimal.getEnergy() + this.simulationParameters.grassEnergy());
            this.map.getGrass().remove(position);
        }

    }

    public void reproduceAnimals(){
        int requiredEnergy = simulationParameters.minimalEnergyToReproduce();

        for(HashSet<Animal> animalSet : map.getAnimals().values()) {
            if(animalSet.size() <= 1) continue;

            List<Animal> animalsToReproduce = animalSet.stream()
                    .filter(animal -> animal.getEnergy() >= requiredEnergy)
                    .sorted(Comparator.comparingInt(Animal::getEnergy).reversed()
                            .thenComparing(Animal::getDaysOfLife, Comparator.reverseOrder())
                            .thenComparing(Animal::getNumberOfChildren, Comparator.reverseOrder()))
                    .limit(2)
                    .toList();

            if(animalsToReproduce.size() <= 1) continue;

            Animal child = AnimalReproduction.reproduce(
                    animalsToReproduce.get(0),
                    animalsToReproduce.get(1),
                    simulationParameters.energyUsedToReproduce(),
                    simulationParameters.maxMutations(),
                    simulationParameters.minMutations()
            );

            animalSet.add(child);
        }
    }

    public void moveAnimals() {
        if(simulationParameters.withOwlBear()) map.getOwlBear().move(map.getGeneSelectionStrategy(),map);

        List<Animal> allAnimals = map.getAnimals().values().stream()
                .flatMap(Set::stream)
                .toList();

        for(Animal animal: allAnimals){
            Vector2d oldPosition = animal.getPosition();
            animal.move(map.getGeneSelectionStrategy(),map);
            if(oldPosition.equals(animal.getPosition())) continue;
            map.getAnimals().get(oldPosition).remove(animal);
            placeAnimal(animal);
        }
    }

    public void removeDeadAnimals(){

        List<Animal> allAnimals = map.getAnimals().values().stream()
                .flatMap(Set::stream)
                .toList();

        for(Animal animal : allAnimals) {
            if(animal.getEnergy() > 0) continue;
            map.getAnimals().get(animal.getPosition()).remove(animal);
        }
    }
}
