package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

public class HumanPlayer extends Player {

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

	@Override
	public BoardCell doMove(Set<BoardCell> adjList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Solution createSuggestion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card doSuggestion(int whoseTurn) {
		// TODO Auto-generated method stub
		return null;
	}

}
