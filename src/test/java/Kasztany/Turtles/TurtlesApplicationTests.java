package Kasztany.Turtles;

import Kasztany.Turtles.model.Board;
import Kasztany.Turtles.model.Turtle;
import Kasztany.Turtles.persistence.GameLogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TurtlesApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void dbTest(){
		List<String> playersNames = new ArrayList<>();
		HashMap<Integer, String> indexPlayers = new HashMap<>();
		playersNames.add("Player1");
		playersNames.add("Player2");
		playersNames.add("Player3");
		playersNames.add("Player4");
		indexPlayers.put(1, "#000000");
		indexPlayers.put(2, "#000000");
		indexPlayers.put(3, "#000000");
		indexPlayers.put(4, "#000000");

		HashMap<Integer, List<String>> players = new HashMap<>();
		for(int i = 0; i < 4; i++){
			players.put(i, List.of(playersNames.get(i), indexPlayers.get(i)));
		}
		Board board = new Board(players, 10);
		ArrayList<Turtle> turtles = board.getTurtles();
		GameLogRepository repository = board.getGameLogRepository();
		long prevRepositoryCount = repository.count();
		board.saveGameLog(0);
		assertEquals(1, repository.count() - prevRepositoryCount);
	}

}
