package game.jdbc.picturepoker;

import game.jdbc.picturepoker.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDAO extends DataAccessObject<Player>{
    private static final String GET_PLAYER_BY_ID = "SELECT "
    + "p_id, p_name, passcode, dollars, first_places, second_places, third_places, "
    + "fourth_places, lifetime_tokens, tokens, bet, rounds_won FROM player WHERE p_id = ?";
    private static final String CREATE_NEW_PLAYER = "INSERT INTO player (p_name, password) VALUES (?, ?)";
    private static final String UPDATE_PLAYER_BY_ID = "UPDATE player SET ? = ? WHERE p_id = ?";
    private static final String GET_ID_BY_NAME = "SELECT p_id FROM player WHERE p_name = ?";
    public PlayerDAO(Connection connection){
        super(connection);
    }

    public Player findById(long id){
        Player player = new Player();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_PLAYER_BY_ID);){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                player.setID(rs.getLong("p_id"));
                player.setPlayerName(rs.getString("p_name"));
                player.setPasscode(rs.getString("passcode"));
                player.setDollars(rs.getInt("dollars"));

                player.setFirstPlaces(rs.getInt("first_places"));
                player.setSecondPlaces(rs.getInt("second_places"));
                player.setThirdPlaces(rs.getInt("third_places"));
                player.setFourthPlaces(rs.getInt("fourth_places"));
                player.setLifetimeTokens(rs.getInt("lifetime_tokens"));

                player.setTokens(rs.getInt("tokens"));
                player.setBet(rs.getInt("bet"));
                player.setRoundsWon(rs.getInt("rounds_won"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return player;
    }

    @Override
    public Player create(Player dto){
        try(PreparedStatement statement = this.connection.prepareStatement(CREATE_NEW_PLAYER);){
            statement.setString(1, dto.getPlayerName());
            statement.setString(2, dto.getPasscode());
            statement.execute();

            //this should never go wrong - at this point, the player either is there, or isn't.
            try(PreparedStatement getNewPlayer = this.connection.prepareStatement(GET_ID_BY_NAME);){
                // We make a new SQL statement, and we want to go from player name to id
                statement.setString(1, dto.getPlayerName());
                ResultSet rs = statement.executeQuery();

                // and this statement gets the player id and returns it.
                long newPlayerID = rs.getLong("p_id");

                // now we create the player that we're returning
                Player player = new Player();
                player.setPlayerName(dto.getPlayerName());
                player.setPasscode(dto.getPasscode());
                player.setID(newPlayerID);
                return player;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Player update_long(String attribute, long value, Player dto){
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_PLAYER_BY_ID)){
            statement.setString(1, attribute);
            statement.setLong(2, value);
            statement.setLong(3, dto.getID());
            statement.execute();
            return dto;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public Player update_string(String attribute, String data, Player dto){
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_PLAYER_BY_ID)){
            statement.setString(1, attribute);
            statement.setString(2, data);
            statement.setLong(3, dto.getID());
            statement.execute();
            return dto;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}