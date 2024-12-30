package darwinWorld.model.worldElements.animals;

import darwinWorld.enums.MoveRotation;
import darwinWorld.model.ILocationProvider;
import darwinWorld.model.Vector2d;
import darwinWorld.model.worldElements.animals.geneSelectionStrategies.IGeneSelectionStrategy;

import java.util.List;

public class Animal extends AbstractAnimal {
    private int energy;
    private int daysOfLife = 0;
    private int numberOfChildren = 0;

    @Override
    public String toString() {
        return "A";
    }

    public Animal(
            Vector2d position,
            MoveRotation rotation,
            List<MoveRotation> genes,
            int geneCurrentIndex,
            int energy
    ) {
        super(
                position,
                rotation,
                genes,
                geneCurrentIndex
        );
        this.energy = energy;
    }

    public int getDaysOfLife() {
        return daysOfLife;
    }

    public int getEnergy() {
        return energy;
    }

    public void setDaysOfLife(int daysOfLive) {
        this.daysOfLife = daysOfLife;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void afterReproduce(int energyUsedToReproduce) {
        this.energy -= energyUsedToReproduce;
        ++numberOfChildren;
    }

    @Override
    public void eat(int energyFromFood) {
        energy += energyFromFood;
    }

    @Override
    public void move(IGeneSelectionStrategy geneSelectionStrategy, ILocationProvider locationProvider) {
        super.move(geneSelectionStrategy, locationProvider);
        energy--;
        ++daysOfLife;
    }
}
