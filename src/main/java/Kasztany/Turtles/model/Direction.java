package Kasztany.Turtles.model;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public Vector toVector(){
        return switch (this){
            case NORTH -> new Vector(0, 1);
            case EAST -> new Vector(1, 0);
            case SOUTH -> new Vector(0, -1);
            case WEST -> new Vector(-1, 0);
        };
    }
}
