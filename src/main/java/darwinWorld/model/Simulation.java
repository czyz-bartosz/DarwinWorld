package darwinWorld.model;

import darwinWorld.model.map.WorldMap;
import darwinWorld.model.simulation.parameters.SimulationParameters;
import darwinWorld.model.simulation.parameters.SimulationParametersBuilder;

public class Simulation implements Runnable {

    private SimulationParameters sp;
    private WorldMap map;

    public Simulation() {
        SimulationParametersBuilder sb = new SimulationParametersBuilder();
        sp = sb.build();
        map = new WorldMap(sp);
    }

    public void run() {
        for(int i = 0; i < 10; i++) {
            System.out.println(map.toString());
            step();
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void step(){
        map.step();
    }

}
