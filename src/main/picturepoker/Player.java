// this is where the file is located
package main.picturepoker;

// this is effectively the C include thing
import main.picturepoker.util.DataTransferObject;

public class Player implements DataTransferObject {
    private String name;
    private String password;
    private int firsts;
    private int seconds;
    private int thirds;
    private int fourths;
    private int gamesPlayed;

    @Override
    public String getPlayerName() {
        return name;
    }

    public void setPlayerName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFirsts() {
        return firsts;
    }

    public void setFirsts(int firsts) {
        this.firsts = firsts;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getThirds() {
        return thirds;
    }

    public void setThirds(int thirds) {
        this.thirds = thirds;
    }

    public int getFourths() {
        return fourths;
    }

    public void setFourths(int fourths) {
        this.fourths = fourths;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    @Override
    public String toString() {
        return "player{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", firsts=" + firsts +
                ", seconds=" + seconds +
                ", thirds=" + thirds +
                ", fourths=" + fourths +
                ", gamesPlayed=" + gamesPlayed +
                '}';
    }
}