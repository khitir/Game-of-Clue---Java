package experiment;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * TestBoard Class
 * 		Acts as a pseudo game board to allow the testing of movement
 */

public class TestBoard {
<<<<<<< HEAD

	public TestBoard() {
=======
	final static int COLS = 4;
	final static int ROWS = 4;
	private TestBoardCell[][] grid = new TestBoardCell[ROWS][COLS];
	private Set<TestBoardCell> targets ;
	private Set<TestBoardCell> visited;
	

	public TestBoard() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				// fill grid, now they are NULL
			}
		}
	}
>>>>>>> 7506cbcb696748743e05b01b83e223bed0ea0194

	}
	
	public TestBoardCell getCell(int i, int j) {
		TestBoardCell temp = new TestBoardCell(i, j);
		return temp;
	}

	public void calcTargets(TestBoardCell cell, int pathLength) {
		// TODO Auto-generated method stub
		
	}

	public Set<TestBoardCell> getTargets() {
		Set<TestBoardCell> temp = new HashSet<TestBoardCell>();
		return temp;
	}

}
