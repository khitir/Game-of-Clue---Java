package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

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
		
		// Create three players
		Player human, comp1, comp2;
		human = new HumanPlayer("Name", Color.red);
		comp1 = new ComputerPlayer("1", Color.blue);
		comp2 = new ComputerPlayer("2", Color.green);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(human);
		players.add(comp1);
		players.add(comp2);
		board.setPlayers(players);
		// Create dummy cards
		Card room1, room2, room3, weapon1, weapon2, player1, player2, player3;
		room1 = new Card("Room1", CardType.ROOM);
		room2 = new Card("Room2", CardType.ROOM);
		room3 = new Card("Room3", CardType.ROOM);
		weapon1 = new Card("Weapon", CardType.WEAPON);
		weapon2 = new Card("Weapon2", CardType.WEAPON);
		player1 = new Card("P1", CardType.PERSON);
		player2 = new Card("P2", CardType.PERSON);
		player3 = new Card("P3", CardType.PERSON);
		// Deal cards to players
		human.deal(room1);
		human.deal(room2);
		comp1.deal(weapon1);
		comp1.deal(player1);
		comp2.deal(player2);
		
		// Make sure player 2 disproves
		Solution suggestion1 = new Solution(room1, player2, weapon1);
		Card dispute = board.handleSuggestion(suggestion1, human);
		assertTrue(dispute.equals(weapon1));
		
		// Test starting from a different player
		// Make sure player 3 disproves
		dispute = board.handleSuggestion(suggestion1, comp1);
		assertTrue(dispute.equals(player2));
		
		// Make sure player 3 disproves from a player 1 suggestion
		Solution suggestion2 = new Solution(room3, player2, weapon1);
		dispute = board.handleSuggestion(suggestion2, human);
		assertTrue(dispute.equals(weapon1));
		
		// Make sure no one but one suggesting can disprove
		Solution suggestion3 = new Solution(room1, player3, weapon2);
		dispute = board.handleSuggestion(suggestion3, human);
		assertNull(dispute);
		
		// Make sure no one can disprove
		Solution nullSuggestion = new Solution(room3, player3, weapon2);
		dispute = board.handleSuggestion(nullSuggestion, human);
		assertNull(dispute);
	}
	
	@Test
	public void testCheckAccusation(){
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();

//		solution that is correct
		Card roomSolution = board.getSolution().getRoom();
		Card personSolution = board.getSolution().getPerson();
		Card weaponSolution = board.getSolution().getWeapon();
		
		assertTrue(board.checkAccusation(roomSolution, personSolution, weaponSolution));
		
		
//		solution with wrong person
		Card cardPerson = new Card("spades");
		cardPerson.setType(CardType.PERSON);
		assertFalse(board.checkAccusation(roomSolution, cardPerson, weaponSolution));
//		solution with wrong weapon
		Card cardWeapon = new Card("spades");
		cardWeapon.setType(CardType.WEAPON);
		assertFalse(board.checkAccusation(roomSolution, personSolution, cardWeapon));
//		solution with wrong room
		Card cardRoom = new Card("spades");
		cardRoom.setType(CardType.ROOM);
		assertFalse(board.checkAccusation(cardRoom, personSolution, weaponSolution));
		
	}	
	@Test
	public void testDisproveSuggestion(){
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		Player pl1 = board.getPlayers().get(0);
		// get 3 cards player holds
		Card card1 = pl1.getHand().get(0);
		Card card2 = pl1.getHand().get(1);
		Card card3 = pl1.getHand().get(2);
		
		Card cardToTest = card1; // test with this correct card
		
//		If player has only one matching card it should be returned
		// create 2 wrong cards, wrong name, no type set
		Card dummy1 = new Card("mess1");
		Card dummy2 = new Card("mess2");
		
		assertTrue(pl1.disproveSuggestion(cardToTest, dummy1, dummy2).equals(card1)); // 
		
//		If players has >1 matching card, returned card should be chosen randomly
		Card temp = pl1.disproveSuggestion(card1, card2, card3); // call disprove once for 3 matching cards
		assertTrue(temp.equals(card1) || temp.equals(card2) || temp.equals(card3)); 
		temp = pl1.disproveSuggestion(card1, card2, dummy1); // call disprove with 2 matching cards
		assertTrue(temp.equals(card1) || temp.equals(card2));
		
//		If player has no matching cards, null is returned
		assertNull(pl1.disproveSuggestion(dummy1, dummy2, dummy1));
	}

}
