package game.jdbc.picturepoker.diffBlue;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
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
import game.jdbc.picturepoker.Game;
import game.jdbc.picturepoker.GameDAO;
import game.jdbc.picturepoker.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GameDAODiffblueTest {
    /**
     * Method under test: {@link GameDAO#findById(long)}
     */
    @Test
    void testFindById() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);

        // Act
        Game actualFindByIdResult = (new GameDAO(connection)).findById(1L);

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement, atLeast(1)).setLong(eq(1), eq(1L));
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertEquals(1, actualFindByIdResult.getActivePlayers());
        assertEquals(1, actualFindByIdResult.getBuyIn());
        assertEquals(1, actualFindByIdResult.getCurRound());
        assertEquals(1, actualFindByIdResult.getDifficulty());
        assertEquals(1, actualFindByIdResult.getNumRounds());
        assertEquals(1, actualFindByIdResult.getPotQuantity());
        assertEquals(1L, actualFindByIdResult.getID());
        assertArrayEquals(new long[]{1L, 0L, 0L, 0L}, actualFindByIdResult.getPlayers());
    }

    /**
     * Method under test: {@link GameDAO#findById(long)}
     */
    @Test
    void testFindById2() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenThrow(new RuntimeException(
                "SELECT g_id, cur_round, num_rounds, active_players, buy_in, pot_quantity, difficulty FROM game WHERE"
                        + " g_id = ?"));
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> (new GameDAO(connection)).findById(1L));
        verify(connection).prepareStatement(eq(
                "SELECT g_id, cur_round, num_rounds, active_players, buy_in, pot_quantity, difficulty FROM game WHERE g_id = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet).getLong(eq("g_id"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link GameDAO#findAllGames()}
     */
    @Test
    void testFindAllGames() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);

        // Act
        ArrayList<Game> actualFindAllGamesResult = (new GameDAO(connection)).findAllGames();

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertEquals(1, actualFindAllGamesResult.size());
    }

    /**
     * Method under test: {@link GameDAO#findAllGames()}
     */
    @Test
    void testFindAllGames2() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenThrow(new RuntimeException(
                "SELECT g_id, cur_round, num_rounds, active_players, buy_in, pot_quantity, difficulty FROM game"));
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> (new GameDAO(connection)).findAllGames());
        verify(connection).prepareStatement(
                eq("SELECT g_id, cur_round, num_rounds, active_players, buy_in, pot_quantity, difficulty FROM game"));
        verify(preparedStatement).executeQuery();
        verify(resultSet).getLong(eq("g_id"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link GameDAO#getPIDsByGame(Game)}
     */
    @Test
    void testGetPIDsByGame() throws SQLException {
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
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act
        long[] actualPIDsByGame = gameDAO.getPIDsByGame(dto);

        // Assert
        verify(connection).prepareStatement(eq("SELECT p_id FROM player_in_game WHERE g_id = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet, atLeast(1)).getLong(eq("p_id"));
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement).close();
        assertArrayEquals(new long[]{1L, 1L, 0L, 0L}, actualPIDsByGame);
    }

    /**
     * Method under test: {@link GameDAO#getPIDsByGame(Game)}
     */
    @Test
    void testGetPIDsByGame2() throws SQLException {
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
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> gameDAO.getPIDsByGame(dto));
        verify(connection).prepareStatement(eq("SELECT p_id FROM player_in_game WHERE g_id = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet).getLong(eq("p_id"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link GameDAO#getPIDsByGame(Game)}
     */
    @Test
    void testGetPIDsByGame3() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any()))
                .thenThrow(new RuntimeException("SELECT p_id FROM player_in_game WHERE g_id = ?"));
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> gameDAO.getPIDsByGame(dto));
        verify(connection).prepareStatement(eq("SELECT p_id FROM player_in_game WHERE g_id = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet).getLong(eq("p_id"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link GameDAO#update_long(String, long, Game)}
     */
    @Test
    void testUpdate_long() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act
        Game actualUpdate_longResult = gameDAO.update_long("Attribute", 42L, dto);

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement).execute();
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertEquals(1, actualUpdate_longResult.getActivePlayers());
        assertEquals(1, actualUpdate_longResult.getBuyIn());
        assertEquals(1, actualUpdate_longResult.getCurRound());
        assertEquals(1, actualUpdate_longResult.getDifficulty());
        assertEquals(1, actualUpdate_longResult.getNumRounds());
        assertEquals(1, actualUpdate_longResult.getPotQuantity());
        assertEquals(1L, actualUpdate_longResult.getID());
        assertArrayEquals(new long[]{1L, 0L, 0L, 0L}, actualUpdate_longResult.getPlayers());
    }

    /**
     * Method under test: {@link GameDAO#update_long(String, long, Game)}
     */
    @Test
    void testUpdate_long2() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenThrow(new RuntimeException(
                "SELECT g_id, cur_round, num_rounds, active_players, buy_in, pot_quantity, difficulty FROM game WHERE"
                        + " g_id = ?"));
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> gameDAO.update_long("Attribute", 42L, dto));
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement).execute();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
        verify(resultSet).getLong(eq("g_id"));
        verify(resultSet).next();
        verify(preparedStatement, atLeast(1)).close();
    }

    /**
     * Method under test: {@link GameDAO#update_int(String, int, Game)}
     */
    @Test
    void testUpdate_int() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act
        Game actualUpdate_intResult = gameDAO.update_int("Attribute", 42, dto);

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement).execute();
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement).setInt(eq(1), eq(42));
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), eq(1L));
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertEquals(1, actualUpdate_intResult.getActivePlayers());
        assertEquals(1, actualUpdate_intResult.getBuyIn());
        assertEquals(1, actualUpdate_intResult.getCurRound());
        assertEquals(1, actualUpdate_intResult.getDifficulty());
        assertEquals(1, actualUpdate_intResult.getNumRounds());
        assertEquals(1, actualUpdate_intResult.getPotQuantity());
        assertEquals(1L, actualUpdate_intResult.getID());
        assertArrayEquals(new long[]{1L, 0L, 0L, 0L}, actualUpdate_intResult.getPlayers());
    }

    /**
     * Method under test: {@link GameDAO#update_int(String, int, Game)}
     */
    @Test
    void testUpdate_int2() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenThrow(new RuntimeException(
                "SELECT g_id, cur_round, num_rounds, active_players, buy_in, pot_quantity, difficulty FROM game WHERE"
                        + " g_id = ?"));
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> gameDAO.update_int("Attribute", 42, dto));
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement).execute();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setInt(eq(1), eq(42));
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), eq(1L));
        verify(resultSet).getLong(eq("g_id"));
        verify(resultSet).next();
        verify(preparedStatement, atLeast(1)).close();
    }

    /**
     * Method under test: {@link GameDAO#create(Game)}
     */
    @Test
    void testCreate() throws SQLException {
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
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act
        Game actualCreateResult = gameDAO.create(dto);

        // Assert
        verify(connection).prepareStatement(eq(
                "INSERT INTO game (num_rounds, active_players, buy_in, pot_quantity, difficulty) VALUES (?, ?, ?, ?, ?) RETURNING g_id"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
        verify(resultSet, atLeast(1)).getLong(eq("g_id"));
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement).close();
        assertEquals(1L, actualCreateResult.getID());
        assertSame(dto, actualCreateResult);
    }

    /**
     * Method under test: {@link GameDAO#create(Game)}
     */
    @Test
    void testCreate2() throws SQLException {
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
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> gameDAO.create(dto));
        verify(connection).prepareStatement(eq(
                "INSERT INTO game (num_rounds, active_players, buy_in, pot_quantity, difficulty) VALUES (?, ?, ?, ?, ?) RETURNING g_id"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
        verify(resultSet).getLong(eq("g_id"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link GameDAO#create(Game)}
     */
    @Test
    void testCreate3() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenThrow(new RuntimeException(
                "INSERT INTO game (num_rounds, active_players, buy_in, pot_quantity, difficulty) VALUES (?, ?, ?, ?,"
                        + " ?) RETURNING g_id"));
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> gameDAO.create(dto));
        verify(connection).prepareStatement(eq(
                "INSERT INTO game (num_rounds, active_players, buy_in, pot_quantity, difficulty) VALUES (?, ?, ?, ?, ?) RETURNING g_id"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
        verify(resultSet).getLong(eq("g_id"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link GameDAO#joinGame(long, Player)}
     */
    @Test
    void testJoinGame() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);

        // Act
        gameDAO.joinGame(1L, new Player());

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement).setInt(eq(1), eq(2));
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
    }

    /**
     * Method under test: {@link GameDAO#joinGame(long, Player)}
     */
    @Test
    void testJoinGame2() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenThrow(new RuntimeException(
                "SELECT g_id, cur_round, num_rounds, active_players, buy_in, pot_quantity, difficulty FROM game WHERE"
                        + " g_id = ?"));
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> gameDAO.joinGame(1L, new Player()));
        verify(connection).prepareStatement(eq(
                "SELECT g_id, cur_round, num_rounds, active_players, buy_in, pot_quantity, difficulty FROM game WHERE g_id = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet).getLong(eq("g_id"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link GameDAO#joinGame(long, Player)}
     */
    @Test
    void testJoinGame3() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(4);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);

        // Act
        Game actualJoinGameResult = gameDAO.joinGame(1L, new Player());

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement, atLeast(1)).setLong(eq(1), eq(1L));
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertEquals(1L, actualJoinGameResult.getID());
        assertEquals(4, actualJoinGameResult.getActivePlayers());
        assertEquals(4, actualJoinGameResult.getBuyIn());
        assertEquals(4, actualJoinGameResult.getCurRound());
        assertEquals(4, actualJoinGameResult.getDifficulty());
        assertEquals(4, actualJoinGameResult.getNumRounds());
        assertEquals(4, actualJoinGameResult.getPotQuantity());
        assertArrayEquals(new long[]{1L, 0L, 0L, 0L}, actualJoinGameResult.getPlayers());
    }

    /**
     * Method under test: {@link GameDAO#joinGame(long, Player)}
     */
    @Test
    void testJoinGame4() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);

        // Act
        gameDAO.joinGame(1L, new Player());

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement).setInt(eq(1), eq(1));
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
        verify(resultSet).getLong(eq("g_id"));
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
    }

    /**
     * Method under test: {@link GameDAO#joinGame(long, Player)}
     */
    @Test
    void testJoinGame5() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenThrow(new RuntimeException(
                "SELECT g_id, cur_round, num_rounds, active_players, buy_in, pot_quantity, difficulty FROM game WHERE"
                        + " g_id = ?"));
        when(resultSet.next()).thenReturn(false).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> gameDAO.joinGame(1L, new Player()));
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement, atLeast(1)).setLong(eq(1), anyLong());
        verify(resultSet).getLong(eq("g_id"));
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
    }

    /**
     * Method under test: {@link GameDAO#createHand(Game)}
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
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act
        Game actualCreateHandResult = gameDAO.createHand(dto);

        // Assert
        verify(connection, atLeast(1))
                .prepareStatement(eq("INSERT INTO dealer_card (g_id, hand_pos, suit) VALUES (?, ?, ?)"));
        verify(preparedStatement, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).setInt(eq(2), anyInt());
        verify(preparedStatement, atLeast(1)).setLong(eq(1), eq(1L));
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
     * Method under test: {@link GameDAO#createHand(Game)}
     */
    @Test
    void testCreateHand2() throws SQLException {
        // Arrange
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        doThrow(new RuntimeException("INSERT INTO dealer_card (g_id, hand_pos, suit) VALUES (?, ?, ?)"))
                .when(preparedStatement)
                .setLong(anyInt(), anyLong());
        doThrow(new RuntimeException("INSERT INTO dealer_card (g_id, hand_pos, suit) VALUES (?, ?, ?)"))
                .when(preparedStatement)
                .close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> gameDAO.createHand(dto));
        verify(connection).prepareStatement(eq("INSERT INTO dealer_card (g_id, hand_pos, suit) VALUES (?, ?, ?)"));
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link GameDAO#update_string(String, String, Game)}
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
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act
        Game actualUpdate_stringResult = gameDAO.update_string("Attribute", "Data", dto);

        // Assert
        verify(connection).prepareStatement(eq("UPDATE game SET Attribute = ? WHERE g_id = ?"));
        verify(preparedStatement).execute();
        verify(preparedStatement).setLong(eq(3), eq(1L));
        verify(preparedStatement, atLeast(1)).setString(anyInt(), Mockito.<String>any());
        verify(preparedStatement).close();
        assertSame(dto, actualUpdate_stringResult);
    }

    /**
     * Method under test: {@link GameDAO#update_string(String, String, Game)}
     */
    @Test
    void testUpdate_string2() throws SQLException {
        // Arrange
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        doThrow(new RuntimeException("foo")).when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doThrow(new RuntimeException("foo")).when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> gameDAO.update_string("Attribute", "Data", dto));
        verify(connection).prepareStatement(eq("UPDATE game SET Attribute = ? WHERE g_id = ?"));
        verify(preparedStatement).setString(eq(1), eq("Attribute"));
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link GameDAO#update_all(Game)}
     */
    @Test
    void testUpdate_all() throws SQLException {
        // Arrange
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act
        Game actualUpdate_allResult = gameDAO.update_all(dto);

        // Assert
        verify(connection).prepareStatement(eq(
                "UPDATE game SET cur_round = ?, num_rounds = ?, active_players = ?, buy_in = ?, pot_quantity = ?, difficulty = ? WHERE g_id = ?"));
        verify(preparedStatement).execute();
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement).setLong(eq(7), eq(1L));
        verify(preparedStatement).close();
        assertSame(dto, actualUpdate_allResult);
    }

    /**
     * Method under test: {@link GameDAO#update_all(Game)}
     */
    @Test
    void testUpdate_all2() throws SQLException {
        // Arrange
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        doThrow(new RuntimeException(
                "UPDATE game SET cur_round = ?, num_rounds = ?, active_players = ?, buy_in = ?, pot_quantity = ?,"
                        + " difficulty = ? WHERE g_id = ?")).when(preparedStatement).setInt(anyInt(), anyInt());
        doThrow(new RuntimeException(
                "UPDATE game SET cur_round = ?, num_rounds = ?, active_players = ?, buy_in = ?, pot_quantity = ?,"
                        + " difficulty = ? WHERE g_id = ?")).when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> gameDAO.update_all(dto));
        verify(connection).prepareStatement(eq(
                "UPDATE game SET cur_round = ?, num_rounds = ?, active_players = ?, buy_in = ?, pot_quantity = ?, difficulty = ? WHERE g_id = ?"));
        verify(preparedStatement).setInt(eq(1), eq(1));
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link GameDAO#updateHand(Game)}
     */
    @Test
    void testUpdateHand() throws SQLException {
        // Arrange
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        doThrow(new RuntimeException("UPDATE dealer_card SET suit = ? WHERE g_id = ? AND hand_pos = ?"))
                .when(preparedStatement)
                .setString(anyInt(), Mockito.<String>any());
        doThrow(new RuntimeException("UPDATE dealer_card SET suit = ? WHERE g_id = ? AND hand_pos = ?"))
                .when(preparedStatement)
                .close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> gameDAO.updateHand(dto));
        verify(connection).prepareStatement(eq("UPDATE dealer_card SET suit = ? WHERE g_id = ? AND hand_pos = ?"));
        verify(preparedStatement).setString(eq(1), eq("MARIO"));
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link GameDAO#removePlayerFromGame(Game, long)}
     */
    @Test
    void testRemovePlayerFromGame() {
        // Arrange
        GameDAO gameDAO = new GameDAO(mock(Connection.class));

        Game dto = new Game();
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");
        dto.setActivePlayers(4);

        // Act and Assert
        assertSame(dto, gameDAO.removePlayerFromGame(dto, 1L));
    }

    /**
     * Method under test: {@link GameDAO#removePlayerFromGame(Game, long)}
     */
    @Test
    void testRemovePlayerFromGame2() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act
        Game actualRemovePlayerFromGameResult = gameDAO.removePlayerFromGame(dto, 1L);

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement).setInt(eq(1), eq(0));
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), eq(1L));
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertEquals(1, actualRemovePlayerFromGameResult.getActivePlayers());
        assertEquals(1, actualRemovePlayerFromGameResult.getBuyIn());
        assertEquals(1, actualRemovePlayerFromGameResult.getCurRound());
        assertEquals(1, actualRemovePlayerFromGameResult.getDifficulty());
        assertEquals(1, actualRemovePlayerFromGameResult.getNumRounds());
        assertEquals(1, actualRemovePlayerFromGameResult.getPotQuantity());
        assertEquals(1L, actualRemovePlayerFromGameResult.getID());
        assertArrayEquals(new long[]{1L, 0L, 0L, 0L}, actualRemovePlayerFromGameResult.getPlayers());
    }

    /**
     * Method under test: {@link GameDAO#removePlayerFromGame(Game, long)}
     */
    @Test
    void testRemovePlayerFromGame3() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any()))
                .thenThrow(new RuntimeException("DELETE FROM player_in_game WHERE p_id = ?"));
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> gameDAO.removePlayerFromGame(dto, 1L));
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).execute();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setInt(eq(1), eq(0));
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), eq(1L));
        verify(resultSet).getLong(eq("g_id"));
        verify(resultSet).next();
        verify(preparedStatement, atLeast(1)).close();
    }

    /**
     * Method under test: {@link GameDAO#removePlayerFromGame(Game, long)}
     */
    @Test
    void testRemovePlayerFromGame4() {
        // Arrange
        GameDAO gameDAO = new GameDAO(mock(Connection.class));

        Game dto = new Game();
        dto.setActivePlayers(1);
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");

        // Act and Assert
        assertSame(dto, gameDAO.removePlayerFromGame(dto, 1L));
    }

    /**
     * Method under test: {@link GameDAO#removePlayerFromGame(Game, long)}
     */
    @Test
    void testRemovePlayerFromGame5() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenThrow(new SQLException());
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);

        Game dto = new Game();
        dto.setBuyIn(1);
        dto.setCurRound(1);
        dto.setDifficulty(1);
        dto.setHand(new Card[]{new Card()});
        dto.setID(1L);
        dto.setNumRounds(10);
        dto.setPlayers(new long[]{1L, -1L, 1L, -1L});
        dto.setPotQuantity(1);
        dto.setWinner("Winner");
        dto.setActivePlayers(3);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> gameDAO.removePlayerFromGame(dto, 1L));
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).execute();
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setInt(eq(1), eq(2));
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), eq(1L));
        verify(resultSet).next();
        verify(preparedStatement, atLeast(1)).close();
    }

    /**
     * Method under test: {@link GameDAO#deleteGame(long)}
     */
    @Test
    void testDeleteGame() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
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
        Game actualDeleteGameResult = (new GameDAO(connection)).deleteGame(1L);

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement).execute();
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement, atLeast(1)).setLong(eq(1), eq(1L));
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertEquals(1, actualDeleteGameResult.getActivePlayers());
        assertEquals(1, actualDeleteGameResult.getBuyIn());
        assertEquals(1, actualDeleteGameResult.getCurRound());
        assertEquals(1, actualDeleteGameResult.getDifficulty());
        assertEquals(1, actualDeleteGameResult.getNumRounds());
        assertEquals(1, actualDeleteGameResult.getPotQuantity());
        assertEquals(1L, actualDeleteGameResult.getID());
        assertArrayEquals(new long[]{1L, 0L, 0L, 0L}, actualDeleteGameResult.getPlayers());
    }

    /**
     * Method under test: {@link GameDAO#deleteGame(long)}
     */
    @Test
    void testDeleteGame2() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(Mockito.<String>any())).thenThrow(new RuntimeException(
                "SELECT g_id, cur_round, num_rounds, active_players, buy_in, pot_quantity, difficulty FROM game WHERE"
                        + " g_id = ?"));
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> (new GameDAO(connection)).deleteGame(1L));
        verify(connection).prepareStatement(eq(
                "SELECT g_id, cur_round, num_rounds, active_players, buy_in, pot_quantity, difficulty FROM game WHERE g_id = ?"));
        verify(preparedStatement).executeQuery();
        verify(preparedStatement).setLong(eq(1), eq(1L));
        verify(resultSet).getLong(eq("g_id"));
        verify(resultSet).next();
        verify(preparedStatement).close();
    }

    /**
     * Method under test: {@link GameDAO#GameDAO(Connection)}
     */
    @Test
    void testNewGameDAO() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     DataAccessObject.connection

        // Arrange and Act
        new GameDAO(mock(Connection.class));
    }
}
