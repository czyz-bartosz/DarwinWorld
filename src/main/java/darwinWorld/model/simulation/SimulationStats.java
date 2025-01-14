package darwinWorld.model.simulation;

import darwinWorld.enums.MoveRotation;
import darwinWorld.model.map.Vector2d;
import darwinWorld.model.map.WorldMap;
import darwinWorld.model.map.WorldMapUtils;
import darwinWorld.model.worldElements.animals.AbstractAnimal;
import darwinWorld.model.worldElements.animals.Animal;
import darwinWorld.utils.CSVUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SimulationStats {
    private int sumOfDeadAnimalsLifeSpan = 0;
    private int numberOfDeadAnimals = 0;
    private final Simulation simulation;
    private final String CSVFileName;
    private boolean isCSVFileCreated = false;

    public SimulationStats(Simulation simulation) {
        this.simulation = simulation;
        CSVFileName = "stats-" + simulation.getUuid().toString() + ".csv";
    }

    public int getSumOfDeadAnimalsLifeSpan() {
        return sumOfDeadAnimalsLifeSpan;
    }

    public void addDeadAnimalLifeSpan(int deadAnimalsLifeSpan) {
        sumOfDeadAnimalsLifeSpan += deadAnimalsLifeSpan;
    }

    public void onAnimalDeath(Animal animal) {
        incrementNumberOfDeadAnimals();
        addDeadAnimalLifeSpan(animal.getStats().getNumberOfDaysOfLife());
    }

    public int getNumberOfDeadAnimals() {
        return numberOfDeadAnimals;
    }

    public void incrementNumberOfDeadAnimals() {
        numberOfDeadAnimals++;
    }

    public int getNumberOfAnimals() {
        WorldMap map = simulation.getMap();
        return WorldMapUtils.getCollectionOfAnimals(map).size();
    }

    public int getNumberOfGrass() {
        WorldMap map = simulation.getMap();

        return map.getGrass().size();
    }

    public int getNumberOfFreeCells() {
        WorldMap map = simulation.getMap();

        Collection<Vector2d> occupiedCells = WorldMapUtils.getOccupiedPositions(map);
        int numberOfAllCells = map.getEarth().getBoundary().area();

        return numberOfAllCells - occupiedCells.size();
    }

    public Optional<List<MoveRotation>> getTheMostPopularGenotype() {
        WorldMap map = simulation.getMap();
        Collection<Animal> animals = WorldMapUtils.getCollectionOfAnimals(map);

        return animals
                .stream()
                .map(AbstractAnimal::getGenes)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    public double getAvgEnergy() {
        WorldMap map = simulation.getMap();
        Collection<Animal> animals = WorldMapUtils.getCollectionOfAnimals(map);

        return animals
                .stream()
                .mapToInt(Animal::getEnergy)
                .average()
                .orElse(0.);
    }

    public double getAvgNumberOfChildrenPerAnimal() {
        WorldMap map = simulation.getMap();
        Collection<Animal> animals = WorldMapUtils.getCollectionOfAnimals(map);

        return animals
                .stream()
                .mapToInt((animal) -> animal.getStats().getNumberOfChildren())
                .average()
                .orElse(0.);
    }

    public double getAvgLifeSpan() {
        if(numberOfDeadAnimals == 0) {
            return 0.;
        }
        return (double) sumOfDeadAnimalsLifeSpan / numberOfDeadAnimals;
    }

    public Map<String, String> getStats() {
        return Map.ofEntries(
                Map.entry("numberOfAnimals", Integer.toString(getNumberOfAnimals())),
                Map.entry("numberOfGrass", Integer.toString(getNumberOfGrass())),
                Map.entry("numberOfFreeCells", Integer.toString(getNumberOfFreeCells())),
                Map.entry("theMostPopularGenotype", getTheMostPopularGenotype().toString()),
                Map.entry("avgEnergy", Double.toString(getAvgEnergy())),
                Map.entry("avgAnimalLifeSpan", Double.toString(getAvgLifeSpan())),
                Map.entry("avgNumberOfChildrenPerAnimal", Double.toString(getAvgNumberOfChildrenPerAnimal()))
        );
    }

    private void createCSV() {
        Set<String> columns = getStats().keySet();
        CSVUtil.appendToFile(CSVFileName, columns);
        isCSVFileCreated = true;
    }

    public void saveToCSV() {
        if(!isCSVFileCreated) {
            createCSV();
        }

        Collection<String> data = getStats().values();
        CSVUtil.appendToFile(CSVFileName, data);
    }
}
