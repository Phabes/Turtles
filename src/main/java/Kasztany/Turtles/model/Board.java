package Kasztany.Turtles.model;

import Kasztany.Turtles.persistence.GameLog;
import Kasztany.Turtles.persistence.GameLogRepository;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class Board {
    private final Neighbourhood neighbourhood;
    private final ArrayList<Turtle> turtles;
    private final Vector2d maxVector = new Vector2d();
    private Field lastField;
    private final GameLogRepository gameLogRepository;

    public Board(GameLogRepository repository) {
        this.gameLogRepository = repository;
        this.neighbourhood = new Neighbourhood();
        this.turtles = new ArrayList<>();
    }

    public void addFields(int boardSize) {
        for (int i = 0; i < boardSize; i++) {
            Vector2d currVec = new Vector2d(i, 0);
            maxVector.setMaximal(currVec);
            neighbourhood.addField(currVec, new Field(i, currVec));
        }
        this.lastField = neighbourhood.getFieldByVector(new Vector2d(boardSize - 1, 0));
    }

    public void addTurtlesFromHashMap(HashMap<Integer, List<String>> players) {
        Vector2d startVector = new Vector2d();
        for (int key : players.keySet()) {
            turtles.add(new Turtle(players.get(key).get(0), players.get(key).get(1), neighbourhood.getFieldByVector(startVector)));
        }

        neighbourhood.getFieldByVector(startVector).linkTurtle(turtles.get(0));

        for (int i = 1; i < turtles.size(); i++) {
            turtles.get(i).linkTurtle(turtles.get(i - 1));
        }
    }

    public void addRandomFruits(int boardSize) {
        int i = 0;
        for (Field field : neighbourhood.getFields()) {
            if (i % 3 == 2) {
                field.addFruit(i);
            }
            i += 1;
        }
    }

    public Neighbourhood getNeighbourhood() {
        return neighbourhood;
    }

    public void addTurtle(Turtle turtle) {
        turtles.add(turtle);
    }

    public Field getStartingField() {
        return this.neighbourhood.getFieldByVector(new Vector2d());
    }

    public Vector2d getMaxVector() {
        return maxVector;
    }

    public ArrayList<Turtle> getTurtles() {
        return turtles;
    }

    public Field getFieldForTurtleMove(Vector2d turtlePosition, Direction direction) {
        Vector2d nextTurtlePosition = turtlePosition.add(direction.toVector());
        return neighbourhood.getFieldByVector(nextTurtlePosition);
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
        saveGameLog(this.turtles.indexOf(winningTurtle));
        return winningTurtle;
    }

    public Boolean isGameEnd() {
        return lastField.hasTurtle();
    }
}
