package Kasztany.Turtles.model;

import java.util.Objects;

import static java.lang.Math.max;


public class Vector2d {
    private final int x;
    private final int y;

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

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }


    public Vector2d setMaximal(Vector2d vector){
        return new Vector2d(max(vector.x, this.x), max(vector.y, this.y));
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
