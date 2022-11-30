package Kasztany.Turtles.model;

import java.util.ArrayList;
import java.util.Optional;

public class Field {
    private final Integer id;
    private ArrayList<Field> neighbourFields;
    private Optional<Turtle> turtle;
    private Vector position;

    public Field(Integer id, Vector position){
        this.id = id;
        this.position = position;
        this.neighbourFields = new ArrayList<Field>();
        this.turtle = Optional.empty();
    }

    public ArrayList<Field> getNeighbourFields() {
        return neighbourFields;
    }

    public Vector getPosition() {
        return position;
    }

    public void linkTurtle(Turtle turtle){
        this.turtle = Optional.of(turtle);
    }

    public void freeField(){
        this.turtle = Optional.empty();
    }

    public void linkField(Field next_field){
        this.neighbourFields.add(next_field);
    }

    public Field getFirstNeigbourField(){
        return neighbourFields.get(0);
    }

    public int getTurtlesNumber(){
        int turtles=0;
        if(this.turtle.isEmpty()){
            return turtles;
        }
        Turtle loopTurtle = this.turtle.get();
        turtles++;
        while(loopTurtle.getTurtleOnBack().isPresent()){
            turtles++;
            loopTurtle = loopTurtle.getTurtleOnBack().get();
        }
        return turtles;
    }

    public Optional<Turtle> getBottomTurtle() {
        return turtle;
    }

    public Optional<Turtle> getTopTurtle(){
        if(this.turtle.isEmpty()){
            return Optional.empty();
        }
        Turtle loopTurtle = this.turtle.get();
        while(loopTurtle.getTurtleOnBack().isPresent()){
            loopTurtle = loopTurtle.getTurtleOnBack().get();
        }
        return Optional.of(loopTurtle);
    }
}
