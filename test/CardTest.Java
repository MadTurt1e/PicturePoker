import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest {
    // Test the Card class

    // Card suit should be STAR
    @Test
    public void testCard() {
        Card card = new Card(Card.Suit.STAR);
        assertEquals(Card.Suit.STAR, card.getSuit());
    }

    // Card suit should be MARIO
    @Test
    public void testToString() {
        Card card = new Card(Card.Suit.MARIO);
        assertEquals("MARIO", card.toString());
    }
}