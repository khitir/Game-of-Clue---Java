package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
	private Set<BoardCell> entrances;
	private BoardCell secretPassageTo;
	private int numPlayersInRoom;
	private ArrayList<Boolean> spotsOccupied;
	// Constructor, for when only the name and label of the room are available
	public Room(char name, String fullNameOfRoom) {
		super();
		this.labelChar = name;
		this.nameString = fullNameOfRoom;
		entrances = new HashSet<BoardCell>();
		spotsOccupied = new ArrayList<Boolean>();
		for (int i = 0; i < 6; i++)
			spotsOccupied.add(false);
	}
	
	public void setOccupied(int index, boolean occupied) {
		spotsOccupied.set(index, occupied);
	}
	
	public ArrayList<Boolean> getOccupied(){
		return spotsOccupied;
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
	public void setEntrance(BoardCell cell) {
		entrances.add(cell);
	}
	public Set<BoardCell> getEntrances() {
		return entrances;
	}
	public void setSecretPassageTo(BoardCell cell) {
		secretPassageTo = cell;
		entrances.add(secretPassageTo);
	}

	public int getNumPlayersInRoom() {
		return numPlayersInRoom;
	}
	
	public void oneMorePlayerInRoom() {
		numPlayersInRoom++;
	}
	
	public void oneLessPlayerInRoom() {
		numPlayersInRoom--;
	}

}