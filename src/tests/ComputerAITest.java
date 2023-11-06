package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
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
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		// Create a player that is in the doorway of an UNSEEN room with a roll of 1
		ComputerPlayer compPlayer = new ComputerPlayer("Player1", "Color");
		compPlayer.setLocation(7, 5);
		ArrayList<Card> roomsUnseen = new ArrayList<Card>();
		roomsUnseen.add(new Card(board.getRoom('P').getName()));
		roomsUnseen.get(0).setType(CardType.ROOM);
		compPlayer.setRoomsNotSeen(roomsUnseen);
		board.calcTargets(new BoardCell(7, 5), 1);
		Set<BoardCell> adjList = board.getTargets();
		// Make sure the computer picks the room as its target
		BoardCell target = compPlayer.pickTarget(adjList);
		assertEquals(2, target.getRow());
		assertEquals(3, target.getCol());
		
		// Put the player in the doorway of a SEEN room with a roll of 1
		ArrayList<Card> roomsUnseen2 = new ArrayList<Card>();
		compPlayer.setRoomsNotSeen(roomsUnseen2);
		board.calcTargets(new BoardCell(7, 5), 1);
		Set<BoardCell> adjList2 = board.getTargets();
		
		int numIterations = 1000;
		for (int i = 0; i < numIterations; i++) {
			// Make sure the computer never picks the room as its target
			BoardCell target2 = compPlayer.pickTarget(adjList2);
			assertTrue(2 != target2.getRow());
			assertTrue(3 != target2.getCol());
		}
		
		// Put the player in a square with no adjacent room
		compPlayer.setLocation(21, 6);
		board.calcTargets(new BoardCell(21, 6), 1);
		adjList = board.getTargets();
		// Count how many times each result occurs
		int[] results = {0, 0, 0, 0};
		for (int i = 0; i < numIterations; i++) {
			target = compPlayer.pickTarget(adjList);
			if (target.getRow() == 21 && target.getCol() == 5)
				results[0]++;
			else if (target.getRow() == 21 && target.getCol() == 7)
				results[1]++;
			else if (target.getRow() == 22 && target.getCol() == 6)
				results[2]++;
			else if (target.getRow() == 20 && target.getCol() == 6)
				results[3]++;
		}
		// Make sure each result was picked a statistically reasonable number of times
		for (int i = 0; i < 4; i++) {
			assertTrue(results[i] < numIterations/2);
			assertTrue(results[i] > numIterations/10);
		}
		
	}
	
	@Test
	void testComputerCreateSuggestions() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		// Create a computer player
		ComputerPlayer compPlayer = new ComputerPlayer("Name", "Color");
		
		// Create lists of weapons and players that haven't been seen yet
		ArrayList<Card> tempSet = new ArrayList<Card>();
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
		ArrayList<Card> weaponsNotSeen = compPlayer.getWeaponsNotSeen();
		assertTrue(weaponsNotSeen.contains(suggestion.getWeapon()));
		assertTrue(weaponsNotSeen.contains(weapon));
		ArrayList<Card> playersNotSeen = compPlayer.getPlayersNotSeen();
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
