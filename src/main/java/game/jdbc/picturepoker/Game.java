package game.jdbc.picturepoker;

import game.jdbc.picturepoker.util.DataTransferObject;
import java.util.Arrays;

public class Game implements DataTransferObject
{
    private long g_id;

    // sorry, but nonpreallocated memory is for the weak.
    private long [] players;
    //This actually accepts players 1 through n
    private int cur_round;
    private int num_rounds;
    private int active_players;
    private int buy_in;
    private int pot_quantity;
    private int difficulty;
    private int playersFinished;
    private int luigiFinished;
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

    public int getPlayersFinished() {
        return playersFinished;
    }

    public void setPlayersFinished(int playersFinished) {
        this.playersFinished = playersFinished;
    }

    public int getLuigiFinished() {
        return luigiFinished;
    }

    public void setLuigiFinished(int luigiFinished) {
        this.luigiFinished = luigiFinished;
    }

    public String getWinner(){
        return winner;
    }

    //honestly, database wise it might be better to store the winner as a string, but whatever.
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
                ", players=" + Arrays.toString(players) +
                ", cur_round=" + cur_round +
                ", num_rounds=" + num_rounds +
                ", active_players=" + active_players +
                ", buy_in=" + buy_in +
                ", pot_quantity=" + pot_quantity +
                ", difficulty=" + difficulty +
                ", players_finished=" + playersFinished +
                ", luigi_finished=" + luigiFinished +
                ", winner='" + winner + '\'' +
                ", hand=" + Arrays.toString(hand) +
                '}';
    }
}
