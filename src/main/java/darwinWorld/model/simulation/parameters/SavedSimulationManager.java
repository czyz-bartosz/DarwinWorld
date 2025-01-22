package darwinWorld.model.simulation.parameters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;

public class SavedSimulationManager {
    private final String path;

    Gson gson = new Gson();

    public SavedSimulationManager(String path) {
        this.path = path;
    }

    public void create() {
        File properties = new File(path);
        try {
            properties.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Map<String,SimulationParameters> sps) {
        String json = gson.toJson(sps);
        create();

        try {
            PrintWriter writer = new PrintWriter(path);
            writer.print(json);
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String,SimulationParameters> load() {
        try {
            String content = Files.readString(Paths.get(path));
            return gson.fromJson(content, new TypeToken<Map<String,SimulationParameters>>(){}.getType());
        }catch (NoSuchFileException e) {
            return new HashMap<>();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
