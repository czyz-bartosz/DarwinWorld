package darwinWorld.model.worldElements.animals.geneSelectionStrategies;

import darwinWorld.enums.MoveRotation;

import java.util.List;

// Strategy pattern
public interface IGeneSelectionStrategy {
    int selectNextGene(List<MoveRotation> genes, int currentGeneIndex);
}
