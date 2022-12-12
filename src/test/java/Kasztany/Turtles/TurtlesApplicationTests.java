package Kasztany.Turtles;

import Kasztany.Turtles.model.*;
import Kasztany.Turtles.persistence.GameLogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TurtlesApplicationTests {
	@Autowired
	Board board;
	@Autowired
	Neighbourhood neighbourhood;
	@Test
	void contextLoads() {
	}

	@Test
	void testTest(){
		assertTrue(true);
	}

	@Test
	void turtleConnectionTest(){
		Field field = new Field(0, new Vector2d(0,0));
		Turtle turtle1 = new Turtle("Player1", "#0000000", field);
		Turtle turtle2 = new Turtle("Player2", "#0000000", field);
		field.linkTurtle(turtle2);
		turtle1.linkTurtle(turtle2);
		assertEquals(turtle1.getTurtleOnBottom().orElse(null), turtle2);
		assertEquals(turtle2.getTurtleOnBack().orElse(null), turtle1);
	}

	@Test
	void fieldConnectionTest(){
		Field field = new Field(0, new Vector2d(0,0));
		Turtle turtle1 = new Turtle("Player1", "#0000000", field);
		Turtle turtle2 = new Turtle("Player2", "#0000000", field);
		Turtle turtle3 = new Turtle("Player2", "#0000000", field);
		turtle1.linkTurtle(turtle2);
		turtle2.linkTurtle(turtle3);
		field.linkTurtle(turtle3);
		assertEquals(field.getTopTurtle().orElse(null), turtle1);
	}


	@Test
	void dbTest(){
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
		for(int i = 0; i < 4; i++){
			players.put(i, List.of(playersNames.get(i), indexPlayers.get(i)));
		}

		board.addFields(10);
		board.addTurtlesFromHashMap(players);
		GameLogRepository repository = board.getGameLogRepository();
		long prevRepositoryCount = repository.count();
		board.saveGameLog(0);
		assertEquals(1, repository.count() - prevRepositoryCount);
	}

}
