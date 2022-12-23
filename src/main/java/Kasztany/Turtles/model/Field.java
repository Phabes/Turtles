package Kasztany.Turtles.model;

import java.util.ArrayList;
import java.util.Optional;

public class Field {
    private final String id;
    private final Vector2d position;
    private Optional<Turtle> turtle;
    private Optional<Fruit> fruit;
    private ArrayList<Direction> possibleDirections = new ArrayList<>();

    public Field(Vector2d position) {
        this.id = "field-" + position.x() + "-" + position.y();
        this.position = position;
        this.turtle = Optional.empty();
        this.fruit = Optional.empty();
    }

    public void addPossibleDirection(Direction direction) {
        possibleDirections.add(direction);
    }

    public String getId() {
        return id;
    }

    public ArrayList<Direction> getPossibleDirections() {
        return possibleDirections;
    }

    public Vector2d getPosition() {
        return position;
    }


    public void linkTurtle(Turtle turtle) {
        this.turtle = Optional.of(turtle);
    }

    public void freeField() {
        turtle = Optional.empty();
    }

    public void addFruit(int points) {
        fruit = Optional.of(new Fruit(points));
    }

    public Optional<Fruit> getFruit() {
        return fruit;
    }

    public void deleteFruit() {
        fruit = Optional.empty();
    }

    public int getTurtlesNumber() {
        int turtles = 0;
        if (this.turtle.isEmpty()) {
            return turtles;
        }
        Turtle loopTurtle = this.turtle.get();
        turtles++;
        while (loopTurtle.getTurtleOnBack().isPresent()) {
            turtles++;
            loopTurtle = loopTurtle.getTurtleOnBack().get();
        }
        return turtles;
    }

    public Optional<Turtle> getBottomTurtle() {
        return turtle;
    }

    public Optional<Turtle> getTopTurtle() {
        if (this.turtle.isEmpty()) {
            return Optional.empty();
        }
        Turtle loopTurtle = this.turtle.get();
        while (loopTurtle.getTurtleOnBack().isPresent()) {
            loopTurtle = loopTurtle.getTurtleOnBack().get();
        }
        return Optional.of(loopTurtle);
    }

    public Boolean hasTurtle() {
        return this.turtle.isPresent();
    }
}
