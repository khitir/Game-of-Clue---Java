package clueGame;
/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * Class used to draw board with all cells and rooms, alongside names and players
 */
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;


import javax.swing.JPanel;


public class BoardPanel extends JPanel{

	Board board;
	int width; // gets board dimensions
	int height;
	int cellWidth; // makes sure resizing will work
	int cellHeight;//// makes sure resizing will work
	Point point;

	public BoardPanel(Board b){
		this.board = b;
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
			// TODO Auto-generated method stub
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			int x = e.getX()/cellWidth;
			int y = e.getY()/cellHeight;
			
			if(x == point.x && y == point.y) {
				
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
