package Kasztany.Turtles.model;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public Vector2d toVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH -> new Vector2d(0, -1);
            case WEST -> new Vector2d(-1, 0);
        };
    }
}
