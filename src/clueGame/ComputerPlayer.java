package clueGame;

import java.util.HashSet;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private Set<Card> roomsNotSeen;
	private Set<Card> playersNotSeen;
	private Set<Card> weaponsNotSeen;

	public ComputerPlayer(String name, String color) {
		super(name, color, true);
		weaponsNotSeen = new HashSet<Card>();
		roomsNotSeen = new HashSet<Card>();
		playersNotSeen = new HashSet<Card>();
	}
	
	public Solution createSuggestion() {
		Solution newSolution = new Solution(new Card("1"), new Card("2"), new Card("3"));
		return newSolution;
	}
	
	@Override
	public void updateHand(Card card) {
	}

	public void setRoomsNotSeen(Set<Card> roomsNotSeen) {
		this.roomsNotSeen = roomsNotSeen;
	}

	public void setPlayersNotSeen(Set<Card> playersNotSeen) {
		this.playersNotSeen = playersNotSeen;
	}

	public void setWeaponsNotSeen(Set<Card> weaponsNotSeen) {
		this.weaponsNotSeen = weaponsNotSeen;
	}
	
	public Set<Card> getRoomsNotSeen() {
		return roomsNotSeen;
	}

	public Set<Card> getPlayersNotSeen() {
		return playersNotSeen;
	}

	public Set<Card> getWeaponsNotSeen() {
		return weaponsNotSeen;
	}

}
