package Kasztany.Turtles.model.cards;

import Kasztany.Turtles.gui.ImageBoxElement;
import Kasztany.Turtles.model.Board;
import Kasztany.Turtles.model.Direction;
import Kasztany.Turtles.model.Field;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Card {
    private final Board board;
    private ImageBoxElement icon;
    private String header;
    private String additionalInfo;


    public Card(Board board) {
        this.board = board;
    }

    public void doTask() {

    }

    private ArrayList<Field> getPossibleFieldsToMove(Field firstField, int steps, boolean moveForward) {
        ArrayList<Field> possibleFields = new ArrayList<>();
        ArrayList<Field> fieldsToCheck = new ArrayList<>();
        ArrayList<Field> nextFieldsToCheck = new ArrayList<>();
        fieldsToCheck.add(firstField);
        for (int i = 0; i < steps; i++) {
            for (Field field : fieldsToCheck) {
                ArrayList<Direction> possibleDirections;
                if (moveForward)
                    possibleDirections = field.getPossibleForwardDirections();
                else
                    possibleDirections = field.getPossibleBackwardDirections();
                for (Direction direction : possibleDirections) {
                    Field nextField = board.getFieldForTurtleMove(field.getPosition(), direction);
                    if (nextField != null) {
                        if (i == steps - 1) {
                            possibleFields.add(nextField);
                        } else {
                            nextFieldsToCheck.add(nextField);
                        }
                    }
                }
            }
            Collections.copy(fieldsToCheck, nextFieldsToCheck);
            nextFieldsToCheck.clear();
        }
        return possibleFields;
    }

    public void setHeader(String text) {
        this.header = text;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String getHeader() {
        return header;
    }

    public void setIcon(String iconName) throws FileNotFoundException {
        this.icon = new ImageBoxElement(iconName);
    }

    public ImageBoxElement getIcon() {
        return icon;
    }

    public HBox getInfo(){
        return new HBox(new Text(additionalInfo));
    }
}