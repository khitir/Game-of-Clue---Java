package tests;

import java.util.Map;

import org.junit.Test;

import clueGame.Board;
import clueGame.HumanPlayer;
import clueGame.Player;
import junit.framework.TestCase;

public class GameSetupTests extends TestCase {
	
	@Test
	public void testPlayer() {
		Player player1 = new HumanPlayer("General Mustard", "Yellow", true);
		assertEquals(player1.getName(), "General Mustard");
		assertEquals(player1.getColor(), "Yellow");
	}
	
	@Test
	public void testPlayersInitialized() {
		Board board = Board.getInstance();
		Map<String, Player> players = board.getPlayers();
		int numComputers = 0, numHumans = 0;
		for (String name : players.keySet()) {
			Player tempPlayer = players.get(name);
			assertEquals(name, tempPlayer.getName());
			if (tempPlayer.isComputer()) {
				numComputers++;
			}
			else if (tempPlayer.isHuman())
				numHumans++;
		}
		assertEquals(numComputers, 5);
		assertEquals(numHumans, 1);
	}
}
