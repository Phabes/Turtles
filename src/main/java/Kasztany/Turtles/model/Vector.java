package Kasztany.Turtles.model;

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
}
