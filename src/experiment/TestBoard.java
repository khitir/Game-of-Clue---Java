package experiment;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TestBoard {
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
