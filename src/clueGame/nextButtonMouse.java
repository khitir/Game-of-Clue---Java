package clueGame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

public class nextButtonMouse implements MouseListener {
	Board board;

	@Override
	public void mouseClicked(MouseEvent e) {
		int whoseTurn = board.getWhoseTurn();
		board = Board.getInstance();
		if (whoseTurn == 0 && board.getPlayerFinished() == false) {
			JOptionPane.showMessageDialog(null, "Your turn is not over.");
		}
		board.nextTurn();
		if (whoseTurn != 0) {
			Player currPlayer = board.getPlayers().get(whoseTurn);
			int row = currPlayer.getRow(), col = currPlayer.getColumn();
			BoardCell newLocation = currPlayer.doMove(board.getAdjList(row, col));
			Card proof = currPlayer.doSuggestion(whoseTurn);
		}
		else {
			// Redraw board with targets lit up
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
