package game.jdbc.picturepoker.diffBlue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.jdbc.picturepoker.Card;
import org.junit.jupiter.api.Test;

class CardDiffblueTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link Card#Card(Card.Suit)}
     *   <li>{@link Card#setSuit(Card.Suit)}
     *   <li>{@link Card#setToChange(boolean)}
     *   <li>{@link Card#toString()}
     *   <li>{@link Card#getSuit()}
     *   <li>{@link Card#getToChange()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange and Act
        Card actualCard = new Card(Card.Suit.CLOUD);
        actualCard.setSuit(Card.Suit.CLOUD);
        actualCard.setToChange(true);
        String actualToStringResult = actualCard.toString();
        Card.Suit actualSuit = actualCard.getSuit();

        // Assert that nothing has changed
        assertEquals("CLOUD", actualToStringResult);
        assertEquals(Card.Suit.CLOUD, actualSuit);
        assertTrue(actualCard.getToChange());
    }

    /**
     * Method under test: {@link Card#redrawSuit()}
     */
    @Test
    void testRedrawSuit() {
        // Arrange
        Card card = new Card();

        // Act
        card.redrawSuit();

        // Assert that nothing has changed
        assertFalse(card.getToChange());
    }

    /**
     * Method under test: {@link Card#redrawSuit()}
     */
    @Test
    void testRedrawSuit2() {
        // Arrange
        Card card = new Card();
        card.setToChange(true);

        // Act
        card.redrawSuit();

        // Assert
        assertFalse(card.getToChange());
    }

    /**
     * Method under test: {@link Card#Card()}
     */
    @Test
    void testNewCard() {
        // Arrange, Act and Assert
        assertFalse((new Card()).getToChange());
    }
}
