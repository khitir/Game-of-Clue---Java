package clueGame;
/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * Class used to draw board with all cells and rooms, alongside names and players
 */
import java.awt.Graphics;
import java.util.Set;

import javax.swing.JPanel;


public class BoardPanel extends JPanel{
	private Board board;
	private Set<BoardCell> currTargets;

	public BoardPanel(Board b){
		this.board = b;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		currTargets = board.getTargets();

		// define cell size
		int width = board.getNumColumns(); // gets board dimensions
		int height = board.getNumRows();
		int cellWidth = getWidth()/width; // makes sure resizing will work
		int cellHeight = getHeight()/height;//// makes sure resizing will work

		// draws cells from boardcell
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				board.getCell(i, j).drawCell(g, cellWidth ,cellHeight, currTargets);

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
		repaint(); // double check if  needed
	}

}
