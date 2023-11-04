package tests;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
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
import clueGame.Solution;
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
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		Map<String, Card> deck = board.getCards();
		int numPersonCards = 0, numRoomCards = 0, numWeaponCards = 0;
		for (String i : deck.keySet()) {
			if (deck.get(i).getType().equals(CardType.PERSON)) {
				numPersonCards++;
			}
			else if (deck.get(i).getType().equals(CardType.WEAPON)) {
				numWeaponCards++;
			}
			else if (deck.get(i).getType().equals(CardType.ROOM)) {
				numRoomCards++;
			}
		}
		assertEquals(board.getNumRoomCards(), numRoomCards);
		assertEquals(board.getNumPersonCards(), numPersonCards);
		assertEquals(board.getNumWeaponCards(), numWeaponCards);
	}
	
	
	@Test
	public void testSolutionDealt() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		Solution gameSolution = board.getSolution();
		assertNotNull(gameSolution.getPerson());
		assertNotNull(gameSolution.getRoom());
		assertNotNull(gameSolution.getWeapon());
		assertEquals(gameSolution.getPerson().getType(), CardType.PERSON);
		assertEquals(gameSolution.getWeapon().getType(), CardType.WEAPON);
		assertEquals(gameSolution.getRoom().getType(), CardType.ROOM);
		
		Map<Card, Integer> peopleInSolution = new HashMap<Card, Integer>();
		Map<Card, Integer> roomsInSolution = new HashMap<Card, Integer>();
		Map<Card, Integer> weaponsInSolution = new HashMap<Card, Integer>();
		int numIterations = 1000;
		for (int i = 0; i < numIterations; i++) {
			board = Board.getInstance();
			board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
			board.initialize();
			Solution solution = board.getSolution();
			Card person = solution.getPerson();
			peopleInSolution.put(person, peopleInSolution.get(person)+1);
			Card weapon = solution.getPerson();
			weaponsInSolution.put(weapon, weaponsInSolution.get(person)+1);
			Card room = solution.getPerson();
			roomsInSolution.put(room, roomsInSolution.get(person)+1);
		}
		for (Card temp : peopleInSolution.keySet()) {
			assertTrue((numIterations/board.getNumPersonCards()*2) > peopleInSolution.get(temp));
			assertTrue((numIterations/board.getNumPersonCards()/10) < peopleInSolution.get(temp));
		}
		for (Card temp : weaponsInSolution.keySet()) {
			assertTrue((numIterations/board.getNumWeaponCards()*2) > weaponsInSolution.get(temp));
			assertTrue((numIterations/board.getNumWeaponCards()/10) < weaponsInSolution.get(temp));
		}
		for (Card temp : roomsInSolution.keySet()) {
			assertTrue((numIterations/board.getNumRoomCards()*2) > roomsInSolution.get(temp));
			assertTrue((numIterations/board.getNumRoomCards()/10) < roomsInSolution.get(temp));
		}
	}
	
	@Test
	public void testPlayerCardsDealt() {
		
	}
	
	
	
}
