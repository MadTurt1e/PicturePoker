package game.jdbc.picturepoker;

import java.util.ArrayList;

// Carrier class for end of round information for all players and Luigi

public class ShowdownInfo {
    private ArrayList<PlayerShowdownInfo> playerShowdownInfos;
    private Card[] luigiHand;
    private String luigiHandType;

    public ArrayList<PlayerShowdownInfo> getPlayerShowdownInfos() {
        return playerShowdownInfos;
    }

    public void setPlayerShowdownInfos(ArrayList<PlayerShowdownInfo> pSD) {
        this.playerShowdownInfos = pSD;
    }

    public Card[] getLuigiHand() {
        return luigiHand;
    }

    public void setLuigiHand(Card[] luigiHand) {
        this.luigiHand = luigiHand;
    }

    public String getLuigiHandType() {
        return luigiHandType;
    }

    public void setLuigiHandType(String luigiHandType) {
        this.luigiHandType = luigiHandType;
    }
}
