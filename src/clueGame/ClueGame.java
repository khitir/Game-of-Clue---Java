package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Draws control and card panels and board together  
 */

public class ClueGame extends JFrame{
	BoardPanel boardPanel;
	ClueGameCardsGUI cardsGUI;
	ClueGameControlPanel controlPanel;

	
	public ClueGame(Board b) {
		// set-up the window
		setSize(700,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game - CSCI306"); // title
		boardPanel = new BoardPanel(b);
		cardsGUI = new ClueGameCardsGUI(b);
		controlPanel = new ClueGameControlPanel();
		
		// set panel locations
		add(boardPanel,BorderLayout.CENTER);
		add(controlPanel,BorderLayout.SOUTH);
		add(cardsGUI, BorderLayout.EAST);
	}
	
	// main function to initialize board and start the game
	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		ClueGame game = new ClueGame(board);
		game.setVisible(true);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
