// this is where the file is located
package game.jdbc.picturepoker;

import static java.lang.Math.random;

public class Card {
    private Suit suit;

    private boolean toChange;

    // Enum for the suit of the card
    public enum Suit {
        // Suits are listed in increasing strength for ordinal() to be used effectively
        CLOUD, MUSHROOM, FIRE_FLOWER, LUIGI, MARIO, STAR;
    }
    
    // Default constructor- Generates random suit
    public Card(){
        this.suit = selectRandomSuit();
        this.toChange = false;
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
            suit = selectRandomSuit();
            toChange = false;
        }
    }
    
    private Suit selectRandomSuit(){
        int newStrength = (int)(random() * 6);
        for(Suit s : Suit.values()){
            if(s.ordinal() == newStrength){
                return s;
            }
        }
        // Mitigate potential out of bounds errors with math.random with failsafe
        return Suit.CLOUD;
    }
    
    // String representation of the card
    // Might be useful for UI, if not we can remove it
    @Override
    public String toString() {
        return suit.name();
    }
}