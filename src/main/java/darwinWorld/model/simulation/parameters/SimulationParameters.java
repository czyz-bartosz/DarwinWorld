package darwinWorld.model.simulation.parameters;

public record SimulationParameters(
        int mapHeight,
        int mapWidth,
        boolean withOwlBear,
        int initialGrassAmount,
        int initialAnimalAmount,
        int initialAnimalEnergy,
        int grassGrowthPerDay,
        int minimalEnergyToReproduce,
        int energyUsedToReproduce,
        int maxMutations,
        int minMutations,
        boolean crazyMutation,
        int grassEnergy,
        int genesLength,
        boolean saveToCSV
) {

}