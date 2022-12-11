package Kasztany.Turtles.settings;

import Kasztany.Turtles.model.Board;
import Kasztany.Turtles.parser.OptionsParser;
import Kasztany.Turtles.persistence.GameLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan("Kasztany.Turtles")
public class TurtlesAppConfig {
    @Autowired(required = true)
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

}
