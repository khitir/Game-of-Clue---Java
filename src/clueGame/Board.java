package clueGame;

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

	final static int COLS = 50;
	final static int ROWS = 50;
	private BoardCell[][] grid;
	private Set<BoardCell> targets ;
	private Set<BoardCell> visited;
	private Set<Room> room;

	private static Board theInstance = new Board();
	
	String csv_file;
	String txt_file;

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
    /*
     * initialize the board (since we are using singleton pattern)
     */
    public void initialize()
    {
		grid = new BoardCell[ROWS][COLS];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				grid[i][j] = new BoardCell(i, j);
			}
		}
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		for (int i = 0; i < ROWS; i++) {
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

	
	public BoardCell getCell(int i, int j) {
		return grid[i][j];
	}

	
	
	public void calcTargets(BoardCell cell, int pathLength) {
		visited.add(cell);
		findTargets(cell, pathLength);
		visited.clear();
	}
	
	public void findTargets(BoardCell cell, int pathLength) {
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

	
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public void setConfigFiles(String csv, String file) {
		csv_file = csv;
		txt_file = file;
	}
	
	public Room getRoom(char room) {
		Room room1 = new Room("name", new BoardCell(0,0) , new BoardCell(0,0));
		return room1;
	}
	
	public Room getRoom(BoardCell room) {
		Room room1 = new Room("name", room , new BoardCell(0,0));
		return room1;
	}
	
	public static int getNumColumns() {
		return COLS;
	}
	public static int getNumRows() {
		return ROWS;
	}

	public void loadSetupConfig() {
		// TODO Auto-generated method stub
		
	}
	public void loadLayoutConfig() {
		// TODO Auto-generated method stub
		
	}
}
