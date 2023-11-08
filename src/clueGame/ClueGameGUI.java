package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

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
	

		// first panel
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
		
		//second panel
		JPanel topPanel2 = new JPanel();
		topPanel2.setLayout(new GridLayout(0,2));
		
		JLabel guessLabel = new JLabel("Guess");
		JTextField guess = new JTextField(15);
		JPanel guessPanel = new JPanel();
		guess.setEditable(false);
		guessPanel.add(guessLabel);
		guessPanel.add(guess);
		topPanel2.add(guessPanel);
		
//		JPanel topPanel3 = new JPanel();
//		topPanel3.setLayout(new GridLayout(1,0));
		
//		JLabel guessResultLabel = new JLabel("Guess Result");
		JTextField guessResult = new JTextField(15);
		JPanel guessResultPanel = new JPanel();
		guessResultPanel.setLayout(new GridLayout(1,0));
		guessResultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
//		guessResult.setEditable(false);
//		guessResultPanel.add(guessResultLabel);
		guessResultPanel.add(guessResult);
		topPanel2.add(guessResultPanel);
		
//		JPanel controlPanel = new JPanel();
//		controlPanel.setSize(2, 0);
//		controlPanel.add(topPanel, BorderLayout.NORTH);
//		controlPanel.add(guess, BorderLayout.SOUTH);
	//	controlPanel.setVisible(true);
		
	//	add(controlPanel, BorderLayout.SOUTH);
		add(topPanel);
		add(topPanel2);
//		add(topPanel3);
		

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
