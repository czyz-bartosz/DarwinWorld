package darwinWorld.model.simulation;

import darwinWorld.model.map.WorldMap;
import darwinWorld.model.map.WorldMapUtils;
import darwinWorld.model.simulation.parameters.SimulationParameters;
import darwinWorld.model.simulation.parameters.SimulationParametersBuilder;

import java.util.UUID;

public class Simulation implements Runnable {
    private final UUID uuid = UUID.randomUUID();
    private SimulationParameters sp;
    private WorldMap map;

    public Simulation() {
        SimulationParametersBuilder sb = new SimulationParametersBuilder();
        sp = sb.build();
        map = new WorldMap(sp);
    }

    public WorldMap getMap() {
        return map;
    }

    public UUID getUuid() {
        return uuid;
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
