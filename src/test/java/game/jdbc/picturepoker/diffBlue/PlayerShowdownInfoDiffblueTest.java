package game.jdbc.picturepoker.diffBlue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import game.jdbc.picturepoker.Card;
import game.jdbc.picturepoker.Player;
import game.jdbc.picturepoker.PlayerShowdownInfo;
import org.junit.jupiter.api.Test;

class PlayerShowdownInfoDiffblueTest {
    /**
     * Method under test: {@link PlayerShowdownInfo#compareTo(PlayerShowdownInfo)}
     */
    @Test
    void testCompareTo() {
        // Arrange
        PlayerShowdownInfo playerShowdownInfo = new PlayerShowdownInfo(new Player());

        // Act and Assert
        assertEquals(0, playerShowdownInfo.compareTo(new PlayerShowdownInfo(new Player())));
    }

    /**
     * Method under test: {@link PlayerShowdownInfo#compareTo(PlayerShowdownInfo)}
     */
    @Test
    void testCompareTo2() {
        // Arrange
        PlayerShowdownInfo playerShowdownInfo = new PlayerShowdownInfo(new Player());
        playerShowdownInfo.setNewTokens(1);

        // Act and Assert
        assertEquals(-1, playerShowdownInfo.compareTo(new PlayerShowdownInfo(new Player())));
    }

    /**
     * Method under test: {@link PlayerShowdownInfo#compareTo(PlayerShowdownInfo)}
     */
    @Test
    void testCompareTo3() {
        // Arrange
        PlayerShowdownInfo playerShowdownInfo = new PlayerShowdownInfo(new Player());
        playerShowdownInfo.setRoundsWon(1);

        // Act and Assert
        assertEquals(-1, playerShowdownInfo.compareTo(new PlayerShowdownInfo(new Player())));
    }

    /**
     * Method under test: {@link PlayerShowdownInfo#getpID()}
     */
    @Test
    void testGetpID() {
        // Arrange, Act and Assert
        assertEquals(0L, (new PlayerShowdownInfo(new Player())).getpID());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link PlayerShowdownInfo#setHand(Card[])}
     *   <li>{@link PlayerShowdownInfo#setHandType(String)}
     *   <li>{@link PlayerShowdownInfo#setNewTokens(int)}
     *   <li>{@link PlayerShowdownInfo#setRoundsWon(int)}
     *   <li>{@link PlayerShowdownInfo#setWinLossAmount(int)}
     *   <li>{@link PlayerShowdownInfo#setpID(long)}
     *   <li>{@link PlayerShowdownInfo#getHand()}
     *   <li>{@link PlayerShowdownInfo#getHandType()}
     *   <li>{@link PlayerShowdownInfo#getNewTokens()}
     *   <li>{@link PlayerShowdownInfo#getRoundsWon()}
     *   <li>{@link PlayerShowdownInfo#getWinLossAmount()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange
        PlayerShowdownInfo playerShowdownInfo = new PlayerShowdownInfo(new Player());
        Card[] hand = new Card[]{new Card()};

        // Act
        playerShowdownInfo.setHand(hand);
        playerShowdownInfo.setHandType("Hand Type");
        playerShowdownInfo.setNewTokens(1);
        playerShowdownInfo.setRoundsWon(1);
        playerShowdownInfo.setWinLossAmount(1);
        playerShowdownInfo.setpID(1L);
        Card[] actualHand = playerShowdownInfo.getHand();
        String actualHandType = playerShowdownInfo.getHandType();
        int actualNewTokens = playerShowdownInfo.getNewTokens();
        int actualRoundsWon = playerShowdownInfo.getRoundsWon();

        // Assert that nothing has changed
        assertEquals("Hand Type", actualHandType);
        assertEquals(1, actualNewTokens);
        assertEquals(1, actualRoundsWon);
        assertEquals(1, playerShowdownInfo.getWinLossAmount());
        assertSame(hand, actualHand);
    }

    /**
     * Method under test: {@link PlayerShowdownInfo#PlayerShowdownInfo(Player)}
     */
    @Test
    void testNewPlayerShowdownInfo() {
        // Arrange
        Player p = new Player();

        // Act
        PlayerShowdownInfo actualPlayerShowdownInfo = new PlayerShowdownInfo(p);

        // Assert
        assertNull(p.getHand());
        assertNull(actualPlayerShowdownInfo.getHand());
        assertNull(p.getPasscode());
        assertNull(p.getPlayerName());
        assertNull(actualPlayerShowdownInfo.getHandType());
        assertEquals(0, p.getBet());
        assertEquals(0, p.getCardsChanged());
        assertEquals(0, p.getDollars());
        assertEquals(0, p.getFinishedRound());
        assertEquals(0, p.getFirstPlaces());
        assertEquals(0, p.getFlushes());
        assertEquals(0, p.getFourthPlaces());
        assertEquals(0, p.getFullHouses());
        assertEquals(0, p.getGamesPlayed());
        assertEquals(0, p.getHandsPlayed());
        assertEquals(0, p.getHighCards());
        assertEquals(0, p.getLifetimeRoundsWon());
        assertEquals(0, p.getLifetimeTokens());
        assertEquals(0, p.getLifetimeTotalBet());
        assertEquals(0, p.getOnePairs());
        assertEquals(0, p.getQuads());
        assertEquals(0, p.getRoundsWon());
        assertEquals(0, p.getSecondPlaces());
        assertEquals(0, p.getThirdPlaces());
        assertEquals(0, p.getTokens());
        assertEquals(0, p.getTriples());
        assertEquals(0, p.getTwoPairs());
        assertEquals(0, actualPlayerShowdownInfo.getNewTokens());
        assertEquals(0, actualPlayerShowdownInfo.getRoundsWon());
        assertEquals(0, actualPlayerShowdownInfo.getWinLossAmount());
        assertEquals(0L, p.getID());
        assertEquals(Double.NaN, p.getAvgBet());
        assertEquals(Double.NaN, p.getAvgCardsChanged());
        assertEquals(Double.NaN, p.getAvgLifetimeTokens());
        assertEquals(Double.NaN, p.getRoundWinrate());
    }
}
