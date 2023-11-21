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

	public BoardPanel(Board b){
		this.board = b;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		board.drawBoard(g, getWidth(), getHeight());
		repaint(); // double check if  needed
	}

}
