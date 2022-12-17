package Kasztany.Turtles.persistence;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import java.time.LocalDate;

@Entity
public class GameLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer numberOfPlayers;
    private Integer fieldsNum;
    private String winnerName;
    private Integer winnerPoints;
    private LocalDate date;

    public GameLog(Integer numberOfPlayers, Integer fieldsNum, String winnerName, Integer winnerPoints) {
        this.numberOfPlayers = numberOfPlayers;
        this.fieldsNum = fieldsNum;
        this.winnerName = winnerName;
        this.winnerPoints = winnerPoints;
        this.date = LocalDate.now();
    }

    public GameLog() {
        this.numberOfPlayers = 0;
        this.fieldsNum = 0;
        this.winnerName = " ";
        this.winnerPoints = 0;
        this.date = LocalDate.now();
    }

    public Integer getId() {
        return id;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public Integer getFieldsNum() {
        return fieldsNum;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public Integer getWinnerPoints() {
        return winnerPoints;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setFieldsNum(Integer fieldsNum) {
        this.fieldsNum = fieldsNum;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public void setWinnerPoints(Integer winnerPoints) {
        this.winnerPoints = winnerPoints;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
