package Kasztany.Turtles.model;

import Kasztany.Turtles.parser.MapParser;
import Kasztany.Turtles.persistence.GameLog;
import Kasztany.Turtles.persistence.GameLogRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.io.*;
import java.util.*;

@Component
@Scope("prototype")
public class Board {
    private final Neighbourhood neighbourhood;
    private final ArrayList<Turtle> turtles;
    private Vector2d maxVector = new Vector2d();
    private Field lastField;
    private Field startField;
    private final GameLogRepository gameLogRepository;

    public Board(GameLogRepository repository) {
        this.gameLogRepository = repository;
        this.neighbourhood = new Neighbourhood();
        this.turtles = new ArrayList<>();
    }

    public void addFields(File map) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(map));
        MapParser mapParser = new MapParser();

        Field startField = mapParser.parseMapLine(bufferedReader.readLine());
        maxVector = maxVector.setMaximal(startField.getPosition());
        neighbourhood.addField(startField.getPosition(), startField);
        this.startField = startField;

        bufferedReader.lines().forEach(line -> {
            System.out.println(line);
            Field field = mapParser.parseMapLine(line);
            if (field.getPossibleDirections().isEmpty()) {
                this.lastField = field;
            }
            maxVector = maxVector.setMaximal(field.getPosition());
            neighbourhood.addField(field.getPosition(), field);
        });

    }

    public void addTurtlesFromHashMap(HashMap<Integer, List<String>> players) {
        Vector2d startVector = startField.getPosition();
        for (int key : players.keySet()) {
            turtles.add(new Turtle(players.get(key).get(0), players.get(key).get(1), neighbourhood.getFieldByVector(startVector)));
        }

        for(Turtle turtle: turtles){
            startField.addTurtle(turtle);
        }
    }

    public Neighbourhood getNeighbourhood() {
        return neighbourhood;
    }

    public Field getStartingField() {
        return this.neighbourhood.getFieldByVector(new Vector2d());
    }

    public Field getLastField() {
        return lastField;
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

    public GameLogRepository getGameLogRepository() {
        return gameLogRepository;
    }

    public void saveGameLog(int winnerIndex) {
        Turtle winner = turtles.get(winnerIndex);
        GameLog gameLog = new GameLog(turtles.size(), neighbourhood.getWholeNeighbourhood().size(), winner.getName(), winner.getPoints());
        gameLogRepository.save(gameLog);
    }

    public Turtle findWinner() {
        int winnerPoints = turtles.size() * 5;

        Turtle currTurtle = lastField.getTopTurtle().orElse(null);
        while (currTurtle != null) {
            currTurtle.addPoints(winnerPoints);
            winnerPoints -= 5;
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
