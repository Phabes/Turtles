package Kasztany.Turtles.gui;

import Kasztany.Turtles.settings.GlobalSettings;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageBoxElement {
    private final Image image;
    private int size;

    public ImageBoxElement(String pictureName) throws FileNotFoundException {
        this.image = new Image(new FileInputStream("src/main/resources/images/" + pictureName));
        this.size = GlobalSettings.FRUIT_SIZE;
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
