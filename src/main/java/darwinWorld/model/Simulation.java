package darwinWorld.model;

import darwinWorld.model.map.WorldMap;
import darwinWorld.model.simulation.parameters.SimulationParameters;
import darwinWorld.model.simulation.parameters.SimulationParametersBuilder;
import darwinWorld.model.worldElements.animals.Animal;

public class Simulation implements Runnable {

    private WorldMap map;
    private Animal selectedAnimal;
    private int currentDay = 0;
    private boolean isRunning = false;
    private final Object pauseLock = new Object();



    //Todo add stepDelay to parameters
    private int stepDelay = 100;


    public Simulation(SimulationParameters sp) {
        map = new WorldMap(sp);
    }

    public void run() {
        while (true) {
            while (!isRunning) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println(map.toString());
            step();

            if(currentDay == 100){ // Needs to be replaced with SimulationStats.getNumberOfAnimals()
                isRunning = false;
                return;
            }

            try {
                Thread.sleep(stepDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void pauseSimulation(){
        isRunning = false;
        notify();
    }
    public void startSimulation(){
        isRunning = true;
    }

    public void step(){
        currentDay++;
        map.step();
    }

}
