package game.jdbc.picturePoker;

public class gamePlay{

    //Relavant variables - current game, and the list of players
    private Game curGame;
    private Player playerList = new Player[4];

    private boolean executeTurn(Player player){
        // Step 1: Deal cards at random
        // Step 2: Take bets
        // Step 3: Change out the cards which were selected for changing
        // Step 4: Let the player cry at their new cards. 

        // return true upon success, false upon failure. 
        return False;
    }

    private boolean executeLuigi(){
        //I believe Luigi should be his own class. 
        // The class will be referenced here. 
        Luigi luigi = new luigi();
        // step 1: Get cards at random
        // Step 1: Chose cards to swap out
        // Step 3: that's it. 

        //return true on success, false on failure
        return False;
    }
    
    public Game gameTurn(long gameID){
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db","picturepoker", "postgres", "password");
        Connection connection = dcm.getConnection();
        try{
            //in the game, the first thing we want to do is get DAO objects to evyerthing
            GameDAO gamedao = new GameDAO(connection);
            PlayerDAO playerdao = new PlayerDAO(connection);

            //Next, we get the current game state using the game ID. 
            Game curGame = gamedao.findById(gameID);

            //we get the list of all the players so it is iteratable. 
            Player playerList[0] = curGame.getP1();
            Player playerList[1] = curGame.getP2();
            Player playerList[2] = curGame.getP3(); 
            Player playerList[3] = gamedao.getP4();

            // TODO: We want to clear the stuff from each player item that we want to clear. 

            // this is where you'd implement turn order. I'm not going to do it. 
            for (int i = 0; i < playerList.length(); ++i){
                executeTurn(playerList[i]);
            }

            // this is where we'd execute Luigi's turn. 
            executeLuigi();

            // now, we look at all the players to figure out token payouts. 
            for (int i = 0; i < playerList.length(); ++i){
                determinePayout(playerList[i]);
            }

            // we can do a check on the number of rounds at this point to see if the game is ongoing. 
            if (game.getCurRound() == game.getNumRounds()){
                //If the game is complete, figure out the winners. 
                determineWinner(playerList[i]);
            }
            //otherwise, increment the current round and keep on going. 
            else{
                game.setCurRound(game.getCurRound() + 1);
            }
            //update the game at this point to the database. 
            //TODO: We need an update function in GameDAO which updates the full game class. 
            // This function should be working. 
            // gamedao.update(game);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}