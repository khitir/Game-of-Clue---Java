package clueGame;
/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * Class used to draw board with all cells and rooms, alongside names and players
 */
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BoardPanel extends JPanel{

	Board board;
	int width; // gets board dimensions
	int height;
	int cellWidth; // makes sure resizing will work
	int cellHeight;//// makes sure resizing will work

	// Create a dropdown menu for accusation options
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

	private class movementMouseEvent implements MouseListener{

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
					
					board.setPlayerTurnFinished(true);
					
					if(board.getCell(y, x).isRoom()) {
						JDialog dialog = new JDialog();
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
						JComboBox playerDropdown = new JComboBox();
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
						JComboBox weaponDropdown = new JComboBox();
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

//						dialog.repaint();
						dialog.setSize(300, 200);
						dialog.setVisible(true);
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

	public class cancelButtonMouse implements MouseListener {
		Board board = Board.getInstance();

		@Override
		public void mouseClicked(MouseEvent e) {

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
