package game.jdbc.picturepoker;

import game.jdbc.picturepoker.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDAO extends DataAccessObject<Game>{
    private static final String GET_GAME_BY_ID = "SELECT "
    + "g_id, cur_round, num_rounds, active_player, buy_in, pot_quantity, difficulty FROM game WHERE g_id = ?";
    private static final String GET_PLAYERS_BY_GAME = "SELECT p_id FROM player_in_game WHERE g_id = ?";
    private static final String CREATE_NEW_GAME = "INSERT INTO game ("
    + "num_rounds, active_player, buy_in, pot_quantity, difficulty) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_GAME_BY_ID = "UPDATE game SET ? = ? WHERE g_id = ?";

    private static final String MASS_UPDATE_GAME_BY_ID = "UPDATE game SET cur_round = ?, "
            + "num_rounds = ?, active_player = ?, buy_in = ?, pot_quantity = ?, difficulty = ? WHERE g_id = ?";

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
                game.setID(rs.getLong("g_id"));
                game.setCurRound(rs.getInt("cur_round"));
                game.setNumRounds(rs.getInt("num_rounds"));
                game.setActivePlayer(rs.getInt("active_player"));
                game.setBuyIn(rs.getInt("buy_in"));
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

    public Player[] getPlayersByGame(Game game){
        long gameId = game.getID();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_PLAYERS_BY_GAME);){
            statement.setLong(1, gameId);
            ResultSet rs = statement.executeQuery();
            Player[] result = new Player[4];
            while(rs.next()){
                game.setID(rs.getLong("g_id"));

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
            statement.setLong(1, dto.getNumRounds());
            statement.setLong(2, dto.getActivePlayer());
            statement.setLong(3, dto.getBuyIn());
            statement.setLong(4, dto.getPotQuantity());
            statement.setLong(5, dto.getDifficulty());
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

    // Because some people are too lazy to use the nice framework I provided to update a few values
    public Game update_all(Game dto){
        try(PreparedStatement statement = this.connection.prepareStatement(MASS_UPDATE_GAME_BY_ID)){
            statement.setLong(1, dto.getCurRound());
            statement.setLong(2, dto.getNumRounds());
            statement.setLong(3, dto.getActivePlayer());
            statement.setLong(4, dto.getBuyIn());
            statement.setLong(5, dto.getPotQuantity());
            statement.setLong(6, dto.getDifficulty());
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