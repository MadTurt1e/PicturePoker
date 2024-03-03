package game.jdbc.picturepoker;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;


public class GamePlay {

    //Relevant variables - current game, and the list of players
    private Game curGame;
    private final Player[] playerList = new Player[4];

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
        // I noticed there is a raise function just now. This will be useful for a GUI feature where you can spam a button.
        // Terminal inputs make it so that spamming a button is a bit hard, though.
        // to do when we have GUI set up and everything.
        System.out.println("How much do you want to bet? ");
        Scanner scan = new Scanner(System.in);
        int betCount = 0;
        while (betCount < 1 || betCount > 6) {
            //Case: Broke
            if (player.getTokens() == 0){
                System.out.println("You have no tokens to bet. How did you get here? ");
                player.setBet(0);
                return player;
            }
            try {
                betCount = scan.nextInt();
                //case: too much / little money.
                if (betCount < 1 || betCount > 6){
                    System.out.println("Bet must be between 1 and 5! ");
                    continue;
                }
                // Case: broke part 2
                if (betCount > player.getTokens()){
                    System.out.println("You don't have enough tokens! Right now, you have " + player.getTokens() + " tokens. ");
                    betCount = 1000;
                    continue;
                }
                //case: we are in the clear
                break;
            }catch (InputMismatchException e) {
                //case: what even was typed?
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
            }catch (InputMismatchException e) {
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

    private void executeLuigi() {
        // Luigi is an external game force.
        Card[] luigiHand = new Card[5];
        //initialize values
        for (int i = 0; i < luigiHand.length; ++i){
            luigiHand[i] = new Card();
        }
        //updates the game with luigi's hand
        curGame.setHand(luigiHand);


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
        curGame.setHand(luigiHand);

        for (int i = 0; i < luigiHand.length; ++i) {
            System.out.println("Card " + i + ": " + luigiHand[i]);
        }

        //no return needed.
    }

    //This function does a score calculation on a player.
    private int playerScore(Card[] hand) {
        int score = 0;
        int[] suitCount = new int[6];
        boolean pairFound = false; // For evaluating two pairs
        int singleton_mult = 120;
        int singletons_found = 0;

        Card.Suit curSuit;
        //count the number of each suit - start from 1 to prevent any weird errors
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
        // We should evaluate from high to low so that a higher pair breaks ties first
        for (int i = suitCount.length - 1; i >= 0; --i){
            // Each digit represents whether a certain set of cards exists and of what type
            // Ex: a 30000000 = 3*10000000 represents a 5 of a kind of Flowers
            if (suitCount[i] == 5){
                score += (i+1) * 10000000; // easy way to tell differences between hand strengths - just use huge numbers
                break;
            }
            if (suitCount[i] == 4){
                score += (i+1) * 1000000;
            }
            if (suitCount[i] == 3){
                score += (i+1) * 100000; // pairs start mattering from here
            }
            if (suitCount[i] == 2){
                if(!pairFound){
                    score += (i+1) * 10000; //Double pair is 11000, single triple becomes 100000, and full house is 110000.
                    pairFound = true;
                }
                else{
                    score += (i+1) * 1000; // There should only ever be up to two pairs at once.
                }

            }
            // We note that every singleton is unique, so we can only see one Star, then one Mario, and so on
            // We can represent every combination of singletons uniquely using integers
            // Using the mapping 120*[0,5] + 24[0,4] + 6[0,3] + 2[0,2] + 1[0,1],
            // where each term corresponds to the ith highest singleton.
            // This is necessary to completely correctly evaluate one pair hands
            // This does not interfere with any higher sets because the singleton max
            // is 6! - 1 = 719 < 1000.
            if(suitCount[i] == 1){
                score += singleton_mult * i;
                singleton_mult /= (5-singletons_found);
                singletons_found++;
            }
        }

        //that's the entire score calculation done.
        return score;
    }

    //score calculate, compare, and multiply the pots
    private int determinePayout(Player player) {
        int playerScore = playerScore(player.getHand());
        if (playerScore <= playerScore(curGame.getHand())) {
            System.out.println(player.getPlayerName() + " did not beat Luigi. ");
            return 0;
        }
        if (playerScore >= 10000000) {
            System.out.println(player.getPlayerName() + " got a flush! ");
            return player.getBet() * 12;
        }
        if (playerScore >= 1000000) {
            System.out.println(player.getPlayerName() + " got a four of a kind! ");
            return player.getBet() * 8;
        }
        if (playerScore >= 110000){
            System.out.println(player.getPlayerName() + " got a full house! ");
            return player.getBet() * 6;
        }
        if (playerScore >= 100000){
            System.out.println(player.getPlayerName() + " got a three of a kind! ");
            return player.getBet() * 4;
        }
        if(playerScore >= 11000){
            System.out.println(player.getPlayerName() + " got a two pair! ");
            return player.getBet() * 3;
        }
        if(playerScore >= 10000){
            System.out.println(player.getPlayerName() + " got a one pair! ");
            return player.getBet() * 2;
        }
        else{
            System.out.println(player.getPlayerName() + ", how did you beat Luigi?");
            return player.getBet();
        }
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

            int curPlayerNum;
            ArrayList<Player> playerArrayList = new ArrayList<>(Arrays.asList(playerList));
            // we can do a check on the number of rounds at this point to see if the game is ongoing.
            while (curGame.getCurRound() != curGame.getNumRounds()) {
                //Print out the rounds
                System.out.println("Round " + curGame.getCurRound());
                //I do agree that array lists are easier to sort - but not much else.
                //This probably causes a bunch of memory leaks or something: isn't that a shame.
                playerArrayList = new ArrayList<>(Arrays.asList(playerList));
                Collections.sort(playerArrayList);

                //we execute everyone's turns now
                curPlayerNum = 0;
                for (Player i : playerArrayList){
                    playerList[curPlayerNum++] = executeTurn(i);
                    playerdao.updateHand(i);
                }

                // this is where we'd execute Luigi's turn.
                executeLuigi();

                System.out.println("Calculating payouts");
                //Now we run a function which pays out tokens compared to Luigi
                for (Player player : playerList) {
                    player.setTokens(player.getTokens() + determinePayout(player));
                    System.out.println(player.getPlayerName() + " has " + player.getTokens() + " tokens. ");
                    playerdao.update_long("tokens", player.getTokens(), player);
                }
                // we can do a check on the number of rounds at this point to see if the game is ongoing.
                //we should increment the current round and keep on going.
                curGame.setCurRound(curGame.getCurRound() + 1);

                //update the game at this point to the database.
                gamedao.update_all(curGame);
            }

            //once we break out of the loop we can determine the winner.

            System.out.println("\nGame over. Placements: ");

            curPlayerNum = 0;
            for (Player player : playerArrayList){
                System.out.println((curPlayerNum +1) + " place: " + player.getPlayerName());
                ++curPlayerNum;
                switch (curPlayerNum){
                    case 0:
                        player.setFirstPlaces(player.getFirstPlaces() + 1);
                        playerdao.update_int("first_places", player.getFirstPlaces(), player);
                        continue;
                    case 1:
                        player.setSecondPlaces(player.getSecondPlaces() + 1);
                        playerdao.update_int("second_places", player.getSecondPlaces(), player);
                        continue;
                    case 2:
                        player.setThirdPlaces(player.getThirdPlaces() + 1);
                        playerdao.update_int("third_places", player.getThirdPlaces(), player);
                        continue;
                    case 3:
                        player.setFourthPlaces(player.getFourthPlaces() + 1);
                        playerdao.update_int("fourth_places", player.getFourthPlaces(), player);
                        continue;
                    default:
                }
            }


            //Game updates
            gamedao.update_all(curGame);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        //return the game state
        return curGame;
    }
}
