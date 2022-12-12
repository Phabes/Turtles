package Kasztany.Turtles.model;

import java.util.Objects;

public class Vector2d {
    private int x;
    private int y;

    public Vector2d() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    protected Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public void setMaximal(Vector2d vector) {
        if (vector.x > x)
            x = vector.x;
        if (vector.y > y)
            y = vector.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Vector2d that))
            return false;
        return Objects.equals(this.x, that.x) && Objects.equals(this.y, that.y);
    }

    @Override
    public String toString() {
        return "Vector {" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}
