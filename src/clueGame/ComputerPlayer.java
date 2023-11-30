package clueGame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.swing.Timer;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ComputerPlayer extends Player {
	
	private ArrayList<Card> roomsNotSeen;
	private ArrayList<Character> roomLabelsNotSeen;
	private ArrayList<Card> playersNotSeen;
	private ArrayList<Card> weaponsNotSeen;
	private Card lastPersonUnseen, lastWeaponUnseen;
	private Timer myTimer;
	private timerActionListener myTimerActionListener;
	private boolean updateCoords;
	private JPanel boardPanel;
	int count;
	float deltaRow, deltaCol;
	float steps = 10;
	float finalDisplayCol, finalDisplayRow;

	public ComputerPlayer(String name, Color color) {
		super(name, color, true);
		weaponsNotSeen = new ArrayList<Card>();
		roomsNotSeen = new ArrayList<Card>();
		playersNotSeen = new ArrayList<Card>();
		roomLabelsNotSeen = new ArrayList<Character>();
		myTimerActionListener = new timerActionListener();
		myTimer = new Timer(100, myTimerActionListener);
	}
	
	@Override
	public Solution createSuggestion() {
		Card playerCard, weaponCard, roomCard;
		Random rand = new Random();
		int person, weapon;
		if (playersNotSeen.size() != 0) {
			person = rand.nextInt(playersNotSeen.size());
			playerCard = playersNotSeen.get(person);
			lastPersonUnseen = playerCard;
		}
		else {
			playerCard = lastPersonUnseen;
		}
		if (weaponsNotSeen.size() != 0) {
			weapon = rand.nextInt(weaponsNotSeen.size());
			weaponCard = weaponsNotSeen.get(weapon);
			lastWeaponUnseen = weaponCard;
		}
		else
			weaponCard = lastWeaponUnseen;
		Map<Character, Room> rooms = board.getRooms();
		Room currRoom = rooms.get(board.getCell(row,  column).getRoomLabel());
		roomCard = new Card(currRoom.getName(), CardType.ROOM, Color.WHITE);
		Solution suggestion = new Solution(roomCard, playerCard, weaponCard);
		return suggestion;
	}
	
	@Override
	public BoardCell doMove(Set<BoardCell> targets, JPanel boardPanel, JPanel controlPanel, ClueGameCardsGUI cardsGUI) {
		BoardCell current = board.getCell(row,  column);
		current.setOccupied(false);
//		BoardCell target = pickTarget(targets);
		BoardCell target = findClosestRoom(targets);
		
		this.boardPanel = boardPanel;
		
		int nextRow = target.getRow();
		int nextCol = target.getCol();

		int rowDiff = nextRow - row;
		int colDiff = nextCol - column;
		deltaRow = (float) (rowDiff/steps);
		deltaCol = (float) (colDiff/steps);
//		System.out.println(deltaRow);
//		System.out.println(deltaCol);
		count = 0;
		int numIterations = 0;
		myTimer.start();

		row = target.getRow();
		column = target.getCol();
		
		//TODO: Change
		finalDisplayRow = row;
		finalDisplayCol = column;
//		displayRow = row;
//		displayCol = column;
		Board board = Board.getInstance();
		return board.getCell(row, column);
	}
	
	// Simple AI for computer
	// Computer picks a room if available, otherwise picks randomly
	public BoardCell pickTarget(Set<BoardCell> targets) {
		Card room;
		Board board = Board.getInstance();
		for (BoardCell cell : targets) {
			if (cell.isRoom()) {
				room = new Card(board.getRoom(cell).getName());
				room.setType(CardType.ROOM);
				if (roomsNotSeen.contains(room))
					return cell;
			}
		}
		Random rand = new Random();
		int index = rand.nextInt(targets.size());
		int i = 0;
		for (BoardCell cell : targets) {
			if (i == index)
				return cell;
			i++;
		}
		return null;
	}
	
	// More complicated AI for Computer
	// Computer selects the closest unseen room, and then picks the cell that will get it the closest to that room
	public BoardCell findClosestRoom(Set<BoardCell> targets) {
		Map<BoardCell, Integer> pathLengths = new HashMap<BoardCell, Integer>();
		for (BoardCell target : targets) {
			Set<BoardCell> seen = new HashSet<BoardCell>();
			Set<BoardCell> current = new HashSet<BoardCell>();
			current.add(target);
			Set<BoardCell> adjacent = new HashSet<BoardCell>();
			boolean flag = false;
			int pathLen = 0;
			while (true) {
				pathLen++;
				for (BoardCell cell : current) {
					seen.add(cell);
					for (BoardCell adj : board.getAdjList(cell.getRow(), cell.getCol())) {
						BoardCell adjCell = board.getCell(adj.getRow(), adj.getCol());
						adjacent.add(adjCell);
						if (adjCell.getRoomLabel() != 'W' && adjCell.getRoomLabel() != 'X') {
							Card tempCard = new Card(adjCell.getRoomName());
							tempCard.setType(CardType.ROOM);
							if (roomsNotSeen.contains(tempCard)) {
								flag = true;
								break;
							}
						}
					}
					if (flag)
						break;
				}
				current = adjacent;
				adjacent = new HashSet<BoardCell>();
				if (flag)
					break;
			}
			pathLengths.put(target, pathLen);
		}
		int minPathLength = 100;
		BoardCell selectedTarget = new BoardCell(0,0);
		for (BoardCell target : pathLengths.keySet()) {
			if (pathLengths.get(target) < minPathLength) {
				minPathLength = pathLengths.get(target);
				selectedTarget = target;				
			}
		}
		return selectedTarget;
	}
	
	@Override
	public void updateHand(Card card) {
	}
	
	public void addUnseenPerson(Card card) {
		playersNotSeen.add(card);
	}
	
	public void addUnseenWeapon(Card card) {
		weaponsNotSeen.add(card);
	}

	public void setRoomsNotSeen(ArrayList<Card> roomsNotSeen) {
		for (Card i : roomsNotSeen){
			this.roomsNotSeen.add(i);
//			this.roomLabelsNotSeen.add(i.getCardName)
		}
	}

	public void setPlayersNotSeen(ArrayList<Card> playersNotSeen) {
		for (Card i : playersNotSeen){
			this.playersNotSeen.add(i);
		}
	}

	public void setWeaponsNotSeen(ArrayList<Card> weaponsNotSeen) {
		for (Card i : weaponsNotSeen){
			this.weaponsNotSeen.add(i);
		}
	}
	
	public ArrayList<Card> getRoomsNotSeen() {
		return roomsNotSeen;
	}

	public ArrayList<Card> getPlayersNotSeen() {
		return playersNotSeen;
	}

	public ArrayList<Card> getWeaponsNotSeen() {
		return weaponsNotSeen;
	}

	@Override
	public void setUnseenPlayers(ArrayList<Card> peopleCards) {
		for (Card card : peopleCards) {
			playersNotSeen.add(card);
		}
	}

	@Override
	public void setUnseenWeapons(ArrayList<Card> peopleCards) {
		for (Card card : peopleCards) {
			weaponsNotSeen.add(card);
		}
	}

	@Override
	public void setUnseenRooms(ArrayList<Card> peopleCards) {
		for (Card card : peopleCards) {
			roomsNotSeen.add(card);
		}
	}

	public void setLocation(int i, int j) {
		this.row = i;
		this.column = j;
	}
	
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
				displayRow = finalDisplayRow;
				displayCol = finalDisplayCol;
			}
		}

	}

}
