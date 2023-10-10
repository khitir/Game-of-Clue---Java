package clueGame;

public class Room {
	private char name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	public Room(char name, BoardCell centerCell, BoardCell labelCell) {
		super();
		this.name = name;
		this.centerCell = centerCell;
		this.labelCell = labelCell;
	}

	public char getName() {
		return name;
	}

	public void setName(char name) {
		this.name = name;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}

	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	public BoardCell getLabelCell() {
		return labelCell;
	}

	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}


}