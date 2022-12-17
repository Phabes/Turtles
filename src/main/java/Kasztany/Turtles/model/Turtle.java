package Kasztany.Turtles.model;

import java.util.Optional;

public class Turtle {
    private String name;
    private String color;
    private int points;
    private Optional<Turtle> turtleOnBack;
    private Optional<Turtle> turtleOnBottom;
    private Field currentField;

    public Turtle(String name, String color, Field field) {
        this.name = name;
        this.color = color;
        this.currentField = field;
        this.points = 0;
        this.turtleOnBack = Optional.empty();
        this.turtleOnBottom = Optional.empty();
    }

    public Field getCurrentField() {
        return currentField;
    }

    public int getPoints() {
        return points;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public Optional<Turtle> getTurtleOnBack() {
        return turtleOnBack;
    }

    public Optional<Turtle> getTurtleOnBottom() {
        return turtleOnBottom;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentField(Field currentField) {
        this.currentField = currentField;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void setTurtleOnBack(Turtle turtle) {
        this.turtleOnBack = Optional.of(turtle);
    }

    public void freeTurtleBack() {
        this.turtleOnBack = Optional.empty();
    }

    public void setTurtleOnBottom(Turtle turtle) {
        this.turtleOnBottom = Optional.of(turtle);
    }

    public void freeTurtleBottom() {
        this.turtleOnBottom = Optional.empty();
    }

    public void move(Field nextField) {
        Field prevField = this.currentField;
        Optional<Turtle> turtleToStick = nextField.getTopTurtle();
        setCurrentField(nextField);
        if (turtleOnBottom.isPresent()) {
            if (turtleOnBack.isPresent()) {
                turtleOnBottom.get().setTurtleOnBack(turtleOnBack.get());
                turtleOnBack.get().setTurtleOnBottom(turtleOnBottom.get());
            } else {
                turtleOnBottom.get().freeTurtleBack();
            }
        } else {
            if (turtleOnBack.isPresent()) {
                turtleOnBack.get().freeTurtleBottom();
                prevField.linkTurtle(turtleOnBack.get());
            } else {
                prevField.freeField();
            }
        }
        if (turtleToStick.isPresent()) {
            setTurtleOnBottom(turtleToStick.get());
            turtleToStick.get().setTurtleOnBack(this);
        } else {
            nextField.linkTurtle(this);
            freeTurtleBottom();
        }
        Optional<Turtle> turtleToMove = turtleOnBack;
        freeTurtleBack();
        turtleToMove.ifPresent(turtle -> turtle.move(nextField));
    }

    public void eat(Fruit fruit) {
        System.out.println(name + " is eating fruit with " + fruit.getPoints() + " points");
        points += fruit.getPoints();
    }

    public void linkTurtle(Turtle bottomTurtle) {
        this.turtleOnBottom = Optional.of(bottomTurtle);
        turtleOnBottom.ifPresent(turtle -> turtle.setTurtleOnBack(this));
    }

    public void addPoints(int points) {
        this.points += points;
    }
}
