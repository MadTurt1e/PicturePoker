package game.jdbc.picturepoker.diffBlue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import game.jdbc.picturepoker.Card;
import game.jdbc.picturepoker.Game;
import org.junit.jupiter.api.Test;

class GameDiffblueTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link Game#setActivePlayers(int)}
     *   <li>{@link Game#setBuyIn(int)}
     *   <li>{@link Game#setCurRound(int)}
     *   <li>{@link Game#setDifficulty(int)}
     *   <li>{@link Game#setHand(Card[])}
     *   <li>{@link Game#setID(long)}
     *   <li>{@link Game#setLuigiFinished(int)}
     *   <li>{@link Game#setNumRounds(int)}
     *   <li>{@link Game#setPlayers(long[])}
     *   <li>{@link Game#setPlayersFinished(int)}
     *   <li>{@link Game#setPotQuantity(int)}
     *   <li>{@link Game#setWinner(String)}
     *   <li>{@link Game#toString()}
     *   <li>{@link Game#getActivePlayers()}
     *   <li>{@link Game#getBuyIn()}
     *   <li>{@link Game#getCurRound()}
     *   <li>{@link Game#getDifficulty()}
     *   <li>{@link Game#getHand()}
     *   <li>{@link Game#getID()}
     *   <li>{@link Game#getLuigiFinished()}
     *   <li>{@link Game#getNumRounds()}
     *   <li>{@link Game#getPlayers()}
     *   <li>{@link Game#getPlayersFinished()}
     *   <li>{@link Game#getPotQuantity()}
     *   <li>{@link Game#getWinner()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange
        Game game = new Game();

        // Act
        game.setActivePlayers(1);
        game.setBuyIn(1);
        game.setCurRound(1);
        game.setDifficulty(1);
        Card[] hand = new Card[]{new Card()};
        game.setHand(hand);
        game.setID(1L);
        game.setLuigiFinished(1);
        game.setNumRounds(10);
        long[] players = new long[]{1L, -1L, 1L, -1L};
        game.setPlayers(players);
        game.setPlayersFinished(1);
        game.setPotQuantity(1);
        game.setWinner("Winner");
        game.toString();
        int actualActivePlayers = game.getActivePlayers();
        int actualBuyIn = game.getBuyIn();
        int actualCurRound = game.getCurRound();
        int actualDifficulty = game.getDifficulty();
        Card[] actualHand = game.getHand();
        long actualID = game.getID();
        int actualLuigiFinished = game.getLuigiFinished();
        int actualNumRounds = game.getNumRounds();
        long[] actualPlayers = game.getPlayers();
        int actualPlayersFinished = game.getPlayersFinished();
        int actualPotQuantity = game.getPotQuantity();

        // Assert that nothing has changed
        assertEquals("Winner", game.getWinner());
        assertEquals(1, actualActivePlayers);
        assertEquals(1, actualBuyIn);
        assertEquals(1, actualCurRound);
        assertEquals(1, actualDifficulty);
        assertEquals(1, actualLuigiFinished);
        assertEquals(1, actualPlayersFinished);
        assertEquals(1, actualPotQuantity);
        assertEquals(10, actualNumRounds);
        assertEquals(1L, actualID);
        assertSame(players, actualPlayers);
        assertSame(hand, actualHand);
    }
}
