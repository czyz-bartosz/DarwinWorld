package darwinWorld.model.worldElements;

import darwinWorld.model.map.Vector2d;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class GrassTest {

    @Test
    void shouldReturnCorrectPosition() {
        var position = new Vector2d(5, 5);
        var grass = new Grass(position);
        assertEquals(position, grass.getPosition());
    }

    @Test
    void shouldReturnCorrectPositionAfterChange() {
        var position = new Vector2d(5, 5);
        var grass = new Grass(position);
        var newPosition = new Vector2d(6, 6);
        grass = new Grass(newPosition);
        assertEquals(newPosition, grass.getPosition());
    }
}