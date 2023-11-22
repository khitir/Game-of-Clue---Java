package clueGame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

public class nextButtonMouse implements MouseListener {
	Board board = Board.getInstance();

	@Override
	public void mouseClicked(MouseEvent e) {
		int whoseTurn = board.getWhoseTurn();
		if (whoseTurn == 0 && board.isPlayerTurnFinished() == false) {
			JOptionPane.showMessageDialog(null, "Your turn is not over.");
//			return;
		}
		board.nextTurn();
		whoseTurn = board.getWhoseTurn();
		if (whoseTurn != 0) {
			Player currPlayer = board.getPlayers().get(whoseTurn);
//			int row = currPlayer.getRow(), col = currPlayer.getColumn();
			BoardCell newLocation = currPlayer.doMove(board.getTargets());
			Solution suggestion = currPlayer.createSuggestion();
			board.handleSuggestion(suggestion, currPlayer);
		}
		else {
			// Redraw board with targets lit up
			board.setPlayerTurnFinished(false);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
