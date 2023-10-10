package clueGame;

/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * Room Class
 * 		Serves as a functional wrapper for each of the rooms in the
 * 		board game
 */

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;

	// Constructor. Takes in room names, location of room center
	// and location of room name
	public Room(String name, BoardCell centerCell, BoardCell labelCell) {
		super();
		this.name = name;
		this.centerCell = centerCell;
		this.labelCell = labelCell;
	}

	// Returns the room's name
	public String getName() {
		return name;
	}

	// Sets the room's name
	public void setName(String name) {
		this.name = name;
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