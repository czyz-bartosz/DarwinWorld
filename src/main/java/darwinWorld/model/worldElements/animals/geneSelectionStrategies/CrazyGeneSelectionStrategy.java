package darwinWorld.model.worldElements.animals.geneSelectionStrategies;

import darwinWorld.enums.MoveRotation;
import darwinWorld.utils.RandomNumberGenerator;

import java.util.List;

public class CrazyGeneSelectionStrategy implements IGeneSelectionStrategy{
    @Override
    public int selectNextGene(List<MoveRotation> genes, int currentGeneIndex) {
        double random = RandomNumberGenerator.randomDouble();

        if(random < 0.8) {
            return (currentGeneIndex + 1) % genes.size();
        }else {
            return RandomNumberGenerator.integerFromRange(0, genes.size() - 1);
        }
    }
}