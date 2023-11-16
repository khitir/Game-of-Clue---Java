package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGameControlPanel extends JPanel {
	private JTextField guess = new JTextField(15);
	private JTextField guessResult = new JTextField(15);;
	private JTextField theRoll = new JTextField(5);
	private JTextField whoseTurn = new JTextField(15);
	
	
	
	public ClueGameControlPanel() {
		
		
		setLayout(new GridLayout(2,0)); // create 2 row main grid

		// first panel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,4)); // add 4 columns on first row
		
		// buttons
		JButton accusationButton = new JButton("Make Accusation");
		JButton nextButton = new JButton("NEXT!");
		
		// label for turn
		JLabel turnLabel = new JLabel("Whose turn?");
		JPanel turnPanel = new JPanel();
		whoseTurn.setEditable(false);
		turnPanel.add(turnLabel);
		turnPanel.add(whoseTurn);
		topPanel.add(turnPanel);
		
		//label for roll
		JLabel rollLabel = new JLabel("Roll: ");
		JPanel rollPanel = new JPanel();
		theRoll.setEditable(false);
		rollPanel.add(rollLabel);
		rollPanel.add(theRoll);
		topPanel.add(rollPanel);
		
		// after adding labels, add buttons here
		topPanel.add(accusationButton);
		topPanel.add(nextButton);
		
		//second panel
		JPanel topPanel2 = new JPanel();
		topPanel2.setLayout(new GridLayout(0,2)); // add 2 columns to row 0
		
		// label for guess
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(1,0));
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		guessPanel.add(guess);
		topPanel2.add(guessPanel);
		
        //label for guess result
		JPanel guessResultPanel = new JPanel();
		guessResultPanel.setLayout(new GridLayout(1,0)); 
		guessResultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		guessResultPanel.add(guessResult);
		topPanel2.add(guessResultPanel);
		
		
		// add both panels to main grid
		add(topPanel);
		add(topPanel2);
		
	}
	

    // setters
	public void setTheTurn(Player currentPlayer) {
		whoseTurn.setText(currentPlayer.getName());
		whoseTurn.setBackground(currentPlayer.getColor());
	}
	
	public void setTheRoll(int roll) {
		theRoll.setText(String.valueOf(roll)); // convert int to string
	}
	
	public void setGuess(String guess) {
		this.guess.setText(guess);
	}
	
	public void setGuessResult(String result) {
		guessResult.setText(result);
	}

//	public static void main(String[] args) {
//		ClueGameControlPanel gui = new ClueGameControlPanel();
//		JFrame frame = new JFrame();
//		frame.setContentPane(gui);
//		frame.setSize(750,180);
//		frame.setVisible(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		// test filling in the data
//		//gui.setGuess(new ComputerPlayer( "Col. Mustard", 0, 0, "orange"), 5);
//		gui.setTheRoll(5);
//		gui.setTheTurn(new HumanPlayer("Me", Color.lightGray));
//		gui.setGuess( "I have no guess!");
//		gui.setGuessResult( "So you have nothing?");
//
//	}

}
