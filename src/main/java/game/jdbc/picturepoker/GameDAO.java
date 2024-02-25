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
    + "num_rounds, active_players, pot_quantity, difficulty) VALUES (?, ?, ?, ?, ?, 4, ?, 0)"
    private static final String UPDATE_GAME_BY_ID = "UPDATE game SET ? = ? WHERE p_id = ?";

    @Override
    public Game findById(long id){
        Game game = new Game();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_GAME_BY_ID);){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                game.setPID(rs.getLong("g_id"));
                game.setP1(rs.getLong("p1_id"));
                game.setP2(rs.getLong("p2_id"));
                game.setP3(rs.getLong("p3_id"));
                game.setP4(rs.getLong("p4_id"));
                game.setCurRound(rs.getLong("cur_round"));
                game.setNumRounds(rs.getLong("num_rounds"));
                game.setActivePlayers(rs.getLong("active_players"));
                game.setPotQuantity(rs.getLong("pot_quantity"));
                game.setDifficulty(rs.getLong("difficulty"));
                game.setWinner(rs.getString("lifetime_tokens"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return player;
    }

    @Override
    public Player update_long(String attribute, long value, Game dto){
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_GAME_BY_ID)){
            statement.setString(1, attribute);
            statement.setLong(2, value);
            statement.setLong(3, Game.getID());
            statement.execute();
            return this.findById(dto);
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Game create(Game dto){
        try(PreparedStatement statement = this.connection.prepareStatement(CREATE_NEW_PLAYER);){
            statement.setString(1, dto.getP1());
            statement.setString(2, dto.getP2());
            statement.setString(3, dto.getP3());
            statement.setString(4, dto.getP4());
            statement.setLong(5, dto.getNumRounds());
            statement.setLong(6, dto.getPotQuantity());
            statement.execute();
            return this.findById(dto.getID());
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Player update_string(String attribute, String data, Game dto){
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_GAME_BY_ID)){
            statement.setString(1, attribute);
            statement.setString(2, data);
            statement.setLong(3, Game.getID());
            statement.execute();
            return this.findById(dto);
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}