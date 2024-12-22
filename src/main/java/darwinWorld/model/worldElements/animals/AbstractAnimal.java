package darwinWorld.model.worldElements.animals;

import darwinWorld.enums.MoveRotation;
import darwinWorld.model.Vector2d;
import darwinWorld.model.worldElements.IWorldElement;
import darwinWorld.model.worldElements.animals.geneSelectionStrategies.IGeneSelectionStrategy;

import java.util.List;

public abstract class AbstractAnimal implements IWorldElement {
    private Vector2d position;
    private final MoveRotation rotation;
    private final List<MoveRotation> genes;
    private int geneCurrentIndex;

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

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public MoveRotation getRotation() {
        return rotation;
    }

    public void move(IGeneSelectionStrategy strategy) {
        geneCurrentIndex = strategy.selectNextGene(genes, geneCurrentIndex);
        MoveRotation currentRotation = MoveRotation.add(rotation, genes.get(geneCurrentIndex));
        // TODO: add move validation
        position = position.add(currentRotation.toVector());
    }

    public abstract void eat(int energyFromFood);
}
