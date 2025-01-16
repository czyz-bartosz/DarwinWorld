package darwinWorld.model.simulation.parameters;

// Builder - creational design pattern for SimulationParameters

public class SimulationParametersBuilder {
    private int mapHeight = 10;
    private int mapWidth = 15;
    private int equatorSpan = 2;
    private boolean withOwlBear = false;
    private int initialGrassAmount = 20;
    private int initialAnimalAmount = 6;
    private int initialAnimalEnergy = 10;
    private int grassGrowthPerDay = 6;
    private int minimalEnergyToReproduce = 5;
    private int energyUsedToReproduce = 2;
    private int maxMutations = 0;
    private int minMutations = 0;
    private boolean crazyMutation = false;
    private int grassEnergy = 3;
    private int genesLength = 5;
    private boolean saveToCSV = false;

    public SimulationParametersBuilder setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
        return this;
    }

    public SimulationParametersBuilder setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
        return this;
    }
    public SimulationParametersBuilder setEquatorSpan(int equatorSpan) {
        this.equatorSpan = equatorSpan;
        return this;
    }
    public SimulationParametersBuilder setWithOwlBear(boolean withOwlBear) {
        this.withOwlBear = withOwlBear;
        return this;
    }

    public SimulationParametersBuilder setInitialGrassAmount(int initialGrassAmount) {
        this.initialGrassAmount = initialGrassAmount;
        return this;
    }

    public SimulationParametersBuilder setInitialAnimalAmount(int initialAnimalAmount) {
        this.initialAnimalAmount = initialAnimalAmount;
        return this;
    }

    public SimulationParametersBuilder setInitialAnimalEnergy(int initialAnimalEnergy) {
        this.initialAnimalEnergy = initialAnimalEnergy;
        return this;
    }

    public SimulationParametersBuilder setGrassGrowthPerDay(int grassGrowthPerDay) {
        this.grassGrowthPerDay = grassGrowthPerDay;
        return this;
    }

    public SimulationParametersBuilder setMinimalEnergyToReproduce(int minimalEnergyToReproduce) {
        this.minimalEnergyToReproduce = minimalEnergyToReproduce;
        return this;
    }

    public SimulationParametersBuilder setEnergyUsedToReproduce(int energyUsedToReproduce) {
        this.energyUsedToReproduce = energyUsedToReproduce;
        return this;
    }

    public SimulationParametersBuilder setMaxMutations(int maxMutations) {
        this.maxMutations = maxMutations;
        return this;
    }

    public SimulationParametersBuilder setMinMutations(int minMutations) {
        this.minMutations = minMutations;
        return this;
    }

    public SimulationParametersBuilder setCrazyMutation(boolean crazyMutation) {
        this.crazyMutation = crazyMutation;
        return this;
    }

    public SimulationParametersBuilder setGrassEnergy(int grassEnergy) {
        this.grassEnergy = grassEnergy;
        return this;
    }

    public SimulationParametersBuilder setGenesLength(int genesLength) {
        this.genesLength = genesLength;
        return this;
    }

    public SimulationParametersBuilder setSaveToCSV(boolean saveToCSV) {
        this.saveToCSV = saveToCSV;
        return this;
    }

    public SimulationParameters build() {
        return new SimulationParameters(
                mapHeight,
                mapWidth,
                equatorSpan,
                withOwlBear,
                initialGrassAmount,
                initialAnimalAmount,
                initialAnimalEnergy,
                grassGrowthPerDay,
                minimalEnergyToReproduce,
                energyUsedToReproduce,
                maxMutations,
                minMutations,
                crazyMutation,
                grassEnergy,
                genesLength,
                saveToCSV
        );
    }

}
