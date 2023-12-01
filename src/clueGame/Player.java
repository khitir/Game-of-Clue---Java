package clueGame;
/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * Class used to track players and their cards. Also used to draw a player
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.Timer;

//import clueGame.ComputerPlayer.timerActionListener;

public abstract class Player {
	private String name;
	private Color color;
	protected int row;
	protected int column;
	protected float displayRow, displayCol;
	private boolean isComputer;
	private boolean isHuman;
	Board board = Board.getInstance();
	
	public Room location;

	private Map<String, Card> cards;  // set of cards for each player, hand 
	private Map<Card, Color> seenCards;  
	private ArrayList<Card> hand;
	
	int count;
	float deltaRow, deltaCol;
	float steps = 10;
	float finalDisplayCol, finalDisplayRow;
	ArrayList<Integer> offsets = new ArrayList<Integer>();
	
	private boolean updateCoords;
	protected JPanel boardPanel = BoardPanel.getInstance();
	
	private Timer myTimer;
	private timerActionListener myTimerActionListener;
	
	public abstract void setUnseenPlayers(ArrayList<Card> peopleCards);
	public abstract void setUnseenWeapons(ArrayList<Card> peopleCards);
	public abstract void setUnseenRooms(ArrayList<Card> peopleCards);
	
	public Player(String name, Color color, boolean isComputer) {
		this.name = name;
		this.color = color;
		this.isComputer = isComputer;
		this.isHuman = !isComputer;
		cards = new HashMap<String, Card>(); 
		hand = new ArrayList<Card>();
		seenCards = new HashMap<Card, Color>();
		
		myTimerActionListener = new timerActionListener();
		myTimer = new Timer(100, myTimerActionListener);
		
		offsets.add(0);
		offsets.add(0);
		
		offsets.add(1);
		offsets.add(1);
		
		offsets.add(1);
		offsets.add(0);
		
		offsets.add(1);
		offsets.add(-1);
		
		offsets.add(0);
		offsets.add(-1);
		
		offsets.add(-1);
		offsets.add(-1);
	}
	

	public void deal(Card card) {
		cards.put(card.getCardName(), card);
		hand.add(card);
	}
	
	public abstract void updateHand(Card card);
	

	public void setRoomLocation(Room room) {
		location = room;
	}
	
	public void updateSeen(Card seenCard, Color seenColor) {
		seenCards.put(seenCard, seenColor);
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		Card room = suggestion.getRoom(), person = suggestion.getPerson(), weapon = suggestion.getWeapon();
		ArrayList<Card> matchingCard = new ArrayList<Card>(); // matching cards list for each player
		
		// fill in matching card list with respective cards
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).equals(room) || hand.get(i).equals(person) || hand.get(i).equals(weapon)) {
				matchingCard.add(hand.get(i));
			}	
		}
	
		
		if (matchingCard.isEmpty()) { // no matching cards
			return null;
		}
		else { // multiple matching cards, return a random one 
			Collections.shuffle(matchingCard);
			return matchingCard.get(0);
		}
	}
	
	public void showMove(BoardCell target) {
		int nextRow = target.getRow();
		int nextCol = target.getCol();
		
		board.getCell(row, column).setOccupied(false);;
		
		// Check current room to update occupied spots list
		if (board.getCell(row, column).getRoomLabel() != 'W' && board.getCell(row, column).getRoomLabel() != 'X') {
			board.getRoom(board.getCell(row, column)).oneLessPlayerInRoom();
			Room tempRoom = board.getRoom(board.getCell(row, column));
//			ArrayList<Boolean> spotsOccupied  = tempRoom.getOccupied();
			for (int i = 0; i < offsets.size(); i += 2) {
				if (row + offsets.get(i) == displayRow && column + offsets.get(i+1) == displayCol) {
					tempRoom.setOccupied(i/2, false);
				}
			}
		}
		
		// Check next room for its occupied spots
		if (board.getCell(nextRow, nextCol).getRoomLabel() != 'W' && board.getCell(nextRow, nextCol).getRoomLabel() != 'X') {
			Room tempRoom = board.getRoom(board.getCell(nextRow, nextCol));
			ArrayList<Boolean> spotsOccupied  = tempRoom.getOccupied();
			int index = 0;
			for (int i = 0; i < spotsOccupied.size(); i++) {
				if (spotsOccupied.get(i) == false) {
					tempRoom.setOccupied(i, true);
					index = i;
					break;
				}
			}
			System.out.println(index);
//			int num = tempRoom.getNumPlayersInRoom();
			finalDisplayRow = nextRow + offsets.get(2*index);
			finalDisplayCol = nextCol + offsets.get(2*index+1);
			tempRoom.oneMorePlayerInRoom();
		}
		else {
			finalDisplayRow = nextRow;
			finalDisplayCol = nextCol;
			board.getCell(nextRow, nextCol).setOccupied(true);
		}
		float rowDiff = finalDisplayRow - row;
		float colDiff = finalDisplayCol - column;
		deltaRow = (float) (rowDiff/steps);
		deltaCol = (float) (colDiff/steps);
		count = 0;
		myTimer.start();

		row = target.getRow();
		column = target.getCol();
	}
	
	// function to draw the player as oval with color
	public void drawPlayer(Graphics g, int width, int height) {
		g.setColor(this.color);
		g.fillOval((int) displayCol*width+1, (int) displayRow*height+1, width-2, height-2);
		g.setColor(Color.black);
		g.drawOval((int) displayCol*width+1, (int) displayRow*height+1, width-2, height-2);
	}
	
	public int getWidth() {
		return board.getNumColumns();
	}
	
	public int getHeight() {
		return board.getNumRows();
	}

	public String getName() {
		return name;
	}
	
	public Color getColor() {
		return color;
	}

	public boolean isComputer() {
		return isComputer;
	}

	public boolean isHuman() {
		return isHuman;
	}

	public Map<String, Card> getCards() {
		return cards;
	}
	public Map<Card, Color> getSeenCards() {
		return seenCards;
	}
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
		this.displayRow = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
		this.displayCol = column;
	}
	
	public void setCell(int row, int col) {
//		this.row = row;
//		this.column = col;
		showMove(board.getCell(row,  col));
	}

	public ArrayList<Card> getHand() {
		return hand;
	}
	public BoardCell getCell() {
		return board.getCell(row, column);
	}
	public abstract BoardCell doMove(Set<BoardCell> adjList);
	public abstract Solution createSuggestion();
	
	public class timerActionListener implements ActionListener {
		Board board = Board.getInstance();

		@Override
		public void actionPerformed(ActionEvent e) {
			count++;
			displayRow += deltaRow;
			displayCol += deltaCol;
			boardPanel.repaint();
			
			if (count == steps) {
				myTimer.stop();
				// TODO: Change
				displayRow = finalDisplayRow;
				displayCol = finalDisplayCol;
			}
		}

	}
}
