package clueGame;
/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * Class used to draw board with all cells and rooms, alongside names and players
 */
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BoardPanel extends JPanel{

	Board board;
	int width; // gets board dimensions
	int height;
	int cellWidth; // makes sure resizing will work
	int cellHeight;//// makes sure resizing will work
	Point point;
	
	// Create a dropdown menu for accusation options
    private JComboBox<String> roomName, weaponName, personName;


    
    
    
    
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
			if (player.isHuman()) {
				point = new Point(cellWidth, cellHeight);
			}
		}
		//repaint(); // double check if  needed
	}
	
	
	
	
//	public void repaint(Graphics g) {
//		super.paintComponent(g);
//
//		width = board.getNumColumns(); // gets board dimensions
//		height = board.getNumRows();
//		cellWidth = getWidth()/width; // makes sure resizing will work
//		cellHeight = getHeight()/height;//// makes sure resizing will work
//
//		// draws cells from boadcell
//		for (int i = 0; i < height; i++) {
//			for (int j = 0; j < width; j++) {
//				board.getCell(i, j).drawCell(g, cellWidth ,cellHeight, board.getTargets(), board.getWhoseTurn());
//			}
//		}
//
//		// draws room names, function in boardcell
//		for (int i = 0; i < height; i++) {
//			for (int j = 0; j < width; j++) {
//				if (board.getCell(i, j).isLabel()) {
//					board.getCell(i, j).drawRoomName(g, cellWidth ,cellHeight);
//				}
//			}
//		}
//
//		// draws players as ovals, function in player, but color map in main
//		for (Player player: board.getPlayers()) {
//			player.drawPlayer(g, cellWidth, cellHeight);
//			if (player.isHuman()) {
//				point = new Point(cellWidth, cellHeight);
//			}
//		}
//		//repaint(); // double check if  needed
//	}
//	

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
					currPlayer.setRow(room.getCenterCell().getRow());
					currPlayer.setColumn(room.getCenterCell().getCol());
					board.setPlayerTurnFinished(true);
					if(board.getCell(y, x).isRoom()) {
						roomName = new JComboBox<String>();
						weaponName = new JComboBox<String>();
						personName = new JComboBox<String>();
						
						roomName.addItem(board.getCell(y, x).getRoomName());
						add(roomName);
						
						weaponName.addItem("Laser");
						weaponName.addItem("Cs137");
						weaponName.addItem("Chuck's bicycle");
						weaponName.addItem("Dilution Refrigerator");
						weaponName.addItem("Oscilloscope");
						weaponName.addItem("Lead Block");
						
						add(weaponName);
						
						personName.addItem("Physics Major");
						personName.addItem("Dr. Callan");
						personName.addItem("Laith Haddad");
						personName.addItem("Chuck Ston");
						personName.addItem("Pat Kohl");
						personName.addItem("Vince Kuo");
						
						add(personName);
						
						//handle suggestion
						// update results
						repaint();
						roomName.setVisible(false);
						weaponName.setVisible(false);
						personName.setVisible(false);
						
						return;
					}
				}
				else if (board.getTargets().contains(board.getCell(y, x) ) ) {
					Player currPlayer = board.getPlayers().get(0);
					currPlayer.setRow(y);
					currPlayer.setColumn(x);
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


//		board.drawBoard(g, getWidth(), getHeight());
//		repaint(); // double check if  needed

	}

	

}
