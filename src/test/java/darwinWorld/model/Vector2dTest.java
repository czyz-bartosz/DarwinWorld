package darwinWorld.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


class Vector2dTest {

    @ParameterizedTest
    @CsvSource({
            "1, 2, 1, 2, true",
            "1, 2, 2, 3, true",
            "3, 3, 1, 2, false",
            "1, 1, 0, 2, false",
            "0, 3, 0, 2, false"
    })
    void precedes_WithTwoVectors_ReturnsCorrectResult(int x1, int y1, int x2, int y2, boolean expected) {
        // Given
        Vector2d v1 = new Vector2d(x1, y1);
        Vector2d v2 = new Vector2d(x2, y2);

        // When
        boolean result = v1.precedes(v2);

        // Then
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 1, 2, true",
            "2, 3, 1, 2, true",
            "1, 1, 3, 3, false",
            "0, 2, 1, 1, false",
            "2, 0, 1, 1, false"
    })
    void follows_WithTwoVectors_ReturnsCorrectResult(int x1, int y1, int x2, int y2, boolean expected) {
        // Given
        Vector2d v1 = new Vector2d(x1, y1);
        Vector2d v2 = new Vector2d(x2, y2);

        // When
        boolean result = v1.follows(v2);

        // Then
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 3, 4, 4, 6",
            "0, 0, 5, 5, 5, 5",
            "-1, -2, -3, -4, -4, -6",
            "1, 1, -1, -1, 0, 0"
    })
    void add_WithTwoVectors_ReturnsSum(int x1, int y1, int x2, int y2, int expectedX, int expectedY) {
        // Given
        Vector2d v1 = new Vector2d(x1, y1);
        Vector2d v2 = new Vector2d(x2, y2);

        // When
        Vector2d result = v1.add(v2);

        // Then
        assertEquals(new Vector2d(expectedX, expectedY), result);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4, 1, 2, 2, 2",
            "5, 5, 5, 5, 0, 0",
            "0, 0, -3, -4, 3, 4",
            "-1, -1, 1, 1, -2, -2"
    })
    void subtract_WithTwoVectors_ReturnsDifference(int x1, int y1, int x2, int y2, int expectedX, int expectedY) {
        // Given
        Vector2d v1 = new Vector2d(x1, y1);
        Vector2d v2 = new Vector2d(x2, y2);

        // When
        Vector2d result = v1.subtract(v2);

        // Then
        assertEquals(new Vector2d(expectedX, expectedY), result);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 3, 4, 3, 4",
            "0, 0, -1, -2, 0, 0",
            "5, 5, 2, 3, 5, 5",
            "-1, -1, -3, -4, -1, -1"
    })
    void upperRight_WithTwoVectors_ReturnsCorrectResult(int x1, int y1, int x2, int y2, int expectedX, int expectedY) {
        // Given
        Vector2d v1 = new Vector2d(x1, y1);
        Vector2d v2 = new Vector2d(x2, y2);

        // When
        Vector2d result = v1.upperRight(v2);

        // Then
        assertEquals(new Vector2d(expectedX, expectedY), result);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 3, 4, 1, 2",
            "0, 0, -1, -2, -1, -2",
            "5, 5, 2, 3, 2, 3",
            "-1, -1, -3, -4, -3, -4"
    })
    void lowerLeft_WithTwoVectors_ReturnsCorrectResult(int x1, int y1, int x2, int y2, int expectedX, int expectedY) {
        // Given
        Vector2d v1 = new Vector2d(x1, y1);
        Vector2d v2 = new Vector2d(x2, y2);

        // When
        Vector2d result = v1.lowerLeft(v2);

        // Then
        assertEquals(new Vector2d(expectedX, expectedY), result);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, -1, -2",
            "0, 0, 0, 0",
            "-3, -4, 3, 4",
            "5, 5, -5, -5"
    })
    void opposite_WithVector_ReturnsOpposite(int x, int y, int expectedX, int expectedY) {
        // Given
        Vector2d v = new Vector2d(x, y);

        // When
        Vector2d result = v.opposite();

        // Then
        assertEquals(new Vector2d(expectedX, expectedY), result);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, -1, -2",
            "0, 0, 0, 1",
            "5, 5, -3, 5"
    })
    void equals_WithUnequalVectors_ReturnsFalse(int x1, int y1, int x2, int y2) {
        // Given
        Vector2d v1 = new Vector2d(x1, y1);
        Vector2d v2 = new Vector2d(x2, y2);

        // When & Then
        assertNotEquals(v1, v2);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 1, 2",
            "-3, 5, -3, 5"
    })
    void equals_WithEqualVectors_ReturnsTrue(int x1, int y1, int x2, int y2) {
        // Given
        Vector2d v1 = new Vector2d(x1, y1);
        Vector2d v2 = new Vector2d(x2, y2);

        // When & Then
        assertEquals(v1, v2);
    }

    @Test
    void equals_WithEqualReferencesOfVectors_ReturnsTrue() {
        // Given
        Vector2d v1 = new Vector2d(1, 2);

        // When & Then
        assertEquals(v1, v1);
    }

    @Test
    void equals_WithDifferentObject_ReturnsFalse() {
        // Given
        Vector2d v1 = new Vector2d(1, 2);
        Object otherObject = new Object();

        // When & Then
        assertNotEquals(v1, otherObject);
    }

    @Test
    void hashCode_WithEqualVectors_ReturnsSameHashCode() {
        // Given
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(1, 2);

        // When & Then
        assertEquals(v1.hashCode(), v2.hashCode());
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4",
            "0, 0",
            "-1, -2",
            "5, -5"
    })
    void copyConstructor_WithVector_CreatesIdenticalCopy(int x, int y) {
        // Given
        Vector2d original = new Vector2d(x, y);

        // When
        Vector2d copy = new Vector2d(original);

        // Then
        assertEquals(original, copy);
        assertNotSame(original, copy);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4",
            "0, 0",
            "-1, -2",
            "5, -5"
    })
    void toString_WithVector_ReturnsCorrectString(int x, int y) {
        // Given
        Vector2d v = new Vector2d(x, y);

        // When
        String string = v.toString();

        // Then
        assertEquals(String.format("[%d,%d]", x, y), string);
    }
}
