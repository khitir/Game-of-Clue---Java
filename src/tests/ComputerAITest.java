package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Room;
import clueGame.Solution;

class ComputerAITest {
	Board board;

	@Test
	void testComputerSelectTargets() {
		
	}
	
	@Test
	void testComputerCreateSuggestions() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		// Create a computer player
		ComputerPlayer compPlayer = new ComputerPlayer("Name", "Color");
		
		// Create lists of weapons and players that haven't been seen yet
		Set<Card> tempSet = new HashSet<Card>();
		Card weapon = new Card("Knife");
		weapon.setType(CardType.WEAPON);
		tempSet.add(weapon);
		compPlayer.setWeaponsNotSeen(tempSet);
		tempSet.remove(weapon);
		Card person = new Card("Mr. White");
		person.setType(CardType.PERSON);
		tempSet.add(person);
		compPlayer.setPlayersNotSeen(tempSet);
		tempSet.remove(person);
		
		// Set the Player location in a room
		Room room = new Room('c', "Current");
		compPlayer.setRoomLocation(room);
		
		// Check that the suggestion matches up with the one weapon not yet seen
		Solution suggestion = compPlayer.createSuggestion();
		Set<Card> weaponsNotSeen = compPlayer.getWeaponsNotSeen();
		assertTrue(weaponsNotSeen.contains(suggestion.getWeapon()));
		assertTrue(weaponsNotSeen.contains(weapon));
		Set<Card> playersNotSeen = compPlayer.getPlayersNotSeen();
		assertTrue(playersNotSeen.contains(suggestion.getPerson()));
		assertTrue(playersNotSeen.contains(person));
		
		// Check that room in suggestion is room that player is currently in
		assertTrue(suggestion.getRoom().getCardName().equals(room.getName()));
		
		// Check that each weapon is generated at least once
		Card weapon2 = new Card("Knife2");
		weapon2.setType(CardType.WEAPON);
		tempSet.add(weapon);
		tempSet.add(weapon2);
		compPlayer.setWeaponsNotSeen(tempSet);
		
		boolean weapon1Seen = false, weapon2Seen = false;
		int numIterations = 100;
		for (int i = 0; i < numIterations; i++) {
			suggestion = compPlayer.createSuggestion();
			if (suggestion.getWeapon().equals(weapon))
				weapon1Seen = true;
			else if (suggestion.getWeapon().equals(weapon2))
				weapon2Seen = true;
		}
		assertTrue(weapon1Seen && weapon2Seen);
		
		// Check that each Person is seen at least once
		tempSet.clear();
		Card person2 = new Card("Name2");
		person2.setType(CardType.PERSON);
		tempSet.add(person);
		tempSet.add(person2);
		compPlayer.setPlayersNotSeen(tempSet);
		
		boolean person1Seen = false, person2Seen = false;
		for (int i = 0; i < numIterations; i++) {
			suggestion = compPlayer.createSuggestion();
			if (suggestion.getPerson().equals(person))
				person1Seen = true;
			else if (suggestion.getPerson().equals(person2))
				person2Seen = true;
		}
		assertTrue(person1Seen && person2Seen);
	}

}
