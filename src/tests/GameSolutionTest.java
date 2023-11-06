package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GameSolutionTest {

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	
	
	
	
	
	@Test
	public void testCheckAccusation(){
//		solution that is correct
		
//		solution with wrong person
//		solution with wrong weapon
//		solution with wrong room
		
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
