package experiment;

import java.util.HashSet;
import java.util.Set;

/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * TestBoardCell Class
 * 		Acts as a pseudo cell on the game board to allow the testing of movement
 */

public class TestBoardCell {
	private int row, col;
	private boolean isRoom, isOccupied;
	Set<TestBoardCell> adjList;

	public TestBoardCell(int x, int y) {
		adjList = new HashSet<TestBoardCell>();
		this.row = x;
		this.col = y;
		this.isRoom = false;
		this.isOccupied = false;
	}

	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}

	public void setOccupied(boolean b) {
		this.isOccupied = b;
	}
	
	public boolean isOccupied() {
		return this.isOccupied;
	}

	public void setIsRoom(boolean b) {
		this.isRoom = b;
	}
	
	public boolean isRoom() {
		return this.isRoom;
	}
	
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}

}
