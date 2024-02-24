// this is where the file is located
package game.jdbc.picturepoker;

public class Card {
    // Final to insure that the suit is not changed after a card is created
    private final Suit suit;

    // Enum for the suit of the card
    public enum Suit {
        STAR, MARIO, LUIGI, FIRE_FLOWER, MUSHROOM, CLOUD
    }

    // Constructor
    public Card(Suit suit) {
        this.suit = suit;
    }

    // Getter for the suit
    public Suit getSuit() {
        return suit;
    }

    // String representation of the card
    // Might be useful for UI, if not we can remove it
    @Override
    public String toString() {
        return suit.name();
    }
}