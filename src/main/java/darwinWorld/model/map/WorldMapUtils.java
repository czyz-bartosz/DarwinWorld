package darwinWorld.model.map;


import darwinWorld.model.worldElements.Grass;
import darwinWorld.model.worldElements.animals.Animal;
import darwinWorld.model.worldElements.animals.owlBear.OwlBear;

import java.util.*;

public class WorldMapUtils {
    public static Collection<Vector2d> getPositionsOccupiedByAnimals(WorldMap map) {
        Map<Vector2d, HashSet<Animal>> animals = map.getAnimals();

        Collection<Vector2d> positions = new HashSet<>();

        animals.forEach((position, animalSet) -> {
            if(!animalSet.isEmpty()) positions.add(position);
        });

        return positions;
    }

    public static Collection<Vector2d> getPositionsOccupiedByGrass(WorldMap map) {
        Map<Vector2d, Grass> grass = map.getGrass();

        return grass.keySet();
    }

    public static Optional<Vector2d> getPositionOfOwlBear(WorldMap map) {
        if(map.getOwlBear().isEmpty()) {
            return Optional.empty();
        }

        OwlBear owlBear = map.getOwlBear().get();
        return Optional.ofNullable(owlBear.getPosition());
    }

    public static Collection<Vector2d> getOccupiedPositions(WorldMap map) {
        Set<Vector2d> positions = new HashSet<>();
        positions.addAll(getPositionsOccupiedByAnimals(map));
        positions.addAll(getPositionsOccupiedByGrass(map));
        getPositionOfOwlBear(map).ifPresent(positions::add);

        return positions;
    }

    public static Collection<Animal> getCollectionOfAnimals(WorldMap map) {
        return map
                .getAnimals()
                .values()
                .stream()
                .flatMap(Set::stream)
                .toList();
    }
}