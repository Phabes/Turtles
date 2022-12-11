package Kasztany.Turtles.model;

import Kasztany.Turtles.persistence.GameLog;
import Kasztany.Turtles.persistence.GameLogRepository;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Board {
    private final Neighbourhood neighbourhood;
    private final ArrayList<Turtle> turtles;
    private final Vector maxVector = new Vector();

    private Field lastField;
    private final GameLogRepository gameLogRepository;

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
        this.lastField = neighbourhood.getFieldByVector(new Vector(fieldsNum - 1, 0));

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

    public Turtle findWinner(){
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
        saveGameLog(this.turtles.indexOf(winningTurtle));
        return winningTurtle;
    }

    public Boolean isGameEnd() {
        return lastField.hasTurtle();
    }

}
