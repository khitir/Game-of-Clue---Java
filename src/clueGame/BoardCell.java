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
	private boolean isRoom, isOccupied,isRoomCenter,isLabel,isDoorway;
	Set<BoardCell> adjList;
	public char getSecretPassage() {
		return secretPassage;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}

	DoorDirection doorDirection;
	char secretPassage;

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
	
	public boolean isRoomCenter() {
		return this.isRoomCenter;
	}
	
	public boolean isLabel() {
		return this.isLabel;
	}

	public boolean isDoorway() {
		return this.isDoorway;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}

}
