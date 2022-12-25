package Kasztany.Turtles.controller;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Kasztany.Turtles.FXMLLoaderProvider;
import Kasztany.Turtles.settings.GlobalSettings;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

@Controller
public class GameSettings {
    private final FXMLLoaderProvider loaderProvider;

    public GameSettings(FXMLLoaderProvider loaderProvider) {
        this.loaderProvider = loaderProvider;
    }

    @FXML
    private TextField numberOfPlayers;
    @FXML
    private TextField boardName;
    @FXML
    private Text errorMessage;

    @FXML
    private void handleStartClick(ActionEvent event) throws IOException {
        Resource resource = new ClassPathResource("/map/" + boardName.getText());
        if (resource.exists()) {
            File map = resource.getFile();
            FXMLLoader loader = loaderProvider.getLoader(getClass().getResource("/view/PlayersSettings.fxml"));
            Parent root = loader.load();
            PlayersSettings playersSettings = loader.getController();
            playersSettings.receiveData(numberOfPlayers.getText(), map);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Players Configuration");
            stage.setScene(scene);
            stage.show();
            GlobalSettings.setScreenInTheMiddle(stage);
        } else {
            errorMessage.setText("No such map in resources");
        }

    }
}
