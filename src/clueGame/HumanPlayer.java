package clueGame;

import java.util.ArrayList;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, String color) {
		super(name, color, false);
	}

	@Override
	public void updateHand(Card card) {
		return;
	}

	@Override
	public void setUnseenPlayers(ArrayList<Card> peopleCards) {
		return;
	}

	@Override
	public void setUnseenWeapons(ArrayList<Card> peopleCards) {
		return;
	}

	@Override
	public void setUnseenRooms(ArrayList<Card> peopleCards) {
		return;
	}

}
