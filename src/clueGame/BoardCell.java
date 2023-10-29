package clueGame;

import java.util.HashSet;
import java.util.Set;

/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * BoardCell Class
 * 		Acts as a cell on the game board of Clue
 */

public class BoardCell {
	private int row, col;
	private boolean isRoom, isOccupied, isRoomCenter, isLabel, isDoorway; // bools for checking status
	private Set<BoardCell> adjList;
	private DoorDirection doorDirection;
	private char secretPassage;
	private char roomName;

	public BoardCell(int x, int y) { // constructor, that sets cell location on board, and intitializes room and occupied to false
		adjList = new HashSet<BoardCell>();
		this.row = x;
		this.col = y;
		this.isRoom = false;
		this.isOccupied = false;
		this.isDoorway = false;
	}
	
	public Set<BoardCell> getAdjList() {
		return adjList;
	}

	public char getRoomName() {
		return roomName;
	}

	public void setRoomName(char roomName) {
		this.roomName = roomName;
	}


	public char getSecretPassage() { // gets the secret passage
		return secretPassage;
	}

	public void setSecretPassage(char secretPassage) { // gets the secret passage
		this.secretPassage = secretPassage;
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

	public void setIsRoomCenter(boolean b) {
		this.isRoomCenter = b;
	}

	public boolean isLabel() {
		return this.isLabel;
	}

	public void setIsLabel(boolean b) {
		this.isLabel = b;
	}

	public DoorDirection getDoorDirection() { // gets door orientation
		return doorDirection;
	}

	public void setDoorDirection(DoorDirection doorDirection) { // sets door orientation
		this.doorDirection = doorDirection;
	}

	public boolean isDoorway() {
		return this.isDoorway;
	}

	public void setDoorway(boolean isDoorway) {
		this.isDoorway = isDoorway;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

}
