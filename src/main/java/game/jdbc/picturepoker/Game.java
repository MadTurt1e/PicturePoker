package game.jdbc.picturepoker;

import game.jdbc.picturepoker.util.DataTransferObject;

import java.util.ArrayList;

public class Game implements DataTransferObject
{
    private long g_id;

    // sorry, but nonpreallocated memory is for the weak.
    private long [] players;
    //players 1 through 4 will be stored as the player ID from now on.
    private long p1;
    private long p2;
    private long p3;
    private long p4;
    private int cur_round;
    private int num_rounds;
    private int active_players;
    private int buy_in;
    private int pot_quantity;
    private int difficulty;
    private String winner;
    private Card[] hand;

    @Override
    public long getID() {
        return g_id;
    }

    public void setID(long g_id) {
        this.g_id = g_id;
    }

    public long[] getPlayers() {
        return players;
    }

    public void setPlayers(long[] players) {
        this.players = players;
    }

    public int getCurRound() {
        return cur_round;
    }

    public void setCurRound(int cur_round) {
        this.cur_round = cur_round;
    }

    public int getNumRounds() {
        return num_rounds;
    }

    public void setNumRounds(int num_rounds) {
        this.num_rounds = num_rounds;
    }

    public int getActivePlayers() {
        return active_players;
    }

    public void setActivePlayers(int active_players) {
        this.active_players = active_players;
    }

    public int getBuyIn() {
        return buy_in;
    }

    public void setBuyIn(int buy_in) {
        this.buy_in = buy_in;
    }

    public int getPotQuantity() {
        return pot_quantity;
    }

    public void setPotQuantity(int pot_quantity) {
        this.pot_quantity = pot_quantity;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getWinner(){
        return winner;
    }

    public void setWinner(String winner){
        this.winner = winner;
    }

    public Card[] getHand() {
        return hand;
    }

    public void setHand(Card[] hand) {
        this.hand = hand;
    }

    @Override
    public String toString() {
        return "Game{" +
                "g_id=" + g_id +
                ", p1='" + p1 + '\'' +
                ", p2='" + p2 + '\'' +
                ", p3='" + p3 + '\'' +
                ", p4='" + p4 + '\'' +
                ", cur_round=" + cur_round +
                ", num_rounds=" + num_rounds +
                ", active_players=" + active_players +
                ", buy_in=" + buy_in +
                ", pot_quantity=" + pot_quantity +
                ", difficulty=" + difficulty +
                ", winner= " + winner +
                '}';
    }

    //This is likely where the logic goes to calculate token distribution
    //TODO: Implement method that checks tokens to figure out winner. This is where we'd use it.
//    public String getWinner() {
//        return ;
//    }
}
