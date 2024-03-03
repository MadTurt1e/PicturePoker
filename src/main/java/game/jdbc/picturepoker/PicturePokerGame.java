package game.jdbc.picturepoker;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import com.fasterxml.jackson.core.JsonProcessingException;
// import jdk.internal.misc.InnocuousThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootApplication
@RestController
public class PicturePokerGame {
    //Test operation - is this thing on?
    @GetMapping("/helloWorld")
    public String helloWorld() {
        System.out.println("Hello, World!");
        return ("HELLO WORLD");
    }

    // CREATE Operation: Make new player
    @PostMapping("/createNewPlayer")
    public Player createNewPlayer(@RequestBody String json) throws JsonProcessingException {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "picturepoker", "postgres", "password");
        Player player = new Player();
        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerdao = new PlayerDAO(connection);
            // essentially, a (new) player consists of a player name and a password, and that is it.
            player.setPlayerName(inputMap.get("playerName"));
            player.setPasscode(inputMap.get("password"));
            player = playerdao.create(player);
            System.out.println(player);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    //CREATE Operation: creates new game
    @PostMapping("/createNewGame")
    public Game createNewGame(@RequestBody String json) throws JsonProcessingException {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "picturepoker", "postgres", "password");
        Game game = new Game();
        try {
            Connection connection = dcm.getConnection();
            GameDAO gamedao = new GameDAO(connection);
            PlayerDAO playerDAO = new PlayerDAO(connection);

            // A new game consists a chosen number of rounds, a pot quantity, buy in value (token stakes, and difficulty
            game.setNumRounds(Integer.parseInt(inputMap.get("rounds")));
            game.setPotQuantity(Integer.parseInt(inputMap.get("potQuantity")));
            game.setBuyIn(Integer.parseInt(inputMap.get("buyIn")));
            game.setDifficulty(Integer.parseInt(inputMap.get("difficulty")));
            game = gamedao.create(game);
            //once all the necessary values are created, we can make the game.

            System.out.println(game);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return game;
    }

    //READ Operation - Read a player's full details
    @GetMapping("/getByPlayerName/{playerName}")
    public Player getByPlayerName(@PathVariable("playerName") String playerName) {
        System.out.println(playerName);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "picturepoker", "postgres", "password");
        Player player = new Player();
        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);

            player = playerDAO.findByName(playerName);
            System.out.println(player);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    //READ Operation - Read a game's full details
    @GetMapping("/getByGameID/{g_id}}")
    public Game getByGameID(@PathVariable("g_id") String g_idStr) {
        System.out.println(g_idStr);
        long g_id = Long.parseLong(g_idStr);

        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "picturepoker", "postgres", "password");
        Game game = new Game();
        try {
            Connection connection = dcm.getConnection();
            GameDAO gamedao = new GameDAO(connection);
            game = gamedao.findById(g_id);
            //we need a separate function to get pids because the database is structured poorly
            game.setPlayers(gamedao.getPIDsByGame(game));

            System.out.println(game);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return game;
    }

    //UPDATE Operation - Update a player
    @PutMapping("/updatePlayer/{p_id}")
    public Player updateByPID(@PathVariable("p_id") String p_idStr) {
        System.out.println(p_idStr);

        //TODO: Finish this
    }

    // UPDATE Operation - Update current game details
    @PutMapping("/updateGame/{g_id}")
    public Game updateByGID(@PathVariable("g_id") String g_idStr) {
        System.out.println(g_idStr);
        //TODO: Finish this
    }

    //DELETE Operation - Delete a player
    @DeleteMapping("/deletePlayer/{p_id}")
    public Player deleteByPID(@PathVariable("p_id") String p_idStr) {
        System.out.println(p_idStr);
        long p_id = Long.parseLong(p_idStr);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "picturepoker", "postgres", "password");
        Player player = new Player();
        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);
            player = playerDAO.deletePlayer(p_id);

            System.out.println(player);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    //DELETE OPERATION - Delete a game
    @DeleteMapping("/deleteGame/{g_id}")
    public Game deleteByGID(@PathVariable("g_id") String g_idStr) {
        System.out.println(g_idStr);
        long g_id = Long.parseLong(g_idStr);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "picturepoker", "postgres", "password");
        Game game = new Game();
        try {
            Connection connection = dcm.getConnection();
            GameDAO gamedao = new GameDAO(connection);
            game = gamedao.deleteGame(g_id);

            System.out.println(game);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return game;
    }

    //Actually play the game
    @GetMapping("/playGame/{gameID}")
    public Game playGame(@PathVariable("gameID") String g_idStr) {
        System.out.println(g_idStr);
        long g_id = Long.parseLong(g_idStr);
        Game game = new Game();
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "picturepoker", "postgres", "password");

        try {
            Connection connection = dcm.getConnection();
            GameDAO gamedao = new GameDAO(connection);
            PlayerDAO playerdao = new PlayerDAO(connection);

            //pseudo lobby system - just reject starting when the game is not full.
            int curPlayers = gamedao.findById(g_id).getActivePlayers();
            if (curPlayers < 4){
                System.out.println("The game is not full yet! We need " + (4 - curPlayers) + " more players. ");
                return game;
            }

            GamePlay gamePlay = new GamePlay();
            game = gamePlay.gameSeq(g_id, gamedao, playerdao);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(game);
        return game;
    }

    @PutMapping("/joinGame/{gameID}/{playerName}")
    public Game joinGame(@PathVariable("gameID") String g_IDStr, @PathVariable("playerName") String p_Name) {
        System.out.println("GameID: " + g_IDStr + "Player Name: " + p_Name);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "picturepoker", "postgres", "password");
        Game game = new Game();
        try {
            //Makes a connection, gets a player and the game ID, and tries adding the player to the game.
            Connection connection = dcm.getConnection();
            GameDAO gamedao = new GameDAO(connection);
            PlayerDAO playerdao = new PlayerDAO(connection);
            Player player = playerdao.findByName(p_Name);

            //stick the player into the game (or at least, it tries)
            game = gamedao.joinGame(Long.parseLong(g_IDStr), player);
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return game;
    }

    public static void main(String[] args) {
        SpringApplication.run(PicturePokerGame.class, args);
    }
}
