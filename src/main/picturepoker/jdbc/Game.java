package main.picturepoker.jdbc;

import main.picturepoker.jdbc.util.DataTransferObject;

public class Game implements DataTransferObject
{
    private int g_id;
    private String p1;
    private String p2;
    private String p3;
    private String p4;
    private int cur_round;
    private int num_rounds;
    private int active_players;
    private int pot_quantity;
    private int difficulty;

    @Override
    public int getID() {
        return g_id;
    }

    public void setID(int g_id) {
        this.g_id = g_id;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public String getP3() {
        return p3;
    }

    public void setP3(String p3) {
        this.p3 = p3;
    }

    public String getP4() {
        return p4;
    }

    public void setP4(String p4) {
        this.p4 = p4;
    }

    public int getCur_round() {
        return cur_round;
    }

    public void setCur_round(int cur_round) {
        this.cur_round = cur_round;
    }

    public int getNum_rounds() {
        return num_rounds;
    }

    public void setNum_rounds(int num_rounds) {
        this.num_rounds = num_rounds;
    }

    public int getActive_players() {
        return active_players;
    }

    public void setActive_players(int active_players) {
        this.active_players = active_players;
    }

    public int getPot_quantity() {
        return pot_quantity;
    }

    public void setPot_quantity(int pot_quantity) {
        this.pot_quantity = pot_quantity;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
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
                ", pot_quantity=" + pot_quantity +
                ", difficulty=" + difficulty +
                '}';
    }

    //This is likely where the logic goes to calculate token distribution
    //TODO: Implement method that checks tokens to figure out winner. This is where we'd use it.
//    public String getWinner() {
//        return ;
//    }
}
