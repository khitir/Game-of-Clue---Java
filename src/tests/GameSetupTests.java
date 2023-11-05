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

	@Test
	// This function tests that players can be created correctly, and can share their information correctly
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
	// This function tests that, using our initialization file, 5 computer players are created and 1 human player is created
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
	// This function uses a bad initialization file to make sure it is caught during initialization
	public void testPlayersBadInitialization() {
		assertThrows(BadConfigFormatException.class, () -> {
			board = Board.getInstance();
			board.setConfigFiles("ClueLayout.csv", "ClueSetupBadPlayerFormat.txt");
			board.loadSetupConfig();
			board.loadLayoutConfig();
		});
	}
	
	@Test
	// This function tests to make sure we can check if two card are equivalent, even if they are 2 separate instances
	public void testCardEqualsMethodWorks() {
		Card cardRoom = new Card("spades");
		cardRoom.setType(CardType.ROOM);
		
		Card cardPerson = new Card("spades");
		cardRoom.setType(CardType.ROOM);
		assertFalse(cardRoom.equals(cardPerson));
		assertFalse(cardPerson.equals(cardRoom));
		
		Card cardRoom2 = new Card("spades");
		cardRoom2.setType(CardType.ROOM);
		assertTrue(cardRoom.equals(cardRoom2));
		assertTrue(cardRoom2.equals(cardRoom));
		//boolean exists = cards.containsKey("Card2"); // Checks if a card with the name "Card2" exists in the map
	}
	
	@Test
	// This function tests that the card deck is initialized correctly
	public void testCardDeckCreated() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		// Make sure the number of cards created matches up with what it should be
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
		
		// Checks that any card in our deck is one that is reflected in board
		for (String i : deck.keySet()) {
			assertTrue(board.getPeopleCards().contains(deck.get(i)) || board.getWeaponCards().contains(deck.get(i)) || board.getRoomCards().contains(deck.get(i)));
		}

	}
	
	
	@Test
	// This function checks to make sure our solution is created correctly during initialization
	public void testSolutionDealt() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		Solution gameSolution = board.getSolution();
		// Make sure one weapon, one room, and one person is selected
		assertNotNull(gameSolution.getPerson());
		assertNotNull(gameSolution.getRoom());
		assertNotNull(gameSolution.getWeapon());
		assertEquals(gameSolution.getPerson().getType(), CardType.PERSON);
		assertEquals(gameSolution.getWeapon().getType(), CardType.WEAPON);
		assertEquals(gameSolution.getRoom().getType(), CardType.ROOM);
		
		Map<Card, Integer> peopleInSolution = new HashMap<Card, Integer>();
		for (Card i : board.getPeopleCards()) {
			peopleInSolution.put(i, 0);
		}
		Map<Card, Integer> roomsInSolution = new HashMap<Card, Integer>();
		for (Card i : board.getRoomCards()) {
			roomsInSolution.put(i, 0);
		}
		Map<Card, Integer> weaponsInSolution = new HashMap<Card, Integer>();
		for (Card i : board.getWeaponCards()) {
			weaponsInSolution.put(i, 0);
		}
		//  Create a solution 1000 times to check statistics of each creation
		int numIterations = 1000;
		for (int i = 0; i < numIterations; i++) {
			board = Board.getInstance();
			board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
			board.initialize();
			Solution solution = board.getSolution();
			Card person = solution.getPerson();
			peopleInSolution.put(person, peopleInSolution.get(person)+1);
			Card weapon = solution.getWeapon();
			weaponsInSolution.put(weapon, weaponsInSolution.get(weapon)+1);
			Card room = solution.getRoom();
			roomsInSolution.put(room, roomsInSolution.get(room)+1);
		}
		// Check that each player, room, and weapon is in the solution a statistically sound number of times
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
	// This function checks to make sure that each player is dealt cards correctly
	public void testPlayerCardsDealt() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		// Check that the correct number of cards were dealt among all the players
		int numCards = board.getCards().size();
		int numDealt = 0;
		Map<String, Player> players = board.getPlayers();
		for (String i : players.keySet()) {
			numDealt += players.get(i).getCards().size();
			assertTrue(players.get(i).getCards().size() >= (board.getCards().size()/board.getNumPersonCards()));
			assertTrue(players.get(i).getCards().size() <= (board.getCards().size()/board.getNumPersonCards() + 1));
		}
		assertEquals(numCards, numDealt);
		
		for (String name : players.keySet()) {
			Player tempPlayer = players.get(name);
			for (String card : tempPlayer.getCards().keySet()) {
				for (String otherPlayer : players.keySet()) {
					if (otherPlayer.equals(name))
						continue;
					assertFalse(players.get(otherPlayer).getCards().containsKey(card));
				}
			}
			
		}
	}
	
	
	
}
