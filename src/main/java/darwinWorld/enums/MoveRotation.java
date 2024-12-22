package darwinWorld.enums;

import darwinWorld.model.Vector2d;

public enum MoveRotation {
    DEG_0(0),
    DEG_45(45),
    DEG_90(90),
    DEG_135(135),
    DEG_180(180),
    DEG_225(225),
    DEG_270(270),
    DEG_315(315);

    private final int angle;

    MoveRotation(int angle) {
        this.angle = angle;
    }

    public int getAngle() {
        return angle;
    }

    public static MoveRotation add(MoveRotation r1, MoveRotation r2) {
        int finalAngle = (r1.getAngle() + r2.getAngle()) % 360;
        return getMoveRotation(finalAngle);
    }

    public Vector2d toVector() {
        return switch (this) {
            case DEG_0 -> new Vector2d(0, 1);
            case DEG_45 -> new Vector2d(1, 1);
            case DEG_90 -> new Vector2d(1, 0);
            case DEG_135 -> new Vector2d(1, -1);
            case DEG_180 -> new Vector2d(0, -1);
            case DEG_225 -> new Vector2d(-1, -1);
            case DEG_270 -> new Vector2d(-1, 0);
            case DEG_315 -> new Vector2d(-1, 1);
        };
    }

    private static MoveRotation getMoveRotation(int angle) {
        return MoveRotation.valueOf("DEG_" + angle);
    }
}