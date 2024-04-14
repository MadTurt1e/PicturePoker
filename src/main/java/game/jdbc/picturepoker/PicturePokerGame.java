package game.jdbc.picturepoker;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

// @RequestMapping("/api") We will shift most of the functionality here to be hidden under /api
@SpringBootApplication
@CrossOrigin // This is for the front end to be able to access the backend
@RestController
public class PicturePokerGame {

    // Set the hostname as a pseudo global so that we can change it later
    private final String hostname = "db";

    // Test operation - is this thing on?
    @GetMapping("/helloWorld")
    public String helloWorld() {
        System.out.println("Hello, World!");
        return ("HELLO WORLD");
    }

    // homepage
    @GetMapping("/")
    public String homepage() {
        System.out.println("Welcome to PicturePoker");
        return ("Welcome to Luigi's Picture Poker");
    }

    // CREATE Operation: Make new player
    @PostMapping("/createNewPlayer")
    public Player createNewPlayer(@RequestBody String json) throws JsonProcessingException {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(hostname,
                "picturepoker", "postgres", "password");
        Player player = new Player();
        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerdao = new PlayerDAO(connection);
            // essentially, a (new) player consists of a player name and a password, and that is it.
            player.setPlayerName(inputMap.get("playerName"));
            player.setPasscode(inputMap.get("password"));
            player = playerdao.create(player);
            player = playerdao.createHand(player); // Initialize player hand on creation
            connection.close();
            System.out.println(player);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    // CREATE Operation: creates new game
    @PostMapping("/createNewGame")
    public Game createNewGame(@RequestBody String json) throws JsonProcessingException {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(hostname,
                "picturepoker", "postgres", "password");
        Game game = new Game();
        try {
            Connection connection = dcm.getConnection();
            GameDAO gamedao = new GameDAO(connection);

            // A new game consists a chosen number of rounds, a buy in value (dollar
            // stakes), and difficulty
            game.setNumRounds(Integer.parseInt(inputMap.get("rounds")));
            game.setBuyIn(Integer.parseInt(inputMap.get("buyIn")));
            game.setDifficulty(Integer.parseInt(inputMap.get("difficulty")));
            // Default attributes
            game.setCurRound(1);
            game.setPotQuantity(0);
            game = gamedao.create(game);
            game = gamedao.createHand(game);
            // once all the necessary values are created, we can make the game.
            connection.close();
            System.out.println(game);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return game;
    }

    // READ Operation - Read a player's full details
    @GetMapping("/getByPlayerName/{playerName}")
    public Player getByPlayerName(@PathVariable("playerName") String playerName) {
        System.out.println(playerName);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(hostname,
                "picturepoker", "postgres", "password");
        Player player = new Player();
        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);

            player = playerDAO.findByName(playerName);
            player = playerDAO.getHand(player);
            System.out.println(player);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    //READ Operation - Read a player's full details
    @GetMapping("/getByPlayerID/{p_idStr}")
    public Player getByPlayerID(@PathVariable("p_idStr") String p_idStr) {
        long p_id = Long.parseLong(p_idStr);
        System.out.println(p_id);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(hostname,
                "picturepoker", "postgres", "password");
        Player player = new Player();
        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);

            player = playerDAO.findById(p_id);
            player = playerDAO.getHand(player);
            System.out.println(player);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    //READ Operation - Read a player's full details
    @GetMapping("/getPlayerActiveGame/{pid}")
    public Game getPlayerActiveGame(@PathVariable("pid") long pid) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(hostname,
                "picturepoker", "postgres", "password");
        Game game = new Game();
        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);
            GameDAO gameDAO = new GameDAO(connection);
            Player p = playerDAO.findById(pid);
            long gid = playerDAO.getCurrentGame(p);
            if(gid < 0){
                System.out.println("Player is not in any games!");
                return null;
            }
            game = gameDAO.findById(gid);
            System.out.println(game);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return game;
    }

    //READ Operation - Read a game's full details
    @GetMapping("/getByGameID/{g_id}")
    public Game getByGameID(@PathVariable("g_id") String g_idStr) {
        System.out.println(g_idStr);
        long g_id = Long.parseLong(g_idStr);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(hostname,
                "picturepoker", "postgres", "password");
        Game game = new Game();
        try {
            Connection connection = dcm.getConnection();
            GameDAO gamedao = new GameDAO(connection);
            game = gamedao.findById(g_id);
            //we need a separate function to get pids because the database is structured poorly
            game.setPlayers(gamedao.getPIDsByGame(game));
            System.out.println(game);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return game;
    }

    //READ Operation - Reads players from a game
    @GetMapping("/getPlayersByGame/{g_id}")
    public ArrayList<Player> getPlayersByGame(@PathVariable("g_id") String g_idStr) {
        System.out.println(g_idStr);
        long g_id = Long.parseLong(g_idStr);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(hostname,
                "picturepoker", "postgres", "password");
        ArrayList<Player> playersInGame = new ArrayList<Player>();Game game = new Game();
        try {
            Connection connection = dcm.getConnection();
            GameDAO gamedao = new GameDAO(connection);
            PlayerDAO playerDAO = new PlayerDAO(connection);
            game = gamedao.findById(g_id);
            long[] gamePIDs = gamedao.getPIDsByGame(game);
            for(int i = 0; i < 4; i++){
                playersInGame.add(playerDAO.findById(gamePIDs[i]));
            }
            for(Player p : playersInGame){
                playerDAO.getHand(p);
                System.out.println(p);
            }
            System.out.println(game);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playersInGame;
    }

    //READ Operation - Reads all players
    @GetMapping("/getAllPlayers")
    public ArrayList<Player> getAllPlayers() {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(hostname,
                "picturepoker", "postgres", "password");
        ArrayList<Player> allPlayers = new ArrayList<Player>();
        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);

            allPlayers = playerDAO.findAllPlayers();
            for (Player p : allPlayers) {
                p = playerDAO.getHand(p);
                System.out.println(p);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allPlayers;
    }

    @GetMapping("/getAllGames")
    public ArrayList<Game> getAllGames() {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(hostname,
                "picturepoker", "postgres", "password");
        ArrayList<Game> allGames = new ArrayList<Game>();
        try {
            Connection connection = dcm.getConnection();
            GameDAO gameDAO = new GameDAO(connection);
            //we need a separate function to get pids because the database is structured poorly
            allGames = gameDAO.findAllGames();
            for (Game g : allGames) {
                System.out.println(g);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allGames;
    }

    // UPDATE Operation - Update a player
    @PutMapping("/updatePlayer/{p_id}")
    public Player updateByPID(@PathVariable long p_id, @RequestBody String json) throws JsonProcessingException {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(hostname,
                "picturepoker", "postgres", "password");
        Player updatedPlayer = new Player();
        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerdao = new PlayerDAO(connection);
            updatedPlayer = playerdao.findById(p_id);

            updatedPlayer.setID(p_id);
            updatedPlayer.setPlayerName(inputMap.get("name"));
            updatedPlayer.setPasscode(inputMap.get("password"));
            updatedPlayer.setDollars(Integer.parseInt(inputMap.get("dollars")));

            // Per player game statistics
            updatedPlayer.setFirstPlaces(Integer.parseInt(inputMap.get("firsts")));
            updatedPlayer.setSecondPlaces(Integer.parseInt(inputMap.get("seconds")));
            updatedPlayer.setThirdPlaces(Integer.parseInt(inputMap.get("thirds")));
            updatedPlayer.setFourthPlaces(Integer.parseInt(inputMap.get("fourths")));
            updatedPlayer.setLifetimeTokens(Integer.parseInt(inputMap.get("lifetime_tokens")));
            updatedPlayer.setFlushes(Integer.parseInt(inputMap.get("flushes")));
            updatedPlayer.setQuads(Integer.parseInt(inputMap.get("quads")));
            updatedPlayer.setFullHouses(Integer.parseInt(inputMap.get("full_houses")));
            updatedPlayer.setTriples(Integer.parseInt(inputMap.get("triples")));
            updatedPlayer.setTwoPairs(Integer.parseInt(inputMap.get("two_pairs")));
            updatedPlayer.setOnePairs(Integer.parseInt(inputMap.get("one_pairs")));
            updatedPlayer.setHighCards(Integer.parseInt(inputMap.get("high_card")));
            updatedPlayer.setCardsChanged(Integer.parseInt(inputMap.get("cards_changed")));
            updatedPlayer.setLifetimeRoundsWon(Integer.parseInt(inputMap.get("lifetime_rounds_won")));
            updatedPlayer.setLifetimeTotalBet(Integer.parseInt(inputMap.get("lifetime_total_bet")));

            //update everything
            playerdao.updateAttributes(updatedPlayer);
            System.out.println(updatedPlayer);
            connection.close();
            return updatedPlayer;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedPlayer;
    }

    // UPDATE Operation - Update current game details. We're never really going to
    // need this, but
    @PutMapping("/updateGame/{g_id}")
    public Game updateByGID(@PathVariable Long g_id, @RequestBody String json) throws JsonProcessingException {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
        System.out.println(g_id);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(hostname,
                "picturepoker", "postgres", "password");
        Game updatedGame = new Game();
        try {
            Connection connection = dcm.getConnection();
            GameDAO gamedao = new GameDAO(connection);
            updatedGame = gamedao.findById(g_id);

            // update all the things that we may want to update (no changing ID or any
            // temporary stuff
            updatedGame.setCurRound(Integer.parseInt(inputMap.get("cur_round")));
            updatedGame.setNumRounds(Integer.parseInt(inputMap.get("num_rounds")));
            updatedGame.setActivePlayers(Integer.parseInt(inputMap.get("active_players")));
            updatedGame.setBuyIn(Integer.parseInt(inputMap.get("buy_in")));
            updatedGame.setPotQuantity(Integer.parseInt(inputMap.get("pot_quant")));
            updatedGame.setDifficulty(Integer.parseInt(inputMap.get("difficulty")));

            // and update it in the database.
            updatedGame = gamedao.update_all(updatedGame);
            System.out.println(updatedGame);
            connection.close();
            return updatedGame;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updatedGame;
    }

    //UPDATE Operation - Raise player bet
    @PutMapping("/raise/{p_id}")
    public Player raiseBet(@PathVariable long p_id){
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(hostname,
                "picturepoker", "postgres", "password");
        Player player = new Player();
        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);

            player = playerDAO.findById(p_id);
            if(player.getFinishedRound() > 0){
                System.out.println("Could not raise: Finished round.");
                return player;
            }
            if(player.raise() < 0){
                System.out.println("Could not raise: Not enough tokens.");
                return player;
            }
            System.out.println("Player bet is now :" + player.getBet());
            playerDAO.update_int("bet", player.getBet(), player);
            System.out.println(player);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    //UPDATE Operation - Toggle whether to change out card
    @PutMapping("/changeCard/{p_id}/{pos}")
    public Player toggleToChange(@PathVariable long p_id, @PathVariable int pos){
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(hostname,
                "picturepoker", "postgres", "password");
        Player player = new Player();
        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);

            player = playerDAO.findById(p_id);
            player = playerDAO.getHand(player);
            if(player.getFinishedRound() > 0){
                System.out.println("Could not toggle Card: Finished round.");
            }
            if(pos < 0 || pos > 4){
                System.out.println("Could not toggle Card: Hand position out of bounds.");
                return player;
            }
            Card[] hand = player.getHand();
            hand[pos].setToChange(!hand[pos].getToChange());
            playerDAO.updateHand(player);
            System.out.println(player);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    @PutMapping("/finishRound/{p_id}")
    public Player finishRound(@PathVariable long p_id){
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(hostname,
                "picturepoker", "postgres", "password");
        Player player = new Player();
        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);

            player = playerDAO.findById(p_id);
            player = playerDAO.getHand(player);
            if(player.getFinishedRound() > 0){
                System.out.println("Could not finish round: It is not your turn.");
                return player;
            }
            player.setFinishedRound(1);
            playerDAO.update_int("finished_round", 1, player);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    //DELETE Operation - Delete a player
    @DeleteMapping("/deletePlayer/{p_id}")
    public Player deleteByPID(@PathVariable long p_id) {
        System.out.println(p_id);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(hostname,
                "picturepoker", "postgres", "password");
        Player player = new Player();
        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);
            player = playerDAO.deletePlayer(p_id);

            System.out.println(player);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    // DELETE OPERATION - Delete a game
    @DeleteMapping("/deleteGame/{g_id}")
    public Game deleteByGID(@PathVariable long g_id) {
        System.out.println(g_id);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(hostname,
                "picturepoker", "postgres", "password");
        Game game = new Game();
        try {
            Connection connection = dcm.getConnection();
            GameDAO gamedao = new GameDAO(connection);
            game = gamedao.deleteGame(g_id);

            System.out.println(game);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return game;
    }

    //DELETE OPERATION - Remove a player from a game
    @DeleteMapping("/leaveCurrentGame/{p_id}")
    public Game leaveGame(@PathVariable long p_id) {
        System.out.println(p_id);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(hostname,
                "picturepoker", "postgres", "password");
        Game game = new Game();
        try {
            Connection connection = dcm.getConnection();
            GameDAO gameDAO = new GameDAO(connection);
            PlayerDAO playerDAO = new PlayerDAO(connection);
            Player p = playerDAO.findById(p_id);
            long gid = playerDAO.getCurrentGame(p);
            game = gameDAO.findById(gid);
            game = gameDAO.removePlayerFromGame(game, p_id);
            // Clean up once all players leave.
            if(game.getActivePlayers() == 0){
                game = gameDAO.deleteGame(game.getID());
            }
            System.out.println(game);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return game;
    }

    //Actually play the game
    @GetMapping("/playGame/{g_id}")
    public Game playGame(@PathVariable long g_id) {
        System.out.println(g_id);
        Game game = new Game();
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(hostname,
                "picturepoker", "postgres", "password");

        try {
            Connection connection = dcm.getConnection();
            GameDAO gamedao = new GameDAO(connection);
            PlayerDAO playerdao = new PlayerDAO(connection);

            // pseudo lobby system - just reject starting when the game is not full.
            game = gamedao.findById(g_id);
            int curPlayers = game.getActivePlayers();
            if (curPlayers < 4) {
                System.out.println("The game is not full yet! We need " + (4 - curPlayers) + " more players. ");
                return game;
            }

            GamePlay gamePlay = new GamePlay();
            game = gamePlay.gameSeq(g_id, gamedao, playerdao);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(game);
        return game;
    }

    @PutMapping("/joinGame/{g_id}/{p_id}")
    public Game joinGame(@PathVariable long g_id, @PathVariable long p_id) {
        System.out.println("GameID: " + g_id + "PlayerID: " + p_id);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(hostname,
                "picturepoker", "postgres", "password");
        Game game = new Game();
        try {
            //Makes a connection, gets a player and the game ID, and tries adding the player to the game.
            Connection connection = dcm.getConnection();
            GameDAO gamedao = new GameDAO(connection);
            PlayerDAO playerdao = new PlayerDAO(connection);
            Player player = playerdao.findById(p_id);
            game = gamedao.findById(g_id);

            //stick the player into the game (or at least, it tries)
            game = gamedao.joinGame(game, player);
            gamedao.update_all(game);
            connection.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return game;
    }

    public static void main(String[] args) {
        SpringApplication.run(PicturePokerGame.class, args);
    }
}
