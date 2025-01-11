package darwinWorld.model.map;

import java.util.Objects;

public class Vector2d {
    final private int x, y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d(Vector2d v) {
        this.x = v.x;
        this.y = v.y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "[" + Integer.toString(x) + "," + Integer.toString(y) + "]";
    }

    public boolean precedes(Vector2d other) {
        return x <= other.getX() && y <= other.getY();
    }

    public boolean follows(Vector2d other) {
        return x >= other.getX() && y >= other.getY();
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.getX(), y + other.getY());
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(x - other.getX(), y - other.getY());
    }

    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(Math.max(x, other.getX()), Math.max(y, other.getY()));
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(Math.min(x, other.getX()), Math.min(y, other.getY()));
    }

    public Vector2d opposite() {
        return new Vector2d(-x, -y);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(!(obj instanceof Vector2d)) {
            return false;
        }

        Vector2d other = (Vector2d)obj;
        return x == other.getX() && y == other.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
