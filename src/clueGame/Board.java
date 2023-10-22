package clueGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * Board Class
 * 		Acts as a game board to allow the testing of movement
 */

public class Board {

	private int COLS = 0;
	private int ROWS = 0;
	private BoardCell[][] grid;
	private Set<BoardCell> targets ;
	private Set<BoardCell> visited;
	private Set<Room> rooms;

	private static Board theInstance = new Board();
	
	// The file names to use for the initial configuration
	String csv_file; 
	String txt_file; 

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].adjList;
	}
	
    /*
     * initialize the board (since we are using singleton pattern)
     */
    public void initialize(){
    	try {
			loadSetupConfig();
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    // Helper function for loadLayoutConfig()
    // Sets the room properties of all cells on the board
    // We need to know what room each cell is for setCellPropertiesSecond, where we set the entrances to a room
    private void setCellPropertiesFirst(BoardCell cell, String currSpace) {
    	cell.setRoomName(currSpace.charAt(0));
		// Set space to be a room if it is not unused or a walkway
		if (currSpace.charAt(0) != 'X' && currSpace.charAt(0) != 'W') {
			cell.setIsRoom(true);
		}
		else if (currSpace == "X") {
			cell.setOccupied(true);
		}
		// Cover all possible cases when a space has two characters
		if (currSpace.length() == 2) {
			// Set if the square is a room label
			if (currSpace.charAt(1) == '#') {
				cell.setIsLabel(true);
				for (Room tempRoom : rooms) {
					if (tempRoom.getLabel() == currSpace.charAt(0)) {
						tempRoom.setLabelCell(cell);
					}
				}
			}
			// Set if the square is a room center
			else if (currSpace.charAt(1) == '*') {
				cell.setIsRoomCenter(true);
				for (Room tempRoom : rooms) {
					if (tempRoom.getLabel() == currSpace.charAt(0)) {
						tempRoom.setCenterCell(cell);
					}
				}
			}
		}
    }
    
    private void setCellPropertiesOnePointFive(BoardCell cell, String currSpace) {
    	// Set secret passages
    	if (currSpace.length() == 2) {
			if (currSpace.charAt(0) != 'W' && currSpace.charAt(1) != '#' && currSpace.charAt(1) != '*'){
				cell.setSecretPassage(currSpace.charAt(1));
				Room currRoom = null;
				Room secretPassageRoom = null;
				for (Room tempRoom : rooms) {
					if (currSpace.charAt(0) == tempRoom.getLabel()) {
						currRoom = tempRoom;
					}
					if (currSpace.charAt(1) == tempRoom.getLabel()) {
						secretPassageRoom = tempRoom;
					}
				}
				currRoom.setSecretPassageTo(secretPassageRoom.getCenterCell());
			}
    	}
    }
    
    // Helper function for loadLayoutConfig()
    // Sets if a space is an entrance to a room
    private void setCellPropertiesSecond(BoardCell cell, String currSpace, int row, int col) {
		// Cover all possible cases when a space has two characters
		if (currSpace.length() == 2) {
			// Set door directions
			if (currSpace.charAt(0) == 'W') {
				if (currSpace.charAt(1) == '<') {
					cell.setDoorDirection(DoorDirection.LEFT);
					cell.setDoorway(true);
					for (Room tempRoom : rooms) {
						if (grid[row][col-1].getRoomName() == tempRoom.getLabel()) {
							tempRoom.setEntrance(cell);
							cell.addAdjacency(tempRoom.getCenterCell());
						}
					}
				}
				else if (currSpace.charAt(1) == '>') {
					cell.setDoorDirection(DoorDirection.RIGHT);
				    cell.setDoorway(true);
				    for (Room tempRoom : rooms) {
						if (grid[row][col+1].getRoomName() == tempRoom.getLabel()) {
							tempRoom.setEntrance(cell);
							cell.addAdjacency(tempRoom.getCenterCell());
						}
					}
				}
				else if (currSpace.charAt(1) == '^') {
					cell.setDoorDirection(DoorDirection.UP);
					cell.setDoorway(true);
					for (Room tempRoom : rooms) {
						if (grid[row-1][col].getRoomName() == tempRoom.getLabel()) {
							tempRoom.setEntrance(cell);
							cell.addAdjacency(tempRoom.getCenterCell());
						}
					}
				}
				else if (currSpace.charAt(1) == 'v') {
					cell.setDoorDirection(DoorDirection.DOWN);
					cell.setDoorway(true);
					for (Room tempRoom : rooms) {
						if (grid[row+1][col].getRoomName() == tempRoom.getLabel()) {
							tempRoom.setEntrance(cell);
							cell.addAdjacency(tempRoom.getCenterCell());
						}
					}
				}
			}
		}
    }
    
    // Getter for each cell Object
	public BoardCell getCell(int i, int j) { // gets a cell
		return grid[i][j];
	}
	
	public void calcTargets(BoardCell cell, int pathLength) { // adds visited cells to to list and calls findtarget()
		targets.clear();
		visited.add(cell);
		findTargets(cell, pathLength);
		visited.clear();
	}
	// LEtting you go back into a room
	
	public void findTargets(BoardCell cell, int pathLength) { // recursively finds targets by looking at adjacent list cells and also checks if those cells are occupied or if a room
		for (BoardCell adjCell : cell.adjList) {
			if (adjCell.isRoom() && !visited.contains(adjCell)) {
				targets.add(adjCell);
			}
			else if (!visited.contains(adjCell) && !adjCell.isOccupied()) {
				visited.add(adjCell);
				if (pathLength == 1) {
					targets.add(adjCell);
				}
				else {
					findTargets(adjCell, pathLength - 1);
				}
				visited.remove(adjCell);
			}
		}
	}

	
	
	public Set<BoardCell> getTargets() { // gets target
		return targets;
	}
	
	public void setConfigFiles(String csv, String file) { // sets csv and text files
		csv_file = "data/" + csv;
		txt_file = "data/" +  file;
	}
	
	public Room getRoom(char room) { //  gets a room, need to update in future
		for (Room tempRoom : rooms) {
			if (tempRoom.getLabel() == room) {
				return tempRoom;
			}
		}
		return null;
	}
	
	public Room getRoom(BoardCell cell) {//  gets a room with cell input, need to update in future
		for (Room tempRoom : rooms) {
			if (tempRoom.getLabel() == cell.getRoomName()) {
				return tempRoom;
			}
		}
		return null;
	}

	
	public int getNumColumns() {
		return COLS;
	}
	public int getNumRows() {
		return ROWS;
	}
	
	// Loads the .txt file used for setting up the board
	public void loadSetupConfig() throws BadConfigFormatException {
		FileReader in;
    	BufferedReader reader;
    	rooms = new HashSet<Room>();
    	try {
			in = new FileReader(txt_file);
			reader = new BufferedReader(in);
			String tempLine = reader.readLine();
			while (tempLine != null) {
				String[] elements = tempLine.split(", ");
				// If the line is not a comment or configured in the following format, throw an exception
				// Room/Space, Name, Label
				if (elements.length != 3 && elements[0].charAt(0) != '/') {
					throw new BadConfigFormatException("Invalid Initialization File");
				}
				else if (elements[0].equals("Room") || elements[0].equals("Space")) {
					Room tempRoom = new Room(elements[2].charAt(0), elements[1]);
					rooms.add(tempRoom);
				}
				else if (elements[0].charAt(0) != '/' && !elements[0].isEmpty())
					throw new BadConfigFormatException("Invalid Initialization File");
				tempLine = reader.readLine();
			}
			reader.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Loads the .csv file for the board layout
	public void loadLayoutConfig() throws BadConfigFormatException {
    	ArrayList<String> fileLines = new ArrayList<String>();
    	FileReader in;
    	BufferedReader reader;
    	try {
			in = new FileReader(csv_file);
			reader = new BufferedReader(in);
			String tempLine = reader.readLine();
			while (tempLine != null) {
				fileLines.add(tempLine);
				tempLine = reader.readLine();
			}
			reader.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	// Set the number of rows and columns based on the width and height of the pseudo 2D array
    	boolean badRows = false;
    	String[] tempStr = fileLines.get(0).split(",");
    	int numCols = tempStr.length;
    	for (int i = 1; i < fileLines.size(); i++) {
    		tempStr= fileLines.get(i).split(",");
    		for (String elem : tempStr) {
    			if (elem.isEmpty())
    				throw new BadConfigFormatException("Empty element in Layout file");
    		}
    		if (tempStr.length != numCols)
    			throw new BadConfigFormatException("Unmatched number of columns or rows");
    	}
    	ROWS = fileLines.size();
    	COLS = numCols;
    	
    	// Initialize the board
		grid = new BoardCell[ROWS][COLS];
		for (int row = 0; row < ROWS; row++) {
			String[] spaces = fileLines.get(row).split(",", COLS);
			for (int col = 0; col < COLS; col++) {
				grid[row][col] = new BoardCell(row, col);
				setCellPropertiesFirst(grid[row][col], spaces[col]);
			}
		}
		for (int row = 0; row < ROWS; row++) {
			String[] spaces = fileLines.get(row).split(",", COLS);
			for (int col = 0; col < COLS; col++) {
				setCellPropertiesOnePointFive(grid[row][col], spaces[col]);
			}
		}
		for (int row = 0; row < ROWS; row++) {
			String[] spaces = fileLines.get(row).split(",", COLS);
			for (int col = 0; col < COLS; col++) {
				setCellPropertiesSecond(grid[row][col], spaces[col], row, col);
				if (grid[row][col].isRoom()) {
					// Check if the cell matches those around it for room configuration
					char tempName = grid[row][col].getRoomName();
					if (col != COLS-1 && tempName != spaces[col+1].charAt(0)) {
						if (col != 0 && tempName != grid[row][col-1].getRoomName()) {
							if (row != 0 && tempName != grid[row-1][col].getRoomName()) {
								throw new BadConfigFormatException("Invalid Room Configuration");
							}
						}
					}
				}
			}
		}
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		for (int i = 0; i < ROWS; i++) { // fill in the board
			for (int j = 0; j < COLS; j++) {
				if (grid[i][j].isRoomCenter()) {
					Room currRoom = null;
					for (Room tempRoom : rooms) {
						if (grid[i][j].getRoomName() == tempRoom.getLabel()) {
							currRoom = tempRoom;
						}
					}
					Set<BoardCell> entrances = currRoom.getEntrances();
					for (BoardCell cell : entrances) {
						grid[i][j].addAdjacency(cell);
					}
				}
				else if (!grid[i][j].isRoom()) {
					if (i != 0 && !grid[i-1][j].isOccupied() && grid[i-1][j].getRoomName() == 'W')
						grid[i][j].addAdjacency(grid[i-1][j]);
					if (j != 0 && !grid[i][j-1].isOccupied() && grid[i][j-1].getRoomName() == 'W')
						grid[i][j].addAdjacency(grid[i][j-1]);
					if (i != ROWS-1 && !grid[i+1][j].isOccupied() && grid[i+1][j].getRoomName() == 'W')
						grid[i][j].addAdjacency(grid[i+1][j]);
					if (j != COLS-1 && !grid[i][j+1].isOccupied() && grid[i][j+1].getRoomName() == 'W')
						grid[i][j].addAdjacency(grid[i][j+1]);
				}
			}
		}
	}
}
