package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	Set<TestBoardCell> cells;

	public TestBoard() {
		cells = new HashSet<TestBoardCell>();
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
