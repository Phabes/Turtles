package Kasztany.Turtles.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

public class Field {
    private final String id;
    private final Vector2d position;

    private final LinkedList<Turtle> turtles;
    private Optional<Fruit> fruit;
    private final ArrayList<Direction> possibleForwardDirections = new ArrayList<>();
    private final ArrayList<Direction> possibleBackwardDirections = new ArrayList<>();

    public Field(Vector2d position) {
        this.id = "field-" + position.x() + "-" + position.y();
        this.position = position;
        this.turtles = new LinkedList<>();
        this.fruit = Optional.empty();
    }

    public void addPossibleForwardDirections(Direction direction) {
        possibleForwardDirections.add(direction);
    }

    public void addPossibleBackwardDirections(Direction direction) {
        possibleBackwardDirections.add(direction);
    }

    public String getId() {
        return id;
    }

    public LinkedList<Turtle> getTurtles() {
        return turtles;
    }

    public ArrayList<Direction> getPossibleForwardDirections() {
        return possibleForwardDirections;
    }

    public ArrayList<Direction> getPossibleBackwardDirections() {
        return possibleBackwardDirections;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void addTurtle(Turtle turtle) {
        this.turtles.add(turtle);
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
        return turtles.size();
    }

    public Optional<Turtle> getBottomTurtle() {
        if (!turtles.isEmpty()) {
            return Optional.of(turtles.peek());
        } else return Optional.empty();
    }

    public Optional<Turtle> getTopTurtle() {
        if (turtles.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(turtles.peekLast());
    }

    public Boolean hasTurtle() {
        return !this.turtles.isEmpty();
    }

    public Optional<Turtle> getTurtleWithIndex(int index) {
        if (index >= turtles.size() || index < 0) {
            return Optional.empty();
        }
        return Optional.of(turtles.get(index));
    }

    public int getIndexOfTurtle(Turtle turtle) {
        return turtles.indexOf(turtle);
    }

    public void addTurtles(Collection<Turtle> turtlesToAdd) {
        for (Turtle turtle : turtlesToAdd) {
            turtle.setCurrentField(this);
            this.turtles.add(turtle);
        }
    }

    public LinkedList<Turtle> getAndDeleteTurtlesOverGiven(Turtle turtle) {
        LinkedList<Turtle> connectedTurtles = new LinkedList<>();
        int index = getIndexOfTurtle(turtle);
        while (index < getTurtlesNumber()) {
            connectedTurtles.add(turtles.get(index));
            index += 1;
        }

        for (Turtle turtleToDel : connectedTurtles) {
            turtles.remove(turtleToDel);
        }
        return connectedTurtles;
    }

    public boolean moveTurtleDown(Turtle turtle) {
        if (this.turtles.remove(turtle)) {
            this.turtles.addFirst(turtle);
            return true;
        }
        return false;
    }

    public boolean moveTurtleTop(Turtle turtle) {
        if (this.turtles.remove(turtle)) {
            this.turtles.addLast(turtle);
            return true;
        }
        return false;
    }

    public boolean swapTurtles(Turtle turtle1, Turtle turtle2) {
        if (this.turtles.contains(turtle1) && this.turtles.contains(turtle2)) {
            int indexTurtle1 = this.turtles.indexOf(turtle1);
            int indexTurtle2 = this.turtles.indexOf(turtle2);
            this.turtles.remove(turtle1);
            this.turtles.add(indexTurtle2, turtle1);
            this.turtles.remove(turtle2);
            this.turtles.add(indexTurtle1, turtle2);
            return true;
        }
        return false;
    }


}
