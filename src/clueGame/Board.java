package clueGame;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * Board Class
 * 		Acts as a game board to allow the testing of movement
 */

public class Board {
	Map<String, Color> colorMap; // color map used to set player colors
	private int totalBoardCols = 0;
	private int totalBoardRows = 0;
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private Map<Character, Room> rooms;
	private ArrayList<Player> players;
	private Map<String, Card> cards;
	Set<Player> playerList;
	ArrayList<Card> cardList;

	private int numPersonCards, numWeaponCards, numRoomCards;
	private ArrayList<Card> peopleCards, roomCards, weaponCards;
	private Solution gameSolution;

	public Solution getGameSolution() {
		return gameSolution;
	}

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
	public void initialize() {
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
		players = new ArrayList<Player>();
		cards = new HashMap<String, Card>();
		peopleCards = new ArrayList<Card>();
		roomCards = new ArrayList<Card>();
		weaponCards = new ArrayList<Card>();
		playerList = new HashSet<Player>();
		cardList = new ArrayList<Card>();
		numPersonCards = 0;
		numRoomCards = 0;
		numWeaponCards = 0;
		colorMap = new HashMap<>();
	    // Adding color names and their Color objects to the map
	    colorMap.put("Red", Color.RED);
	    colorMap.put("Green", Color.GREEN);
	    colorMap.put("Blue", Color.BLUE);
	    colorMap.put("Orange", Color.ORANGE);
	    colorMap.put("Cyan", Color.CYAN);
	    colorMap.put("Magenta", Color.MAGENTA);
	    colorMap.put("Black", Color.BLACK);
	    colorMap.put("White", Color.WHITE);
	   
	    

		try {
			in = new FileReader(txt_file);
			reader = new BufferedReader(in);
			String tempLine = reader.readLine();
			while (tempLine != null) {
				String[] elements = tempLine.split(", ");
				// If the line is not a comment or configured in the following format, throw an
				// exception
				// Room/Space, Name, Label
				if (elements[0].equals("Room") && elements.length == 3) { // if it's a room
					Room tempRoom = new Room(elements[2].charAt(0), elements[1]);
					rooms.put(elements[2].charAt(0), tempRoom);

					Card cardRoom = new Card(elements[1], CardType.ROOM, Color.white); // create room card and set it's type**
					cards.put(elements[1], cardRoom);
					numRoomCards++;
					roomCards.add(cardRoom);
				} else if (elements[0].equals("Space") && elements.length == 3) {
					Room tempRoom = new Room(elements[2].charAt(0), elements[1]);
					rooms.put(elements[2].charAt(0), tempRoom);
				}

				else if (elements[0].equals("Weapon") && elements.length == 2) { // load weapon cards
					Card cardWeapon = new Card(elements[1], CardType.WEAPON, Color.white); // create room card and set it's type**
					cards.put(elements[1], cardWeapon);
					numWeaponCards++;
					weaponCards.add(cardWeapon);
				}

				else if (elements[0].equals("Player") && elements.length == 6) {
					Player newPlayer;
					if (elements[3].equals("Computer")) {
						newPlayer = new ComputerPlayer(elements[1], colorMap.get(elements[2]));
						newPlayer.setRow(Integer.parseInt(elements[4]));
						newPlayer.setColumn(Integer.parseInt(elements[5]));
						}
					    
					else if (elements[3].equals("Human")) {
						newPlayer = new HumanPlayer(elements[1], colorMap.get(elements[2]));
						newPlayer.setRow(Integer.parseInt(elements[4]));
						newPlayer.setColumn(Integer.parseInt(elements[5]));
						}
					
					else
						throw new BadConfigFormatException("Formatting for Players incorrect");
					players.add(newPlayer);
					playerList.add(newPlayer);

					Card cardPerson = new Card(elements[1], CardType.PERSON, Color.white); // create room card and set it's type **
					cards.put(elements[1], cardPerson);
					numPersonCards++;
					peopleCards.add(cardPerson);
				} else if (elements[0].equals("Room") && elements.length != 3) {
					throw new BadConfigFormatException("Formatting for rooms incorrect, wrong number of elements");
				} else if (elements[0].equals("Space") && elements.length != 3) {
					throw new BadConfigFormatException("Formatting for spaces incorrect, wrong number of elements");
				} else if (elements[0].equals("Player") && elements.length != 4) {
					throw new BadConfigFormatException("Formatting for Players Incorrect, wrong number of elements");
				} else if (elements[0].equals("Weapon") && elements.length != 2) {
					throw new BadConfigFormatException("Formatting for Weapons Incorrect, wrong number of elements");
				} else if (elements[0].charAt(0) != '/' && !elements[0].isEmpty()) // error case
					throw new BadConfigFormatException("Invalid Initialization File");
				tempLine = reader.readLine();
			}
			reader.close();
			in.close();
		} catch (IOException e) { // throw exception
			e.printStackTrace();
		}

		// Randomly generate a solution
		Random rand = new Random();
		int num = rand.nextInt(numPersonCards);
		Card solutionPerson = peopleCards.get(num);
		num = rand.nextInt(numWeaponCards);
		Card solutionWeapon = weaponCards.get(num);
		num = rand.nextInt(numRoomCards);
		Card solutionRoom = roomCards.get(num);

		gameSolution = new Solution(solutionRoom, solutionPerson, solutionWeapon);

		// Set the unseen cards for each room
		for (Player temp : players) {
			temp.setUnseenPlayers(peopleCards);
			temp.setUnseenWeapons(weaponCards);
			temp.setUnseenRooms(roomCards);
		}

		// Copy the deck into an ArrayList
		for (String i : cards.keySet()) {
			cardList.add(cards.get(i));
		}
		// Deal cards to each player in turn
		int playerIndex = rand.nextInt(numPersonCards);
		while (cardList.size() > 0) {
			if (playerIndex == numPersonCards) {
				playerIndex = 0;
			}
			int cardNum = rand.nextInt(cardList.size());
			players.get(playerIndex).deal(cardList.get(cardNum));
			cardList.remove(cardNum);
			playerIndex++;
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
		// Set the number of rows and columns based on the width and height of the
		// pseudo 2D array
		String[] tempStr = fileLines.get(0).split(",");
		int numCols = tempStr.length;
		for (int i = 1; i < fileLines.size(); i++) {
			tempStr = fileLines.get(i).split(","); // split lines by ","
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
					char tempName = grid[row][col].getRoomLabel();
					if (col != totalBoardCols - 1 && tempName != spaces[col + 1].charAt(0)
							&& (col != 0 && tempName != grid[row][col - 1].getRoomLabel()
									&& (row != 0 && tempName != grid[row - 1][col].getRoomLabel()))) {
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
					Room currRoom = rooms.get(grid[i][j].getRoomLabel()); //
					Set<BoardCell> entrances = currRoom.getEntrances();
					for (BoardCell cell : entrances) {
						grid[i][j].addAdjacency(cell);
					}
				}
				// if not a room, add adacencies by looking lef,right, up, down and checking
				// occuapncy.
				else if (!grid[i][j].isRoom()) {
					if (i != 0 && !grid[i - 1][j].isOccupied() && grid[i - 1][j].getRoomLabel() == 'W')
						grid[i][j].addAdjacency(grid[i - 1][j]);
					if (j != 0 && !grid[i][j - 1].isOccupied() && grid[i][j - 1].getRoomLabel() == 'W')
						grid[i][j].addAdjacency(grid[i][j - 1]);
					if (i != totalBoardRows - 1 && !grid[i + 1][j].isOccupied() && grid[i + 1][j].getRoomLabel() == 'W')
						grid[i][j].addAdjacency(grid[i + 1][j]);
					if (j != totalBoardCols - 1 && !grid[i][j + 1].isOccupied() && grid[i][j + 1].getRoomLabel() == 'W')
						grid[i][j].addAdjacency(grid[i][j + 1]);
				}
			}
		}
	}

	// Helper function for loadLayoutConfig()
	// Sets the room properties of all cells on the board
	// We need to know what room each cell is for setCellPropertiesSecond, where we
	// set the entrances to a room
	private void setCellProperties(BoardCell cell, String currSpace) {
		cell.setRoomLabel(currSpace.charAt(0));
		// Set space to be a room if it is not unused or a walkway
		if (currSpace.charAt(0) != 'X' && currSpace.charAt(0) != 'W') {
			cell.setIsRoom(true);
		} else if (currSpace == "X") {
			cell.setOccupied(true);
		}
		// Cover all possible cases when a space has two characters
		if (currSpace.length() == 2) {
			// Set if the square is a room label
			if (currSpace.charAt(1) == '#') {
				cell.setIsLabel(true);
				cell.setRoomName(rooms.get(currSpace.charAt(0)).getName());
				
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
		if (currSpace.length() == 2
				&& (currSpace.charAt(0) != 'W' && currSpace.charAt(1) != '#' && currSpace.charAt(1) != '*')) {
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
					Room tempRoom = rooms.get(grid[adjacentRow][adjacentCol].getRoomLabel());
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

	public void findTargets(BoardCell cell, int pathLength) { // recursively finds targets by looking at adjacent list
																// cells and also checks if those cells are occupied or
																// if a room
		for (BoardCell adjCell : cell.getAdjList()) {
			if (adjCell.isRoom() && !visited.contains(adjCell)) {
				targets.add(adjCell);
			} else if (!visited.contains(adjCell) && !adjCell.isOccupied()) {
				visited.add(adjCell);
				if (pathLength == 1) {
					targets.add(adjCell);
				} else {
					findTargets(adjCell, pathLength - 1);
				}
				visited.remove(adjCell);
			}
		}
	}

	public boolean checkAccusation(Card room, Card person, Card weapon) {  // returns true if all 3 cards match to solution
		return ((gameSolution.getRoom().equals(room)) && (gameSolution.getWeapon().equals(weapon)) && (gameSolution.getPerson().equals(person)));
	}
	
	public Card handleSuggestion(Solution suggestion1, Player accuser) {
		int index = 0;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).equals(accuser))
				index = i;
		}
		for (int numPlayers = 0; numPlayers < players.size() - 1; numPlayers++) {
			index++;
			if (index == players.size())
				index = 0;
			Player next = players.get(index);
			Card result = next.disproveSuggestion(suggestion1.getRoom(), suggestion1.getPerson(), suggestion1.getWeapon());
			if (result != null)
				return result;
		}
		return null;
	}

	public void setConfigFiles(String csv, String file) { // sets csv and text files
		csv_file = "data/" + csv;
		txt_file = "data/" + file;
	}
	
	public Set<BoardCell> getTargets() { // gets target
		return targets;
	}

	public Room getRoom(char roomLabel) { // gets a room, need to update in future
		return rooms.get(roomLabel);
	}

	public Room getRoom(BoardCell cell) {// gets a room with cell input, need to update in future
		return rooms.get(cell.getRoomLabel());
	}

	public int getNumColumns() {
		return totalBoardCols;
	}

	public int getNumRows() {
		return totalBoardRows;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Map<String, Card> getCards() {
		return cards;
	}

	public int getNumPersonCards() {
		return numPersonCards;
	}

	public int getNumWeaponCards() {
		return numWeaponCards;
	}

	public int getNumRoomCards() {
		return numRoomCards;
	}

	public ArrayList<Card> getPeopleCards() {
		return peopleCards;
	}

	public ArrayList<Card> getRoomCards() {
		return roomCards;
	}

	public ArrayList<Card> getWeaponCards() {
		return weaponCards;
	}

	public Solution getSolution() {
		return gameSolution;
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
}
