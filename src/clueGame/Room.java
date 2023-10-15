package clueGame;

/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * Room Class
 * 		Serves as a functional wrapper for each of the rooms in the
 * 		board game
 */

public class Room {
	private char labelChar;
	private String nameString;
	private BoardCell centerCell;
	private BoardCell labelCell;

	// Constructor. Takes in room names, location of room center
	// and location of room name
	public Room(char name, BoardCell centerCell, BoardCell labelCell) {
		super();
		this.labelChar = name;
		this.centerCell = centerCell;
		this.labelCell = labelCell;
	}
	// Secondary Constructor, for when only the name and label of the room are available
	public Room(char name, String elements) {
		super();
		this.labelChar = name;
		this.nameString = elements;
	}

	// Returns the room's name
	public String getName() {
		return nameString;
	}
	// Returns the room's one character label
	public char getLabel() {
		return labelChar;
	}

	// Sets the room's name
	public void setName(String name) {
		this.nameString = name;
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