package game.jdbc.picturepoker;

// Carrier class for end of round information per player

public class PlayerShowdownInfo implements Comparable<PlayerShowdownInfo>{
    private long pID;
    private Card[] hand;
    private String handType;
    private int winLossAmount; // Should be positive if player won, negative if player lost, and zero if player was broke.
    private int newTokens;
    private int roundsWon;

    public PlayerShowdownInfo(Player p){
        this.pID = p.getID();
    }

    public int compareTo(PlayerShowdownInfo pSD2){
        // Condition 1: Highest Token Count
        if(this.newTokens - pSD2.getNewTokens() != 0){
            return pSD2.getNewTokens() - this.newTokens;
        }
        else{
            // Condition 2: Most Rounds won
            if(this.roundsWon - pSD2.getRoundsWon() != 0){
                return pSD2.getRoundsWon() - this.roundsWon;
            }
            else{
                // Tiebreaker: Lowest Player ID
                return (int)(this.pID - pSD2.getpID());

            }
        }
    }

    public long getpID() {
        return pID;
    }

    public void setpID(long pID) {
        this.pID = pID;
    }

    public Card[] getHand() {
        return hand;
    }

    public void setHand(Card[] hand) {
        this.hand = hand;
    }

    public String getHandType() {
        return handType;
    }

    public void setHandType(String handType) {
        this.handType = handType;
    }

    public int getWinLossAmount() {
        return winLossAmount;
    }

    public void setWinLossAmount(int winLossAmount) {
        this.winLossAmount = winLossAmount;
    }

    public int getNewTokens() {
        return newTokens;
    }

    public void setNewTokens(int newTokens) {
        this.newTokens = newTokens;
    }

    public int getRoundsWon() {
        return roundsWon;
    }

    public void setRoundsWon(int roundsWon) {
        this.roundsWon = roundsWon;
    }
}
