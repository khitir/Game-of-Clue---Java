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
    /*
     * initialize the board (since we are using singleton pattern)
     */
    public void initialize() throws BadConfigFormatException{
    	loadSetupConfig();
    	loadLayoutConfig();
    }
    
    // Helper function for loadLayoutConfig(), to set the properties of each cell on the board that we construct
    private BoardCell setCellProperties(BoardCell tempCell, String currSpace) {
    	tempCell.setRoomName(currSpace.charAt(0));
		// Set space to be a room if it is not unused or a walkway
		if (currSpace.charAt(0) != 'X' && currSpace.charAt(0) != 'W') {
			tempCell.setIsRoom(true);
		}
		else if (currSpace == "X") {
			tempCell.setOccupied(true);
		}
		// Cover all possible cases when a space has two characters
		if (currSpace.length() == 2) {
			// Set door directions
			if (currSpace.charAt(0) == 'W') {
				if (currSpace.charAt(1) == '<') {
					tempCell.setDoorDirection(DoorDirection.LEFT);
					tempCell.setDoorway(true);}
				else if (currSpace.charAt(1) == '>') {
					tempCell.setDoorDirection(DoorDirection.RIGHT);
				    tempCell.setDoorway(true);}
				else if (currSpace.charAt(1) == '^') {
					tempCell.setDoorDirection(DoorDirection.UP);
					tempCell.setDoorway(true);}
				else if (currSpace.charAt(1) == 'v') {
					tempCell.setDoorDirection(DoorDirection.DOWN);
					tempCell.setDoorway(true);}
			}
			// Set if the square is a room label
			else if (currSpace.charAt(1) == '#') {
				tempCell.setIsLabel(true);
				for (Room tempRoom : rooms) {
					if (tempRoom.getLabel() == currSpace.charAt(0)) {
						tempRoom.setLabelCell(tempCell);
					}
				}
			}
			// Set if the square is a room center
			else if (currSpace.charAt(1) == '*') {
				tempCell.setIsRoomCenter(true);
				for (Room tempRoom : rooms) {
					if (tempRoom.getLabel() == currSpace.charAt(0)) {
						tempRoom.setCenterCell(tempCell);
					}
				}
			}
			// Set secret passages
			else {
				tempCell.setSecretPassage(currSpace.charAt(1));
			}
		}
		return tempCell;
    }
    
    // Getter for each cell Object
	public BoardCell getCell(int i, int j) { // gets a cell
		return grid[i][j];
	}
	
	public void calcTargets(BoardCell cell, int pathLength) { // adds visited cells to to list and calls findtarget()
		visited.add(cell);
		findTargets(cell, pathLength);
		visited.clear();
	}
	
	public void findTargets(BoardCell cell, int pathLength) { // recursively finds targets by looking at adjacent list cells and also checks if those cells are occupied or if a room
		for (BoardCell adjCell : cell.adjList) {
			if (!visited.contains(adjCell) && !adjCell.isOccupied()) {
				visited.add(adjCell);
				if (pathLength == 1 || adjCell.isRoom()) {
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
				BoardCell tempCell = new BoardCell(row, col);
				grid[row][col] = setCellProperties(tempCell, spaces[col]);
				if (tempCell.isRoom()) {
					// Check if the cell matches those around it for room configuration
					char tempName = tempCell.getRoomName();
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
				if (i != 0 && !grid[i-1][j].isOccupied())
					grid[i][j].addAdjacency(grid[i-1][j]);
				if (j != 0 && !grid[i][j-1].isOccupied())
					grid[i][j].addAdjacency(grid[i][j-1]);
				if (i != ROWS-1 && !grid[i+1][j].isOccupied())
					grid[i][j].addAdjacency(grid[i+1][j]);
				if (j != COLS-1 && !grid[i][j+1].isOccupied())
					grid[i][j].addAdjacency(grid[i][j+1]);
			}
		}
	}
}
