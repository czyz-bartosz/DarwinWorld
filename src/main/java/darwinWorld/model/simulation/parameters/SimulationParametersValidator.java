package darwinWorld.model.simulation.parameters;

import darwinWorld.utils.InvalidArgumentException;

public class SimulationParametersValidator {

    public static void validate(SimulationParameters parameters) throws InvalidArgumentException {
        validateMapSize(parameters);
        validateEquatorSpan(parameters);
        validateInitialParameters(parameters);
        validateReproductionParameters(parameters);
        validateGenesParameters(parameters);
    }

    private static void validateMapSize(SimulationParameters parameters) throws InvalidArgumentException {
        if (parameters.mapHeight() <= 1) {
            throw new InvalidArgumentException("mapHeight must be greater than 1");
        }

        if (parameters.mapWidth() <= 0) {
            throw new InvalidArgumentException("mapWidth must be greater than 0");
        }
    }
    private static void validateEquatorSpan(SimulationParameters parameters) throws InvalidArgumentException {
        if(parameters.equatorSpan() < 0){
            throw new InvalidArgumentException("Equator span cannot be negative");
        }
        if(parameters.equatorSpan() >= parameters.mapHeight()/2) {
            throw new InvalidArgumentException("Equator cannot cover either hemisphere");
        }
    }

    private static void validateInitialParameters(SimulationParameters parameters) throws InvalidArgumentException {
        if (parameters.initialGrassAmount() < 0) {
            throw new InvalidArgumentException("initialGrassAmount must be greater or equal 0");
        }

        if (parameters.grassGrowthPerDay() < 0) {
            throw new InvalidArgumentException("grassGrowthPerDay must be greater or equal 0");
        }

        if (parameters.initialAnimalAmount() < 0) {
            throw new InvalidArgumentException("initialAnimalAmount must be greater or equal 0");
        }

        if (parameters.initialAnimalEnergy() < 0) {
            throw new InvalidArgumentException("initialAnimalEnergy must be greater or equal 0");
        }
    }

    private static void validateReproductionParameters(SimulationParameters parameters) throws InvalidArgumentException {
        if (parameters.minimalEnergyToReproduce() < 0) {
            throw new InvalidArgumentException("minimalEnergyToReproduce must be greater or equal 0");
        }

        if (parameters.energyUsedToReproduce() < 0) {
            throw new InvalidArgumentException("energyUsedToReproduce must be greater or equal 0");
        }

        if (parameters.energyUsedToReproduce() > parameters.minimalEnergyToReproduce()) {
            throw new InvalidArgumentException("energyUsedToReproduce must be less or equal minimalEnergyToReproduce");
        }
    }

    private static void validateGenesParameters(SimulationParameters parameters) throws InvalidArgumentException {
        if (parameters.maxMutations() < 0) {
            throw new InvalidArgumentException("maxMutations must be greater or equal 0");
        }

        if (parameters.minMutations() < 0) {
            throw new InvalidArgumentException("minMutations must be greater or equal 0");
        }

        if (parameters.maxMutations() < parameters.minMutations()) {
            throw new InvalidArgumentException("maxMutations must be greater or equal minMutations");
        }

        if (parameters.genesLength() <= 0) {
            throw new IllegalArgumentException("genesLength must be greater than 0");
        }
    }
}
