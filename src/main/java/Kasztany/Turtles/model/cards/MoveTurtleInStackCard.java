package Kasztany.Turtles.model.cards;

import Kasztany.Turtles.model.Board;
import Kasztany.Turtles.model.Field;
import Kasztany.Turtles.model.Turtle;
import Kasztany.Turtles.settings.GlobalSettings;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;


public class MoveTurtleInStackCard extends Card {
    private final String color;
    private final boolean toTop;

    public MoveTurtleInStackCard(Board board, ArrayList<String> availableColors) {
        super(board);
        super.setHeader("Move turtle in stack");
        this.color = availableColors.get(GlobalSettings.getRandomNumber(0, availableColors.size()));
        this.toTop = GlobalSettings.getRandomNumber(0, 2) == 0;

        try {
            if (toTop) {
                super.setAdditionalInfo("On top ");
                super.setIcon("arrow-u-up-left-bold.png");
            } else {
                super.setAdditionalInfo("On bottom ");
                super.setIcon("arrow-u-down-left-bold.png");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HBox getInfo() {
        HBox colorBox = new HBox();
        colorBox.setMinSize(GlobalSettings.COLOR_SIZE_ON_CARD, GlobalSettings.COLOR_SIZE_ON_CARD);
        colorBox.setStyle("-fx-background-color: #" + color);

        if (toTop) {
            return new HBox(new Text("Top"), colorBox);
        } else {
            return new HBox(new Text("Bottom"), colorBox);
        }

    }

    @Override
    public boolean doTask(ArrayDeque<Turtle> choosedTurtles, Field choosedField) {
//        Optional<Turtle> optionalTurtle = super.board.getTurtleWithColor(color);
//        Turtle turtle = optionalTurtle.orElse(null);
//        if(turtle == null){
//            return false;
//        }
        Turtle choosedTurtle = choosedTurtles.poll();
        assert choosedTurtle != null;
        Field field = choosedTurtle.getCurrentField();

        if (this.toTop) {
            return field.moveTurtleTop(choosedTurtle);
        } else {
            return field.moveTurtleDown(choosedTurtle);
        }
    }

    @Override
    public ArrayList<Turtle> getTurtles() {
        ArrayList<Turtle> turtles = new ArrayList<>();
        board.getTurtles().forEach(turtle -> {
            if (turtle.getColor().equals(color))
                turtles.add(turtle);
        });
        return turtles;
    }

    @Override
    public boolean changeTurtleDisabled() {
        return true;
    }
}
