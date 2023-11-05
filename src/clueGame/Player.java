package clueGame;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Player {
	private String name;
	private String color;
	private int row, column;
	private boolean isComputer;
	private boolean isHuman;
	
	private Map<String, Card> cards;  // set of cards for each player 
	
	public Player(String name, String color, boolean isComputer) {
		this.name = name;
		this.color = color;
		this.isComputer = isComputer;
		this.isHuman = !isComputer;
		cards = new HashMap<String, Card>(); 
	}
	
	public void deal(Card card) {
		cards.put(card.getCardName(), card);
	}
	
	public abstract void updateHand(Card card);
	
	public String getName() {
		return name;
	}
	
	public String getColor() {
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
}
