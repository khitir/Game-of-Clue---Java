package clueGame;
/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * Class showing control panel of game at the bottom of display, displays the buttons to play game
 */

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGameControlPanel extends JPanel{
	private JTextField guess = new JTextField(15);
	private JTextField guessResult = new JTextField(15);;
	private JTextField theRoll = new JTextField(5);
	private JTextField whoseTurn = new JTextField(15);
	private nextButtonMouse mouse = new nextButtonMouse();
	private JPanel boardPanel;
	private ClueGameCardsGUI cardsGUI;	
	private Board board = Board.getInstance();
	
	
	public ClueGameControlPanel(JPanel boardPanel, ClueGameCardsGUI cardsGUI) {
		this.boardPanel = boardPanel;
		this.cardsGUI = cardsGUI;
		
		setLayout(new GridLayout(2,0)); // create 2 row main grid

		// first panel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,4)); // add 4 columns on first row
		
		// buttons
		JButton accusationButton = new JButton("Make Accusation");
		JButton nextButton = new JButton("NEXT!");
		nextButton.addMouseListener(mouse);
		
		// label for turn
		JLabel turnLabel = new JLabel("Whose turn?");
		JPanel turnPanel = new JPanel();
		whoseTurn.setEditable(false);
//		setTheTurn(board.getPlayers().get(board.getWhoseTurn()));
		turnPanel.add(turnLabel);
		turnPanel.add(whoseTurn);
		topPanel.add(turnPanel);
		
		//label for roll
		JLabel rollLabel = new JLabel("Roll: ");
		JPanel rollPanel = new JPanel();
		theRoll.setEditable(false);
//		setTheRoll(board.getCurrRoll());
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
		
		processNextTurn();
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
	
	public void processNextTurn() {
		int whoseTurn = board.getWhoseTurn();
		if (whoseTurn == 0 && board.isPlayerTurnFinished() == false) {
			JOptionPane.showMessageDialog(null, "Your turn is not over.");
			return;
		}
		board.nextTurn();
		whoseTurn = board.getWhoseTurn();
		setTheTurn(board.getPlayers().get(whoseTurn));
		setTheRoll(board.getCurrRoll());
		if (whoseTurn != 0) {
			Player currPlayer = board.getPlayers().get(whoseTurn);
			BoardCell newLocation = currPlayer.doMove(board.getTargets());
			if (newLocation.isRoom()) {
				Solution suggestion = currPlayer.createSuggestion();
				Card result = board.handleSuggestion(suggestion, currPlayer);
				setGuess(suggestion.getPerson().getCardName() + ", " + suggestion.getRoom().getCardName() + ", " + suggestion.getWeapon().getCardName());
				if (result != null) {
					setGuessResult(result.getCardName());
					result.setWhoShowedCard(currPlayer.getColor());
					for (Player player : board.getPlayers()) {
						player.updateSeen(result, currPlayer.getColor());
					}
				}
				else {
					setGuessResult("None");
				}
			}
		}
		else {
			setGuess("");
			setGuessResult("");
			board.setPlayerTurnFinished(false);
			if (board.getTargets().size() == 0) {
				JOptionPane.showMessageDialog(null, "You cannot move this turn");
				board.setPlayerTurnFinished(true);
			}
		}
		repaint();
		boardPanel.repaint();
		cardsGUI.revalidate();
		cardsGUI.updatePanels(board);
	}
	
	public class nextButtonMouse implements MouseListener {
		Board board = Board.getInstance();

		@Override
		public void mouseClicked(MouseEvent e) {
			processNextTurn();
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

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
