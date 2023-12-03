package clueGame;
import java.awt.BorderLayout;
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
	
	private SuggestionDialogBox dialog;

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
		//repaint(); // double check if  needed
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
			int x = e.getX()/cellWidth;
			int y = e.getY()/cellHeight;
			repaint();

			if(board.getWhoseTurn() == 0 && board.isPlayerTurnFinished() == false) {
				Room room = board.getRooms().get(board.getCell(y,  x).getRoomLabel());
				if (board.getTargets().contains(room.getCenterCell()) && board.getWhoseTurn() == 0 && board.isPlayerTurnFinished() == false) {
					Player currPlayer = board.getPlayers().get(0);
					Set<BoardCell> tempSet = new HashSet<BoardCell>();
					tempSet.add(board.getCell(room.getCenterCell().getRow(), room.getCenterCell().getCol()));
					currPlayer.doMove(tempSet);
					//					currPlayer.setCell(room.getCenterCell().getRow(), room.getCenterCell().getCol());
					
					if(board.getCell(y, x).isRoom()) {
//<<<<<<< HEAD
//						
//						suggestion = new JDialog();
//						suggestion.setTitle("Make a suggestion");
//						suggestion.setSize(300, 200);
//						suggestion.setLayout(new BorderLayout());
//
//						// Initialize JComboBoxes
//						roomName = new JComboBox<String>();
//						weaponName = new JComboBox<String>();
//						personName = new JComboBox<String>();
//
//						// Add items to weaponName JComboBox
//						weaponName.addItem("Laser");
//						weaponName.addItem("Cs137");
//						weaponName.addItem("Chuck's bicycle");
//						weaponName.addItem("Dilution Refrigerator");
//						weaponName.addItem("Oscilloscope");
//						weaponName.addItem("Lead Block");
//
//						// Add items to personName JComboBox
//						personName.addItem("Physics Major");
//						personName.addItem("Dr. Callan");
//						personName.addItem("Laith Haddad");
//						personName.addItem("Chuck Ston");
//						personName.addItem("Pat Kohl");
//						personName.addItem("Vince Kuo");
//
//						// add room
//						roomName.addItem(board.getCell(y, x).getRoomName());
//
//						// Create a panel with a 3x3 GridLayout
//						JPanel suggestionPanel = new JPanel(new GridLayout(3, 3));
//
//						// Add labels and JComboBoxes to the panel
//						suggestionPanel.add(new JLabel("Current room"));
//						suggestionPanel.add(roomName);
//						suggestionPanel.add(new JLabel("Weapon"));
//						suggestionPanel.add(weaponName);
//						suggestionPanel.add(new JLabel("Person"));
//						suggestionPanel.add(personName);
//
//						// Create "Submit" and "Cancel" buttons
//						JButton submitButton = new JButton("Submit");
//						JButton cancelButton = new JButton("Cancel");
//						
//						// ActionListener for the Cancel button
//						cancelButton.addActionListener(r -> {
//						    suggestion.dispose(); // Close the dialog
//						});
//
//						// Create a panel for buttons with FlowLayout
//						JPanel buttonPanel = new JPanel(new FlowLayout());
//
//						// Add buttons to the panel
//						buttonPanel.add(submitButton);
//						buttonPanel.add(Box.createHorizontalGlue()); // Add space between buttons
//						buttonPanel.add(cancelButton);
//
//						// Add suggestionPanel and buttonPanel to the center and bottom of the dialog respectively
//						suggestion.add(suggestionPanel, BorderLayout.CENTER);
//						suggestion.add(buttonPanel, BorderLayout.SOUTH);
//
//						// Display the dialog
//						suggestion.setVisible(true);
//=======
						dialog = new SuggestionDialogBox(currPlayer);
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
	
	public class SuggestionDialogBox extends JDialog{
		public SuggestionDialogBox(Player currPlayer) {
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
}
