package clueGame;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class BoardPanel extends JPanel{
	Board board;

	public BoardPanel(Board b){
		this.board = b;
//		b.getInstance();
//		b.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
//		b.initialize();
		
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int width = board.getNumColumns();
		int height = board.getNumRows();
		int cellWidth = getWidth()/width; //getWidth();
		int cellHeight = getHeight()/height;//getHeight();
		//System.out.println(getWidth());
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				board.getCell(i, j).drawCell(g, cellWidth ,cellHeight);
				
			}
			
		}
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (board.getCell(i, j).isLabel()) {
					board.getCell(i, j).drawRoomName(g, cellWidth ,cellHeight);
				}

			}

		}
		
		for (Player player: board.getPlayers()) {
			System.out.println(player.getName());
			player.drawPlayer(g, cellWidth, cellHeight);
		}
	}

//	public void somefunction() {
//		// update variables
//		//repaint();
//	}
}
