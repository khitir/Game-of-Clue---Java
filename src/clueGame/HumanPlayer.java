package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class HumanPlayer extends Player {
	// human player turn (true/false)

	public HumanPlayer(String name, Color color) {
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
