package game.jdbc.picturepoker;

import game.jdbc.picturepoker.util.DataAccessObject;

import java.sql.*;
import java.util.ArrayList;

public class GameDAO extends DataAccessObject<Game> {
    private static final String GET_GAME_BY_GID = "SELECT g_id, cur_round, num_rounds, active_players, buy_in, pot_quantity, difficulty, players_finished FROM game WHERE g_id = ?";
    private static final String GET_ALL_GAMES = "SELECT g_id, cur_round, num_rounds, active_players, buy_in, pot_quantity, difficulty, players_finished FROM game";
    private static final String GET_CARD = "SELECT suit, to_change FROM dealer_card WHERE g_id = ? AND hand_pos = ?";
    private static final String GET_PIDS_BY_GID = "SELECT p_id FROM player_in_game WHERE g_id = ?";
    private static final String GET_GID_BY_PID = "SELECT g_id FROM player_in_game WHERE p_id = ?";
    private static final String CREATE_NEW_GAME = "INSERT INTO game (num_rounds, active_players, buy_in, pot_quantity, difficulty) VALUES (?, ?, ?, ?, ?) RETURNING g_id";
    private static final String CREATE_NEW_CARD = "INSERT INTO dealer_card (g_id, hand_pos, suit) VALUES (?, ?, ?)";

    private static final String UPDATE_GAME_BY_ID_START = "UPDATE game SET ";
    private static final String UPDATE_GAME_BY_ID_END = " = ? WHERE g_id = ?";
    private static final String MASS_UPDATE_GAME_BY_ID =
            "UPDATE game SET cur_round = ?, num_rounds = ?, active_players = ?, buy_in = ?, pot_quantity = ?,"+
                    " difficulty = ?, players_finished = ? WHERE g_id = ?";
    private static final String UPDATE_CARD = "UPDATE dealer_card SET suit = ? WHERE g_id = ? AND hand_pos = ?";
    //insert player
    private static final String ADD_PLAYER_TO_GAME = "INSERT INTO player_in_game (p_id, g_id) VALUES (?, ?)";
    private static final String REMOVE_PLAYER_FROM_GAME = "DELETE FROM player_in_game WHERE p_id = ?";
    private static final String DELETE_GAME = "DELETE FROM game WHERE g_id = ?";


    //I am not too sure what this is, but it is important.
    public GameDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Game findById(long id) {
        Game game = new Game();
        try (PreparedStatement statement = this.connection.prepareStatement(GET_GAME_BY_GID);) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                game.setID(rs.getLong("g_id"));
                game.setCurRound(rs.getInt("cur_round"));
                game.setNumRounds(rs.getInt("num_rounds"));
                game.setActivePlayers(rs.getInt("active_players"));
                game.setBuyIn(rs.getInt("buy_in"));
                game.setPotQuantity(rs.getInt("pot_quantity"));
                game.setDifficulty(rs.getInt("difficulty"));
                game.setPlayersFinished(rs.getInt("players_finished"));
                game.setPlayers(getPIDsByGame(game));
                game = getHand(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return game;
    }

    public ArrayList<Game> findAllGames() {
        ArrayList<Game> allGames = new ArrayList<Game>();
        try (PreparedStatement statement = this.connection.prepareStatement(GET_ALL_GAMES);) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Game game = new Game();
                game.setID(rs.getLong("g_id"));
                game.setCurRound(rs.getInt("cur_round"));
                game.setNumRounds(rs.getInt("num_rounds"));
                game.setActivePlayers(rs.getInt("active_players"));
                game.setBuyIn(rs.getInt("active_players"));
                game.setPotQuantity(rs.getInt("pot_quantity"));
                game.setDifficulty(rs.getInt("difficulty"));
                game.setPlayersFinished(rs.getInt("players_finished"));
                game.setPlayers(getPIDsByGame(game));
                game = getHand(game);

                allGames.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return allGames;
    }

    public long[] getPIDsByGame(Game dto) {
        try (PreparedStatement statement = this.connection.prepareStatement(GET_PIDS_BY_GID);) {
            statement.setLong(1, dto.getID());
            long[] result = new long[4];
            int i = 0;
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                long cur_pid = rs.getLong("p_id");
                result[i++] = cur_pid;
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Game getHand(Game dto){
        Card[] result = new Card[5];
        for(int i = 0; i < 5; i++){
            try(PreparedStatement statement = this.connection.prepareStatement(GET_CARD)){
                statement.setLong(1, dto.getID());
                statement.setInt(2, i);
                ResultSet rs = statement.executeQuery();
                while(rs.next()){
                    result[i] = new Card(Card.Suit.valueOf(rs.getString("suit")));
                    result[i].setToChange(rs.getBoolean("to_change"));
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        dto.setHand(result);
        return dto;
    }

    @Override
    public Game update_long(String attribute, long value, Game dto) {
        try (PreparedStatement statement = this.connection.prepareStatement(UPDATE_GAME_BY_ID_START + attribute + UPDATE_GAME_BY_ID_END)) {
            statement.setLong(1, value);
            statement.setLong(2, dto.getID());
            statement.execute();
            return this.findById(dto.getID());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Game update_int(String attribute, int value, Game dto) {
        try (PreparedStatement statement = this.connection.prepareStatement(UPDATE_GAME_BY_ID_START + attribute + UPDATE_GAME_BY_ID_END)) {
            statement.setInt(1, value);
            statement.setLong(2, dto.getID());
            statement.execute();
            return this.findById(dto.getID());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Game create(Game dto) {
        try (PreparedStatement statement = this.connection.prepareStatement(CREATE_NEW_GAME);) {
            statement.setLong(1, dto.getNumRounds());
            statement.setLong(2, dto.getActivePlayers());
            statement.setLong(3, dto.getBuyIn());
            statement.setLong(4, dto.getPotQuantity());
            statement.setLong(5, dto.getDifficulty());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                dto.setID(rs.getLong("g_id"));
            }
            return dto; //we just care about the game here - no need for getting and setting something we already have.
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Game joinGame(Game dto, Player player) {
        //this is a way to check if the game id is valid, also lets us return the game in case we want it.

        if (dto.getActivePlayers() >= 4){
            System.out.println("joinGame: The game is already full." );
            return dto;
        }
        long curGid = 0;
        //run a check to see if the original game is still ongoing
        try (PreparedStatement statement = this.connection.prepareStatement(GET_GID_BY_PID);) {
            statement.setLong(1, player.getID());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                curGid = rs.getLong("g_id");
            }

            Game curGame = findById(curGid);
            System.out.println(curGame);
            if (curGame.getCurRound() < curGame.getNumRounds()){
                System.out.println("joinGame: The player is still in an active game.");
                return dto;
            }
            if (dto.getBuyIn() > player.getDollars()){
                System.out.println("joinGame: The player is too broke to join. Required amount to play: " + dto.getBuyIn() + ", current dollar count: " + player.getDollars());
                return dto;
            }

        } catch (SQLException e) {
            System.out.println("joinGame: It doesn't look like the player is in any game.");
        }

        //update that one table for each player.
        try (PreparedStatement statement2 = this.connection.prepareStatement(ADD_PLAYER_TO_GAME);) {
            statement2.setLong(1, player.getID());
            statement2.setLong(2, dto.getID());
            statement2.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        //if we get here, the player has been inserted so we can update the game table accordingly.
        return update_int("active_players", dto.getActivePlayers() + 1, dto);
    }

    public Game createHand(Game dto) {
        Card newHand[] = new Card[5];
        for (int i = 0; i < 5; i++) {
            newHand[i] = new Card();
            try (PreparedStatement statement = this.connection.prepareStatement(CREATE_NEW_CARD)) {
                statement.setLong(1, dto.getID());
                statement.setInt(2, i);
                statement.setString(3, newHand[i].toString());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        dto.setHand(newHand);
        return dto;
    }

    public Game update_string(String attribute, String data, Game dto) {
        try (PreparedStatement statement = this.connection.prepareStatement(UPDATE_GAME_BY_ID_START + attribute + UPDATE_GAME_BY_ID_END)) {
            statement.setString(1, attribute);
            statement.setString(2, data);
            statement.setLong(3, dto.getID());
            statement.execute();
            return dto; // Again, everything is done already. No need to mess with IDs anymore.
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Game update_all(Game dto) {
        try (PreparedStatement statement = this.connection.prepareStatement(MASS_UPDATE_GAME_BY_ID)) {
            statement.setInt(1, dto.getCurRound());
            statement.setInt(2, dto.getNumRounds());
            statement.setInt(3, dto.getActivePlayers());
            statement.setInt(4, dto.getBuyIn());
            statement.setInt(5, dto.getPotQuantity());
            statement.setInt(6, dto.getDifficulty());
            statement.setInt(7, dto.getPlayersFinished());
            statement.setLong(8, dto.getID());
            statement.execute();
            return dto;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Game updateHand(Game dto) {
        for (int i = 0; i < 5; i++) {
            try (PreparedStatement statement = this.connection.prepareStatement(UPDATE_CARD);) {
                statement.setString(1, dto.getHand()[i].toString());
                statement.setLong(2, dto.getID());
                statement.setInt(3, i);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return dto;
    }

    //one part of CRUD - honestly this is not necessary, but it must be done.
    public Game removePlayerFromGame(Game dto, long p_id) {
        if(dto.getActivePlayers() >= 4 && dto.getCurRound() <= dto.getNumRounds()){
            System.out.println("removePlayerFromGame: Cannot remove players from game in progress!");
            return dto;
        }
        long[] playersInGame = dto.getPlayers();
        boolean present = false;
        for(int i = 0; i < playersInGame.length; i++){
            if(playersInGame[i] == p_id){present = true;}
        }
        if(!present){
            System.out.println("removePlayerFromGame: Requested player is not in game!");
            return dto;
        }
        try (PreparedStatement statement = this.connection.prepareStatement(REMOVE_PLAYER_FROM_GAME)) {
            statement.setLong(1, p_id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return update_int("active_players", dto.getActivePlayers() - 1, dto);
    }

    //one part of CRUD - honestly this is not necessary, but it must be done.
    public Game deleteGame(long g_id) {
        Game game = findById(g_id);
        try (PreparedStatement statement = this.connection.prepareStatement(DELETE_GAME)) {
            //RIP game
            statement.setLong(1, g_id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return game;
    }
}