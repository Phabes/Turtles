package Kasztany.Turtles.gui;

import Kasztany.Turtles.settings.GlobalSettings;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageBoxElement {
    private final GlobalSettings globalSettings = new GlobalSettings();
    private final Image image;
    private int size;

    public ImageBoxElement(String pictureName) throws FileNotFoundException {
        this.image = new Image(new FileInputStream("src/main/resources/images/" + pictureName));
        this.size = globalSettings.getFruitSize();
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ImageView getImage() {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        return imageView;
    }
}
