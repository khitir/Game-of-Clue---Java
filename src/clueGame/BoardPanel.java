package clueGame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * Class used to draw board with all cells and rooms, alongside names and players
 */
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BoardPanel extends JPanel{

	private Board board;
	private int width; // gets board dimensions
	private int height;
	private int cellWidth; // makes sure resizing will work
	private int cellHeight;//// makes sure resizing will work
	
	private JDialog dialog;
	
	private JComboBox playerDropdown;
	private JComboBox weaponDropdown;
	private Player currPlayer;
	
	private ClueGameCardsGUI cardsGUI;

//    private JComboBox<String> roomName, weaponName, personName;
//    JDialog suggestion;
	private static BoardPanel theInstance = new BoardPanel(Board.getInstance());

	public static BoardPanel getInstance() {
		return theInstance;
	}

	public BoardPanel(Board b){
		this.board = b;
		addMouseListener(new movementMouseEvent());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		width = board.getNumColumns(); // gets board dimensions
		height = board.getNumRows();
		cellWidth = getWidth()/width; // makes sure resizing will work
		cellHeight = getHeight()/height;//// makes sure resizing will work

		// draws cells from boadcell
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				board.getCell(i, j).drawCell(g, cellWidth ,cellHeight, board.getTargets(), board.getWhoseTurn());
			}
		}

		// draws room names, function in boardcell
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (board.getCell(i, j).isLabel()) {
					board.getCell(i, j).drawRoomName(g, cellWidth ,cellHeight);
				}
			}
		}

		// draws players as ovals, function in player, but color map in main
		for (Player player: board.getPlayers()) {
			player.drawPlayer(g, cellWidth, cellHeight);
		}
	}

	private class movementMouseEvent implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			cardsGUI = ClueGameCardsGUI.getInstance();
			
			int x = e.getX()/cellWidth;
			int y = e.getY()/cellHeight;
			repaint();

			if(board.getWhoseTurn() == 0 && board.isPlayerTurnFinished() == false) {
				Room room = board.getRooms().get(board.getCell(y,  x).getRoomLabel());
				if (board.getTargets().contains(room.getCenterCell()) && board.getWhoseTurn() == 0 && board.isPlayerTurnFinished() == false) {
					currPlayer = board.getPlayers().get(0);
					Set<BoardCell> tempSet = new HashSet<BoardCell>();
					tempSet.add(board.getCell(room.getCenterCell().getRow(), room.getCenterCell().getCol()));
					currPlayer.doMove(tempSet);
					//					currPlayer.setCell(room.getCenterCell().getRow(), room.getCenterCell().getCol());
					
					if(board.getCell(y, x).isRoom()) {
						dialog = new JDialog();
						
						dialog.setTitle("Make a Suggestion");
						dialog.setLayout(new GridLayout(4,0));
						
						JPanel roomRow = new JPanel();
						roomRow.setLayout(new GridLayout(0, 2));
						JTextField roomLabel = new JTextField("Current Room");
						roomLabel.setEditable(false);
						roomRow.add(roomLabel);
						JTextField roomName = new JTextField(board.getCell(currPlayer.getRow(), currPlayer.getColumn()).getRoomName());
						roomName.setEditable(false);
						roomRow.add(roomName);
						dialog.add(roomRow);
						
						JPanel personRow = new JPanel();
						personRow.setLayout(new GridLayout(0,2));
						JTextField personLabel = new JTextField("Person");
						personLabel.setEditable(false);
						personRow.add(personLabel);
						playerDropdown = new JComboBox();
						for (Player p : board.getPlayers()) {
							playerDropdown.addItem(p.getName());
						}
						personRow.add(playerDropdown);
						dialog.add(personRow);

						JPanel weaponRow = new JPanel();
						weaponRow.setLayout(new GridLayout(0,2));
						JTextField weaponLabel = new JTextField("Weapon");
						weaponLabel.setEditable(false);
						weaponRow.add(weaponLabel);
						weaponDropdown = new JComboBox();
						for (Card c : board.getAllCards()) {
							if (c.getType() == CardType.WEAPON)
								weaponDropdown.addItem(c.getCardName());
						}
						weaponRow.add(weaponDropdown);
						dialog.add(weaponRow);

						JPanel buttons = new JPanel();
						buttons.setLayout(new GridLayout(0,2));

						JButton submitButton = new JButton("Submit");
						submitButton.addMouseListener(new submitButtonMouse());
						buttons.add(submitButton);

						JButton cancelButton = new JButton("Cancel");
						cancelButton.addMouseListener(new cancelButtonMouse());
						buttons.add(cancelButton);
						
						dialog.add(buttons);

						dialog.setSize(300, 200);
						dialog.setVisible(true);
						dialog.setModal(true);
					}
				}
				else if (board.getTargets().contains(board.getCell(y, x) ) ) {
					Player currPlayer = board.getPlayers().get(0);
					Set<BoardCell> tempSet = new HashSet<BoardCell>();
					tempSet.add(board.getCell(y, x));
					currPlayer.doMove(tempSet);
					//					currPlayer.setRow(y);
					//					currPlayer.setColumn(x);
					board.setPlayerTurnFinished(true);
					if(board.getCell(y, x).isRoom()) {
						//handle suggestion
						// update results
						repaint();
						return;
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Not a target.");
				}
			}
//			board.nextTurn(); // this is to make sure accusation is only done at the begining of turn+
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
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
			
			String person = (String) playerDropdown.getSelectedItem();
			String weapon = (String) weaponDropdown.getSelectedItem();
			
			Card personCard = new Card(person);
			personCard.setType(CardType.PERSON);
			Card weaponCard = new Card(weapon);
			weaponCard.setType(CardType.WEAPON);
			Card roomCard = new Card(board.getCell(currPlayer.getRow(), currPlayer.getColumn()).getRoomName());
			roomCard.setType(CardType.ROOM);
			
			Solution suggestion = new Solution(roomCard, personCard, weaponCard);
			
			Card result = currPlayer.createSuggestion(suggestion);
			
			dialog.setVisible(false);
			board.setPlayerTurnFinished(true);
			
			ClueGameControlPanel control = ClueGameControlPanel.getInstance();
			
			if (result != null) {
				control.setGuess(suggestion.getPerson().getCardName() + ", " + suggestion.getRoom().getCardName() + ", " + suggestion.getWeapon().getCardName());
				control.setGuessResult(result.getCardName());
				control.setGuessResultColor(result.getWhoShowedColor());
				currPlayer.updateSeen(result, result.getWhoShowedColor());				
			}
			else {
				control.setGuess(suggestion.getPerson().getCardName() + ", " + suggestion.getRoom().getCardName() + ", " + suggestion.getWeapon().getCardName());
				control.setGuessResult("No new clue");
				control.setGuessResultColor(Color.white);
			}
			
			
			
			cardsGUI.updatePanels(board);
			cardsGUI.revalidate();
			
			cardsGUI.repaint();
			
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
}
