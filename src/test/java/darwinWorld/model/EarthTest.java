package darwinWorld.model;

import darwinWorld.model.map.Boundary;
import darwinWorld.model.map.Earth;
import darwinWorld.model.map.Vector2d;
import darwinWorld.utils.RandomNumberGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


class EarthTest {

    @ParameterizedTest
    @CsvSource({
            "6, 6, 1, '0,0,6,1', '0,2,6,4', '0,5,6,6'",
            "10, 10, 0, '0,0,10,4', '0,5,10,5', '0,6,10,10'",
            "9, 15, 3, '0,0,15,0', '0,1,15,7', '0,8,15,9'",
            "8, 10, 0, '0,0,10,3', '0,4,10,4', '0,5,10,8'"
    })
    void initializer_WithMapParameters_CreatesProperSegmentation(int mapHeight, int mapWidth, int equadorSpan,
            @ConvertWith(BoundaryConverter.class) Boundary correctSouthernHemisphere,
            @ConvertWith(BoundaryConverter.class) Boundary correctEquadorStrip,
            @ConvertWith(BoundaryConverter.class) Boundary correctNorthernHemisphere)
    {
        Earth earth = new Earth(mapHeight, mapWidth, equadorSpan);

        assertEquals(correctSouthernHemisphere, earth.getSouthernHemisphere());
        assertEquals(correctEquadorStrip, earth.getEquatorStrip());
        assertEquals(correctNorthernHemisphere, earth.getNorthernHemisphere());
    }


    @ParameterizedTest
    @CsvSource({
            "6, 6, 4",
            "10, 15, 10",
            "9, 15, 4"
    })
    void initializer_WithWrongEquadorSpan_ThrowsError(int mapHeight, int mapWidth, int equadorSpan){
        assertThrows(IllegalArgumentException.class, () -> new Earth(mapHeight, mapWidth, equadorSpan));
    }


    @ParameterizedTest
    @CsvSource({
            "-8, 6, 4",
            "10, 0, 0",
            "9, 15, -4",
            "12,-2,0"
    })
    void initializer_WithWrongArguments_ThrowsError(int mapHeight, int mapWidth, int equadorSpan){
        assertThrows(IllegalArgumentException.class, () -> new Earth(mapHeight, mapWidth, equadorSpan));
    }

    @ParameterizedTest
    @CsvSource({
            "8, 6, -2",
            "10, 12, -5",
    })
    void initializer_WithNegativeEquadorSpan_ThrowsError(int mapHeight, int mapWidth, int equadorSpan){
        assertThrows(IllegalArgumentException.class, () -> new Earth(mapHeight, mapWidth, equadorSpan));
    }


    @ParameterizedTest
    @CsvSource({
            "6, 6, 2, 821",
            "10, 15, 3, 4",
            "9, 15, 2, 3728197"
    })
    void getRandomEquatorPosition_WithInitializedEarth_ReturnsValidPosition(int mapHeight, int mapWidth, int equadorSpan, long seed){
        Earth earth = new Earth(mapHeight, mapWidth, equadorSpan);
        Boundary equadorStrip = earth.getEquatorStrip();
        RandomNumberGenerator.setSeed(seed);

        for(int i=0; i<10; i++){

            Vector2d randomPositon = earth.randomEquatorPosition();


            assertTrue(equadorStrip.contains(randomPositon));
        }

    }


    @ParameterizedTest
    @CsvSource({
            "6, 6, 2, 865",
            "10, 15, 3, 7",
            "9, 15, 2, 4321"
    })
    void getRandomNorthernHemisphere_WithInitializedEarth_ReturnsValidPosition(int mapHeight, int mapWidth, int equadorSpan, long seed){
        Earth earth = new Earth(mapHeight, mapWidth, equadorSpan);
        Boundary northernHemisphere = earth.getNorthernHemisphere();
        RandomNumberGenerator.setSeed(seed);

        for(int i=0; i<10; i++){

            Vector2d randomPositon = earth.randomNorthernHemispherePosition();


            assertTrue(northernHemisphere.contains(randomPositon));
        }

    }


    @ParameterizedTest
    @CsvSource({
            "6, 6, 2, 103",
            "12, 15, 4, 3291",
            "9, 15, 2, 3021"
    })
    void getRandomSouthernHemisphere_WithInitializedEarth_ReturnsValidPosition(int mapHeight, int mapWidth, int equadorSpan, long seed){
        Earth earth = new Earth(mapHeight, mapWidth, equadorSpan);
        Boundary southernHemisphere = earth.getSouthernHemisphere();
        RandomNumberGenerator.setSeed(seed);

        for(int i=0; i<10; i++){

            Vector2d randomPositon = earth.randomSouthernHemispherePosition();


            assertTrue(southernHemisphere.contains(randomPositon));
        }

    }

    //TODO: test unoccupied position generators
}