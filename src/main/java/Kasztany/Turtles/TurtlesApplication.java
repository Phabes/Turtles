package Kasztany.Turtles;

import Kasztany.Turtles.gui.App;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TurtlesApplication {
	public static void main(String[] args) {
		Application.launch(App.class, args);
	}
}