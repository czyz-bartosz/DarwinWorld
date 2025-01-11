package darwinWorld.model.worldElements.animals;

import darwinWorld.enums.MoveRotation;
import darwinWorld.model.map.Boundary;
import darwinWorld.model.map.ILocationProvider;
import darwinWorld.model.map.Vector2d;
import darwinWorld.model.worldElements.animals.geneSelectionStrategies.IGeneSelectionStrategy;

import java.util.List;

public class OwlBear extends AbstractAnimal{
    private final List<Boundary> territory;

    @Override
    public String toString() {
        return "O";
    }

    public OwlBear(
            Vector2d position,
            MoveRotation rotation,
            List<MoveRotation> genes,
            int geneCurrentIndex,
            List<Boundary> territory
    ) {
        super(position, rotation, genes, geneCurrentIndex);
        this.territory = territory;
    }

    private boolean isInTerritory(Vector2d v) {
        return territory
                .stream()
                .anyMatch((boundary) -> {
                    Vector2d lowerLeft = boundary.lowerLeft();
                    Vector2d upperRight = boundary.upperRight();

                    return v.follows(lowerLeft) && v.precedes(upperRight);
                });
    }

    @Override
    public void move(IGeneSelectionStrategy geneSelectionStrategy, ILocationProvider locationProvider) {
        geneCurrentIndex = geneSelectionStrategy.selectNextGene(genes, geneCurrentIndex);
        MoveRotation currentRotation = MoveRotation.add(rotation, genes.get(geneCurrentIndex));
        Vector2d newPosition = position.add(currentRotation.toVector());
        newPosition = locationProvider.getPosition(newPosition);

        if(!isInTerritory(newPosition)) {
            return;
        }

        position = newPosition;
        rotation = locationProvider.getRotation(newPosition,rotation);
    }
}
