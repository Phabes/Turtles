package Kasztany.Turtles.model;

import java.util.Objects;

import static java.lang.Math.max;


public record Vector2d(int x, int y) {

    public Vector2d() {
        this(0,0);
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }


    public Vector2d setMaximal(Vector2d vector) {
        return new Vector2d(max(vector.x, this.x), max(vector.y, this.y));
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
