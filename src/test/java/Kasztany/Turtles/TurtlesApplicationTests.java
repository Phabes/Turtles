package Kasztany.Turtles;

import Kasztany.Turtles.model.*;
import Kasztany.Turtles.persistence.GameLogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TurtlesApplicationTests {
    @Autowired
    Board board;
    @Autowired
    Neighbourhood neighbourhood;


    @Test
    void testTest() {
        assertTrue(true);
    }

    @Test
    void turtleConnectionTest() {
        Field field = new Field(new Vector2d(0, 0));
        Turtle turtle1 = new Turtle("Player1", "#0000000", field);
        Turtle turtle2 = new Turtle("Player2", "#0000000", field);
        field.linkTurtle(turtle2);
        turtle1.linkTurtle(turtle2);
        assertEquals(turtle1.getTurtleOnBottom().orElse(null), turtle2);
        assertEquals(turtle2.getTurtleOnBack().orElse(null), turtle1);
    }

    @Test
    void fieldConnectionTest() {
        Field field = new Field(new Vector2d(0, 0));
        Turtle turtle1 = new Turtle("Player1", "#0000000", field);
        Turtle turtle2 = new Turtle("Player2", "#0000000", field);
        Turtle turtle3 = new Turtle("Player3", "#0000000", field);
        turtle1.linkTurtle(turtle2);
        turtle2.linkTurtle(turtle3);
        field.linkTurtle(turtle3);
        assertEquals(field.getTopTurtle().orElse(null), turtle1);
    }

    @Test
    void dbTest() throws IOException {
        List<String> playersNames = new ArrayList<>();
        HashMap<Integer, String> indexPlayers = new HashMap<>();
        playersNames.add("Player1");
        playersNames.add("Player2");
        playersNames.add("Player3");
        playersNames.add("Player4");
        indexPlayers.put(0, "#000000");
        indexPlayers.put(1, "#000000");
        indexPlayers.put(2, "#000000");
        indexPlayers.put(3, "#000000");

        HashMap<Integer, List<String>> players = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            players.put(i, List.of(playersNames.get(i), indexPlayers.get(i)));
        }

        Resource resource = new ClassPathResource("/map/map0");
        File map = resource.getFile();
        board.addFields(map);
        board.addTurtlesFromHashMap(players);
        GameLogRepository repository = board.getGameLogRepository();
        long prevRepositoryCount = repository.count();
        board.saveGameLog(0);
        assertEquals(1, repository.count() - prevRepositoryCount);
    }

    @Test
    void vector2dMaximalTest(){
        Vector2d vector1 = new Vector2d(0,0);
        Vector2d vector2 = new Vector2d(1,0);
        Vector2d vector3 = new Vector2d(1,1);

        Vector2d resultVec1 = vector1.setMaximal(vector2);
        assertEquals(new Vector2d(1,0), resultVec1);

        Vector2d resultVec2 = resultVec1.setMaximal(vector3);
        assertEquals(new Vector2d(1,1), resultVec2);
    }

    @Test
    void vector2dAddTest(){
        Vector2d vector = new Vector2d(0,0);
        Vector2d resultVec1 = vector.add(Direction.EAST.toVector());
        assertEquals(resultVec1, new Vector2d(1,0));
        Vector2d resultVec2 = resultVec1.add(Direction.EAST.toVector());
        assertEquals(resultVec2, new Vector2d(2,0));
        Vector2d resultVec3 = resultVec2.add(Direction.NORTH.toVector());
        assertEquals(resultVec3, new Vector2d(2,1));
    }

    @Test
    void neighbourFields() throws IOException {
        Resource resource = new ClassPathResource("/map/maptest");
        File map = resource.getFile();
        board.addFields(map);

        Field firstField = board.getStartingField();
        ArrayList<Field> possibleFields = new ArrayList<>();
        assertEquals(firstField.getPossibleDirections().size(),2);

        for (Direction direction : firstField.getPossibleDirections()) {
            Field possibleField = board.getFieldForTurtleMove(firstField.getPosition(), direction);
            possibleFields.add(possibleField);
        }

        assertEquals(firstField.getPossibleDirections().size(), possibleFields.size());

    }

    @Test
    void fruitEatingTest(){
        int fruit_points = 5;
        Field field = new Field(new Vector2d(0, 0));
        Turtle turtle = new Turtle("Player", "#0000000", field);
        Fruit fruit = new Fruit(fruit_points);
        assertEquals(0, turtle.getPoints());
        turtle.eat(fruit);
        assertEquals(fruit_points, turtle.getPoints());
    }

    @Test
    void multipleTurtlesFruitEatingTest() throws IOException {
        List<String> playersNames = new ArrayList<>();
        HashMap<Integer, String> indexPlayers = new HashMap<>();
        playersNames.add("Player1");
        playersNames.add("Player2");
        playersNames.add("Player3");
        indexPlayers.put(0, "#000000");
        indexPlayers.put(1, "#000000");
        indexPlayers.put(2, "#000000");

        HashMap<Integer, List<String>> players = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            players.put(i, List.of(playersNames.get(i), indexPlayers.get(i)));
        }

        Resource resource = new ClassPathResource("/map/maptest");
        File map = resource.getFile();
        board.addFields(map);
        board.addTurtlesFromHashMap(players);

        Field firstField = board.getStartingField();
        ArrayList<Turtle> turtles = board.getTurtles();

        Turtle turtle1 = firstField.getBottomTurtle().orElse(null);
        assertNotNull(turtle1);
        Field nextField = board.getFieldForTurtleMove(firstField.getPosition(), firstField.getPossibleDirections().get(0));
        turtle1.move(nextField);
        Fruit nextFieldFruit = nextField.getFruit().orElse(null);
        assertNotNull(nextFieldFruit);
        turtle1.eat(nextFieldFruit);
        nextField.deleteFruit();
        assertNull(nextField.getFruit().orElse(null));

        assertEquals(nextField.getTurtlesNumber(), 3);

        for(Turtle turtle : turtles){
            if( turtle.equals(turtle1)){
                assertEquals(turtle.getPoints(), 10);
            }else{
                assertEquals(turtle.getPoints(), 0);
            }
        }

    }

}
