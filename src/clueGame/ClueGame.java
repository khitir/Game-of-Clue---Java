package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/* Authors: John Taylor and Zakaria Khitirishvili
 * Draws control and card panels and board together  
 */

public class ClueGame extends JFrame{
	BoardPanel boardPanel;
	ClueGameCardsGUI cardsGUI;
	ClueGameControlPanel controlPanel;
	
	private static ClueGame theInstance = ClueGame.getInstance();
	
	public static ClueGame getInstance() {
		return theInstance;
	}
	
	public ClueGame(Board b) {
		// set-up the window
		setSize(700,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game - CSCI306"); // title
		boardPanel = BoardPanel.getInstance();
		cardsGUI = ClueGameCardsGUI.getInstance();
		controlPanel = ClueGameControlPanel.getInstance();
		
		// set panel locations
		add(boardPanel,BorderLayout.CENTER);
		add(controlPanel,BorderLayout.SOUTH);
		add(cardsGUI, BorderLayout.EAST);
		
	}
	
	public void closeGame() {
		theInstance.setVisible(false);
	}
	
	// main function to initialize board and start the game
	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		theInstance = new ClueGame(board);
		theInstance.setVisible(true);
		JOptionPane.showMessageDialog(null, "You are a Physics Major. Can you find the solution before your professors?");

		theInstance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
