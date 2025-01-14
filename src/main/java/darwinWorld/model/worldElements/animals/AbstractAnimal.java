package darwinWorld.model.worldElements.animals;

import darwinWorld.enums.MoveRotation;
import darwinWorld.model.map.ILocationProvider;
import darwinWorld.model.map.Vector2d;
import darwinWorld.model.worldElements.IWorldElement;
import darwinWorld.model.worldElements.animals.geneSelectionStrategies.IGeneSelectionStrategy;

import java.util.Collections;
import java.util.List;

public abstract class AbstractAnimal implements IWorldElement {
    protected Vector2d position;
    protected MoveRotation rotation;
    protected final List<MoveRotation> genes;
    protected int geneCurrentIndex;

    protected AbstractAnimal(
            Vector2d position,
            MoveRotation rotation,
            List<MoveRotation> genes,
            int geneCurrentIndex
    ) {
        this.position = position;
        this.rotation = rotation;
        this.genes = genes;
        this.geneCurrentIndex = geneCurrentIndex;
    }

    public int getGeneCurrentIndex() {
        return geneCurrentIndex;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public MoveRotation getRotation() {
        return rotation;
    }

    public void setRotation(MoveRotation rotation) {
        this.rotation = rotation;
    }

    public List<MoveRotation> getGenes() {
        return Collections.unmodifiableList(genes);
    }

    public void move(
            IGeneSelectionStrategy geneSelectionStrategy,
            ILocationProvider locationProvider
    ) {
        geneCurrentIndex = geneSelectionStrategy.selectNextGene(genes, geneCurrentIndex);
        MoveRotation currentRotation = MoveRotation.add(rotation, genes.get(geneCurrentIndex));
        Vector2d newPosition = position.add(currentRotation.toVector());
        rotation = locationProvider.getRotation(newPosition,rotation);
        position = locationProvider.getPosition(newPosition);
    }

    public void eat(int energyFromFood) {
        return;
    }
}