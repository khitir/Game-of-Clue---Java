package clueGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//package experiment;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * TestBoard Class
 * 		Acts as a pseudo game board to allow the testing of movement
 */

public class Board {

	private int COLS = 0;
	private int ROWS = 0;
	private BoardCell[][] grid;
	private Set<BoardCell> targets ;
	private Set<BoardCell> visited;
	private Set<Room> rooms;

	private static Board theInstance = new Board();
	
	// set the file names to use config files
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
    		if (tempStr.length != numCols)
    			throw new BadConfigFormatException("Cannot find File");
    	}
    	ROWS = fileLines.size();
    	COLS = numCols;
    	
    	// Initialize the board
		grid = new BoardCell[ROWS][COLS];
		for (int row = 0; row < ROWS; row++) {
			String[] spaces = fileLines.get(row).split(",", COLS);
			for (int col = 0; col < COLS; col++) {
				BoardCell tempCell = new BoardCell(row, col);
				grid[row][col] = setCellProperties(tempCell, spaces, row, col);
				if (tempCell.isRoom()) {
					char a = tempCell.getRoomName();
					if (col != COLS-1 && a != spaces[col+1].charAt(0)) {
						if (col != 0 && a != grid[row][col-1].getRoomName()) {
							if (row != 0 && a != grid[row-1][col].getRoomName()) {
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
    
    private BoardCell setCellProperties(BoardCell tempCell, String[] spaces, int i, int j) {
    	tempCell.setRoomName(spaces[j].charAt(0));
		// Set space to be a room if it is not unused or a walkway
		if (spaces[j].charAt(0) != 'X' && spaces[j].charAt(0) != 'W') {
			tempCell.setIsRoom(true);
		}
		else if (spaces[j] == "X") {
			tempCell.setOccupied(true);
		}
		if (spaces[j].length() == 2) {
			// Set door directions
			if (spaces[j].charAt(0) == 'W') {
				if (spaces[j].charAt(1) == '<')
					tempCell.setDoorDirection(DoorDirection.LEFT);
				else if (spaces[j].charAt(1) == '>')
					tempCell.setDoorDirection(DoorDirection.RIGHT);
				else if (spaces[j].charAt(1) == '^')
					tempCell.setDoorDirection(DoorDirection.UP);
				else if (spaces[j].charAt(1) == 'v')
					tempCell.setDoorDirection(DoorDirection.DOWN);
			}
			// Set if the square is a room label
			else if (spaces[j].charAt(1) == '#') {
				tempCell.setIsLabel(true);
			}
			// Set if the square is a room center
			else if (spaces[j].charAt(1) == '*') {
				tempCell.setIsRoomCenter(true);
			}
			// Set secret passages
			else {
				tempCell.setSecretPassage(spaces[j].charAt(1));
			}
		}
		return tempCell;
    }

	
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
		Room room1 = new Room("name", new BoardCell(0,0) , new BoardCell(0,0));
		return room1;
	}
	
	public Room getRoom(BoardCell cell) {//  gets a room with cell input, need to update in future
		cell.getRoomName();
		for (Room room : rooms ) {
			
		}

	}
	
	public int getNumColumns() {
		return COLS;
	}
	public int getNumRows() {
		return ROWS;
	}

	public void loadSetupConfig() throws BadConfigFormatException {
		ArrayList<String> fileLines = new ArrayList<String>();
		FileReader in;
    	BufferedReader reader;
    	try {
			in = new FileReader(txt_file);
			reader = new BufferedReader(in);
			String tempLine = reader.readLine();
			while (tempLine != null) {
				String[] elements = tempLine.split(", ");
				if (elements.length != 3) {
					throw new BadConfigFormatException("Invalid Initialization File");
				}
				if (elements[0] == "Room") {
					Room tempRoom = new Room(elements[1], elements[2]);
					rooms.add(tempRoom);
				}
				else if (elements[0] != "Space" && elements[0].charAt(0) != '/')
					throw new BadConfigFormatException("Invalid Initialization File");
				fileLines.add(tempLine);
				tempLine = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void loadLayoutConfig() throws BadConfigFormatException {
		initialize();
		
	}
}
