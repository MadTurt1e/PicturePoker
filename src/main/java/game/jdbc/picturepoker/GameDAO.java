package game.jdbc.picturepoker;

import game.jdbc.picturepoker.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDAO extends DataAccessObject<Game>{
    private static final String GET_GAME_BY_ID = "SELECT "
    + "p1_id, p2_id, p3_id, p4_id, cur_round, num_rounds, active_players, pot_quantity, difficulty FROM game WHERE g_id = ?";
    private static final String CREATE_NEW_GAME = "INSERT INTO game (p1_id, p2_id, p3_id, p4_id, "
    + "num_rounds, active_players, pot_quantity, difficulty) VALUES (?, ?, ?, ?, ?, 4, ?, 0)";
    private static final String UPDATE_GAME_BY_ID = "UPDATE game SET ? = ? WHERE game.g_id = ?";

    //I am not too sure what this is, but it is important.
    public GameDAO(Connection connection) {
        super(connection);
    }

    public Game findById(long id){
        Game game = new Game();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_GAME_BY_ID);){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                game.setID(id);
                game.setP1(rs.getLong("p1_id"));
                game.setP2(rs.getLong("p2_id"));
                game.setP3(rs.getLong("p3_id"));
                game.setP4(rs.getLong("p4_id"));
                game.setCurRound(rs.getInt("cur_round"));
                game.setNumRounds(rs.getInt("num_rounds"));
                game.setActivePlayers(rs.getInt("active_players"));
                game.setPotQuantity(rs.getInt("pot_quantity"));
                game.setDifficulty(rs.getInt("difficulty"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return game;
    }

    @Override
    public Game update_long(String attribute, long value, Game dto){
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_GAME_BY_ID)){
            statement.setString(1, attribute);
            statement.setLong(2, value);
            statement.setLong(3, dto.getID());
            statement.execute();
            return this.findById(dto.getID());
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Game create(Game dto){
        try(PreparedStatement statement = this.connection.prepareStatement(CREATE_NEW_GAME);){
            statement.setLong(1, dto.getP1());
            statement.setLong(2, dto.getP2());
            statement.setLong(3, dto.getP3());
            statement.setLong(4, dto.getP4());
            statement.setLong(5, dto.getNumRounds());
            statement.setLong(6, dto.getPotQuantity());
            statement.execute();
            return dto; //we just care about the game here - no need for getting and setting something we already have.
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Game update_string(String attribute, String data, Game dto){
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_GAME_BY_ID)){
            statement.setString(1, attribute);
            statement.setString(2, data);
            statement.setLong(3, dto.getID());
            statement.execute();
            return dto; // Again, everything is done already. No need to mess with IDs anymore.
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}