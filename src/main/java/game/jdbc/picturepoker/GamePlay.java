package game.jdbc.picturepoker;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;


public class GamePlay {

    //Relevant variables - current game, and the list of players
    private Game curGame;
    private Player[] playerList = new Player[4];

    private Player executeTurn(Player player) {
        System.out.println("\n" + player.getPlayerName() + "'s Turn! ");
        System.out.println(player.getPlayerName() + " has "+ player.getTokens() +" tokens!");

        // Step 1: Deal cards at random
        Card[] hand = new Card[5];
        //initialize values
        for (int i = 0; i < hand.length; ++i){
            hand[i] = new Card();
        }

        //update the hand of the player
        player.setHand(hand);

        // This basically shuffles every card in the hand.
        for (Card value : hand) {
            value.redrawSuit();
        }

        player.setHand(hand);


        // Prints out the card outputs
        System.out.println(player.getPlayerName() + "'s cards: ");
        for (int i = 0; i < hand.length; ++i) {
            System.out.println("Card " + i + ": " + hand[i]);
        }

        // Step 2: Take bets
        System.out.println("How much do you want to bet? ");
        Scanner scan = new Scanner(System.in);
        int betCount = 0;
        while (betCount < 1 || betCount > 6) {
            try {
                betCount = scan.nextInt();
                if (betCount < 1 || betCount > 6){
                    System.out.println("Bet must be between 1 and 5! ");
                    continue;
                }
                break;
            }catch (java.util.InputMismatchException e) {
                System.out.println("Bet must be between 1 and 5. ");
                scan.nextLine();
            }
        }
        player.setBet(betCount);
        player.setTokens(player.getTokens() - betCount);

        // Step 3: Change out the cards which were selected for changing
        System.out.println("Which cards would you like to change out? ");
        boolean changeCard;
        for (int i = 0; i < hand.length; ++i) {
            try {
                System.out.println("Change card " + i + "?");
                changeCard = scan.nextBoolean();
                if (changeCard)
                    hand[i].setToChange(true);
            }catch (java.util.InputMismatchException e) {
                System.out.println("Player must input true or false!" );
                scan.nextLine();
            }
        }

        //we change after choosing just in case the player wants to undo choices.
        // We don't actually give the player any options, but it is something that can be done.
        for (Card card : hand) {
            if (card.getToChange())
                card.redrawSuit();
        }

        // Step 4: Let the player cry at their new cards.
        System.out.println("Here are your new cards!");


        // return the player with the newfangled hand.
        for (int i = 0; i < hand.length; ++i) {
            System.out.println("Card " + i + ": " + hand[i]);
        }
        player.setHand(hand);

        return player;
    }

    private Player executeLuigi(Player luigi) {
        //I believe Luigi should be his own class.
        // The class will be referenced here - database accesses can be done whenever wanted - Luigi will essentially be a player.
        Card[] luigiHand = new Card[5];
        //initialize values
        for (int i = 0; i < luigiHand.length; ++i){
            luigiHand[i] = new Card();
        }

        // step 1: Get cards at random
        //This is already done by the card class.
        System.out.println("Luigi Turn! ");
        for (int i = 0; i < luigiHand.length; ++i) {
            System.out.println("Card " + i + ": " + luigiHand[i]);
        }

        // Step 2: Chose cards to swap out.
        //Luigi is physically cheating, so he always swaps out the third and fifth card and gets stars. How lucky.
        luigiHand[2].setToChange(true);
        luigiHand[4].setToChange(true);

        // Step 3: that's it.
        for (int i = 0; i < luigiHand.length; ++i){
            if (luigiHand[i].getToChange()) {
                System.out.println("Luigi is changing out card " + i + "!");
                luigiHand[i].setSuit(Card.Suit.STAR);
            }
        }
        luigi.setHand(luigiHand);


        for (int i = 0; i < luigiHand.length; ++i) {
            System.out.println("Card " + i + ": " + luigiHand[i]);
        }

        //return luigi
        return luigi;
    }

    //This function does a score calculation on a player.
    private int playerScore(Player player) {
        Card[] hand = player.getHand();
        int score = 0;

        int[] suitCount = new int[6];

        Card.Suit curSuit;
        //count the number of each suit on hand - we're going to use a "flag" system to count suits.
        for (Card card : hand) {
            curSuit = card.getSuit();
            switch (curSuit) {
                case STAR:
                    ++suitCount[5];
                    break;
                case MARIO:
                    ++suitCount[4];
                    break;
                case LUIGI:
                    ++suitCount[3];
                    break;
                case FIRE_FLOWER:
                    ++suitCount[2];
                    break;
                case MUSHROOM:
                    ++suitCount[1];
                    break;
                case CLOUD:
                    ++suitCount[0];
                    break;
            }
        }

        //convert suits to int
        for (int i = 0; i < suitCount.length; ++i){
            //we also tally up the individual cards as an extra differentiator as well.
            score += (suitCount[i] * i * 10);
            // 5 stars gain 300 extra bonus points, and 5 clouds gain nothing - suits help, but they can't beat an entire hand
            // Also, 4 stars is much better than 4 clouds, etc.

            if (suitCount[i] == 5){
                score += (i * 6000); // easy way to tell differences between hand strengths - just use huge numbers
                break;
            }
            if (suitCount[i] == 4){
                score += (i * 5000);
            }
            if (suitCount[i] == 3){
                score += (i * 3000); // pairs start mattering from here
            }
            if (suitCount[i] == 2){
                score += (i * 1000); //Double pair is 2000, single triple becomes 3000, and triple and pair is 4000.
            }
        }

        //that's the entire score calculation done.
        return score;
    }

    //score calculate, compare, and multiply the pots
    private int determinePayout(Player player, Player luigi){
        int playerScore = playerScore(player);
        if (playerScore <= playerScore(luigi)){
            System.out.println(player.getPlayerName() + " did not beat Luigi. ");
            return -player.getBet();
        }
        int prizeTier = playerScore / 1000;
        return switch (prizeTier) {
            case 6 -> {
                System.out.println(player.getPlayerName() + " got a flush! ");
                yield player.getBet() * 12;
            }
            case 5 -> {
                System.out.println(player.getPlayerName() + " got a four of a kind! ");
                yield player.getBet() * 8;
            }
            case 4 -> {
                System.out.println(player.getPlayerName() + " got a full house! ");
                yield player.getBet() * 6;
            }
            case 3 -> {
                System.out.println(player.getPlayerName() + " got a three of a kind! ");
                yield player.getBet() * 4;
            }
            case 2 -> {
                System.out.println(player.getPlayerName() + " got two pairs! ");
                yield player.getBet() * 3;
            }
            case 1 -> {
                System.out.println(player.getPlayerName() + " got a pair! ");
                yield player.getBet() * 2;
            }
            case 0 -> {
                System.out.println(player.getPlayerName() + ", how did you beat Luigi?");
                yield player.getBet();
            }
            default ->

                //there should be no case anyone gets here.
                    0;
        };

    }

    private Player determineWinner(Player[] playerList){
        int winner = 0;
        for (int i = 0; i < playerList.length; ++i){
            System.out.println(playerList[i].getPlayerName() + " has " + playerList[i].getTokens() + "tokens! ");
            if (playerList[i].getTokens() > playerList[winner].getTokens()){
                winner = i;
            }
        }
        return playerList[winner];
    }

    public Game gameSeq(long gameID) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "picturepoker", "postgres", "password");
        try {
            Connection connection = dcm.getConnection();

            //in the game, the first thing we want to do is get DAO objects to everything
            GameDAO gamedao = new GameDAO(connection);
            PlayerDAO playerdao = new PlayerDAO(connection);

            //Next, we get the current game state using the game ID.
            curGame = gamedao.findById(gameID);

            long[]playerIDList = curGame.getPlayers();

            //we get the list of all the players, so it is iterable.
            for (int i = 0; i < playerList.length; ++i){
                playerList[i] = playerdao.findById(playerIDList[i]);
            }

            //reset everything we'd need to reset before the game.
            for (Player value : playerList) {
                value.setTokens(5);
                value.setRoundsWon(0);
            }

            // we can do a check on the number of rounds at this point to see if the game is ongoing.
            while (curGame.getCurRound() != curGame.getNumRounds()) {
                // this is where you'd implement turn order. I'm not going to do it.
                for (int i = 0; i < playerList.length; ++i) {
                    playerList[i] = executeTurn(playerList[i]);
                }

                // this is where we'd execute Luigi's turn.
                Player luigi = new Player();
                luigi = executeLuigi(luigi);

                System.out.println("Calculating payouts");
                //Now we run a function which pays out tokens compared to Luigi
                for (Player player : playerList) {
                    player.setTokens(player.getTokens() + determinePayout(player, luigi));
                    System.out.println(player.getPlayerName() + " has " + player.getTokens() + " tokens. ");
                }
                // we can do a check on the number of rounds at this point to see if the game is ongoing.
                //we should increment the current round and keep on going.
                curGame.setCurRound(curGame.getCurRound() + 1);

                //update the game at this point to the database.
                gamedao.update_all(curGame);
            }

            //once we break out of the loop we can determine the winner.
            Player winner = determineWinner(playerList);

            System.out.println("\nCongrats, " + winner.getPlayerName() + " has won! ");


            //Game updates
            curGame.setWinner(winner.getPlayerName());
            gamedao.update_all(curGame);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        //return the game state
        return curGame;
    }
}
