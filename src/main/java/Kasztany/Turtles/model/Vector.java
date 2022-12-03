package Kasztany.Turtles.model;

import org.springframework.stereotype.Service;

import java.util.Objects;

public class Vector {
    private Integer x;
    private Integer y;

    public Vector(){
        this.x = 0;
        this.y = 0;
    }

    public Vector(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }
    public void setMaximal(Vector vector){
        if(vector.x>x)
            x=vector.x;
        if(vector.y>y)
            y=vector.y;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Objects.equals(x, vector.x) && Objects.equals(y, vector.y);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}
