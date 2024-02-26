package game.jdbc.picturepoker;

import com.fasterxml.jackson.core.JsonProcessingException;
import jdk.internal.misc.InnocuousThread;
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
    @GetMapping("/helloClass")
    public String helloClass() {
        System.out.println("HELLO VAIBHAV");
        return "Hello Vaibhav";
    }

    @GetMapping("/getByPlayerName/{playerName}")
    public Player getByPlayerName(@PathVariable("playerName") String playerName) {
        System.out.println(playerName);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "picturepoker", "postgres", "password");
        Player player = new Player();
        player.setPlayerName(playerName);
        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);

            player = playerDAO.findById(player.getID());
            System.out.println(player);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    @PostMapping("/createNewPlayer")
    public Player createNewPlayer(@RequestBody String json) throws JsonProcessingException {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "picturepoker", "postgres", "password");
        Player player = new Player();
        try {
            Connection connection = dcm.getConnection();
            PlayerDAO playerDAO = new PlayerDAO(connection);
            // essentially, a (new) player consists of a player name and a password, and that is it. 
            player.setPlayerName(inputMap.get("playerName"));
            player.setPasscode(inputMap.get("password"));
            player = playerDAO.create(player);
            System.out.println(player);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    @PostMapping("/startNewGame")
    public Game createNewGame(@RequestBody String json) throws JsonProcessingException{
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "picturepoker", "postgres", "password");
        Game game = new Game();
        try {
            Connection connection = dcm.getConnection();
            GameDAO gamedao = new GameDAO(connection);
            PlayerDAO playerDAO = new PlayerDAO(connection);
            // A new game consists of 4 players, a chosen number of rounds, and a pot quantity
            game.setP1(playerDAO.findIDByName(inputMap.get("p1Name")));
            game.setP2(playerDAO.findIDByName(inputMap.get("p2Name")));
            game.setP3(playerDAO.findIDByName(inputMap.get("p3Name")));
            game.setP4(playerDAO.findIDByName(inputMap.get("p4Name")));

            game.setRounds(Integer.parseInt(inputMap.get("rounds")));
            game.setBet(Integer.parseInt(inputMap.get("bet")));
            game = gameDAO.create(game);
            //once all the necessary values are created, we can make the game. 

            System.out.println(game);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return game;
    }
    public static void main (String[]args){
        SpringApplication.run(PicturePokerGame.class, args);
    }
}
