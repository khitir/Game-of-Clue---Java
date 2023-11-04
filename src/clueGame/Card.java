package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	public Card(String cardName, CardType type) {
		this.cardName = cardName;
		this.type = type;
	}
	
	
	public CardType getType() {
		return type;
	}


	public void setType(CardType type) {
		this.type = type;
	}
	
	public String getCardName() {
		return cardName;
	}


	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;  // Same object reference
	    }

	    if (obj == null || getClass() != obj.getClass()) {
	        return false;  // Not the same class or null
	    }

	    Card otherCard = (Card) obj;
	    return this.cardName.equals(otherCard.cardName);
	}


	@Override
    public int hashCode() {
        return cardName.hashCode();
    }
	
	
	
	
}
