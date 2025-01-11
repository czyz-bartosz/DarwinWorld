package darwinWorld.model.simulation.parameters;

public class SimulationParametersValidator {

    public static void validate(SimulationParameters parameters) {
        validateMapSize(parameters);
        validateInitialParameters(parameters);
        validateReproductionParameters(parameters);
        validateGenesParameters(parameters);
    }

    private static void validateMapSize(SimulationParameters parameters) {
        if (parameters.mapHeight() <= 0) {
            throw new IllegalArgumentException("mapHeight must be greater than 0");
        }

        if (parameters.mapWidth() <= 0) {
            throw new IllegalArgumentException("mapWidth must be greater than 0");
        }
    }

    private static void validateInitialParameters(SimulationParameters parameters) {
        if (parameters.initialGrassAmount() < 0) {
            throw new IllegalArgumentException("initialGrassAmount must be greater or equal 0");
        }

        if (parameters.grassGrowthPerDay() < 0) {
            throw new IllegalArgumentException("grassGrowthPerDay must be greater or equal 0");
        }

        if (parameters.initialAnimalAmount() < 0) {
            throw new IllegalArgumentException("initialAnimalAmount must be greater or equal 0");
        }

        if (parameters.initialAnimalEnergy() < 0) {
            throw new IllegalArgumentException("initialAnimalEnergy must be greater or equal 0");
        }
    }

    private static void validateReproductionParameters(SimulationParameters parameters) {
        if (parameters.minimalEnergyToReproduce() < 0) {
            throw new IllegalArgumentException("minimalEnergyToReproduce must be greater or equal 0");
        }

        if (parameters.energyUsedToReproduce() < 0) {
            throw new IllegalArgumentException("energyUsedToReproduce must be greater or equal 0");
        }

        if (parameters.energyUsedToReproduce() > parameters.minimalEnergyToReproduce()) {
            throw new IllegalArgumentException("energyUsedToReproduce must be less or equal minimalEnergyToReproduce");
        }
    }

    private static void validateGenesParameters(SimulationParameters parameters) {
        if (parameters.maxMutations() < 0) {
            throw new IllegalArgumentException("maxMutations must be greater or equal 0");
        }

        if (parameters.minMutations() < 0) {
            throw new IllegalArgumentException("minMutations must be greater or equal 0");
        }

        if (parameters.maxMutations() < parameters.minMutations()) {
            throw new IllegalArgumentException("maxMutations must be greater or equal minMutations");
        }

        if (parameters.genesLength() <= 0) {
            throw new IllegalArgumentException("genesLength must be greater than 0");
        }
    }
}
