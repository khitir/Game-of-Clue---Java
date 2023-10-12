package clueGame;

/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * Room Class
 * 		Serves as a functional wrapper for each of the rooms in the
 * 		board game
 */

public class Room {
	private char nameChar;
	private String labelString;
	private BoardCell centerCell;
	private BoardCell labelCell;

	// Constructor. Takes in room names, location of room center
	// and location of room name
	public Room(char name, BoardCell centerCell, BoardCell labelCell) {
		super();
		this.nameChar = name;
		this.centerCell = centerCell;
		this.labelCell = labelCell;
	}
	
	public Room(char name, String elements) {
		super();
		this.nameChar = name;
		this.labelString = elements;
	}

	// Returns the room's name
	public char getName() {
		return nameChar;
	}

	// Sets the room's name
	public void setName(char name) {
		this.nameChar = name;
	}

	// Returns the center cell of the room
	public BoardCell getCenterCell() {
		return centerCell;
	}

	// Sets the center cell of the room
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	// Returns the cell upon which the room's label is drawn
	public BoardCell getLabelCell() {
		return labelCell;
	}
	// Sets the cell where the room's label is drawn
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}


}