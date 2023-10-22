package tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;

class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
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

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// First, Pat Kohl's Office, which has a single door
		Set<BoardCell> testList = board.getAdjList(18, 2);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(17, 7)));
		
		// Next, test Vince Kuo's office, which has three doors
		testList = board.getAdjList(23, 11);
		for (BoardCell i : testList) {
			System.out.println(i.getRow());
			System.out.println(i.getCol());
		}
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(21, 8)));
		assertTrue(testList.contains(board.getCell(21, 15)));
		assertTrue(testList.contains(board.getCell(26, 9)));
		
		// Finally, the SPS Room, which has a door and a secret passageway
		testList = board.getAdjList(24, 20);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(20, 17)));
		assertTrue(testList.contains(board.getCell(2, 3)));
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(9, 16);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(9, 15)));
		assertTrue(testList.contains(board.getCell(10, 16)));
		assertTrue(testList.contains(board.getCell(6, 19)));

		testList = board.getAdjList(10, 12);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(11, 12)));
		assertTrue(testList.contains(board.getCell(10, 11)));
		assertTrue(testList.contains(board.getCell(10, 13)));
		assertTrue(testList.contains(board.getCell(6, 11)));
		
		testList = board.getAdjList(23, 6);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(22, 6)));
		assertTrue(testList.contains(board.getCell(24, 6)));
		assertTrue(testList.contains(board.getCell(23, 7)));
		assertTrue(testList.contains(board.getCell(25, 3)));
	}
		
	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(27, 7);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(26, 7)));
		
		// Test near a door but not adjacent
		testList = board.getAdjList(7, 7);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(7, 6)));
		assertTrue(testList.contains(board.getCell(7, 8)));
		assertTrue(testList.contains(board.getCell(6, 7)));
		assertTrue(testList.contains(board.getCell(8, 7)));

		// Test adjacent to to two doors
		testList = board.getAdjList(10, 16);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(10, 15)));
		assertTrue(testList.contains(board.getCell(10, 17)));
		assertTrue(testList.contains(board.getCell(9, 16)));
		assertTrue(testList.contains(board.getCell(11, 16)));

		// Test next to door and unused
		testList = board.getAdjList(26,10);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(26, 9)));
		assertTrue(testList.contains(board.getCell(26, 11)));
	
	}
		
		
	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE (Cyan) on the planning spreadsheet
	@Test
	public void testTargetsInCK130() {
		// test a roll of 1
		board.calcTargets(board.getCell(15, 20), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(11, 16)));
		assertTrue(targets.contains(board.getCell(17, 18)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(15, 20), 3);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(9, 16)));
		assertTrue(targets.contains(board.getCell(10, 15)));
		assertTrue(targets.contains(board.getCell(10, 17)));
		assertTrue(targets.contains(board.getCell(11, 14)));
		assertTrue(targets.contains(board.getCell(11, 18)));
		assertTrue(targets.contains(board.getCell(12, 15)));
		assertTrue(targets.contains(board.getCell(19, 18)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(15, 20), 4);
		targets= board.getTargets();
		assertEquals(14, targets.size());
		assertTrue(targets.contains(board.getCell(6, 19)));
		assertTrue(targets.contains(board.getCell(9, 15)));	
		assertTrue(targets.contains(board.getCell(10, 14)));
		assertTrue(targets.contains(board.getCell(10, 16)));
		assertTrue(targets.contains(board.getCell(10, 18)));
		assertTrue(targets.contains(board.getCell(11, 13)));
		assertTrue(targets.contains(board.getCell(11, 15)));
		assertTrue(targets.contains(board.getCell(11, 17)));
		assertTrue(targets.contains(board.getCell(11, 19)));
		assertTrue(targets.contains(board.getCell(12, 14)));
		assertTrue(targets.contains(board.getCell(13, 15)));
		assertTrue(targets.contains(board.getCell(19, 17)));
		assertTrue(targets.contains(board.getCell(19, 19)));
		assertTrue(targets.contains(board.getCell(20, 18)));
	}

	@Test
	public void testTargetsInSPSRoom() {
		// test a roll of 1
		board.calcTargets(board.getCell(24, 20), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(20, 17)));
		assertTrue(targets.contains(board.getCell(2, 3)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(24, 20), 3);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(19, 16)));
		assertTrue(targets.contains(board.getCell(19, 18)));	
		assertTrue(targets.contains(board.getCell(20, 15)));
		assertTrue(targets.contains(board.getCell(20, 19)));
		assertTrue(targets.contains(board.getCell(21, 16)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(24, 20), 4);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(18, 18)));
		assertTrue(targets.contains(board.getCell(19, 15)));	
		assertTrue(targets.contains(board.getCell(19, 17)));
		assertTrue(targets.contains(board.getCell(19, 19)));
		assertTrue(targets.contains(board.getCell(20, 16)));
		assertTrue(targets.contains(board.getCell(20, 18)));
		assertTrue(targets.contains(board.getCell(20, 20)));
		assertTrue(targets.contains(board.getCell(2, 3)));	
		assertTrue(targets.contains(board.getCell(21, 15)));
		assertTrue(targets.contains(board.getCell(22, 16)));
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE(Cyan) on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(17, 7), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(16, 7)));
		assertTrue(targets.contains(board.getCell(17, 8)));	
		assertTrue(targets.contains(board.getCell(18, 7)));	
		assertTrue(targets.contains(board.getCell(18, 2)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(17, 7), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(14, 7)));
		assertTrue(targets.contains(board.getCell(15, 6)));
		assertTrue(targets.contains(board.getCell(15, 8)));	
		assertTrue(targets.contains(board.getCell(16, 7)));
		assertTrue(targets.contains(board.getCell(17, 8)));	
		assertTrue(targets.contains(board.getCell(18, 2)));
		assertTrue(targets.contains(board.getCell(18, 7)));
		assertTrue(targets.contains(board.getCell(18, 9)));
		assertTrue(targets.contains(board.getCell(19, 8)));
		assertTrue(targets.contains(board.getCell(20, 7)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(17, 7), 4);
		targets= board.getTargets();
		assertEquals(14, targets.size());
		assertTrue(targets.contains(board.getCell(13, 7)));
		assertTrue(targets.contains(board.getCell(14, 8)));
		assertTrue(targets.contains(board.getCell(15, 5)));	
		assertTrue(targets.contains(board.getCell(15, 7)));
		assertTrue(targets.contains(board.getCell(16, 6)));	
		assertTrue(targets.contains(board.getCell(16, 8)));
		assertTrue(targets.contains(board.getCell(18, 2)));
		assertTrue(targets.contains(board.getCell(18, 8)));
		assertTrue(targets.contains(board.getCell(18, 10)));
		assertTrue(targets.contains(board.getCell(19, 7)));
		assertTrue(targets.contains(board.getCell(19, 9)));
		assertTrue(targets.contains(board.getCell(20, 6)));
		assertTrue(targets.contains(board.getCell(20, 8)));
		assertTrue(targets.contains(board.getCell(21, 7)));	
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(15, 1), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(15, 2)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(15, 1), 3);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(15, 4)));
	
		
		// test a roll of 4
		board.calcTargets(board.getCell(15, 1), 4);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(15, 5)));

	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(10, 11), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(10, 10)));
		assertTrue(targets.contains(board.getCell(10, 12)));	
		assertTrue(targets.contains(board.getCell(11, 11)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(10, 11), 3);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(6, 11)));
		assertTrue(targets.contains(board.getCell(10, 8)));
		assertTrue(targets.contains(board.getCell(10, 10)));	
		assertTrue(targets.contains(board.getCell(10, 12)));	
		assertTrue(targets.contains(board.getCell(10, 14)));	
		assertTrue(targets.contains(board.getCell(11, 9)));	
		assertTrue(targets.contains(board.getCell(11, 11)));	
		assertTrue(targets.contains(board.getCell(11, 13)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(10, 11), 4);
		targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(6, 11)));
		assertTrue(targets.contains(board.getCell(9, 8)));
		assertTrue(targets.contains(board.getCell(9, 14)));	
		assertTrue(targets.contains(board.getCell(10, 7)));	
		assertTrue(targets.contains(board.getCell(10, 9)));	
		assertTrue(targets.contains(board.getCell(10, 13)));	
		assertTrue(targets.contains(board.getCell(10, 15)));	
		assertTrue(targets.contains(board.getCell(11, 8)));	
		assertTrue(targets.contains(board.getCell(11, 10)));	
		assertTrue(targets.contains(board.getCell(11, 12)));	
		assertTrue(targets.contains(board.getCell(11, 14)));	
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 2 down
		board.getCell(19, 7).setOccupied(true);
		board.calcTargets(board.getCell(17, 7), 4);
		board.getCell(19, 7).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(!targets.contains(null));
		assertTrue(targets.contains(board.getCell(13, 7)));
		assertTrue(targets.contains(board.getCell(14, 8)));
		assertTrue(targets.contains(board.getCell(15, 5)));	
		assertTrue(targets.contains(board.getCell(15, 7)));
		assertTrue(targets.contains(board.getCell(16, 6)));	
		assertTrue(targets.contains(board.getCell(16, 8)));
		assertTrue(targets.contains(board.getCell(18, 2)));
		assertTrue(targets.contains(board.getCell(18, 8)));
		assertTrue(targets.contains(board.getCell(18, 10)));
		assertTrue(targets.contains(board.getCell(19, 9)));
		assertTrue(targets.contains(board.getCell(20, 8)));	
		assertFalse(targets.contains(board.getCell(19, 7)));
		assertFalse(targets.contains(board.getCell(21, 7)));
		assertFalse(targets.contains(board.getCell(20, 6)));
	
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(15, 20).setOccupied(true);
		board.getCell(11, 17).setOccupied(true);
		board.calcTargets(board.getCell(11, 16), 1);
		board.getCell(15, 20).setOccupied(false);
		board.getCell(11, 17).setOccupied(false);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(10, 16)));	
		assertTrue(targets.contains(board.getCell(11, 15)));
		assertTrue(targets.contains(board.getCell(15, 20)));	
		
		// check leaving a room with a blocked doorway
		board.getCell(21, 8).setOccupied(true);
		board.calcTargets(board.getCell(23, 11), 3);
		board.getCell(21, 8).setOccupied(false);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(26, 11)));
		assertFalse(targets.contains(board.getCell(21, 6)));
		assertFalse(targets.contains(board.getCell(22, 7)));
		assertFalse(targets.contains(board.getCell(20, 7)));
		assertFalse(targets.contains(board.getCell(19, 8)));	
		
	}
}
