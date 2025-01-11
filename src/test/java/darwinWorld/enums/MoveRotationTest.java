package darwinWorld.enums;

import darwinWorld.model.map.Vector2d;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class MoveRotationTest {
    @ParameterizedTest
    @CsvSource({
            "DEG_0, 0",
            "DEG_45, 45",
            "DEG_90, 90",
            "DEG_135, 135",
            "DEG_180, 180",
            "DEG_225, 225",
            "DEG_270, 270",
            "DEG_315, 315"
    })
    void getAngle_WithRotation_ReturnsCorrectAngle(String rotationName, int expectedAngle) {
        // Given
        MoveRotation rotation = MoveRotation.valueOf(rotationName);

        // When
        int angle = rotation.getAngle();

        // Then
        assertEquals(expectedAngle, angle);
    }

    @ParameterizedTest
    @CsvSource({
            "DEG_90, DEG_90, DEG_180",
            "DEG_270, DEG_90, DEG_0",
            "DEG_0, DEG_315, DEG_315",
            "DEG_315, DEG_180, DEG_135",
            "DEG_180, DEG_180, DEG_0"
    })
    void add_WithTwoRotations_ReturnsValidSumWithWrapping(
            String rotation1,
            String rotation2,
            String expectedRotation
    ) {
        // Given
        MoveRotation r1 = MoveRotation.valueOf(rotation1);
        MoveRotation r2 = MoveRotation.valueOf(rotation2);

        // When
        MoveRotation result = MoveRotation.add(r1, r2);

        // Then
        assertEquals(MoveRotation.valueOf(expectedRotation), result);
    }

    @ParameterizedTest
    @EnumSource(MoveRotation.class)
    void toVector_ShouldReturnCorrectVector(MoveRotation direction) {
        // When
        Vector2d result = direction.toVector();

        // Then
        switch (direction) {
            case DEG_0 -> assertEquals(new Vector2d(0, 1), result);
            case DEG_45 -> assertEquals(new Vector2d(1, 1), result);
            case DEG_90 -> assertEquals(new Vector2d(1, 0), result);
            case DEG_135 -> assertEquals(new Vector2d(1, -1), result);
            case DEG_180 -> assertEquals(new Vector2d(0, -1), result);
            case DEG_225 -> assertEquals(new Vector2d(-1, -1), result);
            case DEG_270 -> assertEquals(new Vector2d(-1, 0), result);
            case DEG_315 -> assertEquals(new Vector2d(-1, 1), result);
        }
    }
}