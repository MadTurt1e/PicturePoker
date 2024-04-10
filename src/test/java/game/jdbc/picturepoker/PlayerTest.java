package game.jdbc.picturepoker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    @DisplayName("compareTo ")
    void compareToTest() {
        Player p1 = new Player();
        Player p2 = new Player();
        p1.setID(31415);
        p2.setID(30000);
        p1.setTokens(140);
        p2.setTokens(100);
        p1.setRoundsWon(3);
        p2.setRoundsWon(4);
        // Here p1 has more tokens, so we check first condition of tokens
        assertEquals(-40, p1.compareTo(p2));
        p1.setTokens(100);
        // Here both players have 100 tokens, so we use rounds won as a tiebreaker
        assertEquals(1, p1.compareTo(p2));
        p1.setRoundsWon(4);
        // Here both players have 100 tokens and 4 rounds won, so we use pid as a tiebreaker
        assertEquals(1415, p1.compareTo(p2));
    }

    @Test
    @DisplayName("Raise: No tokens, have tokens, hit cap")
    void raiseTest() {
        Player p1 = new Player();
        p1.setID(42);
        p1.setBet(4);
        p1.setTokens(0);
        assertEquals(4, p1.getBet());
        // With no tokens the player should not be able to raise
        p1.raise();
        assertEquals(4, p1.getBet());
        p1.setTokens(9001);
        // When the player raises they should now have exactly 9000 tokens
        int raiseStatus = p1.raise();
        assertEquals(5, p1.getBet());
        assertEquals(9000, p1.getTokens());
        assertEquals(0, raiseStatus);
        // Bet should max out at 5
        raiseStatus = p1.raise();
        assertEquals(5, p1.getBet());
        assertEquals(9000, p1.getTokens());
        assertEquals(-1, raiseStatus);
    }

    @Test
    @DisplayName("Lifetime Tokens")
    void avgTokensTest(){
        Player p1 = new Player();
        // At start 0 games are played so avg bet should be NaN (divide by zero)
        assertTrue(Double.isNaN(p1.getAvgLifetimeTokens()));
        p1.setFirstPlaces(2);
        p1.setSecondPlaces(4);
        p1.setThirdPlaces(1);
        p1.setFourthPlaces(0);
        assertEquals(7, p1.getGamesPlayed());
        p1.setLifetimeTokens(350);
        assertEquals(50.0, p1.getAvgLifetimeTokens());
    }

    @Test
    @DisplayName("Round Stats")
    void roundStatsTest(){
        Player p1 = new Player();
        // At start 0 hands are played so avg bet should be NaN (divide by zero)
        assertTrue(Double.isNaN(p1.getAvgCardsChanged()));
        assertTrue(Double.isNaN(p1.getAvgBet()));
        assertTrue(Double.isNaN(p1.getRoundWinrate()));

        p1.setFlushes(1);
        p1.setQuads(3);
        p1.setFullHouses(8);
        p1.setTriples(10);
        p1.setTwoPairs(23);
        p1.setOnePairs(19);
        p1.setHighCards(2);

        assertEquals(66, p1.getHandsPlayed());
        p1.setLifetimeTotalBet(165);
        p1.setCardsChanged(231);
        p1.setLifetimeRoundsWon(33);
        assertEquals(2.5, p1.getAvgBet());
        assertEquals(3.5, p1.getAvgCardsChanged());
        assertEquals(50.0, p1.getRoundWinrate());
    }
}