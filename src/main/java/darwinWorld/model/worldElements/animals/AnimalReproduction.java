package darwinWorld.model.worldElements.animals;

import darwinWorld.enums.MoveRotation;
import darwinWorld.utils.RandomNumberGenerator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AnimalReproduction {
    public static Animal reproduce(
            Animal parent1,
            Animal parent2,
            int energyUsedToReproduce,
            int maxMutations,
            int minMutations
    ) {
        int numberOfMutations = RandomNumberGenerator.integerFromRange(minMutations, maxMutations);
        List<MoveRotation> childGene = generateGenotype(
                parent1,
                parent2,
                numberOfMutations
        );
        int childEnergy = energyUsedToReproduce * 2;

        Animal child = new Animal(
                parent1.getPosition(),
                MoveRotation.randomMoveRotation(),
                childGene,
                0,
                childEnergy
        );

        parent1.afterReproduce(energyUsedToReproduce, child);
        parent2.afterReproduce(energyUsedToReproduce, child);

        return child;
    }

    private static List<MoveRotation> generateGenotype(
            Animal parent1,
            Animal parent2,
            int numberOfMutations
    ) {
        // random position for parents
        if (RandomNumberGenerator.randomDouble() < 0.5) {
            Animal temp = parent1;
            parent1 = parent2;
            parent2 = temp;
        }

        int genesLength = parent1.getGenes().size();
        int sumOfEnergy = parent1.getEnergy() + parent2.getEnergy();
        int splitPosition = (int) (((double) parent1.getEnergy() / sumOfEnergy) * genesLength);

        List<MoveRotation> genes = Stream.concat(
                parent1.getGenes().stream().limit(splitPosition),
                parent2.getGenes().stream().skip(splitPosition)
        ).collect(Collectors.toList());

        List<Integer> genesIndexesToMute =
                IntStream.range(0, genesLength)
                        .boxed()
                        .collect(Collectors.toList());

        Collections.shuffle(genesIndexesToMute);

        genesIndexesToMute
                .stream()
                .limit(numberOfMutations)
                .forEach((index) -> genes.set(index, MoveRotation.randomMoveRotation()));

        return genes;
    }
}
