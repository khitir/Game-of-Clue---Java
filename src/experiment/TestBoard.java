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

	final static int COLS = 4;
	final static int ROWS = 4;
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets ;
	private Set<TestBoardCell> visited;
	

	public TestBoard() {
		grid = new TestBoardCell[ROWS][COLS];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				grid[i][j] = new TestBoardCell(i, j);
			}
		}
		targets = new HashSet<TestBoardCell>();
		visited = new HashSet<TestBoardCell>();
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (i != 0 && !grid[i-1][j].isOccupied())
					grid[i][j].addAdjacency(grid[i-1][j]);
				if (j != 0 && !grid[i][j-1].isOccupied())
					grid[i][j].addAdjacency(grid[i][j-1]);
				if (i != ROWS-1 && !grid[i+1][j].isOccupied())
					grid[i][j].addAdjacency(grid[i+1][j]);
				if (j != COLS-1 && !grid[i][j+1].isOccupied())
					grid[i][j].addAdjacency(grid[i][j+1]);
			}
		}
	}
	
	public TestBoardCell getCell(int i, int j) {
		return grid[i][j];
	}

	public void calcTargets(TestBoardCell cell, int pathLength) {
		visited.add(cell);
		findTargets(cell, pathLength);
		visited.clear();
	}
	
	public void findTargets(TestBoardCell cell, int pathLength) {
		for (TestBoardCell adjCell : cell.adjList) {
			if (!visited.contains(adjCell) && !adjCell.isOccupied()) {
				visited.add(adjCell);
				if (pathLength == 1 || adjCell.isRoom()) {
					targets.add(adjCell);
				}
				else {
					findTargets(adjCell, pathLength - 1);
				}
				visited.remove(adjCell);
			}
		}
	}

	public Set<TestBoardCell> getTargets() {
		return targets;
	}

}
