package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClueGame extends JFrame{
	BoardPanel boardPanel;
	ClueGameCardsGUI cardsGUI;
	ClueGameControlPanel controlPanel;

	
	public ClueGame(Board b) {
		setSize(700,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		boardPanel = new BoardPanel(b);
		cardsGUI = new ClueGameCardsGUI(b);
		controlPanel = new ClueGameControlPanel();
		add(boardPanel,BorderLayout.CENTER);
		add(controlPanel,BorderLayout.SOUTH);
		add(cardsGUI, BorderLayout.EAST);
	}
	
	public void updateBoard() {
		// some code here
		//boardPanel.functionname();
	}
	
	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		ClueGame game = new ClueGame(board);
		game.setVisible(true);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
