package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;

class GameSolutionTest {

	private static Board board;
	
	@BeforeAll
	public static void setUp() {
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
	
	@Test
	public void testDisproveSuggestion(){
//		If player has only one matching card it should be returned
//		If players has >1 matching card, returned card should be chosen randomly
//		If player has no matching cards, null is returned
	}
	
	
	
	@Test
	public void testHandleSuggestion(){
//		Suggestion no one can disprove returns null
//		Suggestion only suggesting player can disprove returns null
//		Suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
//		Suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
	}

}
