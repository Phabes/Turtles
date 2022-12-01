package Kasztany.Turtles.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


import java.time.LocalDate;

@Entity
public class GameLog {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer numberOfPlayers;

    private String fieldsNum;

    private String winnerName;

    private Integer winnerPoints;

    private LocalDate date;
}
