package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

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

	@Override
	public BoardCell doMove(Set<BoardCell> adjList) {
		for (BoardCell target : adjList) {
			showMove(target.getRow(),  target.getCol());
		}
		return board.getCell(row,  column);
	}

	@Override
	public Card createSuggestion(Solution suggestion) {
		Card result = board.handleSuggestion(suggestion, this);
		seenCards.put(result, result.getWhoShowedColor());
		return result;
	}

	@Override
	public Solution createAccusation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Solution createSuggestion() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
