package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Player {
	private String name;
	private Color color;
	private int row, column;
	private boolean isComputer;
	private boolean isHuman;
	
	public Room location;

	private Map<String, Card> cards;  // set of cards for each player, hand 
	private Map<Card, Color> seenCards;  
	private ArrayList<Card> hand;
	
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
	
	public Card disproveSuggestion(Card room, Card person, Card weapon) {
		ArrayList<Card> matchingCard = new ArrayList<Card>(); // matching cards list for each player
		
		// fill in matching card list with respective cards
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).equals(room) || hand.get(i).equals(person) || hand.get(i).equals(weapon)) {
				matchingCard.add(hand.get(i));
			}	
		}
	
		
		if (matchingCard.size() == 0) { // no matching cards
			return null;
		}
		else { // multiple matching cards, return a random one 
			Collections.shuffle(matchingCard);
			return matchingCard.get(0);
		}
	}
	
	
	// function to draw the player as oval with color
	public void drawPlayer(Graphics g, int width, int height) {
			g.setColor(this.color);
			g.fillOval(column*width+1, row*height+1, width-2, height-2);
			g.setColor(Color.black);
			g.drawOval(column*width+1, row*height+1, width-2, height-2);
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
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}
}
