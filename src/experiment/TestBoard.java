package experiment;

import java.util.HashSet;
import java.util.Set;

/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * TestBoard Class
 * 		Acts as a pseudo game board to allow the testing of movement
 */

public class TestBoard {

	public TestBoard() {

	}
	
	public TestBoardCell getCell(int i, int j) {
		TestBoardCell temp = new TestBoardCell(i, j);
		return temp;
	}

	public void calcTargets(TestBoardCell cell, int pathLength) {
		// TODO Auto-generated method stub
		
	}

	public Set<TestBoardCell> getTargets() {
		Set<TestBoardCell> temp = new HashSet<TestBoardCell>();
		return temp;
	}

}
