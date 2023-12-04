package clueGame;
import java.awt.Graphics;
/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * Cards panel class, used to display east side of game panel to show cards held and seen
 */
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGameCardsGUI extends JPanel{
	JLabel inHandLabel1 = new JLabel("In Hand:"), inHandLabel2 = new JLabel("In Hand:"), inHandLabel3 = new JLabel("In Hand:");
	JLabel seenLabel1 = new JLabel("Seen:"), seenLabel2 = new JLabel("Seen:"), seenLabel3 = new JLabel("Seen:");
	private boolean initialized = false;
	
	private static ClueGameCardsGUI theInstance = new ClueGameCardsGUI(Board.getInstance());
	
	public static ClueGameCardsGUI getInstance() {
		return theInstance;
	}
	
	public ClueGameCardsGUI(Board board) {
		this.updatePanels(board);	
	}
	
	public void updatePanels(Board board) {
		if (initialized)
			this.remove(0);
//		removeAll();
		setLayout(new GridLayout(0,1)); // create 2 row main grid
		// first panel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(0,1)); // add 4 columns on first row
		topPanel.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
		
		topPanel.add(createSubpanel("People", board, CardType.PERSON, inHandLabel1, seenLabel1));
		topPanel.add(createSubpanel("Rooms", board, CardType.ROOM, inHandLabel2, seenLabel2));
		topPanel.add(createSubpanel("Weapons", board, CardType.WEAPON, inHandLabel3, seenLabel3));
		
		// add panel to main grid
		add(topPanel);
//		revalidate();
		repaint();
		initialized = true;
	}
	
	public JPanel createSubpanel(String panelName, Board board, CardType type, JLabel inHandLabel, JLabel seenLabel) {
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(0,1));
		temp.setBorder(new TitledBorder(new EtchedBorder(), panelName));
		temp.add(inHandLabel);
		
		Player humanPlayer = board.getPlayers().get(0);
		
		int z = 0;
		for (Card card : humanPlayer.getHand()) {
			if (card.getType().equals(type)) {
				JTextField tempText = new JTextField(15);
				tempText.setText(card.getCardName());
				tempText.setBackground(board.getPlayers().get(0).getColor());
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
		for (Card card : humanPlayer.getSeenCards().keySet()) {
			if (card.getType().equals(type)) {
				JTextField tempText = new JTextField(15);
				tempText.setText(card.getCardName());
				tempText.setBackground(humanPlayer.getSeenCards().get(card));
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
	
//	public static void main(String[] args) {
//		Board board = Board.getInstance();
//		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
//		board.initialize();
//		ClueGameCardsGUI gui = new ClueGameCardsGUI(board);
//		JFrame frame = new JFrame();
//		frame.setContentPane(gui);
//		frame.setSize(400,750);
//		frame.setVisible(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		// test filling in the data
//		for (Card card : board.getCards().values()) {
//			if (!board.getPlayers().get(0).getHand().contains(card)) {
//				card.setWhoShowedCard(Color.yellow);
//				board.getPlayers().get(0).updateSeen(card, Color.yellow);
//			}
//		}
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		gui.updatePanels(board);
//		frame.revalidate();
//	}
	
	
}
