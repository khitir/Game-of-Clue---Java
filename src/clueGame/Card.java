package clueGame;

public class Card {
	private String cardName;
	
	
	public boolean equals(Card target) {
		if (target.getCardName().equals(cardName)) {
			return true;
		}
		else {
			return false;
		}
	}


	public String getCardName() {
		return cardName;
	}


	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	
	
	
	
	
}
