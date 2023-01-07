package Kasztany.Turtles.model.cards;

import Kasztany.Turtles.model.Board;
import Kasztany.Turtles.model.Direction;
import Kasztany.Turtles.model.Field;
import Kasztany.Turtles.model.Turtle;
import Kasztany.Turtles.settings.GlobalSettings;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class LastTurtleMoveCard extends Card {
    private final int steps;

    public LastTurtleMoveCard(Board board) {
        super(board);
        this.steps = GlobalSettings.getRandomNumber(1, 3);
        super.setHeader("Move last turtle");
        super.setAdditionalInfo("Steps " + steps);
        try {
            super.setIcon("arrow-right-bold.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HBox getInfo() {
        return new HBox(new Text("Steps " + steps));
    }


    public ArrayList<Turtle> findLastTurtles() {

        ArrayList<Field> fieldsToCheck = new ArrayList<>();
        ArrayList<Field> nextFieldsToCheck = new ArrayList<>();

        ArrayList<Turtle> lastTurtles = new ArrayList<>();
        fieldsToCheck.add(board.getStartingField());

        while(lastTurtles.isEmpty()) {
            for (Field field : fieldsToCheck) {
                ArrayList<Direction> possibleDirections;
                possibleDirections = field.getPossibleForwardDirections();
                for (Direction direction : possibleDirections) {
                    Field nextField = board.getFieldForTurtleMove(field.getPosition(), direction);
                    if (nextField != null) {
                        if (nextField.hasTurtle()) {
                            lastTurtles.addAll(nextField.getTurtles());
                        } else {
                            nextFieldsToCheck.add(nextField);
                        }
                    }
                }
            }
            Collections.copy(fieldsToCheck, nextFieldsToCheck);
            nextFieldsToCheck.clear();
        }
        return lastTurtles;
    }


    @Override
    public void doTask() {
        ArrayList<Turtle> lastTurtles = this.findLastTurtles();

    }
}
