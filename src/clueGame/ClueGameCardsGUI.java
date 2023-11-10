package clueGame;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGameCardsGUI extends JPanel{
//	private JTextField people = new JTextField(15);
//	private JTextField rooms = new JTextField(15);;
//	private JTextField weapons = new JTextField(15);
	JLabel inHandLabel1 = new JLabel("In Hand:"), inHandLabel2 = new JLabel("In Hand:"), inHandLabel3 = new JLabel("In Hand:");
	JLabel seenLabel1 = new JLabel("Seen:"), seenLabel2 = new JLabel("Seen:"), seenLabel3 = new JLabel("Seen:");
	
	
	public ClueGameCardsGUI() {
		setLayout(new GridLayout(0,1)); // create 2 row main grid
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		// first panel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(0,1)); // add 4 columns on first row
		topPanel.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
		
		topPanel.add(createSubpanel("People", board, CardType.PERSON, inHandLabel1, seenLabel1));
		topPanel.add(createSubpanel("Rooms", board, CardType.ROOM, inHandLabel2, seenLabel2));
		topPanel.add(createSubpanel("Weapons", board, CardType.WEAPON, inHandLabel3, seenLabel3));
		
		// add panel to main grid
		add(topPanel);	
	}
	
	public JPanel createSubpanel(String panelName, Board board, CardType type, JLabel inHandLabel, JLabel seenLabel) {
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(0,1));
		temp.setBorder(new TitledBorder(new EtchedBorder(), panelName));
		temp.add(inHandLabel);
		
		int z = 0;
		for (Card card : board.getPlayers().get(0).getHand()) {
			if (card.getType().equals(type)) {
				JTextField tempText = new JTextField(15);
				tempText.setText(card.getCardName());
				temp.add(tempText);
				z++;
			}
		}

		if(z == 0) {
			JTextField noneText = new JTextField(15);
			noneText.setText("None");
			temp.add(noneText);
		}
		
		temp.add(seenLabel);
		z = 0;
		for (Card card : board.getPlayers().get(0).getSeenCards()) {
			if (card.getType().equals(type)) {
				JTextField tempText = new JTextField(15);
				tempText.setText(card.getCardName());
				tempText.setBackground(card.getWhoShowedColor());
				temp.add(tempText);
				z++;
			}
		}

		if(z == 0) {
			JTextField noneText = new JTextField(15);
			noneText.setText("None");
			temp.add(noneText);
		}
		return temp;
	}
	
	public static void main(String[] args) {
		ClueGameCardsGUI gui = new ClueGameCardsGUI();
		JFrame frame = new JFrame();
		frame.setContentPane(gui);
		frame.setSize(400,750);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// test filling in the data
		//gui.setGuess(new ComputerPlayer( "Col. Mustard", 0, 0, "orange"), 5);
//		gui.setTheRoll(5);
//		gui.setTheTurn(new HumanPlayer("Me", Color.lightGray));
//		gui.setGuess( "I have no guess!");
//		gui.setGuessResult( "So you have nothing?");

	}
	
	
}
