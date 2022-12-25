package Kasztany.Turtles.model;

import java.util.Optional;

public class Turtle {
    private String name;
    private String color;
    private int points;
    private Field currentField;

    public Turtle(String name, String color, Field field) {
        this.name = name;
        this.color = color;
        this.currentField = field;
        this.points = 0;
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
        int index = currentField.getIndexOfTurtle(this);
        return currentField.getTurtleWithIndex(index + 1);
    }

    public Optional<Turtle> getTurtleOnBottom() {
        int index = currentField.getIndexOfTurtle(this);
        return currentField.getTurtleWithIndex(index - 1);
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void setCurrentField(Field currentField) {
        this.currentField = currentField;
    }

    public void move(Field nextField) {
        Field prevField = this.currentField;
        this.currentField = nextField;
        this.currentField.addTurtles(prevField.getAndDeleteTurtlesOverGiven(this));
    }

    public void eat(Fruit fruit) {
        System.out.println(name + " is eating fruit with " + fruit.getPoints() + " points");
        points += fruit.getPoints();
    }

    public void addPoints(int points) {
        this.points += points;
    }
}
