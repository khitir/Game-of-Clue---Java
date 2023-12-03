package clueGame;
/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * Class showing control panel of game at the bottom of display, displays the buttons to play game
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.BoardPanel.cancelButtonMouse;
import clueGame.BoardPanel.submitButtonMouse;

public class ClueGameControlPanel extends JPanel{
	private JTextField guess = new JTextField(15);
	private JTextField guessResult = new JTextField(15);;
	private JTextField theRoll = new JTextField(5);
	private JTextField whoseTurn = new JTextField(15);
	private nextButtonMouse mouse = new nextButtonMouse();
	private JPanel boardPanel = BoardPanel.getInstance();
	private ClueGameCardsGUI cardsGUI = ClueGameCardsGUI.getInstance();	
	private Board board = Board.getInstance();
	
	private AccusationDialogBox dialog;
	
	private static ClueGameControlPanel theInstance = new ClueGameControlPanel();
	
	public static ClueGameControlPanel getInstance() {
		return theInstance;
	}
	
	
	public ClueGameControlPanel() {
		
		setLayout(new GridLayout(2,0)); // create 2 row main grid

		// first panel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,4)); // add 4 columns on first row
		
		// buttons
		JButton accusationButton = new JButton("Make Accusation");
		accusationButton.addMouseListener(new accusationButtonMouse());
		JButton nextButton = new JButton("NEXT!");
		nextButton.addMouseListener(mouse);
		
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
	
	public void setGuessResultColor(Color color) {
		guessResult.setBackground(color);
	}
	
	public void processNextTurn() {
		setGuess("");
		setGuessResult("");
		setGuessResultColor(Color.WHITE);
		int whoseTurn = board.getWhoseTurn();
		board.nextTurn();
		whoseTurn = board.getWhoseTurn();
		setTheTurn(board.getPlayers().get(whoseTurn));
		setTheRoll(board.getCurrRoll());
		if (whoseTurn != 0) {
			Player currPlayer = board.getPlayers().get(whoseTurn);
			Solution accusation = currPlayer.createAccusation();
			if (accusation != null) {
				board.handleAccusation(accusation);
			}
			BoardCell newLocation = currPlayer.doMove(board.getTargets());
			newLocation.setOccupied(true);
			if (newLocation.isRoom()) {
				Solution suggestion = currPlayer.createSuggestion();
				Card result = board.handleSuggestion(suggestion, currPlayer);
				setGuess(suggestion.getPerson().getCardName() + ", " + suggestion.getRoom().getCardName() + ", " + suggestion.getWeapon().getCardName());
				if (result != null) {
					if (whoseTurn == 0) {
						setGuessResult(result.getCardName());
					}
					else {
						setGuessResult("Suggestion Disproven");
					}
					setGuessResultColor(result.getWhoShowedColor());
					board.getPlayers().get(whoseTurn).updateSeen(result, result.getWhoShowedColor());
				}
				else {
					setGuessResult("No new clue");
				}
			}
		}
		else {
			setGuess("");
			setGuessResult("");
			setGuessResultColor(Color.white);
			board.setPlayerTurnFinished(false);
			if (board.getTargets().size() == 0) {
				JOptionPane.showMessageDialog(null, "You cannot move this turn");
				board.setPlayerTurnFinished(true);
			}
		}
		this.repaint();
		boardPanel.repaint();
		cardsGUI.revalidate();
		cardsGUI.updatePanels(board);
	}
	
	public class nextButtonMouse implements MouseListener {
		Board board = Board.getInstance();

		@Override
		public void mouseClicked(MouseEvent e) {
			int whoseTurn = board.getWhoseTurn();
			if (whoseTurn == 0 && board.isPlayerTurnFinished() == false) {
				JOptionPane.showMessageDialog(null, "Your turn is not over.");
				return;
			}
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
	
	public class accusationButtonMouse implements MouseListener {
		Board board = Board.getInstance();

		@Override
		public void mouseClicked(MouseEvent e) {
			if (board.getWhoseTurn() == 0) {
				dialog = new AccusationDialogBox(board.getPlayers().get(0));
			}
			else {
				JOptionPane.showMessageDialog(null, "It is not your turn.");
			}
			
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
	
	
	
	
	
	public class AccusationDialogBox extends JDialog{
		public AccusationDialogBox(Player currPlayer) {
			setTitle("Make a Suggestion");
			setLayout(new GridLayout(4,0));
			
			JPanel roomRow = new JPanel();
			roomRow.setLayout(new GridLayout(0, 2));
			JTextField roomLabel = new JTextField("Current Room");
			roomLabel.setEditable(false);
			roomRow.add(roomLabel);
			JTextField roomName = new JTextField(board.getCell(currPlayer.getRow(), currPlayer.getColumn()).getRoomName());
			roomName.setEditable(false);
			roomRow.add(roomName);
			add(roomRow);

			JPanel personRow = new JPanel();
			personRow.setLayout(new GridLayout(0,2));
			JTextField personLabel = new JTextField("Person");
			personLabel.setEditable(false);
			personRow.add(personLabel);
			JComboBox playerDropdown = new JComboBox();
			for (Player p : board.getPlayers()) {
				playerDropdown.addItem(p.getName());
			}
			personRow.add(playerDropdown);
			add(personRow);

			JPanel weaponRow = new JPanel();
			weaponRow.setLayout(new GridLayout(0,2));
			JTextField weaponLabel = new JTextField("Weapon");
			weaponLabel.setEditable(false);
			weaponRow.add(weaponLabel);
			JComboBox weaponDropdown = new JComboBox();
			for (Card c : board.getAllCards()) {
				if (c.getType() == CardType.WEAPON)
					weaponDropdown.addItem(c.getCardName());
			}
			weaponRow.add(weaponDropdown);
			add(weaponRow);

			JPanel buttons = new JPanel();
			buttons.setLayout(new GridLayout(0,2));

			JButton submitButton = new JButton("Submit");
			submitButton.addMouseListener(new submitButtonMouse());
			buttons.add(submitButton);

			JButton cancelButton = new JButton("Cancel");
			cancelButton.addMouseListener(new cancelButtonMouse());
			buttons.add(cancelButton);
			
			add(buttons);

			setSize(300, 200);
			setVisible(true);
		}
	}

	public class cancelButtonMouse implements MouseListener {
		Board board = Board.getInstance();

		@Override
		public void mouseClicked(MouseEvent e) {
			dialog.setVisible(false);
			board.setPlayerTurnFinished(true);
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

	public class submitButtonMouse implements MouseListener {
		Board board = Board.getInstance();

		@Override
		public void mouseClicked(MouseEvent e) {
			// Figure out how to get selected player and weapon from the dialog box
//			board.getPlayers().get(0).createSuggestion(null);
			System.out.println("Suggestion Made");
			dialog.setVisible(false);
			board.setPlayerTurnFinished(true);
			BoardPanel.getInstance().repaint();
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
