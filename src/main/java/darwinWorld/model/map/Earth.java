package darwinWorld.model.map;

import darwinWorld.utils.NoPositonException;
import darwinWorld.utils.RandomNumberGenerator;

public class Earth {

    private final Boundary boundary;

    private final Boundary northernHemisphere;
    private final Boundary equatorStrip;
    private final Boundary southernHemisphere;

    public Earth(int mapHeight, int mapWidth, int equatorSpan){

        Vector2d lowerLeft = new Vector2d(0,0);
        Vector2d upperRight = new Vector2d(mapWidth, mapHeight);
        this.boundary = new Boundary(lowerLeft, upperRight);

        int heightCenter = mapHeight / 2;
        int northernHemisphereEnd = heightCenter + equatorSpan + 1;
        int southernHemisphereEnd = heightCenter - equatorSpan - 1;

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
    public Vector2d randomEquatorPosition(){ return equatorStrip.randomPosition(); }

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
