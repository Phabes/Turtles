package Kasztany.Turtles.model;

import java.util.ArrayList;


public class Board {
    private final ArrayList<Field> fields;
    private final ArrayList<Turtle> turtles;

    public Board(int fieldsNum){
        this.fields = new ArrayList<>();
        this.turtles = new ArrayList<>();
        for(int i = 0; i < fieldsNum; i++){
            Vector currVec = new Vector(0,i);
            this.fields.add(new Field(i, currVec));
        }
    }

    public void addTurtle(Turtle turtle){
        this.turtles.add(turtle);
    }

    public Field getStartingField(){
        return this.fields.get(0);
    }


}
