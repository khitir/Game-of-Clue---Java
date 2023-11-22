package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * BoardCell Class
 * 		Acts as a cell on the game board of Clue
 */

public class BoardCell {
	public String getRoomName() {
		return RoomName;
	}

	private int row, col;
	private boolean isRoom, isOccupied, isRoomCenter, isLabel, isDoorway; // bools for checking status
	private Set<BoardCell> adjList;
	private DoorDirection doorDirection;
	private char secretPassage;
	private char roomLabel;
	private String RoomName;


	public BoardCell(int x, int y) { // constructor, that sets cell location on board, and intitializes room and occupied to false
		adjList = new HashSet<BoardCell>();
		this.row = x;
		this.col = y;
		this.isRoom = false;
		this.isOccupied = false;
		this.isDoorway = false;
		this.isLabel = false;
	}
	
	public Set<BoardCell> getAdjList() {
		return adjList;
	}

	public char getRoomLabel() {
		return roomLabel;
	}

	public void setRoomLabel(char roomLabel) {
		this.roomLabel = roomLabel;
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
	

	
	
	public void setRoomName(String roomName) {
		RoomName = roomName;
	}

	
	// draw a cell, pass in location where draw and what color to set, get's called in BoardPanel
	public void drawCell(Graphics g, int width, int height, Set<BoardCell> targets, int whoseTurn) {
		Board board = Board.getInstance();
		if (isRoom == true) {
			Room room = board.getRooms().get(this.getRoomLabel());
			if (targets.contains(room.getCenterCell()) && whoseTurn == 0 && board.isPlayerTurnFinished() == false)
				g.setColor(Color.cyan);
			else
				g.setColor(Color.GRAY);
			g.fillRect(col*width, row*height, width, height);
		}
		else if (isDoorway == true) {
			if (targets.contains(this) && whoseTurn == 0 && board.isPlayerTurnFinished() == false)
				g.setColor(Color.CYAN);
			else
				g.setColor(Color.YELLOW);
			g.fillRect(col*width, row*height, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(col*width, row*height, width, height);
			g.setColor(Color.blue);
			switch (doorDirection) { // if it's a door, adjust the size of rectangle
				case UP:
					g.fillRect(col*width, row*height, width, height/4);
					break;
				case DOWN:
					g.fillRect(col*width, (row+1)*height - height/4, width, height/4);
					break;
				case LEFT:
					g.fillRect(col*width, row*height, width/4, height);
					break;
				case RIGHT:
					g.fillRect((col+1)*width - width/4, row*height, width/4, height);
					break;
			}
		}
		else if (roomLabel == 'W') { // check if walkway
			if (targets.contains(this) && whoseTurn == 0 && board.isPlayerTurnFinished() == false)
				g.setColor(Color.CYAN);
			else
				g.setColor(Color.YELLOW);
			g.fillRect(col*width, row*height, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(col*width, row*height, width, height);
		}
		else if (roomLabel == 'X') { // check if empty space
			g.setColor(Color.black);
			g.fillRect(col*width, row*height, width, height);
		}
	}
	
	// draws the door name
	public void drawRoomName(Graphics g, int width, int height) {
			g.setColor(Color.BLACK);
			Font myFont = new Font("Arial", Font.PLAIN, (width+height)/3);
	        g.setFont(myFont);
			g.drawString(RoomName, col*width, row*height);
	}

	

}
