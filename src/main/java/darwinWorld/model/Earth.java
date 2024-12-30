package darwinWorld.model;

import darwinWorld.enums.MoveRotation;
import darwinWorld.model.worldElements.IWorldElement;
import darwinWorld.utils.NoPositonException;
import darwinWorld.utils.RandomNumberGenerator;

import java.util.Map;
import java.util.Vector;

public class Earth {

    private final Boundary boundary;

    private final Boundary northernHemisphere;
    private final Boundary equatorStrip;
    private final Boundary southernHemisphere;

    public Earth(int mapHeight, int mapWidth, int equadorSpan) throws IllegalArgumentException {
        if(mapHeight <= 0 || mapWidth <= 0){
            throw new IllegalArgumentException("Map dimensions cannot be zero or negative");
        }
        if(equadorSpan < 0){
            throw new IllegalArgumentException("Equador span cannot be negative");
        }
        if(equadorSpan >= mapHeight/2) {
            throw new IllegalArgumentException("Equador cannot cover either hemisphere");
        }

        Vector2d lowerLeft = new Vector2d(0,0);
        Vector2d upperRight = new Vector2d(mapWidth, mapHeight);
        this.boundary = new Boundary(lowerLeft, upperRight);

        int heightCenter = mapHeight / 2;
        int northernHemisphereEnd = heightCenter + equadorSpan + 1;
        int southernHemisphereEnd = heightCenter - equadorSpan - 1;

        Vector2d southernHemisphereUpperRight = new Vector2d(mapWidth,southernHemisphereEnd);
        this.southernHemisphere = new Boundary(lowerLeft,southernHemisphereUpperRight);

        Vector2d equatorStripLowerLeft = new Vector2d(0,southernHemisphereEnd + 1);
        Vector2d equatorStripUpperRight = new Vector2d(mapWidth,northernHemisphereEnd - 1);
        this.equatorStrip = new Boundary(equatorStripLowerLeft,equatorStripUpperRight);

        Vector2d northernHemisphereLowerLeft = new Vector2d(0,northernHemisphereEnd);
        this.northernHemisphere = new Boundary(northernHemisphereLowerLeft,upperRight);
    }

    public Boundary getBoundary() { return this.boundary; }
    public Boundary getNorthernHemisphere() { return this.northernHemisphere; }
    public Boundary getSouthernHemisphere() { return this.southernHemisphere; }
    public Boundary getEquatorStrip() { return this.equatorStrip; }

    public Vector2d randomNorthernHemispherePosition(){ return northernHemisphere.randomPosition(); }
    public Vector2d randomSouthernHemispherePosition(){ return southernHemisphere.randomPosition(); }
    public Vector2d randomEquadorPosition(){ return equatorStrip.randomPosition(); }

    public Vector2d randomPosition(){ return boundary.randomPosition(); }
    public Vector2d randomUnoccupiedPosition(WorldMap map) throws NoPositonException {
        return boundary.randomUnoccupiedPosition(map);
    }

    public Vector2d randomUnoccupiedPositionWithEquatorFavored(WorldMap map) throws NoPositonException {
        double random = RandomNumberGenerator.randomDouble();

        if (random < 0.8)
            return equatorStrip.randomUnoccupiedPosition(map);
        else{
            double hemisphere = RandomNumberGenerator.randomDouble();

            if (hemisphere < 0.5) return northernHemisphere.randomUnoccupiedPosition(map);
            else return southernHemisphere.randomUnoccupiedPosition(map);

        }
    }
}
