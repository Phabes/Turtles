package Kasztany.Turtles.model;

import java.util.Optional;

public class Field {
    private final Integer id;
    private final Vector position;
    private final Neighbourhood neighbourhood;
    private Optional<Turtle> turtle;

    public Field(Integer id, Vector position, Neighbourhood neighbourhood) {
        this.id = id;
        this.position = position;
        this.neighbourhood = neighbourhood;
        this.turtle = Optional.empty();
    }

    public Vector getPosition() {
        return position;
    }

    public void linkTurtle(Turtle turtle) {
        this.turtle = Optional.of(turtle);
    }

    public void freeField() {
        this.turtle = Optional.empty();
    }

    public Optional<Field> getNeighbour(Direction direction) {
        Vector directionVector = direction.toVector();
        Vector neighbourPosition = position.add(directionVector);
        Field neighbourField = neighbourhood.getFieldByVector(neighbourPosition);
        return Optional.of(neighbourField);
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
