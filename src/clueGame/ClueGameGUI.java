package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClueGameGUI extends JPanel {
	
	public ClueGameGUI() {
		setLayout(new GridLayout(2,0));
		
//		JTextField playerGuess = new JTextField();
//		JPanel guessPanel = new JPanel();
//		guessPanel.setSize(1, 0);
//		guessPanel.add(playerGuess, BorderLayout.CENTER);
//		
//		JTextField guessResult = new JTextField();
//		JPanel guessResultPanel = new JPanel();
//		guessResultPanel.setSize(1, 0);
//		guessResultPanel.add(guessResult, BorderLayout.CENTER);
//		
//		JPanel guess = new JPanel();
//		guess.setSize(0, 2);
//		guess.add(guessPanel, BorderLayout.WEST);
//		guess.add(guessResultPanel, BorderLayout.EAST);
	

		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,4));
		
		
		JButton accusationButton = new JButton("Make Accusation");
		JButton nextButton = new JButton("NEXT!");
		
		JLabel turnLabel = new JLabel("Whose turn?");
		JTextField whoseTurn = new JTextField(15);
		JPanel turnPanel = new JPanel();
		whoseTurn.setEditable(false);
		turnPanel.add(turnLabel);
		turnPanel.add(whoseTurn);
		topPanel.add(turnPanel);
		
		JLabel rollLabel = new JLabel("Roll: ");
		JTextField theRoll = new JTextField(5);
		JPanel rollPanel = new JPanel();
		theRoll.setEditable(false);
		rollPanel.add(rollLabel);
		rollPanel.add(theRoll);
		topPanel.add(rollPanel);
		
		
//		topPanel.add(turnPanel);
//		topPanel.add(rollPanel);
		topPanel.add(accusationButton);
		topPanel.add(nextButton);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setSize(2, 0);
//		controlPanel.add(topPanel, BorderLayout.NORTH);
//		controlPanel.add(guess, BorderLayout.SOUTH);
		controlPanel.setVisible(true);
		
		add(controlPanel, BorderLayout.SOUTH);
		add(topPanel, BorderLayout.NORTH);

	}
	

	public static void main(String[] args) {
		ClueGameGUI gui = new ClueGameGUI();
		JFrame frame = new JFrame();
		frame.setContentPane(gui);
		frame.setSize(750,180);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
