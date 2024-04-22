package game.jdbc.picturepoker;

import java.util.*;

public class GamePlay {

    //Relevant variables - current game, and the list of players
    private Game curGame;
    private Player[] playerList;

    public enum PokerHand {
        HIGH_CARD("High Card"),
        ONE_PAIR("One Pair"),
        TWO_PAIR("Two Pair"),
        THREE_OF_A_KIND("Three of a Kind"),
        FULL_HOUSE("Full House"),
        FOUR_OF_A_KIND("Four of a Kind"),
        FLUSH("Flush");
        private String handName;
        PokerHand(String handName){
            this.handName = handName;
        }
        public String getHandName(){return handName;}
    }

    // Constructor since we are using this multiple times within the API calls
    public GamePlay(Game game, Player[] playerList){
        this.curGame = game;
        this.playerList = playerList;
    }

    public Game getCurGame() {
        return curGame;
    }

    private Player executeRoundFromTerminal(Player player, GameDAO gameDAO, PlayerDAO playerDAO) {
        System.out.println("\n" + player.getPlayerName() + "'s Turn! ");
        System.out.println(player.getPlayerName() + " has " + player.getTokens() + " tokens!");

        // Step 1: Deal cards at random
        Card[] hand = new Card[5];
        //initialize values
        for (int i = 0; i < hand.length; ++i) {
            hand[i] = new Card();
        }

        //update the hand of the player
        player.setHand(hand);

        // Prints out the card outputs
        System.out.println(player.getPlayerName() + "'s cards: ");
        for (int i = 0; i < hand.length; ++i) {
            System.out.println("Card " + i + ": " + hand[i]);
        }

        //Case: Broke- Do this before we even give them input
        if (player.getTokens() == 0) {
            System.out.println("You have no tokens to bet. How did you get here? ");
            player.setBet(0);
            return player;
        }

        // Step 2: Take bets
        // I noticed there is a raise function just now. This will be useful for a GUI feature where you can spam a button.
        // Terminal inputs make it so that spamming a button is a bit hard, though.
        // TODO: when we have GUI set up and everything.
        System.out.println("How much do you want to bet? ");
        Scanner scan = new Scanner(System.in);
        int betCount = 0;
        while (betCount < 1 || betCount > 5) {
            try {
                betCount = scan.nextInt();
                //case: too much / little money.
                if (betCount < 1 || betCount > 5) {
                    System.out.println("Bet must be between 1 and 5. ");
                    continue;
                }
                // Case: broke part 2
                if (betCount > player.getTokens()) {
                    System.out.println("You don't have enough tokens! Right now, you have " + player.getTokens() + " tokens. ");
                    betCount = 0;
                    continue;
                }
                //case: we are in the clear
                break;
            } catch (InputMismatchException e) {
                //case: what even was typed?
                System.out.println("Bet must be between 1 and 5. ");
                scan.nextLine();
            }
        }
        player.setBet(betCount);
        player.setTokens(player.getTokens() - betCount);
        playerDAO.update_int("tokens", player.getTokens(), player);
        playerDAO.update_int("bet", player.getBet(), player);

        // Step 3: Change out the cards which were selected for changing
        System.out.println("Which cards would you like to change out? ");
        boolean changeCard;
        for (int i = 0; i < hand.length; ++i) {
            try {
                System.out.println("Change card " + i + "?");
                changeCard = scan.nextBoolean();
                if (changeCard)
                    hand[i].setToChange(true);
            } catch (InputMismatchException e) {
                System.out.println("Player must input true or false!");
                --i;
                scan.nextLine();
            }
        }

        // We change out desired cards.
        player.redrawHand();
        playerDAO.updateHand(player);

        // Step 4: Let the player cry at their new cards.
        System.out.println("Here are your new cards!");


        // return the player with the newfangled hand.
        for (int i = 0; i < hand.length; ++i) {
            System.out.println("Card " + i + ": " + player.getHand()[i]);
        }

        return player;
    }

    public void executeLuigi() {
        // Luigi is an external game force.
        Card[] luigiHand = new Card[5];
        //initialize values
        for (int i = 0; i < luigiHand.length; ++i) {
            luigiHand[i] = new Card();
        }
        //updates the game with luigi's hand
        curGame.setHand(luigiHand);


        // step 1: Get cards at random
        // This is already done by the card class.
        System.out.println("Luigi Turn! ");
        for (int i = 0; i < luigiHand.length; ++i) {
            System.out.println("Card " + i + ": " + luigiHand[i]);
        }
        // Step 2: Calculate current score and change out cards if needed
        int luigiScore = playerScore(luigiHand);
        if (luigiScore > 1000000) {
            System.out.println("Luigi is happy with his hand! ");
            return;
        } else {
            List<Integer> cardToSwapIndex = findCardsToChange(luigiHand);
            for (Integer toSwapIndex : cardToSwapIndex) {
                luigiHand[toSwapIndex].setToChange(true);
            }
        }

        // Step 3: that's it.
        for (int i = 0; i < luigiHand.length; ++i) {
            if (luigiHand[i].getToChange()) {
                System.out.println("Luigi is changing out card " + i + "!");
                luigiHand[i].redrawSuit();
            }
        }

        curGame.setHand(luigiHand);

        for (int i = 0; i < luigiHand.length; ++i) {
            System.out.println("Card " + i + ": " + luigiHand[i]);
        }

        curGame.setLuigiFinished(1);
        //no return needed.
    }


    // Returns the index of the card with the least potential to form a good hand,
    // if no card should be changed returns -1
    private int findCardWithLeastPotential(Card[] hand, boolean[] alreadyChosen) {
        // Count number of cards of each suit
        int[] suitCount = new int[Card.Suit.values().length];
        for (int i = 0; i < hand.length; ++i) {
            if (!alreadyChosen[i]) {
                suitCount[hand[i].getSuit().ordinal()]++;
            }
        }

        // First Check for 4-of-a-kind
        for (int i = 0; i < suitCount.length; ++i) {
            if (suitCount[i] == 4) {
                for (int j = 0; j < hand.length; ++j) {
                    if (!alreadyChosen[j] && hand[j].getSuit().ordinal() != i) {
                        return j; // return the index of the card to change
                    }
                }
            }
        }

        // Next, Check for possible full house (3-of-a-kind and pair)
        boolean threeOfAKind = false;
        int suitOfThree = -1;

        for (int i = 0; i < suitCount.length; ++i) {
            if (suitCount[i] == 3) {
                threeOfAKind = true;
                suitOfThree = i;
            }
        }

        if (threeOfAKind) {
            for (int i = 0; i < hand.length; ++i) {
                // Finds both cards that are not part of three of a kind, if they are a pair
                // then dont change, else change the one with the lowest value according to
                // ordinal
                if (!alreadyChosen[i] && hand[i].getSuit().ordinal() != suitOfThree) {
                    for (int j = i + 1; j < hand.length; ++j) {
                        if (!alreadyChosen[j] && hand[j].getSuit().ordinal() != suitOfThree) {
                            if (hand[i].getSuit().ordinal() != hand[j].getSuit().ordinal()) {
                                // returns the index of whichever card has a lower suit
                                return (hand[i].getSuit().ordinal() < hand[j].getSuit().ordinal()) ? i : j;
                            } else {
                                return -1; // no change needed
                            }
                        }
                    }
                }
            }
        }

        // Next, we mark pairs to ensure they are not changed

        boolean[] isPair = new boolean[hand.length];
        for (int i = 0; i < hand.length; i++) {
            if (!alreadyChosen[i]) {
                for (int j = i + 1; j < hand.length; j++) {
                    if (!alreadyChosen[j] && hand[i].getSuit().ordinal() == hand[j].getSuit().ordinal()) {
                        isPair[i] = true;
                        isPair[j] = true;
                    }
                }
            }
        }

        // Finally, we change the card with the lowest value

        int cardToDiscard = -1;
        int lowestOrdinal = Integer.MAX_VALUE;

        for (int i = 0; i < hand.length; ++i) {
            if (!alreadyChosen[i] && !isPair[i]) {
                if (hand[i].getSuit().ordinal() < lowestOrdinal) {
                    lowestOrdinal = hand[i].getSuit().ordinal();
                    cardToDiscard = i;
                }
            }
        }

        return cardToDiscard;
    }

    // Returns a list of cards to be changed
    private List<Integer> findCardsToChange(Card[] hand) {
        List<Integer> cardsToChange = new ArrayList<>();
        boolean[] alreadyChosen = new boolean[hand.length];

        // Tries to change up to 5 cards
        for (int changes = 0; changes < 5; changes++) {
            int cardIndex = findCardWithLeastPotential(hand, alreadyChosen);
            // if a card will be beneficial to change, add it to the list and try again
            // if no card is beneficial, break out of the loop
            if (cardIndex == -1) {
                break;
            }
            cardsToChange.add(cardIndex);
            alreadyChosen[cardIndex] = true;
        }

        return cardsToChange;
    }

    // This function does a score calculation on a player.
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
        for (int i = suitCount.length - 1; i >= 0; --i) {
            // Each digit represents whether a certain set of cards exists and of what type
            // Ex: a 30000000 = 3*10000000 represents a 5 of a kind of Flowers
            if (suitCount[i] == 5) {
                score += (i + 1) * 10000000; // easy way to tell differences between hand strengths - just use huge numbers
                break;
            }
            if (suitCount[i] == 4) {
                score += (i + 1) * 1000000;
            }
            if (suitCount[i] == 3) {
                score += (i + 1) * 100000; // pairs start mattering from here
            }
            if (suitCount[i] == 2) {
                if (!pairFound) {
                    score += (i + 1) * 10000; //Double pair is 11000, single triple becomes 100000, and full house is 110000.
                    pairFound = true;
                } else {
                    score += (i + 1) * 1000; // There should only ever be up to two pairs at once.
                }

            }
            // We note that every singleton is unique, so we can only see one Star, then one Mario, and so on
            // We can represent every combination of singletons uniquely using integers
            // Using the mapping 120*[0,5] + 24[0,4] + 6[0,3] + 2[0,2] + 1[0,1],
            // where each term corresponds to the ith highest singleton.
            // This is necessary to completely correctly evaluate one pair hands
            // This does not interfere with any higher sets because the singleton max
            // is 6! - 1 = 719 < 1000.
            if (suitCount[i] == 1) {
                score += singleton_mult * i;
                singleton_mult /= (5 - singletons_found);
                singletons_found++;
            }
        }

        //that's the entire score calculation done.
        return score;
    }

    // This function converts a calculated score to a hand name.
    private PokerHand scoreToHand(int playerScore){
        if (playerScore >= 10000000) {
            return PokerHand.FLUSH;
        }
        if (playerScore >= 1000000) {
            return PokerHand.FOUR_OF_A_KIND;
        }
        if (playerScore >= 100000) {
            // If 10^4 digit is nonzero the two remaining cards are a pair, otherwise kickers
            if((playerScore % 100000) / 10000 > 0){
                return PokerHand.FULL_HOUSE;
            }
            return PokerHand.THREE_OF_A_KIND;
        }
        if (playerScore >= 10000) {
            // If 10^3 digit is nonzero there is a second pair, otherwise three kickers
            if((playerScore % 10000) / 1000 > 0){
                return PokerHand.TWO_PAIR;
            }
            return PokerHand.ONE_PAIR;
        }
        else {
            return PokerHand.HIGH_CARD;
        }
    }

    //score calculate, compare, and multiply the pots
    private int determinePayout(Player player) {
        int playerScore = playerScore(player.getHand());
        PokerHand playerHand = scoreToHand(playerScore);
        // A player is considered to have won a round if they beat Luigi- they do not have to beat anyone else
        int winner = 1;
        int luigiScore = playerScore(curGame.getHand());
        PokerHand luigiHand = scoreToHand(luigiScore);
        System.out.println("Luigi got a "+ luigiHand.getHandName() +"!");
        System.out.println(player.getPlayerName()  + " got a " + playerHand.getHandName() + "!");
        // NOTE: The scoring calculator is somewhat bugged- it does not properly compare
        // triples and full houses, or one and two pairs.
        // As a workaround we compare hands first, then use scores as a tiebreaker
        if(playerHand.ordinal() < luigiHand.ordinal()){
            System.out.println(player.getPlayerName() + " did not beat Luigi. ");
            winner = 0;
        }
        else{
            if(playerHand.ordinal() == luigiHand.ordinal() && playerScore < luigiScore){
                System.out.println(player.getPlayerName() + " did not beat Luigi. ");
                winner = 0;
            }
            else{
                System.out.println(player.getPlayerName() + " defeated Luigi! ");
            }
        }
        player.setRoundsWon(player.getRoundsWon() + winner);
        player.setLifetimeRoundsWon(player.getLifetimeRoundsWon() + winner);

        switch(playerHand){
            case FLUSH:
                player.setFlushes(player.getFlushes() + 1);
                return player.getBet() * 12 * winner;
            case FOUR_OF_A_KIND:
                player.setQuads(player.getQuads() + 1);
                return player.getBet() * 8 * winner;
            case FULL_HOUSE:
                player.setFullHouses(player.getFullHouses() + 1);
                return player.getBet() * 6 * winner;
            case THREE_OF_A_KIND:
                player.setTriples(player.getTriples() + 1);
                return player.getBet() * 4 * winner;
            case TWO_PAIR:
                player.setTwoPairs(player.getTwoPairs() + 1);
                return player.getBet() * 3 * winner;
            case ONE_PAIR:
                player.setOnePairs(player.getOnePairs() + 1);
                return player.getBet() * 2 * winner;
            case HIGH_CARD:
                player.setHighCards(player.getHighCards() + 1);
                return player.getBet() * winner;
            default:
                System.out.println("Invalid hand found!");
                return 0;
        }
    }

    // If commitResults is false, the results of the showdown will not be recorded in the database
    public ShowdownInfo showdownResolution(GameDAO gameDAO, PlayerDAO playerDAO, boolean commitResults){
        System.out.println("Showdown time!");

        // We want to track the players that ran out of tokens so we can just skip their turns
        int playersBankrupted = 0;

        // Begin tracking players' end of round information

        ShowdownInfo sdInfo = new ShowdownInfo();
        sdInfo.setLuigiHand(curGame.getHand());
        ArrayList<PlayerShowdownInfo> pSDList = new ArrayList<PlayerShowdownInfo>();

        //Now we run a function which pays out tokens compared to Luigi
        for (Player player : playerList) {
            PlayerShowdownInfo pSD = new PlayerShowdownInfo(player);
            // Need to create a deep copy of hand so that resetting cards does not affect returned info
            Card[] dupHand = new Card[5];
            for(int i = 0; i < 5; i++){
                dupHand[i] = new Card(player.getHand()[i].getSuit());
            }
            pSD.setHand(dupHand);
            int coinsWon = determinePayout(player);
            if(coinsWon > 0){
                System.out.println(player.getPlayerName() + " won " + coinsWon + " tokens!");
                pSD.setWinLossAmount(coinsWon);
            }
            else{
                if(player.getTokens() == 0) {
                    System.out.println(player.getPlayerName() + " is bankrupt...");
                }
                else{
                    System.out.println(player.getPlayerName() + " lost " + player.getBet() + " tokens.");
                }
                pSD.setWinLossAmount(-player.getBet());
            }
            pSD.setHandType(scoreToHand(playerScore(player.getHand())).getHandName());
            player.setTokens(player.getTokens() + coinsWon);

            System.out.println(player.getPlayerName() + " now has " + player.getTokens() + " tokens. ");
            pSD.setNewTokens(player.getTokens());
            if(commitResults) {
                playerDAO.update_long("tokens", player.getTokens(), player);
                playerDAO.update_int("rounds_won", player.getRoundsWon(), player);
            }

            //a new bit to reset the player's hand at the end of each round
            if(commitResults) {
                player.resetHand();
                playerDAO.updateHand(player);
            }

            // Handle bankruptcy logic here
            playersBankrupted += (player.getTokens() > 0 ? 0 : 1);
            player.setFinishedRound(player.getTokens() > 0 ? 0 : 1);
            player.setBet(player.getTokens() > 0 ? 1 : 0);
            player.setTokens(player.getTokens() > 0 ? player.getTokens() - 1: 0);
            if (commitResults) {
                playerDAO.update_int("finished_round", player.getFinishedRound(), player);
                playerDAO.update_int("bet", player.getBet(), player);
                playerDAO.update_int("tokens", player.getTokens(), player);
                playerDAO.updateAttributes(player);
            }
            pSDList.add(pSD);
        }

        //we should increment the current round and keep on going.
        curGame.setCurRound(curGame.getCurRound() + 1);
        curGame.setPlayersFinished(playersBankrupted);
        //update the game at this point to the database, and reenable Luigi to act.
        if(commitResults) {
            curGame.setLuigiFinished(0);
            gameDAO.update_int("luigi_finished", 0, curGame);
            gameDAO.update_all(curGame);
        }
        sdInfo.setPlayerShowdownInfos(pSDList);
        return sdInfo;
    }

    public void gameEndResolution(GameDAO gameDAO, PlayerDAO playerDAO){
        System.out.println("\nGame over. Placements: ");

        //update this one last time just in case anything has changed
        ArrayList<Player> playerArrayList = new ArrayList<>(Arrays.asList(playerList));
        Collections.sort(playerArrayList);

        int curPlayerNum = 0;
        for (Player player : playerArrayList) {
            System.out.println((curPlayerNum + 1) + " place: " + player.getPlayerName());

            //we also want to update the lifetime tokens while we're here. Also the dollar counts.
            player.setLifetimeTokens(player.getTokens() + player.getLifetimeTokens());
            playerDAO.update_long("lifetime_tokens", player.getLifetimeTokens(), player);

            switch (curPlayerNum++) {
                case 0:
                    player.setFirstPlaces(player.getFirstPlaces() + 1);
                    playerDAO.update_int("first_places", player.getFirstPlaces(), player);
                    //We also want to update the dollars accordingly
                    playerDAO.update_int("dollars", player.getDollars() + (int) (curGame.getPotQuantity() * 0.55), player);

                    curGame.setWinner(player.getPlayerName());
                    continue;
                case 1:
                    player.setSecondPlaces(player.getSecondPlaces() + 1);
                    playerDAO.update_int("second_places", player.getSecondPlaces(), player);
                    playerDAO.update_int("dollars", player.getDollars() + (int) (curGame.getPotQuantity() * 0.30), player);
                    continue;
                case 2:
                    player.setThirdPlaces(player.getThirdPlaces() + 1);
                    playerDAO.update_int("third_places", player.getThirdPlaces(), player);
                    playerDAO.update_int("dollars", player.getDollars() + (int) (curGame.getPotQuantity() * 0.15), player);
                    continue;
                case 3:
                    player.setFourthPlaces(player.getFourthPlaces() + 1);
                    playerDAO.update_int("fourth_places", player.getFourthPlaces(), player);
                    playerDAO.update_int("dollars", player.getDollars(), player);
                    continue;
                default:
            }
        }

        //Game updates
        gameDAO.update_all(curGame);
    }

    public Game gameSeq(GameDAO gamedao, PlayerDAO playerdao) {
        //in the game, the first thing we want to do is get DAO objects to everything

        //Next, we get the current game state using the game ID.
        curGame = gamedao.findById(curGame.getID());
        //of course, we also have to get the players because gameDAO is weird like that.
        curGame.setPlayers(gamedao.getPIDsByGame(curGame));

        long[] playerIDList = curGame.getPlayers();

        //we get the list of all the players, so it is iterable.
        for (int i = 0; i < playerList.length; ++i) {
            playerList[i] = playerdao.findById(playerIDList[i]);
            playerList[i] = playerdao.getHand(playerList[i]);
        }

        //reset everything we'd need to reset before the game. Do not reset if in the middle of a game
        if(curGame.getCurRound() == 1) {
            for (Player value : playerList) {
                value.resetPerGameInfo();
                //drain people's bank accounts and add to pot
                value.setDollars(value.getDollars() - curGame.getBuyIn());
                curGame.setPotQuantity(curGame.getPotQuantity() + curGame.getBuyIn());
            }
        }

        int curPlayerNum;
        ArrayList<Player> playerArrayList = new ArrayList<>(Arrays.asList(playerList));
        // we can do a check on the number of rounds at this point to see if the game is ongoing.
        while (curGame.getCurRound() <= curGame.getNumRounds()) {
            //Print out the rounds
            System.out.println("Round " + curGame.getCurRound());
            //I do agree that array lists are easier to sort - but not much else.
            //This probably causes a bunch of memory leaks or something: isn't that a shame.
            playerArrayList = new ArrayList<>(Arrays.asList(playerList));
            Collections.sort(playerArrayList);

            //we execute everyone's turns now
            curPlayerNum = 0;
            for (Player i : playerArrayList) {
                playerList[curPlayerNum++] = executeRoundFromTerminal(i, gamedao, playerdao);
                i.setFinishedRound(1);
                playerdao.update_int("finished_round", 1, i);
                curGame.setPlayersFinished(curGame.getPlayersFinished() + 1);
                gamedao.update_int("players_finished", curGame.getPlayersFinished(), curGame);
            }
            // this is where we'd execute Luigi's turn.
            executeLuigi();
            gamedao.updateHand(curGame);
            gamedao.update_int("luigi_finished", 1, curGame);

            // Runs Luigi logic then pays out to players
            showdownResolution(gamedao, playerdao, true);
        }

        //once we break out of the loop, all rounds are over and we can determine the winner.
        gameEndResolution(gamedao, playerdao);

        //return the game state
        return curGame;
    }
}
