package game.jdbc.picturepoker.diffBlue;

import org.junit.Ignore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import game.jdbc.picturepoker.Card;
import game.jdbc.picturepoker.Player;
import game.jdbc.picturepoker.PlayerDAO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlayerDAODiffblueTest {
    /**
     * Method under test: {@link PlayerDAO#findById(long)}
     */
    @Test
    void testFindById() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);

        // Act
        Player actualFindByIdResult = (new PlayerDAO(connection)).findById(1L);

        // Assert
        verify(connection).prepareStatement(eq(
                "SELECT p_id, p_name, passcode, dollars, first_places, second_places, third_places, fourth_places, lifetime_tokens, flushes, quads, full_houses, triples, two_pairs, one_pairs, high_cards, cards_changed, lifetime_rounds_won, lifetime_total_bet,  tokens, bet, rounds_won, finished_round FROM player WHERE p_id = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(eq("p_id"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement).close();
        assertEquals("String", actualFindByIdResult.getPasscode());
        assertEquals("String", actualFindByIdResult.getPlayerName());
        assertEquals(1, actualFindByIdResult.getBet());
        assertEquals(1, actualFindByIdResult.getCardsChanged());
        assertEquals(1, actualFindByIdResult.getDollars());
        assertEquals(1, actualFindByIdResult.getFinishedRound());
        assertEquals(1, actualFindByIdResult.getFirstPlaces());
        assertEquals(1, actualFindByIdResult.getFlushes());
        assertEquals(1, actualFindByIdResult.getFourthPlaces());
        assertEquals(1, actualFindByIdResult.getFullHouses());
        assertEquals(1, actualFindByIdResult.getHighCards());
        assertEquals(1, actualFindByIdResult.getLifetimeRoundsWon());
        assertEquals(1, actualFindByIdResult.getLifetimeTokens());
        assertEquals(1, actualFindByIdResult.getLifetimeTotalBet());
        assertEquals(1, actualFindByIdResult.getOnePairs());
        assertEquals(1, actualFindByIdResult.getQuads());
        assertEquals(1, actualFindByIdResult.getRoundsWon());
        assertEquals(1, actualFindByIdResult.getSecondPlaces());
        assertEquals(1, actualFindByIdResult.getThirdPlaces());
        assertEquals(1, actualFindByIdResult.getTokens());
        assertEquals(1, actualFindByIdResult.getTriples());
        assertEquals(1, actualFindByIdResult.getTwoPairs());
        assertEquals(1L, actualFindByIdResult.getID());
    }

    /**
     * Method under test: {@link PlayerDAO#findById(long)}
     */
    @Test
    void testFindById2() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenThrow(new RuntimeException(
                "SELECT p_id, p_name, passcode, dollars, first_places, second_places, third_places, fourth_places,"
                        + " lifetime_tokens, flushes, quads, full_houses, triples, two_pairs, one_pairs, high_cards, cards_changed,"
                        + " lifetime_rounds_won, lifetime_total_bet,  tokens, bet, rounds_won, finished_round FROM player WHERE"
                        + " p_id = ?"));
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> (new PlayerDAO(connection)).findById(1L));
        verify(connection).prepareStatement(eq(
                "SELECT p_id, p_name, passcode, dollars, first_places, second_places, third_places, fourth_places, lifetime_tokens, flushes, quads, full_houses, triples, two_pairs, one_pairs, high_cards, cards_changed, lifetime_rounds_won, lifetime_total_bet,  tokens, bet, rounds_won, finished_round FROM player WHERE p_id = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet).getLong(eq("p_id"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link PlayerDAO#findAllPlayers()}
     */
    @Test
    void testFindAllPlayers() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);

        // Act
        ArrayList<Player> actualFindAllPlayersResult = (new PlayerDAO(connection)).findAllPlayers();

        // Assert
        verify(connection).prepareStatement(eq(
                "SELECT p_id, p_name, passcode, dollars, first_places, second_places, third_places, fourth_places, lifetime_tokens, flushes, quads, full_houses, triples, two_pairs, one_pairs, high_cards, cards_changed, lifetime_rounds_won, lifetime_total_bet,  tokens, bet, rounds_won, finished_round FROM player"));
        verify(preparedStatement).executeQuery();
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(eq("p_id"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement).close();
        assertEquals(2, actualFindAllPlayersResult.size());
    }

    /**
     * Method under test: {@link PlayerDAO#findAllPlayers()}
     */
    @Test
    void testFindAllPlayers2() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenThrow(new RuntimeException(
                "SELECT p_id, p_name, passcode, dollars, first_places, second_places, third_places, fourth_places,"
                        + " lifetime_tokens, flushes, quads, full_houses, triples, two_pairs, one_pairs, high_cards, cards_changed,"
                        + " lifetime_rounds_won, lifetime_total_bet,  tokens, bet, rounds_won, finished_round FROM player"));
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> (new PlayerDAO(connection)).findAllPlayers());
        verify(connection).prepareStatement(eq(
                "SELECT p_id, p_name, passcode, dollars, first_places, second_places, third_places, fourth_places, lifetime_tokens, flushes, quads, full_houses, triples, two_pairs, one_pairs, high_cards, cards_changed, lifetime_rounds_won, lifetime_total_bet,  tokens, bet, rounds_won, finished_round FROM player"));
        verify(preparedStatement).executeQuery();
        verify(resultSet).getLong(eq("p_id"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link PlayerDAO#findIDByName(String)}
     */
    @Test
    void testFindIDByName() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);

        // Act
        long actualFindIDByNameResult = (new PlayerDAO(connection)).findIDByName("Name");

        // Assert
        verify(connection).prepareStatement(eq("SELECT p_id FROM player WHERE p_name = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setString(eq(1), eq("Name"));
        verify(resultSet, atLeast(1)).getLong(eq("p_id"));
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement).close();
        assertEquals(1L, actualFindIDByNameResult);
    }

    /**
     * Method under test: {@link PlayerDAO#findIDByName(String)}
     */
    @Test
    void testFindIDByName2() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenThrow(new SQLException());
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> (new PlayerDAO(connection)).findIDByName("Name"));
        verify(connection).prepareStatement(eq("SELECT p_id FROM player WHERE p_name = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setString(eq(1), eq("Name"));
        verify(resultSet).getLong(eq("p_id"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link PlayerDAO#findIDByName(String)}
     */
    @Test
    void testFindIDByName3() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any()))
                .thenThrow(new RuntimeException("SELECT p_id FROM player WHERE p_name = ?"));
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> (new PlayerDAO(connection)).findIDByName("Name"));
        verify(connection).prepareStatement(eq("SELECT p_id FROM player WHERE p_name = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setString(eq(1), eq("Name"));
        verify(resultSet).getLong(eq("p_id"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link PlayerDAO#findByName(String)}
     */
    @Test
    void testFindByName() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);

        // Act
        (new PlayerDAO(connection)).findByName("Name");

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(preparedStatement).setString(eq(1), eq("Name"));
        verify(resultSet, atLeast(1)).getLong(eq("p_id"));
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
    }

    /**
     * Method under test: {@link PlayerDAO#findByName(String)}
     */
    @Test
    void testFindByName2() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenThrow(new SQLException());
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> (new PlayerDAO(connection)).findByName("Name"));
        verify(connection).prepareStatement(eq("SELECT p_id FROM player WHERE p_name = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setString(eq(1), eq("Name"));
        verify(resultSet).getLong(eq("p_id"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link PlayerDAO#findByName(String)}
     */
    @Test
    void testFindByName3() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any()))
                .thenThrow(new RuntimeException("SELECT p_id FROM player WHERE p_name = ?"));
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> (new PlayerDAO(connection)).findByName("Name"));
        verify(connection).prepareStatement(eq("SELECT p_id FROM player WHERE p_name = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setString(eq(1), eq("Name"));
        verify(resultSet).getLong(eq("p_id"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link PlayerDAO#findByName(String)}
     */
    @Test
    void testFindByName4() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);

        // Act
        Player actualFindByNameResult = (new PlayerDAO(connection)).findByName("Name");

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(0L));
        verify(preparedStatement).setString(eq(1), eq("Name"));
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet).getLong(eq("p_id"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertEquals("String", actualFindByNameResult.getPasscode());
        assertEquals("String", actualFindByNameResult.getPlayerName());
        assertEquals(1, actualFindByNameResult.getBet());
        assertEquals(1, actualFindByNameResult.getCardsChanged());
        assertEquals(1, actualFindByNameResult.getDollars());
        assertEquals(1, actualFindByNameResult.getFinishedRound());
        assertEquals(1, actualFindByNameResult.getFirstPlaces());
        assertEquals(1, actualFindByNameResult.getFlushes());
        assertEquals(1, actualFindByNameResult.getFourthPlaces());
        assertEquals(1, actualFindByNameResult.getFullHouses());
        assertEquals(1, actualFindByNameResult.getHighCards());
        assertEquals(1, actualFindByNameResult.getLifetimeRoundsWon());
        assertEquals(1, actualFindByNameResult.getLifetimeTokens());
        assertEquals(1, actualFindByNameResult.getLifetimeTotalBet());
        assertEquals(1, actualFindByNameResult.getOnePairs());
        assertEquals(1, actualFindByNameResult.getQuads());
        assertEquals(1, actualFindByNameResult.getRoundsWon());
        assertEquals(1, actualFindByNameResult.getSecondPlaces());
        assertEquals(1, actualFindByNameResult.getThirdPlaces());
        assertEquals(1, actualFindByNameResult.getTokens());
        assertEquals(1, actualFindByNameResult.getTriples());
        assertEquals(1, actualFindByNameResult.getTwoPairs());
        assertEquals(1L, actualFindByNameResult.getID());
    }

    /**
     * Method under test: {@link PlayerDAO#findByName(String)}
     */
    @Test
    void testFindByName5() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any()))
                .thenThrow(new RuntimeException("SELECT p_id FROM player WHERE p_name = ?"));
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> (new PlayerDAO(connection)).findByName("Name"));
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(0L));
        verify(preparedStatement).setString(eq(1), eq("Name"));
        verify(resultSet).getLong(eq("p_id"));
        verify(resultSet).getString(eq("p_name"));
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
    }

    /**
     * Method under test: {@link PlayerDAO#create(Player)}
     */
    @Test
    void testCreate() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PlayerDAO playerDAO = new PlayerDAO(connection);

        // Act
        Player actualCreateResult = playerDAO.create(new Player());

        // Assert
        verify(connection).prepareStatement(eq("INSERT INTO player (p_name, passcode) VALUES (?, ?) RETURNING p_id"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement, atLeast(1)).setString(anyInt(), isNull());
        verify(resultSet, atLeast(1)).getLong(eq("p_id"));
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement).close();
        assertNull(actualCreateResult.getPasscode());
        assertNull(actualCreateResult.getPlayerName());
        assertEquals(1L, actualCreateResult.getID());
    }

    /**
     * Method under test: {@link PlayerDAO#create(Player)}
     */
    @Test
    void testCreate2() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenThrow(new SQLException());
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PlayerDAO playerDAO = new PlayerDAO(connection);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> playerDAO.create(new Player()));
        verify(connection).prepareStatement(eq("INSERT INTO player (p_name, passcode) VALUES (?, ?) RETURNING p_id"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement, atLeast(1)).setString(anyInt(), isNull());
        verify(resultSet).getLong(eq("p_id"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link PlayerDAO#create(Player)}
     */
    @Test
    void testCreate3() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any()))
                .thenThrow(new RuntimeException("INSERT INTO player (p_name, passcode) VALUES (?, ?) RETURNING p_id"));
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PlayerDAO playerDAO = new PlayerDAO(connection);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> playerDAO.create(new Player()));
        verify(connection).prepareStatement(eq("INSERT INTO player (p_name, passcode) VALUES (?, ?) RETURNING p_id"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement, atLeast(1)).setString(anyInt(), isNull());
        verify(resultSet).getLong(eq("p_id"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link PlayerDAO#getCurrentGame(Player)}
     */
    @Test
    void testGetCurrentGame() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PlayerDAO playerDAO = new PlayerDAO(connection);

        // Act
        long actualCurrentGame = playerDAO.getCurrentGame(new Player());

        // Assert
        verify(connection).prepareStatement(eq("SELECT g_id FROM player_in_game WHERE p_id = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(0L));
        verify(resultSet, atLeast(1)).getLong(eq("g_id"));
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement).close();
        assertEquals(1L, actualCurrentGame);
    }

    /**
     * Method under test: {@link PlayerDAO#getCurrentGame(Player)}
     */
    @Test
    void testGetCurrentGame2() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenThrow(new SQLException());
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PlayerDAO playerDAO = new PlayerDAO(connection);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> playerDAO.getCurrentGame(new Player()));
        verify(connection).prepareStatement(eq("SELECT g_id FROM player_in_game WHERE p_id = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(0L));
        verify(resultSet).getLong(eq("g_id"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link PlayerDAO#getCurrentGame(Player)}
     */
    @Test
    void testGetCurrentGame3() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any()))
                .thenThrow(new RuntimeException("SELECT g_id FROM player_in_game WHERE p_id = ?"));
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PlayerDAO playerDAO = new PlayerDAO(connection);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> playerDAO.getCurrentGame(new Player()));
        verify(connection).prepareStatement(eq("SELECT g_id FROM player_in_game WHERE p_id = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(0L));
        verify(resultSet).getLong(eq("g_id"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link PlayerDAO#getHand(Player)}
     */
    @Test
    void testGetHand() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(Mockito.<String>any())).thenThrow(new SQLException());
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PlayerDAO playerDAO = new PlayerDAO(connection);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> playerDAO.getHand(new Player()));
        verify(connection).prepareStatement(eq("SELECT suit, to_change FROM player_card WHERE p_id = ? AND hand_pos = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setInt(eq(2), eq(0));
        verify(preparedStatement).setLong(eq(1), eq(0L));
        verify(resultSet).getString(eq("suit"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link PlayerDAO#getHand(Player)}
     */
    @Test
    void testGetHand2() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(false).thenReturn(false).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PlayerDAO playerDAO = new PlayerDAO(connection);
        Player dto = new Player();

        // Act
        Player actualHand = playerDAO.getHand(dto);

        // Assert
        verify(connection, atLeast(1))
                .prepareStatement(eq("SELECT suit, to_change FROM player_card WHERE p_id = ? AND hand_pos = ?"));
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement, atLeast(1)).setInt(eq(2), anyInt());
        verify(preparedStatement, atLeast(1)).setLong(eq(1), eq(0L));
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertNull(actualHand.getHand()[0]);
        assertSame(dto, actualHand);
    }

    /**
     * Method under test: {@link PlayerDAO#createHand(Player)}
     */
    @Test
    void testCreateHand() throws SQLException {
        // Arrange
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PlayerDAO playerDAO = new PlayerDAO(connection);
        Player dto = new Player();

        // Act
        Player actualCreateHandResult = playerDAO.createHand(dto);

        // Assert
        verify(connection, atLeast(1))
                .prepareStatement(eq("INSERT INTO player_card (p_id, hand_pos, suit) VALUES (?, ?, ?)"));
        verify(preparedStatement, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).setInt(eq(2), anyInt());
        verify(preparedStatement, atLeast(1)).setLong(eq(1), eq(0L));
        verify(preparedStatement, atLeast(1)).setString(eq(3), Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).close();
        Card[] hand = actualCreateHandResult.getHand();
        assertFalse((hand[0]).getToChange());
        assertFalse((hand[1]).getToChange());
        assertFalse((hand[2]).getToChange());
        assertFalse((hand[3]).getToChange());
        assertFalse((hand[4]).getToChange());
        assertSame(dto, actualCreateHandResult);
    }

    /**
     * Method under test: {@link PlayerDAO#createHand(Player)}
     */
    @Test
    void testCreateHand2() throws SQLException {
        // Arrange
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        doThrow(new RuntimeException("INSERT INTO player_card (p_id, hand_pos, suit) VALUES (?, ?, ?)"))
                .when(preparedStatement)
                .setLong(anyInt(), anyLong());
        doThrow(new RuntimeException("INSERT INTO player_card (p_id, hand_pos, suit) VALUES (?, ?, ?)"))
                .when(preparedStatement)
                .close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PlayerDAO playerDAO = new PlayerDAO(connection);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> playerDAO.createHand(new Player()));
        verify(connection).prepareStatement(eq("INSERT INTO player_card (p_id, hand_pos, suit) VALUES (?, ?, ?)"));
        verify(preparedStatement).setLong(eq(1), eq(0L));
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link PlayerDAO#update_long(String, long, Player)}
     */
    @Test
    void testUpdate_long() throws SQLException {
        // Arrange
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PlayerDAO playerDAO = new PlayerDAO(connection);
        Player dto = new Player();

        // Act
        Player actualUpdate_longResult = playerDAO.update_long("Attribute", 42L, dto);

        // Assert
        verify(connection).prepareStatement(eq("UPDATE player SET Attribute = ? WHERE p_id = ?"));
        verify(preparedStatement).execute();
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
        verify(preparedStatement).close();
        assertSame(dto, actualUpdate_longResult);
    }

    /**
     * Method under test: {@link PlayerDAO#update_long(String, long, Player)}
     */
    @Test
    void testUpdate_long2() throws SQLException {
        // Arrange
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        doThrow(new RuntimeException("foo")).when(preparedStatement).setLong(anyInt(), anyLong());
        doThrow(new RuntimeException("foo")).when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PlayerDAO playerDAO = new PlayerDAO(connection);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> playerDAO.update_long("Attribute", 42L, new Player()));
        verify(connection).prepareStatement(eq("UPDATE player SET Attribute = ? WHERE p_id = ?"));
        verify(preparedStatement).setLong(eq(1), eq(42L));
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link PlayerDAO#update_int(String, int, Player)}
     */
    @Test
    void testUpdate_int() throws SQLException {
        // Arrange
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PlayerDAO playerDAO = new PlayerDAO(connection);
        Player dto = new Player();

        // Act
        Player actualUpdate_intResult = playerDAO.update_int("Attribute", 42, dto);

        // Assert
        verify(connection).prepareStatement(eq("UPDATE player SET Attribute = ? WHERE p_id = ?"));
        verify(preparedStatement).execute();
        verify(preparedStatement).setInt(eq(1), eq(42));
        verify(preparedStatement).setLong(eq(2), eq(0L));
        verify(preparedStatement).close();
        assertSame(dto, actualUpdate_intResult);
    }

    /**
     * Method under test: {@link PlayerDAO#update_int(String, int, Player)}
     */
    @Test
    void testUpdate_int2() throws SQLException {
        // Arrange
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        doThrow(new RuntimeException("foo")).when(preparedStatement).setInt(anyInt(), anyInt());
        doThrow(new RuntimeException("foo")).when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PlayerDAO playerDAO = new PlayerDAO(connection);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> playerDAO.update_int("Attribute", 42, new Player()));
        verify(connection).prepareStatement(eq("UPDATE player SET Attribute = ? WHERE p_id = ?"));
        verify(preparedStatement).setInt(eq(1), eq(42));
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link PlayerDAO#update_string(String, String, Player)}
     */
    @Test
    void testUpdate_string() throws SQLException {
        // Arrange
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PlayerDAO playerDAO = new PlayerDAO(connection);
        Player dto = new Player();

        // Act
        Player actualUpdate_stringResult = playerDAO.update_string("Attribute", "Data", dto);

        // Assert
        verify(connection).prepareStatement(eq("UPDATE player SET Attribute = ? WHERE p_id = ?"));
        verify(preparedStatement).execute();
        verify(preparedStatement).setLong(eq(2), eq(0L));
        verify(preparedStatement).setString(eq(1), eq("Data"));
        verify(preparedStatement).close();
        assertSame(dto, actualUpdate_stringResult);
    }

    /**
     * Method under test: {@link PlayerDAO#update_string(String, String, Player)}
     */
    @Test
    void testUpdate_string2() throws SQLException {
        // Arrange
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        doThrow(new RuntimeException("foo")).when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doThrow(new RuntimeException("foo")).when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PlayerDAO playerDAO = new PlayerDAO(connection);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> playerDAO.update_string("Attribute", "Data", new Player()));
        verify(connection).prepareStatement(eq("UPDATE player SET Attribute = ? WHERE p_id = ?"));
        verify(preparedStatement).setString(eq(1), eq("Data"));
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link PlayerDAO#updateAttributes(Player)}
     */
    @Test
    void testUpdateAttributes() throws SQLException {
        // Arrange
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PlayerDAO playerDAO = new PlayerDAO(connection);
        Player dto = new Player();

        // Act
        Player actualUpdateAttributesResult = playerDAO.updateAttributes(dto);

        // Assert
        verify(connection).prepareStatement(eq(
                "UPDATE player SET p_name = ?, passcode = ?, dollars = ?, first_places = ?, second_places = ?, third_places = ?, fourth_places = ?, lifetime_tokens = ?,flushes = ?, quads = ?, full_houses = ?, triples = ?, two_pairs = ?, one_pairs = ?, high_cards = ?, cards_changed = ?, lifetime_rounds_won = ?, lifetime_total_bet = ?, finished_round = ? WHERE p_id = ?"));
        verify(preparedStatement).execute();
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), eq(0));
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), eq(0L));
        verify(preparedStatement, atLeast(1)).setString(anyInt(), isNull());
        verify(preparedStatement).close();
        assertSame(dto, actualUpdateAttributesResult);
    }

    /**
     * Method under test: {@link PlayerDAO#updateAttributes(Player)}
     */
    @Test
    void testUpdateAttributes2() throws SQLException {
        // Arrange
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        doThrow(new RuntimeException(
                "UPDATE player SET p_name = ?, passcode = ?, dollars = ?, first_places = ?, second_places = ?, third_places"
                        + " = ?, fourth_places = ?, lifetime_tokens = ?,flushes = ?, quads = ?, full_houses = ?, triples = ?,"
                        + " two_pairs = ?, one_pairs = ?, high_cards = ?, cards_changed = ?, lifetime_rounds_won = ?, lifetime_total_bet"
                        + " = ?, finished_round = ? WHERE p_id = ?")).when(preparedStatement)
                .setString(anyInt(), Mockito.<String>any());
        doThrow(new RuntimeException(
                "UPDATE player SET p_name = ?, passcode = ?, dollars = ?, first_places = ?, second_places = ?, third_places"
                        + " = ?, fourth_places = ?, lifetime_tokens = ?,flushes = ?, quads = ?, full_houses = ?, triples = ?,"
                        + " two_pairs = ?, one_pairs = ?, high_cards = ?, cards_changed = ?, lifetime_rounds_won = ?, lifetime_total_bet"
                        + " = ?, finished_round = ? WHERE p_id = ?")).when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PlayerDAO playerDAO = new PlayerDAO(connection);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> playerDAO.updateAttributes(new Player()));
        verify(connection).prepareStatement(eq(
                "UPDATE player SET p_name = ?, passcode = ?, dollars = ?, first_places = ?, second_places = ?, third_places = ?, fourth_places = ?, lifetime_tokens = ?,flushes = ?, quads = ?, full_houses = ?, triples = ?, two_pairs = ?, one_pairs = ?, high_cards = ?, cards_changed = ?, lifetime_rounds_won = ?, lifetime_total_bet = ?, finished_round = ? WHERE p_id = ?"));
        verify(preparedStatement).setString(eq(1), isNull());
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link PlayerDAO#updateHand(Player)}
     */
    @Ignore
    @Test
    void testUpdateHand() throws SQLException {
        // Arrange
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        doThrow(new RuntimeException("UPDATE player_card SET suit = ? WHERE p_id = ? AND hand_pos = ?"))
                .when(preparedStatement)
                .setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        PlayerDAO playerDAO = new PlayerDAO(connection);

        Player dto = new Player();
        dto.setHand(new Card[]{new Card()});

        // Act and Assert
        assertThrows(RuntimeException.class, () -> playerDAO.updateHand(dto));
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link PlayerDAO#deletePlayer(long)}
     */
    @Test
    void testDeletePlayer() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getString(Mockito.<String>any())).thenReturn("String");
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);

        // Act
        Player actualDeletePlayerResult = (new PlayerDAO(connection)).deletePlayer(1L);

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement).execute();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement, atLeast(1)).setLong(eq(1), eq(1L));
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(eq("p_id"));
        verify(resultSet, atLeast(1)).getString(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertEquals("String", actualDeletePlayerResult.getPasscode());
        assertEquals("String", actualDeletePlayerResult.getPlayerName());
        assertEquals(1, actualDeletePlayerResult.getBet());
        assertEquals(1, actualDeletePlayerResult.getCardsChanged());
        assertEquals(1, actualDeletePlayerResult.getDollars());
        assertEquals(1, actualDeletePlayerResult.getFinishedRound());
        assertEquals(1, actualDeletePlayerResult.getFirstPlaces());
        assertEquals(1, actualDeletePlayerResult.getFlushes());
        assertEquals(1, actualDeletePlayerResult.getFourthPlaces());
        assertEquals(1, actualDeletePlayerResult.getFullHouses());
        assertEquals(1, actualDeletePlayerResult.getHighCards());
        assertEquals(1, actualDeletePlayerResult.getLifetimeRoundsWon());
        assertEquals(1, actualDeletePlayerResult.getLifetimeTokens());
        assertEquals(1, actualDeletePlayerResult.getLifetimeTotalBet());
        assertEquals(1, actualDeletePlayerResult.getOnePairs());
        assertEquals(1, actualDeletePlayerResult.getQuads());
        assertEquals(1, actualDeletePlayerResult.getRoundsWon());
        assertEquals(1, actualDeletePlayerResult.getSecondPlaces());
        assertEquals(1, actualDeletePlayerResult.getThirdPlaces());
        assertEquals(1, actualDeletePlayerResult.getTokens());
        assertEquals(1, actualDeletePlayerResult.getTriples());
        assertEquals(1, actualDeletePlayerResult.getTwoPairs());
        assertEquals(1L, actualDeletePlayerResult.getID());
    }

    /**
     * Method under test: {@link PlayerDAO#deletePlayer(long)}
     */
    @Test
    void testDeletePlayer2() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenThrow(new RuntimeException(
                "SELECT p_id, p_name, passcode, dollars, first_places, second_places, third_places, fourth_places,"
                        + " lifetime_tokens, flushes, quads, full_houses, triples, two_pairs, one_pairs, high_cards, cards_changed,"
                        + " lifetime_rounds_won, lifetime_total_bet,  tokens, bet, rounds_won, finished_round FROM player WHERE"
                        + " p_id = ?"));
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> (new PlayerDAO(connection)).deletePlayer(1L));
        verify(connection).prepareStatement(eq(
                "SELECT p_id, p_name, passcode, dollars, first_places, second_places, third_places, fourth_places, lifetime_tokens, flushes, quads, full_houses, triples, two_pairs, one_pairs, high_cards, cards_changed, lifetime_rounds_won, lifetime_total_bet,  tokens, bet, rounds_won, finished_round FROM player WHERE p_id = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet).getLong(eq("p_id"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link PlayerDAO#PlayerDAO(Connection)}
     */
    @Test
    void testNewPlayerDAO() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     DataAccessObject.connection

        // Arrange and Act
        new PlayerDAO(mock(Connection.class));
    }
}
