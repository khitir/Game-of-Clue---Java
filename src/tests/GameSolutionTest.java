package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

class GameSolutionTest {
	Board board;

	@Test
	void handleSuggestionTest() {
		board = Board.getInstance();
//		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
//		board.initialize();
		
		Player human, comp1, comp2;
		human = new HumanPlayer("Name", "Red");
		comp1 = new ComputerPlayer("1", "Blue");
		comp2 = new ComputerPlayer("2", "green");
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(human);
		players.add(comp1);
		players.add(comp2);
		board.setPlayers(players);
		Card room1, room2, room3, weapon1, weapon2, player1, player2, player3;
		room1 = new Card("Room1", CardType.ROOM);
		room2 = new Card("Room2", CardType.ROOM);
		room3 = new Card("Room3", CardType.ROOM);
		weapon1 = new Card("Weapon", CardType.WEAPON);
		weapon2 = new Card("Weapon2", CardType.WEAPON);
		player1 = new Card("P1", CardType.PERSON);
		player2 = new Card("P2", CardType.PERSON);
		player3 = new Card("P3", CardType.PERSON);
		human.deal(room1);
		human.deal(room2);
		comp1.deal(weapon1);
		comp1.deal(player1);
		comp2.deal(player2);
		
		Solution suggestion1 = new Solution(room1, player2, weapon1);
		Card dispute = board.handleSuggestion(suggestion1, human);
		assertTrue(dispute.equals(weapon1));
		
		// Test starting from a different player
		dispute = board.handleSuggestion(suggestion1, comp1);
		assertTrue(dispute.equals(player2));
		
		Solution suggestion2 = new Solution(room3, player2, weapon1);
		dispute = board.handleSuggestion(suggestion2, human);
		assertTrue(dispute.equals(weapon1));
		
		Solution nullSuggestion = new Solution(room3, player3, weapon2);
		dispute = board.handleSuggestion(nullSuggestion, human);
		assertNull(dispute);
	}

}
