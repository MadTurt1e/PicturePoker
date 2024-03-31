package game.jdbc.picturepoker;

import game.jdbc.picturepoker.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlayerDAO extends DataAccessObject<Player>{
    private static final String GET_PLAYER_BY_ID = "SELECT "
            + "p_id, p_name, passcode, dollars, first_places, second_places, third_places, "
            + "fourth_places, lifetime_tokens, flushes, quads, full_houses, triples, two_pairs, "
            + "one_pairs, high_cards, cards_changed, tokens, bet, rounds_won FROM player WHERE p_id = ?";
    private static final String GET_ALL_PLAYERS = "SELECT "
            + "p_id, p_name, passcode, dollars, first_places, second_places, third_places, "
            + "fourth_places, lifetime_tokens, flushes, quads, full_houses, triples, two_pairs, "
            + "one_pairs, high_cards, cards_changed, tokens, bet, rounds_won FROM player";
    private static final String CREATE_NEW_PLAYER = "INSERT INTO player (p_name, passcode) VALUES (?, ?)";
    private static final String ADD_NEW_PLAYER_INTO_GAMES = "INSERT INTO player_in_game (p_id, g_id) VALUES (?, 0)";
    private static final String CREATE_NEW_CARD = "INSERT INTO player_card (p_id, hand_pos, suit) VALUES (?, ?, ?)";
    private static final String UPDATE_PLAYER_BY_ID_START = "UPDATE player SET ";
    private static final String UPDATE_PLAYER_BY_ID_END = " = ? WHERE p_id = ?";
    private static final String GET_ID_BY_NAME = "SELECT p_id FROM player WHERE p_name = ?";
    private static final String UPDATE_CARD = "UPDATE player_card SET suit = ? WHERE p_id = ? AND hand_pos = ?";
    private static final String DELETE_PLAYER = "DELETE FROM player WHERE p_id = ?";

    private static final String UPDATE_ALL =
            "UPDATE player SET p_name = ?, passcode = ?, dollars = ?,"+
                    " first_places = ?, second_places = ?, third_places = ?, fourth_places = ?, lifetime_tokens = ?,"
                    + "flushes = ?, quads = ?, full_houses = ?, triples = ?, two_pairs = ?, one_pairs = ?, "
                    + "high_cards = ?, cards_changed = ? WHERE p_id = ?";
    public PlayerDAO(Connection connection){
        super(connection);
    }

    public Player findById(long id){
        Player player = new Player();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_PLAYER_BY_ID)){
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

                player.setFlushes(rs.getInt("flushes"));
                player.setQuads(rs.getInt("quads"));
                player.setFullHouses(rs.getInt("full_houses"));
                player.setTriples(rs.getInt("triples"));
                player.setTwoPairs(rs.getInt("two_pairs"));
                player.setOnePairs(rs.getInt("one_pairs"));
                player.setHighCards(rs.getInt("high_cards"));
                player.setCardsChanged(rs.getInt("cards_changed"));

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

    public ArrayList<Player> findAllPlayers(){
        ArrayList<Player> allPlayers = new ArrayList<Player>();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ALL_PLAYERS)){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Player player = new Player();

                player.setID(rs.getLong("p_id"));
                player.setPlayerName(rs.getString("p_name"));
                player.setPasscode(rs.getString("passcode"));
                player.setDollars(rs.getInt("dollars"));

                player.setFirstPlaces(rs.getInt("first_places"));
                player.setSecondPlaces(rs.getInt("second_places"));
                player.setThirdPlaces(rs.getInt("third_places"));
                player.setFourthPlaces(rs.getInt("fourth_places"));
                player.setLifetimeTokens(rs.getInt("lifetime_tokens"));

                player.setFlushes(rs.getInt("flushes"));
                player.setQuads(rs.getInt("quads"));
                player.setFullHouses(rs.getInt("full_houses"));
                player.setTriples(rs.getInt("triples"));
                player.setTwoPairs(rs.getInt("two_pairs"));
                player.setOnePairs(rs.getInt("one_pairs"));
                player.setHighCards(rs.getInt("high_cards"));
                player.setCardsChanged(rs.getInt("cards_changed"));

                player.setTokens(rs.getInt("tokens"));
                player.setBet(rs.getInt("bet"));
                player.setRoundsWon(rs.getInt("rounds_won"));

                allPlayers.add(player);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return allPlayers;
    }

    public long findIDByName(String name){
        long foundID = 0;
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ID_BY_NAME)){
            // We make a new SQL statement, and we want to go from player name to id
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                // and this statement gets the player id and returns it
                foundID = rs.getLong("p_id");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // returned
        return foundID;
    }

    //Way to find the player by name, instead of by ID, which seems to be more "colloquial"
    public Player findByName(String name){
        long playerID = findIDByName(name);
        return findById(playerID);
    }

    @Override
    public Player create(Player dto){
        try(PreparedStatement statement = this.connection.prepareStatement(CREATE_NEW_PLAYER)){
            statement.setString(1, dto.getPlayerName());
            statement.setString(2, dto.getPasscode());
            statement.execute();

            //this should never go wrong - at this point, the player either is there, or isn't.
            long newPlayerID = findIDByName(dto.getPlayerName());

            // now we create the player that we're returning
            Player player = new Player();
            player.setPlayerName(dto.getPlayerName());
            player.setPasscode(dto.getPasscode());
            player.setID(newPlayerID);
            return player;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Player createHand(Player dto){
        Card[] newHand = new Card[5];
        for(int i = 0; i < 5; i++){
            newHand[i] = new Card();
            try(PreparedStatement statement = this.connection.prepareStatement(CREATE_NEW_CARD)){
                statement.setLong(1, dto.getID());
                statement.setInt(2, i);
                statement.setString(3, newHand[i].toString());
                statement.execute();
            }
            catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        dto.setHand(newHand);
        return dto;
    }

    @Override
    public Player update_long(String attribute, long value, Player dto){
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_PLAYER_BY_ID_START + attribute + UPDATE_PLAYER_BY_ID_END)){
            statement.setLong(1, value);
            statement.setLong(2, dto.getID());
            statement.execute();
            return dto;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Player update_int(String attribute, int value, Player dto){
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_PLAYER_BY_ID_START + attribute + UPDATE_PLAYER_BY_ID_END)){
            statement.setInt(1, value);
            statement.setLong(2, dto.getID());
            statement.execute();
            return dto;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public Player update_string(String attribute, String data, Player dto){
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_PLAYER_BY_ID_START + attribute + UPDATE_PLAYER_BY_ID_END)){
            statement.setString(1, data);
            statement.setLong(2, dto.getID());
            statement.execute();
            return dto;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Player update_all(Player dto) {
        try (PreparedStatement statement = this.connection.prepareStatement(UPDATE_ALL)) {
            statement.setString(1, dto.getPlayerName());
            statement.setString(2, dto.getPasscode());
            statement.setInt(3, dto.getDollars());
            statement.setInt(4, dto.getFirstPlaces());
            statement.setInt(5, dto.getSecondPlaces());
            statement.setInt(6, dto.getThirdPlaces());
            statement.setInt(7, dto.getFourthPlaces());
            statement.setLong(8, dto.getLifetimeTokens());
            statement.setInt(9, dto.getFlushes());
            statement.setInt(10, dto.getQuads());
            statement.setInt(11, dto.getFullHouses());
            statement.setInt(12, dto.getTriples());
            statement.setInt(13, dto.getTwoPairs());
            statement.setInt(14, dto.getOnePairs());
            statement.setInt(15, dto.getHighCards());
            statement.setInt(16, dto.getCardsChanged());
            statement.setLong(17, dto.getID());
            statement.execute();
            return dto;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Player updateHand(Player dto){
        for(int i = 0; i < 5; i++){
            try(PreparedStatement statement = this.connection.prepareStatement(UPDATE_CARD)){
                statement.setString(1, dto.getHand()[i].toString());
                statement.setLong(2, dto.getID());
                statement.setInt(3, i);
                statement.execute();
            }
            catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return dto;
    }

    //one part of CRUD - honestly this is not necessary, but it must be done.
    public Player deletePlayer(long p_id) {
        Player player = findById(p_id);
        try (PreparedStatement statement = this.connection.prepareStatement(DELETE_PLAYER)) {
            //RIP game
            statement.setLong(1, p_id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return player;
    }
}