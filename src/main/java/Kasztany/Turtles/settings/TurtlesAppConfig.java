package Kasztany.Turtles.settings;

import Kasztany.Turtles.controller.BoardController;
import Kasztany.Turtles.controller.GameSettings;
import Kasztany.Turtles.gui.App;
import Kasztany.Turtles.model.Board;
import Kasztany.Turtles.parser.OptionsParser;
import Kasztany.Turtles.persistence.GameLogRepository;
import javafx.fxml.FXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;


@Configuration
@ComponentScan("Kasztany.Turtles")
public class TurtlesAppConfig {
    @Autowired
    GameLogRepository repository;

    @Bean
    public Board board() {
        return new Board(this.repository);
    }

    @Bean
    public GlobalSettings globalSettings(){
        return new GlobalSettings();
    }

    @Bean
    public OptionsParser optionsParser(){
        return new OptionsParser();
    }

    @Bean
    public FXMLLoader loader(){
        return new FXMLLoader();
    }
}
