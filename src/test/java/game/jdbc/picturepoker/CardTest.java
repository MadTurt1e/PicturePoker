package game.jdbc.picturepoker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void testSuit() {
        Card testCard = new Card();
        testCard.setSuit(Card.Suit.CLOUD);
        assertEquals(Card.Suit.CLOUD, testCard.getSuit());
    }

    @Test
    void testToString() {
        Card testCard = new Card(Card.Suit.MARIO);
        assertEquals("MARIO", testCard.toString());
    }

    @Test
    void testToChangeStatus() {
        Card testCard = new Card();
        assertFalse(testCard.getToChange());
        testCard.setToChange(true);
        assertTrue(testCard.getToChange());
        testCard.redrawSuit();
        assertFalse(testCard.getToChange());
    }
}