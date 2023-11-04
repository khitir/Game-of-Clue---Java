package tests;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import junit.framework.TestCase;

public class GameSetupTests extends TestCase {
	Board board;

	@BeforeAll
	public void setup() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}

	@Test
	public void testPlayer() {
		Player player1 = new HumanPlayer("General Mustard", "Yellow");
		assertEquals(player1.getName(), "General Mustard");
		assertEquals(player1.getColor(), "Yellow");
		assertEquals(false, player1.isComputer());
		player1 = new ComputerPlayer("Doctor Gribble", "Brown");
		assertEquals(player1.getName(), "Doctor Gribble");
		assertEquals(player1.getColor(), "Brown");
		assertEquals(true, player1.isComputer());
	}

	@Test
	public void testPlayersInitialized() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		Map<String, Player> players = board.getPlayers();
		int numComputers = 0, numHumans = 0;
		for (String name : players.keySet()) {
			Player tempPlayer = players.get(name);
			assertEquals(name, tempPlayer.getName());
			if (tempPlayer.isComputer()) {
				numComputers++;
			} else if (tempPlayer.isHuman())
				numHumans++;
		}
		assertEquals(5, numComputers);
		assertEquals(1, numHumans);
	}
	
	@Test
	public void testPlayersBadInitialization() {
		assertThrows(BadConfigFormatException.class, () -> {
			board = Board.getInstance();
			board.setConfigFiles("ClueLayout.csv", "ClueSetupBadPlayerFormat.txt");
			board.loadSetupConfig();
			board.loadLayoutConfig();
		});
	}
	
	
	
	
	
	@Test
	public void testCardEqualsMethodWorks() {
		Card cardRoom = new Card("spades");
		cardRoom.setType(CardType.ROOM);
		
		Card cardPerson = new Card("spades");
		cardRoom.setType(CardType.PERSON);
		assertEquals(cardRoom,cardPerson);
		//boolean exists = cards.containsKey("Card2"); // Checks if a card with the name "Card2" exists in the map
	}
	
	@Test
	public void testCardDeckCreated() {


	}
	
	
	@Test
	public void SolutionDealt() {
		
	}
	
	@Test
	public void testPlayerCardsDealt() {
		
	}
	
	
	
}
