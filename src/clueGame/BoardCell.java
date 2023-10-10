package clueGame;

//package experiment;

import java.util.HashSet;
import java.util.Set;

/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * TestBoardCell Class
 * 		Acts as a pseudo cell on the game board to allow the testing of movement
 */

public class BoardCell {
	private int row, col;
	private boolean isRoom, isOccupied;
	Set<BoardCell> adjList;

	public BoardCell(int x, int y) {
		adjList = new HashSet<BoardCell>();
		this.row = x;
		this.col = y;
		this.isRoom = false;
		this.isOccupied = false;
	}

	public Set<BoardCell> getAdjList() {
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
	
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}

}
