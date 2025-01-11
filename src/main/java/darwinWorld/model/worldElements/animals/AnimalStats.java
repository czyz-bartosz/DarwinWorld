package darwinWorld.model.worldElements.animals;

import java.util.OptionalInt;

public class AnimalStats {
    private int numberOfEatenGrass = 0;
    private int numberOfDaysOfLife = 0;
    private int dayOfDeath = -1;
    private final Animal animal;

    public AnimalStats(Animal animal) {
        this.animal = animal;
    }

    public int getNumberOfEatenGrass() {
        return numberOfEatenGrass;
    }

    public void incrementNumberOfEatenGrass() {
        numberOfEatenGrass++;
    }

    public int getNumberOfChildren() {
        return animal.getChildren().size();
    }

    public int getNumberOfDescendants() {
        return animal.getDescendants().size();
    }

    public int getNumberOfDaysOfLife() {
        return numberOfDaysOfLife;
    }

    public void incrementNumberOfDaysOfLife() {
        numberOfDaysOfLife++;
    }

    public void setDayOfDeath(int dayOfDeath) {
        this.dayOfDeath = dayOfDeath;
    }

    public OptionalInt getDayOfDeath() {
        if(dayOfDeath == -1) {
            return OptionalInt.empty();
        }
        return OptionalInt.of(dayOfDeath);
    }
}
