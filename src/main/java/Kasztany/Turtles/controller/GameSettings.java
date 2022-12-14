package Kasztany.Turtles.controller;

import Kasztany.Turtles.settings.GlobalSettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;

@Controller
public class GameSettings {
    private final GlobalSettings globalSettings = new GlobalSettings();
    @FXML
    private TextField numberOfPlayers;
    @FXML
    private TextField boardName;
    @FXML
    private Text errorMessage;

    @FXML
    private void handleStartClick(ActionEvent event) throws IOException {
        Resource resource = new ClassPathResource("/map/"+boardName.getText());
        if(resource.exists()){
            File map=resource.getFile();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlayersSettings.fxml"));
            Parent root = loader.load();
            PlayersSettings playersSettings = loader.getController();
            playersSettings.receiveData(numberOfPlayers.getText(), map);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Players Configuration");
            stage.setScene(scene);
            stage.show();
            globalSettings.setScreenInTheMiddle(stage);
        }else{
            errorMessage.setText("No such map in resources");
        }

    }
}
