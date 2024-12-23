package darwinWorld.model.worldElements.animals;

import darwinWorld.enums.MoveRotation;
import darwinWorld.model.ILocationProvider;
import darwinWorld.model.Vector2d;
import darwinWorld.model.worldElements.animals.geneSelectionStrategies.IGeneSelectionStrategy;

import java.util.List;

public class Animal extends AbstractAnimal {
    private int energy;
    private int daysOfLive = 0;
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

    public int getDaysOfLive() {
        return daysOfLive;
    }

    public int getEnergy() {
        return energy;
    }

    public void setDaysOfLive(int daysOfLive) {
        this.daysOfLive = daysOfLive;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    @Override
    public void eat(int energyFromFood) {
        energy += energyFromFood;
    }

    @Override
    public void move(IGeneSelectionStrategy geneSelectionStrategy, ILocationProvider locationProvider) {
        super.move(geneSelectionStrategy, locationProvider);
        ++daysOfLive;
    }
}
