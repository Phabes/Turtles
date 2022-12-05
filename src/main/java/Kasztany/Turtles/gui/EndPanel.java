package Kasztany.Turtles.gui;

import Kasztany.Turtles.settings.GlobalSettings;
import Kasztany.Turtles.model.Turtle;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EndPanel {
    private final Stage endStage = new Stage();
    private GlobalSettings globalSettings = new GlobalSettings();

    public EndPanel(Turtle winner){
        Text winnerName = new Text("Winner: " + winner.getName());
        Text winnerPoints = new Text("Points: " + winner.getPoints());
        VBox box = new VBox(winnerName, winnerPoints);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(globalSettings.getOptionsSpace());
        Scene settingsScene = new Scene(box);
        endStage.setTitle("End Game");
        endStage.setScene(settingsScene);
        endStage.setWidth(globalSettings.getEndPanelWidth());
    }

    public void showPanel(){
        endStage.show();
    }
}
