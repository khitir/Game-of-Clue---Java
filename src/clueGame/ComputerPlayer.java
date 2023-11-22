package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private ArrayList<Card> roomsNotSeen;
	private ArrayList<Character> roomLabelsNotSeen;
	private ArrayList<Card> playersNotSeen;
	private ArrayList<Card> weaponsNotSeen;
	private Card lastPersonUnseen, lastWeaponUnseen;

	public ComputerPlayer(String name, Color color) {
		super(name, color, true);
		weaponsNotSeen = new ArrayList<Card>();
		roomsNotSeen = new ArrayList<Card>();
		playersNotSeen = new ArrayList<Card>();
		roomLabelsNotSeen = new ArrayList<Character>();
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
	public BoardCell doMove(Set<BoardCell> targets) {
		BoardCell target = pickTarget(targets);
		row = target.getRow();
		column = target.getCol();
		Board board = Board.getInstance();
		return board.getCell(row, column);
	}
	
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

}
