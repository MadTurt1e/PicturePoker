// this is where the file is located
package game.jdbc.picturepoker;

import Java.lang.Math.random;

public class Card {
    // Final to insure that the suit is not changed after a card is created
    private Suit suit;

    private boolean toChange;

    // Enum for the suit of the card
    public enum Suit {
        STAR(5), MARIO(4), LUIGI(3), FIRE_FLOWER(2), MUSHROOM(1), CLOUD(0);
        public final int strength;
    }

    // Constructor
    public Card(Suit suit) {
        this.suit = suit;
        this.toChange = false;
    }

    // Getter for the suit
    public Suit getSuit() {
        return suit;
    }

    // Suit setter

    public void setSuit(Suit suit){
        this.suit = suit;
    }
    
    public boolean getToChange(){
        return toChange;
    }
    
    public void setToChange(boolean toChange){
        this.toChange = toChange;
    }

    public void changeSuit(){
        if(toChange){
            int newStrength = (int)(Math.random() * 6);
            // Mitigate potential out of bounds errors with math.random
            if(newStrength > 5 || newStrength < 0){
                newStrength = 0;
            }
            for(Suit s : values()){
                if(s.strength = newStrength){
                    this.suit = s;
                    break;
                }
            }
            toChange = false;
        }
    }
    // String representation of the card
    // Might be useful for UI, if not we can remove it
    @Override
    public String toString() {
        return suit.name();
    }
}