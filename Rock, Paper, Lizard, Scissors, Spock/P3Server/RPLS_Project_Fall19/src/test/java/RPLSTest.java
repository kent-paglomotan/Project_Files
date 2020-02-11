import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RPLSTest {

	GameInfo game_TEST = new GameInfo();

	@Test
	void bothPlayed_1_TEST() {
		assertEquals(false, game_TEST.bothPlayed(), "ERROR: BothPlayed()");
	}

	@Test
	void bothPlayed_2_TEST() {
		game_TEST.p1Plays = "ROCK";
		game_TEST.p2Plays = "ROCK";
		assertEquals(true, game_TEST.bothPlayed(), "ERROR: BothPlayed()");
	}

	@Test
	void whoWon_1_TEST() {
		game_TEST.p1Plays = "PAPER";
		game_TEST.p2Plays = "ROCK";
		assertEquals("PLAYER 1 WON", game_TEST.whoWon("PAPER", "ROCK"), "ERROR: whoWon()");
	}

	@Test
	void whoWon_2_TEST() {
		game_TEST.p1Plays = "ROCK";
		game_TEST.p2Plays = "PAPER";
		assertEquals("PLAYER 2 WON", game_TEST.whoWon("ROCK", "PAPER"), "ERROR: whoWon()");
	}

	@Test
	void whoWon_3_TEST() {
		game_TEST.p1Plays = "ROCK";
		game_TEST.p2Plays = "ROCK";
		assertEquals("TIE", game_TEST.whoWon("ROCK", "ROCK"), "ERROR: whoWon()");
	}

	@Test
	void evaluateRound_TEST() {
		game_TEST.evaluateRound("PLAYER 1 WON");
		assertEquals(1, game_TEST.roundNumber, "ERROR: evaluateRound()");
		assertEquals(1, game_TEST.p1Points, "ERROR: evaluateRound()");
		assertEquals(0, game_TEST.p2Points, "ERROR: evaluateRound()");
	}

	@Test
	void gameOver_TEST() {
		assertEquals(false, game_TEST.gameOver(), "ERROR: gameOver()");
		game_TEST.p1Points = 3;
		assertEquals(true,  game_TEST.gameOver(), "ERROR: gameOver()");
	}

}
