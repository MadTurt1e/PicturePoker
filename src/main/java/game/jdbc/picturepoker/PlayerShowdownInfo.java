package game.jdbc.picturepoker;

// Carrier class for end of round information per player

public class PlayerShowdownInfo{
    private long pID;
    private Card[] hand;
    private String handType;
    private int winLossAmount; // Should be positive if player won, negative if player lost, and zero if player was broke.
    private int newTokens;

    public PlayerShowdownInfo(Player p){
        this.pID = p.getID();
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
}
