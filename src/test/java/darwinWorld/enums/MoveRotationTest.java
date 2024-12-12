package darwinWorld.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveRotationTest {

    @Test
    void getAngle() {
        MoveRotation moveRotation = MoveRotation.DEG_0;
        MoveRotation moveRotation1 = MoveRotation.DEG_135;

        int angle = moveRotation.getAngle();
        int angle1 = moveRotation1.getAngle();

        assertEquals(angle, 0);
        assertEquals(angle1, 135);
    }

    @Test
    void add() {
        MoveRotation moveRotation = MoveRotation.DEG_0;
        MoveRotation moveRotation1 = MoveRotation.DEG_135;
        MoveRotation moveRotation2 = MoveRotation.DEG_270;

        MoveRotation add01 = MoveRotation.add(moveRotation, moveRotation1);
        MoveRotation add12 = MoveRotation.add(moveRotation1, moveRotation2);

        MoveRotation res01 = MoveRotation.DEG_135;
        MoveRotation res12 = MoveRotation.DEG_45;

        assertEquals(res01, add01);
        assertEquals(res12, add12);
    }

    @Test
    void valueOf() {
        String DEG_0_String = "DEG_0";
        String DEG_135_String = "DEG_135";
        String DEG_270_String = "DEG_270";

        assertEquals(MoveRotation.valueOf(DEG_0_String), MoveRotation.DEG_0 );
        assertEquals(MoveRotation.valueOf(DEG_135_String), MoveRotation.DEG_135);
        assertEquals(MoveRotation.valueOf(DEG_270_String), MoveRotation.DEG_270);
    }
}