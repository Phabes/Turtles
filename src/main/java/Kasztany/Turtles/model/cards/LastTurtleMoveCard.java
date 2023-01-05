package Kasztany.Turtles.model.cards;

import Kasztany.Turtles.model.Board;
import Kasztany.Turtles.settings.GlobalSettings;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;

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

    @Override
    public void doTask() {

    }
}
