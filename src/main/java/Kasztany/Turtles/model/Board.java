package Kasztany.Turtles.model;

import Kasztany.Turtles.persistence.GameLog;
import Kasztany.Turtles.persistence.GameLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Board {
    private final ArrayList<Field> fields;
    private final ArrayList<Turtle> turtles;
    private final Vector maxVetor = new Vector();
    @Autowired
    private GameLogRepository gameLogRepository;

    public Board(HashMap<Integer, List<String>>  players, int fieldsNum){
        this.fields = new ArrayList<>();
        this.turtles = new ArrayList<>();

        for(int i = 0; i < fieldsNum; i++){
            Vector currVec = new Vector(i,0);
            maxVetor.setMaximal(currVec);
            this.fields.add(new Field(i, currVec));
        }
        Vector currVec1 = new Vector(2,1);
        maxVetor.setMaximal(currVec1);
        this.fields.add(new Field(11, currVec1));
        Vector currVec2 = new Vector(2,2);
        maxVetor.setMaximal(currVec2);
        this.fields.add(new Field(12, currVec2));
        Vector currVec3 = new Vector(3,2);
        maxVetor.setMaximal(currVec3);
        this.fields.add(new Field(13, currVec3));
        Vector currVec4 = new Vector(4,2);
        maxVetor.setMaximal(currVec4);
        this.fields.add(new Field(13, currVec4));
        Vector currVec5 = new Vector(4,1);
        maxVetor.setMaximal(currVec5);
        this.fields.add(new Field(13, currVec5));

        for (int key : players.keySet() ) {
            System.out.println(key + " " + players.get(key));
            this.turtles.add(new Turtle(players.get(key).get(0),players.get(key).get(1),this.fields.get(0)));
        }

        this.fields.get(0).linkTurtle(this.turtles.get(0));
        this.turtles.get(1).linkTurtle(this.turtles.get(0));
        this.turtles.get(2).linkTurtle(this.turtles.get(1));
        this.turtles.get(3).linkTurtle(this.turtles.get(2));

        if (this.fields.get(0).getTopTurtle().isPresent())
            System.out.println(this.fields.get(0).getTopTurtle().get().getName());

    }

    public void addTurtle(Turtle turtle){
        this.turtles.add(turtle);
    }

    public Field getStartingField(){
        return this.fields.get(0);
    }

    public Vector getMaxVetor() {
        return maxVetor;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public ArrayList<Turtle> getTurtles() {
        return turtles;
    }

    public GameLogRepository getGameLogRepository() {
        return gameLogRepository;
    }

    public void saveGameLog(int winnerIndex){
        Turtle winner = turtles.get(winnerIndex);
        GameLog gameLog = new GameLog(turtles.size(), fields.size(), winner.getName(), winner.getPoints());
        gameLogRepository.save(gameLog);

    }

}
