package darwinWorld.model.worldElements.animals;

import darwinWorld.enums.MoveRotation;
import darwinWorld.model.map.ILocationProvider;
import darwinWorld.model.map.Vector2d;
import darwinWorld.model.worldElements.animals.geneSelectionStrategies.IGeneSelectionStrategy;
import javafx.beans.binding.Bindings;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.*;

public class Animal extends AbstractAnimal {
    private int energy;
    private final AnimalStats stats = new AnimalStats(this);
    private final Collection<Animal> children = new HashSet<>();
    private boolean isDead = false;

    @Override
    public String toString() {
        return "Animal " + position.toString();
    }

    public Pane getGraphicalRepresentation() {
        StackPane pane = new StackPane();
        double energyValue = Math.min(getEnergy() / 20., 1.);

        double hue = energyValue * 120;
        double saturation = 1.0;
        double brightness = 1.0 - energyValue * 0.5;

        Circle circle = new Circle();
        circle.setFill(Color.hsb(hue, saturation, brightness));
        pane.getChildren().add(circle);
        circle.radiusProperty().bind(Bindings.min(
                pane.widthProperty(), pane.heightProperty()).divide(3));
        pane.setStyle("-fx-background-color: transparent;");
        return pane;
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

    public Collection<Animal> getChildren() {
        return Collections.unmodifiableCollection(children);
    }

    public Collection<Animal> getDescendants() {
        HashSet<Animal> descendants = new HashSet<>(this.getChildren());

        for(Animal child : children) {
            descendants.addAll(child.getDescendants());
        }

        return descendants;
    }

    public AnimalStats getStats() {
        return stats;
    }

    public int getEnergy() {
        return energy;
    }

    public void afterReproduce(int energyUsedToReproduce, Animal child) {
        this.energy -= energyUsedToReproduce;
        children.add(child);
    }

    public void kill(int currentDay) {
        if(isDead) {
            return;
        }
        stats.setDayOfDeath(currentDay);
        isDead = true;
    }

    @Override
    public void eat(int energyFromFood) {
        energy += energyFromFood;
        stats.incrementNumberOfEatenGrass();
    }

    @Override
    public void move(IGeneSelectionStrategy geneSelectionStrategy, ILocationProvider locationProvider) {
        super.move(geneSelectionStrategy, locationProvider);
        energy--;
        stats.incrementNumberOfDaysOfLife();
    }
}
