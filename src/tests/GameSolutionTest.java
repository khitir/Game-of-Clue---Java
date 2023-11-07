package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

class GameSolutionTest {

	Board board;
	
	@BeforeEach
	public void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}
	
	
	
	@Test
	public void testCheckAccusation(){

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
//	@Test
//	public void testDisproveSuggestion(){
//		board = Board.getInstance();
//		// set the file names to use my config files
//		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
//		// Initialize will load config files 
//		board.initialize();
//		Player pl1 = board.getPlayers().get(0);
//		Card card1 = pl1.getHand().get(0);
//		Card card2 = pl1.getHand().get(1);
//		Card card3 = pl1.getHand().get(2);
//		
//		Card cardToTest = card1;
//		
////		If player has only one matching card it should be returned
//		Card dummy1 = new Card("mess1");
//		Card dummy2 = new Card("mess2");
//		
//		assertTrue(pl1.disproveSuggestion(cardToTest, dummy1, dummy2).equals(card1));
//		
////		If players has >1 matching card, returned card should be chosen randomly
//		assertTrue(pl1.disproveSuggestion(card1, card2, card3).equals(card1) || pl1.disproveSuggestion(card1, card2, card3).equals(card2) || pl1.disproveSuggestion(card1, card2, card3).equals(card3));
//		assertTrue(pl1.disproveSuggestion(card1, card2, dummy1).equals(card1) || pl1.disproveSuggestion(card1, card2, dummy1).equals(card2));
//		
////		If player has no matching cards, null is returned
//		assertFalse(pl1.disproveSuggestion(dummy1, dummy2, dummy1).equals(card1) || pl1.disproveSuggestion(dummy1, dummy2, dummy1).equals(card2) || pl1.disproveSuggestion(dummy1, dummy2, dummy1).equals(card3));
//	}
//	
//	
	
//	@Test
//	public void testHandleSuggestion(){
////		Suggestion no one can disprove returns null
////		Suggestion only suggesting player can disprove returns null
////		Suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
////		Suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
//	}

}
