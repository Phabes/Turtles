package Kasztany.Turtles.model;

import Kasztany.Turtles.persistence.GameLog;
import Kasztany.Turtles.persistence.GameLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class Board {
    private final Neighbourhood neighbourhood;
    private final ArrayList<Turtle> turtles;
    private final Vector maxVector = new Vector();

    private Field lastField;
    private GameLogRepository gameLogRepository;

    public Board(HashMap<Integer, List<String>> players, int fieldsNum) {
        this.neighbourhood = new Neighbourhood();
        this.turtles = new ArrayList<>();

        for (int i = 0; i < fieldsNum; i++) {
            Vector currVec = new Vector(i, 0);
            maxVector.setMaximal(currVec);
            Field field = new Field(i, currVec, neighbourhood);
            neighbourhood.addField(currVec, field);
        }

        this.lastField = neighbourhood.getFieldByVector(new Vector(fieldsNum - 1, 0));

        Vector startVector = new Vector();

        for (int key : players.keySet()) {
            System.out.println(key + " " + players.get(key));
            this.turtles.add(new Turtle(players.get(key).get(0), players.get(key).get(1), neighbourhood.getFieldByVector(startVector)));
        }

        neighbourhood.getFieldByVector(startVector).linkTurtle(turtles.get(0));

        for (int i = 1; i < turtles.size(); i++) {
            turtles.get(i).linkTurtle(turtles.get(i - 1));
        }
    }

    @Autowired
    public Board(GameLogRepository repository) {
        this.gameLogRepository = repository;
        this.neighbourhood = new Neighbourhood();
        this.turtles = new ArrayList<>();
    }

    public void addFields(int fieldsNum) {
        for (int i = 0; i < fieldsNum; i++) {
            Vector currVec = new Vector(i, 0);
            maxVector.setMaximal(currVec);
            neighbourhood.addField(currVec, new Field(i, currVec, neighbourhood));
        }
        Vector currVec1 = new Vector(2, 1);
        maxVector.setMaximal(currVec1);
        neighbourhood.addField(currVec1, new Field(11, currVec1, neighbourhood));
        Vector currVec2 = new Vector(2, 2);
        maxVector.setMaximal(currVec2);
        neighbourhood.addField(currVec2, new Field(12, currVec2, neighbourhood));
        Vector currVec3 = new Vector(3, 2);
        maxVector.setMaximal(currVec3);
        neighbourhood.addField(currVec3, new Field(13, currVec3, neighbourhood));
        Vector currVec4 = new Vector(4, 2);
        maxVector.setMaximal(currVec4);
        neighbourhood.addField(currVec4, new Field(14, currVec4, neighbourhood));
        Vector currVec5 = new Vector(4, 1);
        maxVector.setMaximal(currVec5);
        neighbourhood.addField(currVec5, new Field(15, currVec5, neighbourhood));
    }

    public void addTurtlesFromHashMap(HashMap<Integer, List<String>> players) {
        Vector startVector = new Vector();
        for (int key : players.keySet()) {
            turtles.add(new Turtle(players.get(key).get(0), players.get(key).get(1), neighbourhood.getFieldByVector(startVector)));
        }

        neighbourhood.getFieldByVector(startVector).linkTurtle(turtles.get(0));

        for (int i = 1; i < turtles.size(); i++) {
            turtles.get(i).linkTurtle(turtles.get(i - 1));
        }
    }

    public void addTurtle(Turtle turtle) {
        turtles.add(turtle);
    }

    public Field getStartingField() {
        return this.neighbourhood.getFieldByVector(new Vector());
    }

    public Vector getMaxVector() {
        return maxVector;
    }

    public Neighbourhood getNeighbourhood() {
        return neighbourhood;
    }

    public ArrayList<Turtle> getTurtles() {
        return turtles;
    }

    @Autowired
    public GameLogRepository getGameLogRepository() {
        return gameLogRepository;
    }

    public void saveGameLog(int winnerIndex) {
        Turtle winner = turtles.get(winnerIndex);
        GameLog gameLog = new GameLog(turtles.size(), neighbourhood.getWholeNeighbourhood().size(), winner.getName(), winner.getPoints());
        gameLogRepository.save(gameLog);
    }

    public Turtle findWinner() {
        int winnerPoints = 10;

        Turtle currTurtle = lastField.getTopTurtle().orElse(null);
        while (currTurtle != null) {
            currTurtle.addPoints(winnerPoints);
            winnerPoints--;
            currTurtle = currTurtle.getTurtleOnBottom().orElse(null);
        }


        Turtle winningTurtle = this.turtles.get(0);
        for (Turtle turtle : this.turtles) {
            if (turtle.getPoints() > winningTurtle.getPoints()) {
                winningTurtle = turtle;
            }
        }
        //saveGameLog(this.turtles.indexOf(winningTurtle));
        return winningTurtle;
    }

    public Boolean isGameEnd() {
        return lastField.hasTurtle();
    }

}
