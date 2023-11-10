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
	
	
	public ClueGameCardsGUI() {
		setLayout(new GridLayout(0,1)); // create 2 row main grid
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		// first panel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(0,1)); // add 4 columns on first row
		topPanel.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
		
		
		// label for people
		JPanel peoplePanel = new JPanel();
		JLabel inHandLabel = new JLabel("In Hand:");
		peoplePanel.setLayout(new GridLayout(0,1));
		peoplePanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		peoplePanel.add(inHandLabel);
		int x = 0;
		for (Card card : board.getPlayers().get(0).getHand()) {
			if (card.getType().equals(CardType.PERSON)) {
				JTextField people = new JTextField(15);
				people.setText(card.getCardName());
				people.setBackground(card.getWhoShowedColor());
				peoplePanel.add(people);
				x++;
			}
		}

		if(x>0) {
			topPanel.add(peoplePanel);
		}
		else {
			JTextField people = new JTextField(15);
			people.setText("null");
			peoplePanel.add(people);
		}

		
		
		//label for room
		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(0,1));
		roomPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		roomPanel.add(inHandLabel);
		//rooms.setEditable(false);
	//	roomPanel.add(rooms);
		topPanel.add(roomPanel);
		int y = 0;
		for (Card card : board.getPlayers().get(0).getHand()) {
			if (card.getType().equals(CardType.ROOM)) {
				JTextField room = new JTextField(15);
				room.setText(card.getCardName());
				room.setBackground(card.getWhoShowedColor());
				roomPanel.add(room);
				y++;
			}
		}

		if(y>0) {
			topPanel.add(roomPanel);
		}
		else {
			JTextField room = new JTextField(15);
			room.setText("null");
			roomPanel.add(room);
		}
		
		
		
		// label for weapon
		JPanel weaponPanel = new JPanel();
		weaponPanel.setLayout(new GridLayout(0,1));
		weaponPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		weaponPanel.add(inHandLabel);
	//	weaponPanel.add(weapons);
		topPanel.add(weaponPanel);
		
		int z = 0;
		for (Card card : board.getPlayers().get(0).getHand()) {
			if (card.getType().equals(CardType.WEAPON)) {
				JTextField weapon = new JTextField(15);
				weapon.setText(card.getCardName());
				weapon.setBackground(card.getWhoShowedColor());
				weaponPanel.add(weapon);
				z++;
			}
		}

		if(z>0) {
			topPanel.add(weaponPanel);
		}
		else {
			JTextField weapon = new JTextField(15);
			weapon.setText("null");
			weaponPanel.add(weapon);
		}
		
		
		
		// add panel to main grid
		add(topPanel);
		
		
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
