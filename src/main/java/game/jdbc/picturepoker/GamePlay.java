package game.jdbc.picturepoker;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class GamePlay {

    //Relavant variables - current game, and the list of players
    private Game curGame;
    private Player playerList[] = new Player[4];
    private static final int PAYOUTS[] = {55, 30, 15, 0};

    private boolean executeTurn(Player player) {
        // Step 1: Deal cards at random
        // Step 2: Take bets
        // Step 3: Change out the cards which were selected for changing
        // Step 4: Let the player cry at their new cards.

        // return true upon success, false upon failure.
        return false;
    }

    private boolean executeLuigi() {
        //I believe Luigi should be his own class.
        // The class will be referenced here.
        Luigi luigi = new Luigi();
        // step 1: Get cards at random
        // Step 1: Chose cards to swap out
        // Step 3: that's it.

        //return true on success, false on failure
        return false;
    }

    //This function returns the player that has won.
    private Player determineWinner(Player playerList[]) {
        int winner = 0;
        for (int i = 0; i < playerList.length; ++i) {
            if (playerList[i].getTokens() > playerList[winner].getTokens()) {
                winner = i;
            }
        }
        return playerList[winner];
    }
    
    private int determinePayout(Player player){
        return 0;
    }

    public Game gameSeq(long gameID) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db", "picturepoker", "postgres", "password");
        try {
            Connection connection = dcm.getConnection();

            //in the game, the first thing we want to do is get DAO objects to evyerthing
            GameDAO gamedao = new GameDAO(connection);
            PlayerDAO playerdao = new PlayerDAO(connection);

            //Next, we get the current game state using the game ID.
            Game curGame = gamedao.findById(gameID);

            //we get the list of all the players so it is iteratable.
            playerList[0] = playerdao.findById(curGame.getP1());
            playerList[1] = playerdao.findById(curGame.getP2());
            playerList[2] = playerdao.findById(curGame.getP3());
            playerList[3] = playerdao.findById(curGame.getP4());

            // TODO: We want to clear the stuff from each player item that we want to clear.

            // we can do a check on the number of rounds at this point to see if the game is ongoing.
            while (curGame.getCurRound() != curGame.getNumRounds()) {
                // this is where you'd implement turn order. I'm not going to do it.
                for (int i = 0; i < playerList.length; ++i) {
                    executeTurn(playerList[i]);
                }

                // this is where we'd execute Luigi's turn.
                executeLuigi();

                // we can do a check on the number of rounds at this point to see if the game is ongoing.
                //we should increment the current round and keep on going.
                curGame.setCurRound(curGame.getCurRound() + 1);

                //update the game at this point to the database.
                //TODO: We need an update function in GameDAO which updates the full game class.
                // This function should be working.
                // gamedao.update(game);
            }

            //once we break out of the loop we can determine the winner.
            determineWinner(playerList);

            // now, we look at all the players to figure out token payouts.
            for (int i = 0; i < playerList.length; ++i) {
                determinePayout(playerList[i]);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        //return the gamestate
        return curGame;
    }
}
