package clueGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * Board Class
 * 		Acts as a game board to allow the testing of movement
 */

public class Board {

	private int totalBoardCols = 0;
	private int totalBoardRows = 0;
	private BoardCell[][] grid;
	private Set<BoardCell> targets ;
	private Set<BoardCell> visited;
	private Map<Character, Room> rooms;
	private Map<String, Player> players;
	private Map<String, Card> cards; 

	private static Board theInstance = new Board();

	// The file names to use for the initial configuration
	String csv_file; 
	String txt_file; 

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList();
	}

	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize(){
		try {
			loadSetupConfig();
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
	}

	// Loads the .txt file used for setting up the board
	public void loadSetupConfig() throws BadConfigFormatException {
		FileReader in;
		BufferedReader reader;
		rooms = new HashMap<Character, Room>();
		players = new HashMap<String, Player>();
		cards = new HashMap<String, Card>();
//		Player tempPlayer = new HumanPlayer("Name", "Color", false);
//		players.put("Name", tempPlayer);
//		players.put("Name2", tempPlayer);
//		players.put("Name3", tempPlayer);
//		for (String i : players.keySet()) {
//			System.out.println(i);
//		}
		try {
			in = new FileReader(txt_file);
			reader = new BufferedReader(in);
			String tempLine = reader.readLine();
			while (tempLine != null) {
				String[] elements = tempLine.split(", ");
				// If the line is not a comment or configured in the following format, throw an exception
				// Room/Space, Name, Label
				for (String i : elements)
					System.out.println(i);
				if ((elements.length != 3 && elements.length != 4) && elements[0].charAt(0) != '/') { // make sure we are looking at some object
					throw new BadConfigFormatException("Invalid Initialization File");
				}
				else if (elements[0].equals("Room") || elements[0].equals("Space")) { // if it's a room or space
					Room tempRoom = new Room(elements[2].charAt(0), elements[1]);
					rooms.put(elements[2].charAt(0), tempRoom);
				}
				else if (elements[0].equals("Player") && elements.length == 4) {
					Player newPlayer;
					if (elements[3].equals("Computer"))
						newPlayer = new ComputerPlayer(elements[1], elements[2]);
					else if (elements[3].equals("Human"))
						newPlayer = new HumanPlayer(elements[1], elements[2]);
					else
						throw new BadConfigFormatException("Formatting for Players incorrect");
					players.put(newPlayer.getName(), newPlayer);
				}
				else if (elements[0].equals("Player") && elements.length != 4) {
					throw new BadConfigFormatException("Formatting for Players Incorrect, wrong number of elements");
				}
				else if (elements[0].charAt(0) != '/' && !elements[0].isEmpty()) // error case
					throw new BadConfigFormatException("Invalid Initialization File");
				tempLine = reader.readLine();
			}
			reader.close();
			in.close();
		} catch (IOException e) { // throw exception
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
		} catch (IOException e) { // catch error
			e.printStackTrace();
		} 
		// Set the number of rows and columns based on the width and height of the pseudo 2D array
		String[] tempStr = fileLines.get(0).split(",");
		int numCols = tempStr.length;
		for (int i = 1; i < fileLines.size(); i++) {
			tempStr= fileLines.get(i).split(","); // split lines by ","
			// make sure to go though all the way through file
			for (String elem : tempStr) {
				if (elem.isEmpty())
					throw new BadConfigFormatException("Empty element in Layout file");
			}
			if (tempStr.length != numCols)
				throw new BadConfigFormatException("Unmatched number of columns or rows");
		}
		totalBoardRows = fileLines.size(); // set board dimensions based on file data size
		totalBoardCols = numCols;

		// Initialize the board
		grid = new BoardCell[totalBoardRows][totalBoardCols];
		for (int row = 0; row < totalBoardRows; row++) {
			String[] spaces = fileLines.get(row).split(",", totalBoardCols);
			for (int col = 0; col < totalBoardCols; col++) {
				grid[row][col] = new BoardCell(row, col);
				setCellProperties(grid[row][col], spaces[col]);
			}
		}
		for (int row = 0; row < totalBoardRows; row++) {
			String[] spaces = fileLines.get(row).split(",", totalBoardCols);
			for (int col = 0; col < totalBoardCols; col++) {
				setRoomProperties(grid[row][col], spaces[col]);
			}
		}
		for (int row = 0; row < totalBoardRows; row++) {
			String[] spaces = fileLines.get(row).split(",", totalBoardCols);
			for (int col = 0; col < totalBoardCols; col++) {
				setDoorProperties(grid[row][col], spaces[col], row, col);
				if (grid[row][col].isRoom()) {
					// Check if the cell matches those around it for room configuration
					char tempName = grid[row][col].getRoomName();
					if (col != totalBoardCols-1 && tempName != spaces[col+1].charAt(0) &&  (col != 0 && tempName != grid[row][col-1].getRoomName() &&  (row != 0 && tempName != grid[row-1][col].getRoomName()))) {
						throw new BadConfigFormatException("Invalid Room Configuration");


					}
				}
			}
		}

		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();

		for (int i = 0; i < totalBoardRows; i++) { // fill in the board
			for (int j = 0; j < totalBoardCols; j++) {
				// for a center of room, find entrances and adjacency  
				if (grid[i][j].isRoomCenter()) { // check if room center
					Room currRoom = rooms.get(grid[i][j].getRoomName()); // 
					Set<BoardCell> entrances = currRoom.getEntrances();
					for (BoardCell cell : entrances) {
						grid[i][j].addAdjacency(cell);
					}
				}
				// if not a room, add adacencies by looking lef,right, up, down and checking occuapncy.
				else if (!grid[i][j].isRoom()) {
					if (i != 0 && !grid[i-1][j].isOccupied() && grid[i-1][j].getRoomName() == 'W')
						grid[i][j].addAdjacency(grid[i-1][j]);
					if (j != 0 && !grid[i][j-1].isOccupied() && grid[i][j-1].getRoomName() == 'W')
						grid[i][j].addAdjacency(grid[i][j-1]);
					if (i != totalBoardRows-1 && !grid[i+1][j].isOccupied() && grid[i+1][j].getRoomName() == 'W')
						grid[i][j].addAdjacency(grid[i+1][j]);
					if (j != totalBoardCols-1 && !grid[i][j+1].isOccupied() && grid[i][j+1].getRoomName() == 'W')
						grid[i][j].addAdjacency(grid[i][j+1]);
				}
			}
		}
	}

	// Helper function for loadLayoutConfig()
	// Sets the room properties of all cells on the board
	// We need to know what room each cell is for setCellPropertiesSecond, where we set the entrances to a room
	private void setCellProperties(BoardCell cell, String currSpace) {
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
				Room tempRoom = rooms.get(currSpace.charAt(0));
				tempRoom.setLabelCell(cell);
				rooms.put(tempRoom.getLabel(), tempRoom);
			}
			// Set if the square is a room center
			else if (currSpace.charAt(1) == '*') {
				cell.setIsRoomCenter(true);
				Room tempRoom = rooms.get(currSpace.charAt(0));
				tempRoom.setCenterCell(cell);
				rooms.put(tempRoom.getLabel(), tempRoom);
			}
		}
	}

	private void setRoomProperties(BoardCell cell, String currSpace) {
		// Set secret passages
		if (currSpace.length() == 2 &&  (currSpace.charAt(0) != 'W' && currSpace.charAt(1) != '#' && currSpace.charAt(1) != '*')){
			cell.setSecretPassage(currSpace.charAt(1));
			Room currRoom = rooms.get(currSpace.charAt(0));
			Room secretPassageRoom = rooms.get(currSpace.charAt(1));
			currRoom.setSecretPassageTo(secretPassageRoom.getCenterCell());
			rooms.put(currRoom.getLabel(), currRoom);

		}
	}

	// Helper function for loadLayoutConfig()
	// Sets if a space is an entrance to a room
	private void setDoorProperties(BoardCell cell, String currSpace, int row, int col) {
		// if it's a door, define directions based on second character
		if (currSpace.length() == 2 && currSpace.charAt(0) == 'W') {
			char doorDirectionChar = currSpace.charAt(1);
			DoorDirection doorDirection = null;

			switch (doorDirectionChar) {
			case '<':
				doorDirection = DoorDirection.LEFT;
				break;
			case '>':
				doorDirection = DoorDirection.RIGHT;
				break;
			case '^':
				doorDirection = DoorDirection.UP;
				break;
			case 'v':
				doorDirection = DoorDirection.DOWN;
				break;
			}

			if (doorDirection != null) {
				cell.setDoorDirection(doorDirection);
				cell.setDoorway(true); // set doorways

				int adjacentRow = row;
				int adjacentCol = col;

				switch (doorDirection) { // define how to move on a board based on DoorDirections
				case LEFT:
					adjacentCol--;
					break;
				case RIGHT:
					adjacentCol++;
					break;
				case UP:
					adjacentRow--;
					break;
				case DOWN:
					adjacentRow++;
					break;
				}
				if (isValidCell(adjacentRow, adjacentCol)) {
					Room tempRoom = rooms.get(grid[adjacentRow][adjacentCol].getRoomName());
					tempRoom.setEntrance(cell);
					rooms.put(tempRoom.getLabel(), tempRoom);
					cell.addAdjacency(tempRoom.getCenterCell());
				}
			}
		}
	}

	private boolean isValidCell(int row, int col) {
		return row >= 0 && row < totalBoardRows && col >= 0 && col < totalBoardCols;
	}    

	// Getter for each cell Object
	public BoardCell getCell(int i, int j) { // gets a cell
		return grid[i][j];
	}

	public void calcTargets(BoardCell cell, int pathLength) { // adds visited cells to to list and calls findTargets()
		targets.clear();
		visited.add(cell);
		findTargets(cell, pathLength);
		visited.clear();
	}

	public void findTargets(BoardCell cell, int pathLength) { // recursively finds targets by looking at adjacent list cells and also checks if those cells are occupied or if a room
		for (BoardCell adjCell : cell.getAdjList()) {
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

	public Room getRoom(char roomLabel) { //  gets a room, need to update in future
		return rooms.get(roomLabel);
	}

	public Room getRoom(BoardCell cell) {//  gets a room with cell input, need to update in future
		return rooms.get(cell.getRoomName());
	}

	public int getNumColumns() {
		return totalBoardCols;
	}
	public int getNumRows() {
		return totalBoardRows;
	}

	public Map<String, Player> getPlayers() {
		return players;
	}
}
