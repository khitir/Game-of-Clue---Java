package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * BoardTestsExp JUnit Test Class
 * 		Tests out the functionality of the TestBoard and TestBoardCell Class
 */

class BoardTestsExp {
	TestBoard board;
	
	@BeforeEach
	public void setupBoard() {
		board = new TestBoard();
	}
	
	@Test
	public void testAdjacency() {
		// Test the upper left hand corner of the board
		TestBoardCell cell = board.getCell(0,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(0,1)));
		assertTrue(testList.contains(board.getCell(1,0)));
		assertEquals(2, testList.size());
		
		// Test the lower right hand corner of the board
		cell = board.getCell(3,3);
		testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(2,3)));
		assertTrue(testList.contains(board.getCell(3,2)));
		assertEquals(2, testList.size());
		
		// Test the right edge of the board
		cell = board.getCell(1,3);
		testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(0,3)));
		assertTrue(testList.contains(board.getCell(1,2)));
		assertTrue(testList.contains(board.getCell(2,3)));
		assertEquals(3, testList.size());
		
		// Test the bottom edge of the board
		cell = board.getCell(3,1);
		testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(3,2)));
		assertTrue(testList.contains(board.getCell(3,0)));
		assertTrue(testList.contains(board.getCell(2,1)));
		assertEquals(3, testList.size());
		
		// Center of the board
		cell = board.getCell(2, 2);
		testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(3,2)));
		assertTrue(testList.contains(board.getCell(1,2)));
		assertTrue(testList.contains(board.getCell(2,1)));
		assertTrue(testList.contains(board.getCell(2,3)));
		assertEquals(4, testList.size());
	}
	
	@Test
	public void testMovementTargetsEmpty() {
		// Tests the possible targets for an empty board
		
		// Upper left corner
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		
		// Lower right corner
		cell = board.getCell(3, 3);
		targets.clear();
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(3, 2)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		// 1,2	0,3	0,1	3,0	2,3	3,2	2,1	1,0
		// 2,1		3,0		1,0		0,3		3,2		2,3		1,2		0,1
		
		// Upper Edge
		cell = board.getCell(0, 1);
		targets.clear();
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		
		// Left Edge
		cell = board.getCell(1, 0);
		targets.clear();
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		
		// Right Edge
		cell = board.getCell(2, 3);
		targets.clear();
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(3, 3)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		
		// Bottom Edge
		cell = board.getCell(3, 2);
		targets.clear();
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(3, 3)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		
		// Center of the board
		cell = board.getCell(2, 2);
		targets.clear();
		board.calcTargets(cell, 4);
		targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	@Test
	public void testMovementTargetsMixed() {
		// Test if one occupied square and one room
		TestBoardCell cell = board.getCell(1, 1);
		board.getCell(0, 0).setOccupied(true);
		board.getCell(1, 2).setIsRoom(true);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		
		// Test if surrounded on 2 sides by occupied squares
		board = new TestBoard();
		cell = board.getCell(2, 2);
		board.getCell(2, 1).setOccupied(true);
		board.getCell(2, 3).setOccupied(true);
		board.getCell(1, 2).setIsRoom(true);
		targets.clear();
		board.calcTargets(cell,  2);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(3, 3)));
		assertTrue(targets.contains(board.getCell(3, 1)));
	}
	
	
	@Test
	public void testTargetsRoom() {
		// case 1, when dice number is not within exact straigth count to Room  
		TestBoardCell cell = board.getCell(1, 1);
		board.getCell(0, 1).setIsRoom(true);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		
		
		// case 2, when dice number is exact straigth count to Room  
		board = new TestBoard();
		cell = board.getCell(1, 1);
		board.getCell(0, 1).setIsRoom(true);
		targets.clear();
		board.calcTargets(cell, 1);
		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
	
	}
	
	@Test
	public void testTargetsOccupied() {
		// case 1, when 1 potential target is occupied, simple case for dice = 1
		TestBoardCell cell = board.getCell(1, 2);
		board.getCell(0, 2).setOccupied(true);
		board.calcTargets(cell, 1);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertFalse(targets.contains(board.getCell(0, 2)));


		// case 2, When only 1 place is occupied, but more complex case with dice = 3 
		board = new TestBoard();
		cell = board.getCell(3, 0);
		board.getCell(2, 2).setOccupied(true);
		targets.clear();
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));
		assertFalse(targets.contains(board.getCell(2, 2)));
		
		// case 3, when all available targets are occupied, so nothings available
		board = new TestBoard();
		cell = board.getCell(3, 0);
		board.getCell(2, 2).setOccupied(true);
		board.getCell(0, 0).setOccupied(true);
		board.getCell(1, 1).setOccupied(true);
		board.getCell(2, 0).setOccupied(true);
		board.getCell(3, 1).setOccupied(true);
		board.getCell(3, 3).setOccupied(true);
		targets.clear();
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		assertEquals(0, targets.size());
		assertFalse(targets.contains(board.getCell(0, 0)));
		assertFalse(targets.contains(board.getCell(1, 1)));
		assertFalse(targets.contains(board.getCell(2, 0)));
		assertFalse(targets.contains(board.getCell(3, 1)));
		assertFalse(targets.contains(board.getCell(3, 3)));
		assertFalse(targets.contains(board.getCell(2, 2)));
		assertFalse(targets.contains(board.getCell(3, 0)));
	
	}

}
