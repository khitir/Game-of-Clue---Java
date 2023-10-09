package experiment;

import java.util.HashSet;
import java.util.Set;

/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * TestBoardCell Class
 * 		Acts as a pseudo cell on the game board to allow the testing of movement
 */

public class TestBoardCell {
	private int row, col;
	private Boolean isRoom, isOccupied ;

	public TestBoardCell(int x, int y) {

	}

	public Set<TestBoardCell> getAdjList() {
		Set<TestBoardCell> temp = new HashSet<TestBoardCell>();
		return temp;
	}

	public void setOccupied(boolean b) {
		return;
	}
	
	public boolean isOccupied() {
		return false;
	}

	public void setIsRoom(boolean b) {
		return;
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public void addAdjacency(TestBoardCell cell) {
		return;
	}

}
