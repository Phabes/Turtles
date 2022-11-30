package Kasztany.Turtles.model;

import java.util.Optional;

public class Turtle {
    private String name;
    private String color;
    private Integer points;
    private Optional<Turtle> turtleOnBack;
    private Optional<Turtle> turtleOnBottom;
    private Field currentField;

    public Turtle(String name, String color, Field field){
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

    public Integer getPoints() {
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

    public void freeTurtleBack(){
        this.turtleOnBack = Optional.empty();
    }

    public void setTurtleOnBottom(Turtle turtle) {
        this.turtleOnBottom = Optional.of(turtle);
    }

    public void freeTurtleBottom(){
        this.turtleOnBottom = Optional.empty();
    }

    public void move(){
        Field prevField = this.currentField;
        this.currentField = currentField.getFirstNeigbourField();
        Optional<Turtle> turtleToStick = this.currentField.getTopTurtle();

        if(turtleToStick.isPresent()){
            turtleToStick.get().setTurtleOnBack(this);
            if(this.turtleOnBottom.isPresent()){
                this.turtleOnBottom.get().freeTurtleBack();
            }else{
                prevField.freeField();
            }
            this.turtleOnBottom.get().setTurtleOnBottom(turtleToStick.get());
            //
        }else{
            this.currentField.linkTurtle(this);
        }
    }

    public void linkTurtle(Optional<Turtle> bottomTurtle){
        this.turtleOnBottom = bottomTurtle;
        bottomTurtle.ifPresent(turtle -> turtle.setTurtleOnBack(this));
    }
}
