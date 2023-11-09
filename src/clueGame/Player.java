package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public abstract class Player {
	private String name;
	private Color color;
	private int row, column;
	private boolean isComputer;
	private boolean isHuman;
	
	public Room location;

	private Map<String, Card> cards;  // set of cards for each player, hand 
	private Set <Card> seenCards;  // seen cards, not yet implemented.
	private ArrayList<Card> hand;
	
	public Player(String name, Color color, boolean isComputer) {
		this.name = name;
		this.color = color;
		this.isComputer = isComputer;
		this.isHuman = !isComputer;
		cards = new HashMap<String, Card>(); 
		hand = new ArrayList<Card>();
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}

	public void deal(Card card) {
		cards.put(card.getCardName(), card);
		hand.add(card);
	}
	
	public abstract void updateHand(Card card);
	

	public void setRoomLocation(Room room) {
		location = room;
	}
	

	public void updateSeen(Card seenCard) {
		
	}
	
	public Card disproveSuggestion(Card room, Card person, Card weapon) {
//		ArrayList<Card> matchingCards = new ArrayList<Card>();
		int numMatch = 0; // number of matching cards
		ArrayList<Card> matchingCard = new ArrayList<Card>(); // matching cards list for each player
		
		// fill in matching card list with respective cards
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).equals(room) || hand.get(i).equals(person) || hand.get(i).equals(weapon)) {
				matchingCard.add(hand.get(i));
				numMatch++;
			}	
		}
	
		
		if (numMatch == 0) { // no matching cards
			return null;
		}
		else if (numMatch == 1) { // 1 matching card
			return matchingCard.get(0);
		}

		else { // multiple matching cards, return a random one 
			Random rand = new Random();
			int randIndex = rand.nextInt(numMatch);
			return matchingCard.get(randIndex);
		}
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

	public abstract void setUnseenPlayers(ArrayList<Card> peopleCards);
	public abstract void setUnseenWeapons(ArrayList<Card> peopleCards);
	public abstract void setUnseenRooms(ArrayList<Card> peopleCards);
}
