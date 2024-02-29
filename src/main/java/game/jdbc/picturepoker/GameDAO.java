package game.jdbc.picturepoker;

import game.jdbc.picturepoker.util.DataAccessObject;

import java.sql.*;
import java.util.ArrayList;

public class GameDAO extends DataAccessObject<Game>{
    private static final String GET_GAME_BY_GID = "SELECT cur_round, num_rounds, active_players, buy_in, pot_quantity, difficulty FROM game WHERE g_id = ?";
    private static final String GET_PIDS_BY_GID = "SELECT p_id FROM players_in_game WHERE g_id = ?";
    private static final String CREATE_NEW_GAME = "INSERT INTO game (num_rounds, active_players, buy_in, pot_quantity, difficulty) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_GAME_BY_ID = "UPDATE game SET ? = ? WHERE p_id = ?";
    private static final String MASS_UPDATE_GAME_BY_ID =
            "UPDATE game SET cur_round = ?, num_rounds = ?, active_players = ?, buy_in = ?, pot_quantity = ?, difficulty = ? WHERE g_id = ?";

    //I am not too sure what this is, but it is important.
    public GameDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Game findById(long id){
        Game game = new Game();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_GAME_BY_GID);){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                game.setCurRound(rs.getInt("cur_round"));
                game.setNumRounds(rs.getInt("num_rounds"));
                game.setActivePlayers(rs.getInt("active_players"));
                game.setBuyIn(rs.getInt("active_players"));
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

    public ArrayList<Long> getPIDsByGame(Game dto){
        try(PreparedStatement statement = this.connection.prepareStatement(GET_PIDS_BY_GID);){
            statement.setLong(1, dto.getID());
            ArrayList<Long> result = new ArrayList<Long>();
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Long cur_pid = rs.getLong("p_id");
                result.add(cur_pid);
            }
            return result;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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

    // This is the second time I had to program this. Richard, please don't go around reverting my changes willy nilly.
    public Game update_all(Game dto){
        try(PreparedStatement statement = this.connection.prepareStatement(MASS_UPDATE_GAME_BY_ID)){
            statement.setInt(1, dto.getCurRound());
            statement.setInt(2, dto.getNumRounds());
            statement.setInt(3, dto.getActivePlayers());
            statement.setInt(4, dto.getBuyIn());
            statement.setInt(5, dto.getPotQuantity());
            statement.setInt(6, dto.getDifficulty());
            statement.setLong(7, dto.getID());
            statement.execute();
            return dto;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}