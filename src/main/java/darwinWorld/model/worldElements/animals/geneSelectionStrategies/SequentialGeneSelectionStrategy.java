package darwinWorld.model.worldElements.animals.geneSelectionStrategies;

import darwinWorld.enums.MoveRotation;

import java.util.List;

public class SequentialGeneSelectionStrategy implements IGeneSelectionStrategy {
    @Override
    public int selectNextGene(List<MoveRotation> genes, int currentGeneIndex) {
        return (currentGeneIndex + 1) % genes.size();
    }
}
