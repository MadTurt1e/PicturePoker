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
    private static final String CREATE_NEW_PLAYER = "INSERT INTO player (p_name, password) VALUES (?, ?)"
    private static final String UPDATE_PLAYER_BY_ID = "UPDATE player SET ? = ? WHERE p_id = ?";

    public PlayerDAO(Connection connection){
        super(connection);
    }

    @Override
    public Player findById(long id){
        Player player = new Player();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_PLAYER_BY_ID);){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                player.setPID(rs.getLong("p_id"));
                player.setPName(rs.getString("p_name"));
                player.setPasscode(rs.getString("passcode"));
                player.setDollars(rs.getLong("dollars"));

                player.setFirstPlaces(rs.getLong("first_places"));
                player.setSecondPlaces(rs.getLong("second_places"));
                player.setThirdPlaces(rs.getLong("third_places"));
                player.setFourthPlaces(rs.getLong("fourth_places"));
                player.setLifetimeTokens(rs.getLong("lifetime_tokens"));

                player.setTokens(rs.getLong("tokens"));
                player.setBet(rs.getLong("bet"));
                player.setRounds_won(rs.getLong("rounds_won"));
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
            return this.findById(dto.getID());
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
            statement.setLong(3, Player.getID());
            statement.execute();
            return this.findById(dto);
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Player update_string(String attribute, String data, Player dto){
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_PLAYER_BY_ID)){
            statement.setString(1, attribute);
            statement.setString(2, data);
            statement.setLong(3, Player.getID());
            statement.execute();
            return this.findById(dto);
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}