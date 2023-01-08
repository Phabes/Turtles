package Kasztany.Turtles.model.cards;

import Kasztany.Turtles.model.Board;
import Kasztany.Turtles.model.Direction;
import Kasztany.Turtles.model.Field;
import Kasztany.Turtles.model.Turtle;
import Kasztany.Turtles.settings.GlobalSettings;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

public class LastTurtleMoveCard extends Card {
    private final int steps;

    public LastTurtleMoveCard(Board board) {
        super(board);
        this.steps = GlobalSettings.getRandomNumber(1, 3);
        super.setFieldRequired(true);
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


    public boolean doTask(ArrayDeque<Turtle> choosedTurtles, Field choosedField) {
        System.out.println("Did sth");
        return true;
    }

    @Override
    public ArrayList<Turtle> getTurtles() {
        ArrayList<Turtle> turtles = new ArrayList<>();
        Turtle lastTurtle = getLastTurtle();
        if (lastTurtle != null)
            turtles.add(lastTurtle);
        return turtles;
    }

    private Turtle getLastTurtle() {

        ArrayList<Field> fieldsToCheck = new ArrayList<>();
        ArrayList<Field> nextFieldsToCheck = new ArrayList<>();
        fieldsToCheck.add(board.getStartingField());

        while (fieldsToCheck.size() > 0) {
            for (Field field : fieldsToCheck) {

                if (field.getBottomTurtle().isPresent())
                    return field.getBottomTurtle().get();
                ArrayList<Direction> possibleDirections;
                possibleDirections = field.getPossibleForwardDirections();
                for (Direction direction : possibleDirections) {
                    Field nextField = board.getFieldForTurtleMove(field.getPosition(), direction);
                    if (nextField != null) {
                        nextFieldsToCheck.add(nextField);
                    }
                }
            }
            Collections.copy(fieldsToCheck, nextFieldsToCheck);
            nextFieldsToCheck.clear();
        }
        return null;
    }

    @Override
    public ArrayList<Field> getFieldsToHighlight(Turtle turtle) {
        return super.getPossibleFieldsToMove(turtle, steps, true);
    }
}
