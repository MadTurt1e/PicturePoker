package game.jdbc.picturepoker.diffBlue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import game.jdbc.picturepoker.Game;
import game.jdbc.picturepoker.GameDAO;
import game.jdbc.picturepoker.GamePlay;
import game.jdbc.picturepoker.PlayerDAO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class GamePlayDiffblueTest {
    /**
     * Method under test: {@link GamePlay#gameSeq(long, GameDAO, PlayerDAO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGameSeq() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.sql.ResultSet.next()" because "rs" is null
        //       at game.jdbc.picturepoker.PlayerDAO.findById(PlayerDAO.java:47)
        //       at game.jdbc.picturepoker.GamePlay.gameSeq(GamePlay.java:455)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        // TODO: Populate arranged inputs
        GamePlay gamePlay = null;
        long gameID = 0L;
        GameDAO gamedao = null;
        PlayerDAO playerdao = null;

        // Act
        Game actualGameSeqResult = gamePlay.gameSeq(gameID, gamedao, playerdao);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link GamePlay.PokerHand#getHandName()}
     */
    @Test
    void testPokerHandGetHandName() {
        // Arrange, Act and Assert
        assertEquals("High Card", GamePlay.PokerHand.valueOf("HIGH_CARD").getHandName());
    }
}
