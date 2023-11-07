package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClueGameGUI extends JFrame {
	
	public ClueGameGUI() {
		setTitle("Clue");
		setSize(new Dimension(800, 800));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextField playerGuess = new JTextField();
		JPanel guessPanel = new JPanel();
		guessPanel.setSize(1, 0);
		guessPanel.add(playerGuess, BorderLayout.CENTER);
		
		JTextField guessResult = new JTextField();
		JPanel guessResultPanel = new JPanel();
		guessResultPanel.setSize(1, 0);
		guessResultPanel.add(guessResult, BorderLayout.CENTER);
		
		JPanel guess = new JPanel();
		guess.setSize(0, 2);
		guess.add(guessPanel, BorderLayout.WEST);
		guess.add(guessResultPanel, BorderLayout.EAST);
		
		JLabel turnLabel = new JLabel();
		JTextField whoseTurn = new JTextField();
		JPanel turnPanel = new JPanel();
		
		JLabel rollLabel = new JLabel();
		JTextField roll = new JTextField();
		JPanel rollPanel = new JPanel();
		
		JButton accusationButton = new JButton();
		JButton nextButton = new JButton();
		
		JPanel topPanel = new JPanel();
		topPanel.setSize(1, 4);
		topPanel.add(turnPanel);
		topPanel.add(rollPanel);
		topPanel.add(accusationButton);
		topPanel.add(nextButton);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setSize(2, 0);
		controlPanel.add(topPanel, BorderLayout.NORTH);
		controlPanel.add(guess, BorderLayout.SOUTH);
		controlPanel.setVisible(true);
		
		add(controlPanel, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		ClueGameGUI gui = new ClueGameGUI();
		gui.setVisible(true);

	}

}
