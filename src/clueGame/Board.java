package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * Board Class
 * 		Acts as a game board to allow the testing of movement
 */

public class Board {
	private Map<String, Color> colorMap; // color map used to set player colors
	private int totalBoardCols = 0;
	private int totalBoardRows = 0;
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private Map<Character, Room> rooms;
	private ArrayList<Player> players;
	private ArrayList<Card> cardDeck;
	private ArrayList<Card> allCards;
	private boolean ishuman = false;

	private ArrayList<Card> peopleCards, roomCards, weaponCards;
	private Solution gameSolution;
	// The file names to use for the initial configuration
	String csv_file;
	String txt_file;
	
	private int whoseTurn;
	private int currRoll;
	private boolean playerTurnFinished, playerSuggestionFinished;
	
	public boolean isPlayerSuggestionFinished() {
		return playerSuggestionFinished;
	}

	public void setPlayerSuggestionFinished(boolean playerSuggestionFinished) {
		this.playerSuggestionFinished = playerSuggestionFinished;
	}

	public boolean isPlayerTurnFinished() {
		return playerTurnFinished;
	}

	public void setPlayerTurnFinished(boolean playerTurnFinished) {
		this.playerTurnFinished = playerTurnFinished;
	}

	public int getWhoseTurn() {
		return whoseTurn;
	}

	public Solution getGameSolution() {
		return gameSolution;
	}
	
	private static Board theInstance = new Board();

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
		peopleCards = new ArrayList<Card>();
		roomCards = new ArrayList<Card>();
		weaponCards = new ArrayList<Card>();
		cardDeck = new ArrayList<Card>();
		allCards = new ArrayList<Card>();
		colorMap = new HashMap<String, Color>();
	    // Adding color names and their Color objects to the map
	    colorMap.put("Red", Color.RED);
	    colorMap.put("Green", Color.GREEN);
	    colorMap.put("Blue", Color.BLUE);
	    colorMap.put("Orange", Color.ORANGE);
	    colorMap.put("Cyan", Color.CYAN);
	    colorMap.put("Magenta", Color.MAGENTA);
	    colorMap.put("Black", Color.BLACK);
	    colorMap.put("White", Color.WHITE);
	    colorMap.put("Gray", Color.GRAY);
	    
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
					allCards.add(cardRoom);
					roomCards.add(cardRoom);
				} else if (elements[0].equals("Space") && elements.length == 3) {
					Room tempRoom = new Room(elements[2].charAt(0), elements[1]);
					rooms.put(elements[2].charAt(0), tempRoom);
				}

				else if (elements[0].equals("Weapon") && elements.length == 2) { // load weapon cards
					Card cardWeapon = new Card(elements[1], CardType.WEAPON, Color.white); // create room card and set it's type**
					allCards.add(cardWeapon);
					weaponCards.add(cardWeapon);
				}

				else if (elements[0].equals("Player") && elements.length == 6) {
					Player newPlayer;
					if (elements[3].equals("Computer")) {
						newPlayer = new ComputerPlayer(elements[1], colorMap.get(elements[2]));
					}
					else if (elements[3].equals("Human")) {
						newPlayer = new HumanPlayer(elements[1], colorMap.get(elements[2]));
					}
					else
						throw new BadConfigFormatException("Formatting for Players incorrect");
					newPlayer.setRow(Integer.parseInt(elements[4]));
					newPlayer.setColumn(Integer.parseInt(elements[5]));
					players.add(newPlayer);

					Card cardPerson = new Card(elements[1], CardType.PERSON, Color.white); // create room card and set it's type **
					allCards.add(cardPerson);
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
		
		// Copy the deck into an ArrayList
		for (Card card : allCards) {
			cardDeck.add(card);
		}

		// Randomly generate a solution
		Collections.shuffle(peopleCards);
		Card solutionPerson = peopleCards.get(0);
		cardDeck.remove(cardDeck.indexOf(solutionPerson));
		
		Collections.shuffle(weaponCards);
		Card solutionWeapon = weaponCards.get(0);
		cardDeck.remove(cardDeck.indexOf(solutionWeapon));
		
		Collections.shuffle(roomCards);
		Card solutionRoom = roomCards.get(0);
		cardDeck.remove(cardDeck.indexOf(solutionRoom));

		gameSolution = new Solution(solutionRoom, solutionPerson, solutionWeapon);

		// Set the unseen cards for each room
		for (Player player : players) {
			player.setUnseenPlayers(peopleCards);
			player.setUnseenWeapons(weaponCards);
			player.setUnseenRooms(roomCards);
		}
		
		Collections.shuffle(cardDeck);
		Random rand = new Random();
		int playerIndex = rand.nextInt(players.size());
		// Deal cards to each player in turn
		while (cardDeck.size() > 0) {
			if (playerIndex == players.size()) {
				playerIndex = 0;
			}
			players.get(playerIndex).deal(cardDeck.get(0));
			cardDeck.remove(0);
			playerIndex++;
		}
		whoseTurn = rand.nextInt(players.size());
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
				// if not a room, add adjacencies by looking left, right, up, down and checking
				// occupancy.
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
				cell.setRoomName(rooms.get(currSpace.charAt(0)).getName());
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
				case NONE:
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

	public void calcTargets(BoardCell cell, int pathLength, boolean justPulledIntoRoom) { // adds visited cells to to list and calls findTargets()
		targets.clear();
		visited.add(cell);
		findTargets(cell, pathLength);
		visited.clear();
		if (justPulledIntoRoom) {
			targets.add(cell);
		}
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

	public boolean checkAccusation(Solution s) {  // returns true if all 3 cards match to solution
		return ((gameSolution.getRoom().equals(s.getRoom())) && (gameSolution.getWeapon().equals(s.getWeapon())) && 
				(gameSolution.getPerson().equals(s.getPerson())));
	}
	
	public Card handleSuggestion(Solution suggestion1, Player accuser) {
		int index = 0, indexAccusee = 0;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).equals(accuser))
				index = i;
			if (players.get(i).getName().equals(suggestion1.getPerson().getCardName()))
				indexAccusee = i;
		}
		players.get(indexAccusee).setCell(players.get(index).getRow(), players.get(index).getColumn());
		if (index != indexAccusee)
			players.get(indexAccusee).justPulledIntoRoom = true;
		for (int numPlayers = 0; numPlayers < players.size() - 1; numPlayers++) {
			index++;
			if (index == players.size())
				index = 0;
			Player next = players.get(index);
			Card result = next.disproveSuggestion(suggestion1);
			if (result != null) {
				result.setWhoShowedCard(next.getColor());
				accuser.setSuggestionDisproven(true);
				return result;
			}
		}
		accuser.setSuggestionDisproven(false);
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

	public ArrayList<Card> getAllCards() {
		return allCards;
	}
	
	public ArrayList<Card> getCardDeck() {
		return cardDeck;
	}

	public int getNumPersonCards() {
		return players.size();
	}

	public int getNumWeaponCards() {
		return weaponCards.size();
	}

	public int getNumRoomCards() {
		return roomCards.size();
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

	public void nextTurn() {
		whoseTurn++;
		if (whoseTurn >= players.size())
			whoseTurn = 0;
		if (players.get(whoseTurn).getCanPlay() == false)
			whoseTurn++;
		if (whoseTurn >= players.size())
			whoseTurn = 0;
		Random rand = new Random();
		currRoll = rand.nextInt(6);
		currRoll++;
		calcTargets(players.get(whoseTurn).getCell(), currRoll, players.get(whoseTurn).justPulledIntoRoom);
		if (players.get(whoseTurn).justPulledIntoRoom) {
			players.get(whoseTurn).justPulledIntoRoom = false;
		}
	}

	public int getCurrRoll() {
		return currRoll;
	}
	
	// draw a cell, pass in location where draw and what color to set, get's called in BoardPanel
	public void drawBoard(Graphics g, int boardWidth, int boardHeight) {
		// define cell size
		int width = totalBoardCols; // gets board dimensions
		int height = totalBoardRows;
		int cellWidth = boardWidth/width; // makes sure resizing will work
		int cellHeight = boardHeight/height;//// makes sure resizing will work

		// draws cells from boardcell
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				grid[i][j].drawCell(g, cellWidth ,cellHeight, targets, whoseTurn);
			}
		}

		// draws room names, function in boardcell
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (grid[i][j].isLabel()) {
					grid[i][j].drawRoomName(g, cellWidth ,cellHeight);
				}
			}
		}

		// draws players as ovals, function in player, but color map in main
		for (Player player: players) {
			player.drawPlayer(g, cellWidth, cellHeight);
		}
	}

	public Map<Character, Room> getRooms() {
		return rooms;
	}

	public void handleAccusation(Solution accusation) {
		for (Card c : players.get(whoseTurn).getSeenCards().keySet()) {
			System.out.println(c.getCardName());
		}
		System.out.println(accusation.getPerson().getCardName());
		System.out.println(accusation.getWeapon().getCardName());
		System.out.println(accusation.getRoom().getCardName());
		if (checkAccusation(accusation)) {
			// Display a message ending the game
			JOptionPane.showMessageDialog(null, players.get(whoseTurn).getName() + " has won the game.\nThe solution was:\n" + 
					gameSolution.getPerson().getCardName() + ", in " + gameSolution.getRoom().getCardName() + ", with the " + 
					gameSolution.getWeapon().getCardName() + ".");
//			ClueGameControlPanel control = ClueGameControlPanel.getInstance();
//			control.disable();
			
			ClueGame game = ClueGame.getInstance();
			// Close out game somehow
		}
		else {
			JOptionPane.showMessageDialog(null, players.get(whoseTurn).getName() + " has made an incorrect accusation.");
			Board.getInstance().getPlayers().get(whoseTurn).setCanPlay(false);
		}
	}

}
