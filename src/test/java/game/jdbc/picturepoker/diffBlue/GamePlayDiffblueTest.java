package game.jdbc.picturepoker.diffBlue;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import game.jdbc.picturepoker.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GamePlayDiffblueTest {
    /**
     * Method under test: {@link GamePlay#executeLuigi()}
     */
    @Test
    void testExecuteLuigi() throws SQLException {
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

        GamePlay gamePlay = new GamePlay(new Game(), new Player[]{});
        gamePlay.showdownResolution(gameDAO, new PlayerDAO(mock(Connection.class)), true);

        // Act
        gamePlay.executeLuigi();

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
    }

    /**
     * Method under test: {@link GamePlay#executeLuigi()}
     */
    @Test
    void testExecuteLuigi2() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(5L);
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

        GamePlay gamePlay = new GamePlay(new Game(), new Player[]{});
        gamePlay.showdownResolution(gameDAO, new PlayerDAO(mock(Connection.class)), true);

        // Act
        gamePlay.executeLuigi();

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
    }

    /**
     * Method under test: {@link GamePlay.PokerHand#getHandName()}
     */
    @Test
    void testPokerHandGetHandName() {
        // Arrange, Act and Assert
        assertEquals("High Card", GamePlay.PokerHand.valueOf("HIGH_CARD").getHandName());
    }

    /**
     * Method under test:
     * {@link GamePlay#showdownResolution(GameDAO, PlayerDAO, boolean)}
     */
    @Test
    void testShowdownResolution() throws SQLException {
        // Arrange
        GamePlay gamePlay = new GamePlay(new Game(), new Player[]{});
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

        // Act
        ArrayList<PlayerShowdownInfo> actualShowdownResolutionResult = gamePlay.showdownResolution(gameDAO,
                new PlayerDAO(mock(Connection.class)), true);

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertTrue(actualShowdownResolutionResult.isEmpty());
    }

    /**
     * Method under test:
     * {@link GamePlay#showdownResolution(GameDAO, PlayerDAO, boolean)}
     */
    @Test
    void testShowdownResolution2() {
        // Arrange
        GamePlay gamePlay = new GamePlay(new Game(), new Player[]{});
        GameDAO gameDAO = new GameDAO(mock(Connection.class));

        // Act and Assert
        assertTrue(gamePlay.showdownResolution(gameDAO, new PlayerDAO(mock(Connection.class)), false).isEmpty());
    }

    /**
     * Method under test: {@link GamePlay#gameEndResolution(GameDAO, PlayerDAO)}
     */
    @Test
    void testGameEndResolution() throws SQLException {
        // Arrange
        Game game = new Game();
        GamePlay gamePlay = new GamePlay(game, new Player[]{new Player()});
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.execute()).thenReturn(true);
        doNothing().when(preparedStatement2).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection connection2 = mock(Connection.class);
        when(connection2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);

        // Act
        gamePlay.gameEndResolution(gameDAO, new PlayerDAO(connection2));

        // Assert
        verify(connection2, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(connection).prepareStatement(eq(
                "UPDATE game SET cur_round = ?, num_rounds = ?, active_players = ?, buy_in = ?, pot_quantity = ?, difficulty = ?, players_finished = ?, luigi_finished = ? WHERE g_id = ?"));
        verify(preparedStatement).execute();
        verify(preparedStatement2, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), eq(0));
        verify(preparedStatement2, atLeast(1)).setInt(eq(1), anyInt());
        verify(preparedStatement2, atLeast(1)).setLong(anyInt(), eq(0L));
        verify(preparedStatement).setLong(eq(9), eq(0L));
        verify(preparedStatement).close();
        verify(preparedStatement2, atLeast(1)).close();
    }

    /**
     * Method under test: {@link GamePlay#gameEndResolution(GameDAO, PlayerDAO)}
     */
    @Test
    void testGameEndResolution2() throws SQLException {
        // Arrange
        Game game = new Game();
        Player player = new Player();
        GamePlay gamePlay = new GamePlay(game, new Player[]{player, new Player()});
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.execute()).thenReturn(true);
        doNothing().when(preparedStatement2).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection connection2 = mock(Connection.class);
        when(connection2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);

        // Act
        gamePlay.gameEndResolution(gameDAO, new PlayerDAO(connection2));

        // Assert
        verify(connection2, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(connection).prepareStatement(eq(
                "UPDATE game SET cur_round = ?, num_rounds = ?, active_players = ?, buy_in = ?, pot_quantity = ?, difficulty = ?, players_finished = ?, luigi_finished = ? WHERE g_id = ?"));
        verify(preparedStatement).execute();
        verify(preparedStatement2, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), eq(0));
        verify(preparedStatement2, atLeast(1)).setInt(eq(1), anyInt());
        verify(preparedStatement2, atLeast(1)).setLong(anyInt(), eq(0L));
        verify(preparedStatement).setLong(eq(9), eq(0L));
        verify(preparedStatement).close();
        verify(preparedStatement2, atLeast(1)).close();
    }

    /**
     * Method under test: {@link GamePlay#gameEndResolution(GameDAO, PlayerDAO)}
     */
    @Test
    void testGameEndResolution3() throws SQLException {
        // Arrange
        Game game = new Game();
        Player player = new Player();
        Player player2 = new Player();
        GamePlay gamePlay = new GamePlay(game, new Player[]{player, player2, new Player()});
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.execute()).thenReturn(true);
        doNothing().when(preparedStatement2).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection connection2 = mock(Connection.class);
        when(connection2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);

        // Act
        gamePlay.gameEndResolution(gameDAO, new PlayerDAO(connection2));

        // Assert
        verify(connection2, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(connection).prepareStatement(eq(
                "UPDATE game SET cur_round = ?, num_rounds = ?, active_players = ?, buy_in = ?, pot_quantity = ?, difficulty = ?, players_finished = ?, luigi_finished = ? WHERE g_id = ?"));
        verify(preparedStatement).execute();
        verify(preparedStatement2, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), eq(0));
        verify(preparedStatement2, atLeast(1)).setInt(eq(1), anyInt());
        verify(preparedStatement2, atLeast(1)).setLong(anyInt(), eq(0L));
        verify(preparedStatement).setLong(eq(9), eq(0L));
        verify(preparedStatement).close();
        verify(preparedStatement2, atLeast(1)).close();
    }

    /**
     * Method under test: {@link GamePlay#gameEndResolution(GameDAO, PlayerDAO)}
     */
    @Test
    void testGameEndResolution4() throws SQLException {
        // Arrange
        Player player = new Player();
        player.setTokens(1);
        GamePlay gamePlay = new GamePlay(new Game(), new Player[]{player, new Player()});
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.execute()).thenReturn(true);
        doNothing().when(preparedStatement2).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection connection2 = mock(Connection.class);
        when(connection2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);

        // Act
        gamePlay.gameEndResolution(gameDAO, new PlayerDAO(connection2));

        // Assert
        verify(connection2, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(connection).prepareStatement(eq(
                "UPDATE game SET cur_round = ?, num_rounds = ?, active_players = ?, buy_in = ?, pot_quantity = ?, difficulty = ?, players_finished = ?, luigi_finished = ? WHERE g_id = ?"));
        verify(preparedStatement).execute();
        verify(preparedStatement2, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), eq(0));
        verify(preparedStatement2, atLeast(1)).setInt(eq(1), anyInt());
        verify(preparedStatement2, atLeast(1)).setLong(anyInt(), anyLong());
        verify(preparedStatement).setLong(eq(9), eq(0L));
        verify(preparedStatement).close();
        verify(preparedStatement2, atLeast(1)).close();
    }

    /**
     * Method under test: {@link GamePlay#gameEndResolution(GameDAO, PlayerDAO)}
     */
    @Test
    void testGameEndResolution5() throws SQLException {
        // Arrange
        Player player = new Player();
        player.setRoundsWon(1);
        GamePlay gamePlay = new GamePlay(new Game(), new Player[]{player, new Player()});
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gameDAO = new GameDAO(connection);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        when(preparedStatement2.execute()).thenReturn(true);
        doNothing().when(preparedStatement2).setInt(anyInt(), anyInt());
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection connection2 = mock(Connection.class);
        when(connection2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);

        // Act
        gamePlay.gameEndResolution(gameDAO, new PlayerDAO(connection2));

        // Assert
        verify(connection2, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(connection).prepareStatement(eq(
                "UPDATE game SET cur_round = ?, num_rounds = ?, active_players = ?, buy_in = ?, pot_quantity = ?, difficulty = ?, players_finished = ?, luigi_finished = ? WHERE g_id = ?"));
        verify(preparedStatement).execute();
        verify(preparedStatement2, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), eq(0));
        verify(preparedStatement2, atLeast(1)).setInt(eq(1), anyInt());
        verify(preparedStatement2, atLeast(1)).setLong(anyInt(), eq(0L));
        verify(preparedStatement).setLong(eq(9), eq(0L));
        verify(preparedStatement).close();
        verify(preparedStatement2, atLeast(1)).close();
    }

    /**
     * Method under test: {@link GamePlay#gameSeq(GameDAO, PlayerDAO)}
     */
    @Test
    void testGameSeq() throws SQLException {
        // Arrange
        GamePlay gamePlay = new GamePlay(new Game(), new Player[]{});
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(preparedStatement).setBoolean(anyInt(), anyBoolean());
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gamedao = new GameDAO(connection);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        doNothing().when(preparedStatement2).setInt(anyInt(), anyInt());
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection connection2 = mock(Connection.class);
        when(connection2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);

        // Act
        Game actualGameSeqResult = gamePlay.gameSeq(gamedao, new PlayerDAO(connection2));

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement, atLeast(1)).setBoolean(eq(2), anyBoolean());
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
        verify(preparedStatement, atLeast(1)).setString(eq(1), Mockito.<String>any());
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertEquals(0, actualGameSeqResult.getLuigiFinished());
        assertEquals(0, actualGameSeqResult.getPlayersFinished());
        assertEquals(1, actualGameSeqResult.getActivePlayers());
        assertEquals(1, actualGameSeqResult.getBuyIn());
        assertEquals(1, actualGameSeqResult.getDifficulty());
        assertEquals(1, actualGameSeqResult.getNumRounds());
        assertEquals(1, actualGameSeqResult.getPotQuantity());
        assertEquals(1L, actualGameSeqResult.getID());
        assertEquals(2, actualGameSeqResult.getCurRound());
        assertEquals(5, actualGameSeqResult.getHand().length);
        assertArrayEquals(new long[]{0L, 0L, 0L, 0L}, actualGameSeqResult.getPlayers());
    }

    /**
     * Method under test: {@link GamePlay#gameSeq(GameDAO, PlayerDAO)}
     */
    @Test
    void testGameSeq2() throws SQLException {
        // Arrange
        GamePlay gamePlay = new GamePlay(new Game(), new Player[]{});
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(false);
        doNothing().when(preparedStatement).setBoolean(anyInt(), anyBoolean());
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gamedao = new GameDAO(connection);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        doNothing().when(preparedStatement2).setInt(anyInt(), anyInt());
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection connection2 = mock(Connection.class);
        when(connection2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);

        // Act
        Game actualGameSeqResult = gamePlay.gameSeq(gamedao, new PlayerDAO(connection2));

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement, atLeast(1)).setBoolean(eq(2), anyBoolean());
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
        verify(preparedStatement, atLeast(1)).setString(eq(1), Mockito.<String>any());
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertEquals(0, actualGameSeqResult.getLuigiFinished());
        assertEquals(0, actualGameSeqResult.getPlayersFinished());
        assertEquals(1, actualGameSeqResult.getActivePlayers());
        assertEquals(1, actualGameSeqResult.getBuyIn());
        assertEquals(1, actualGameSeqResult.getDifficulty());
        assertEquals(1, actualGameSeqResult.getNumRounds());
        assertEquals(1, actualGameSeqResult.getPotQuantity());
        assertEquals(1L, actualGameSeqResult.getID());
        assertEquals(2, actualGameSeqResult.getCurRound());
        assertEquals(5, actualGameSeqResult.getHand().length);
        assertArrayEquals(new long[]{0L, 0L, 0L, 0L}, actualGameSeqResult.getPlayers());
    }

    /**
     * Method under test: {@link GamePlay#gameSeq(GameDAO, PlayerDAO)}
     */
    @Test
    void testGameSeq3() throws SQLException {
        // Arrange
        GamePlay gamePlay = new GamePlay(new Game(), new Player[]{});
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(preparedStatement).setBoolean(anyInt(), anyBoolean());
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(4);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gamedao = new GameDAO(connection);
        Connection connection2 = mock(Connection.class);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        doNothing().when(preparedStatement2).setInt(anyInt(), anyInt());
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        when(connection2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);

        // Act
        Game actualGameSeqResult = gamePlay.gameSeq(gamedao, new PlayerDAO(connection2));

        // Assert
        int actualActivePlayers = actualGameSeqResult.getActivePlayers();
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement, atLeast(1)).setBoolean(eq(2), anyBoolean());
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
//        verify(preparedStatement, atLeast(1)).setString(eq(1), eq("LUIGI"));
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertEquals(0, actualGameSeqResult.getLuigiFinished());
        assertEquals(0, actualGameSeqResult.getPlayersFinished());
        assertEquals(1L, actualGameSeqResult.getID());
        assertEquals(4, actualActivePlayers);
        assertEquals(4, actualGameSeqResult.getBuyIn());
        assertEquals(4, actualGameSeqResult.getDifficulty());
        assertEquals(4, actualGameSeqResult.getNumRounds());
        assertEquals(4, actualGameSeqResult.getPotQuantity());
        assertEquals(5, actualGameSeqResult.getCurRound());
        assertFalse((actualGameSeqResult.getHand()[4]).getToChange());
        assertArrayEquals(new long[]{0L, 0L, 0L, 0L}, actualGameSeqResult.getPlayers());
    }

    /**
     * Method under test: {@link GamePlay#gameSeq(GameDAO, PlayerDAO)}
     */
    @Test
    void testGameSeq4() throws SQLException {
        // Arrange
        GamePlay gamePlay = new GamePlay(new Game(), new Player[]{});
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(5);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(preparedStatement).setBoolean(anyInt(), anyBoolean());
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gamedao = new GameDAO(connection);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        doNothing().when(preparedStatement2).setInt(anyInt(), anyInt());
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection connection2 = mock(Connection.class);
        when(connection2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);

        // Act
        Game actualGameSeqResult = gamePlay.gameSeq(gamedao, new PlayerDAO(connection2));

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement, atLeast(1)).setBoolean(eq(2), anyBoolean());
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
        verify(preparedStatement, atLeast(1)).setString(eq(1), Mockito.<String>any());
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertEquals(0, actualGameSeqResult.getLuigiFinished());
        assertEquals(0, actualGameSeqResult.getPlayersFinished());
        assertEquals(1L, actualGameSeqResult.getID());
        assertEquals(5, actualGameSeqResult.getActivePlayers());
        assertEquals(5, actualGameSeqResult.getBuyIn());
        assertEquals(5, actualGameSeqResult.getDifficulty());
        assertEquals(5, actualGameSeqResult.getNumRounds());
        assertEquals(5, actualGameSeqResult.getPotQuantity());
        assertEquals(5, actualGameSeqResult.getHand().length);
        assertEquals(6, actualGameSeqResult.getCurRound());
        assertArrayEquals(new long[]{0L, 0L, 0L, 0L}, actualGameSeqResult.getPlayers());
    }

    /**
     * Method under test: {@link GamePlay#gameSeq(GameDAO, PlayerDAO)}
     */
    @Test
    void testGameSeq5() throws SQLException {
        // Arrange
        GamePlay gamePlay = new GamePlay(new Game(), new Player[]{});
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(5L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(preparedStatement).setBoolean(anyInt(), anyBoolean());
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gamedao = new GameDAO(connection);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        doNothing().when(preparedStatement2).setInt(anyInt(), anyInt());
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection connection2 = mock(Connection.class);
        when(connection2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);

        // Act
        Game actualGameSeqResult = gamePlay.gameSeq(gamedao, new PlayerDAO(connection2));

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement, atLeast(1)).setBoolean(eq(2), anyBoolean());
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
        verify(preparedStatement, atLeast(1)).setString(eq(1), Mockito.<String>any());
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertEquals(0, actualGameSeqResult.getLuigiFinished());
        assertEquals(0, actualGameSeqResult.getPlayersFinished());
        assertEquals(1, actualGameSeqResult.getActivePlayers());
        assertEquals(1, actualGameSeqResult.getBuyIn());
        assertEquals(1, actualGameSeqResult.getDifficulty());
        assertEquals(1, actualGameSeqResult.getNumRounds());
        assertEquals(1, actualGameSeqResult.getPotQuantity());
        assertEquals(2, actualGameSeqResult.getCurRound());
        assertEquals(5, actualGameSeqResult.getHand().length);
        assertEquals(5L, actualGameSeqResult.getID());
        assertArrayEquals(new long[]{0L, 0L, 0L, 0L}, actualGameSeqResult.getPlayers());
    }

    /**
     * Method under test: {@link GamePlay#gameSeq(GameDAO, PlayerDAO)}
     */
    @Test
    void testGameSeq6() throws SQLException {
        // Arrange
        GamePlay gamePlay = new GamePlay(new Game(), new Player[]{});
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(3L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(preparedStatement).setBoolean(anyInt(), anyBoolean());
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gamedao = new GameDAO(connection);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        doNothing().when(preparedStatement2).setInt(anyInt(), anyInt());
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection connection2 = mock(Connection.class);
        when(connection2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);

        // Act
        Game actualGameSeqResult = gamePlay.gameSeq(gamedao, new PlayerDAO(connection2));

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement, atLeast(1)).setBoolean(eq(2), anyBoolean());
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
        verify(preparedStatement, atLeast(1)).setString(eq(1), Mockito.<String>any());
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertEquals(0, actualGameSeqResult.getLuigiFinished());
        assertEquals(0, actualGameSeqResult.getPlayersFinished());
        assertEquals(1, actualGameSeqResult.getActivePlayers());
        assertEquals(1, actualGameSeqResult.getBuyIn());
        assertEquals(1, actualGameSeqResult.getDifficulty());
        assertEquals(1, actualGameSeqResult.getNumRounds());
        assertEquals(1, actualGameSeqResult.getPotQuantity());
        assertEquals(2, actualGameSeqResult.getCurRound());
        assertEquals(3L, actualGameSeqResult.getID());
        assertEquals(5, actualGameSeqResult.getHand().length);
        assertArrayEquals(new long[]{0L, 0L, 0L, 0L}, actualGameSeqResult.getPlayers());
    }

    /**
     * Method under test: {@link GamePlay#gameSeq(GameDAO, PlayerDAO)}
     */
    @Test
    void testGameSeq7() throws SQLException {
        // Arrange
        GamePlay gamePlay = new GamePlay(new Game(), new Player[]{});
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(Mockito.<String>any())).thenReturn(1);
        when(resultSet.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(preparedStatement.execute()).thenReturn(true);
        doNothing().when(preparedStatement).setBoolean(anyInt(), anyBoolean());
        doNothing().when(preparedStatement).setString(anyInt(), Mockito.<String>any());
        doNothing().when(preparedStatement).setInt(anyInt(), anyInt());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        doNothing().when(preparedStatement).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement).close();
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement);
        GameDAO gamedao = new GameDAO(connection);
        ResultSet resultSet2 = mock(ResultSet.class);
        when(resultSet2.getInt(Mockito.<String>any())).thenReturn(2);
        when(resultSet2.getLong(Mockito.<String>any())).thenReturn(1L);
        when(resultSet2.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
        doNothing().when(preparedStatement2).setInt(anyInt(), anyInt());
        when(preparedStatement2.executeQuery()).thenReturn(resultSet2);
        doNothing().when(preparedStatement2).setLong(anyInt(), anyLong());
        doNothing().when(preparedStatement2).close();
        Connection connection2 = mock(Connection.class);
        when(connection2.prepareStatement(Mockito.<String>any())).thenReturn(preparedStatement2);

        // Act
        Game actualGameSeqResult = gamePlay.gameSeq(gamedao, new PlayerDAO(connection2));

        // Assert
        verify(connection, atLeast(1)).prepareStatement(Mockito.<String>any());
        verify(preparedStatement, atLeast(1)).execute();
        verify(preparedStatement, atLeast(1)).executeQuery();
        verify(preparedStatement, atLeast(1)).setBoolean(eq(2), anyBoolean());
        verify(preparedStatement, atLeast(1)).setInt(anyInt(), anyInt());
        verify(preparedStatement, atLeast(1)).setLong(anyInt(), anyLong());
        verify(preparedStatement, atLeast(1)).setString(eq(1), Mockito.<String>any());
        verify(resultSet, atLeast(1)).getInt(Mockito.<String>any());
        verify(resultSet, atLeast(1)).getLong(Mockito.<String>any());
        verify(resultSet, atLeast(1)).next();
        verify(preparedStatement, atLeast(1)).close();
        assertEquals(0, actualGameSeqResult.getLuigiFinished());
        assertEquals(0, actualGameSeqResult.getPlayersFinished());
        assertEquals(1, actualGameSeqResult.getActivePlayers());
        assertEquals(1, actualGameSeqResult.getBuyIn());
        assertEquals(1, actualGameSeqResult.getDifficulty());
        assertEquals(1, actualGameSeqResult.getNumRounds());
        assertEquals(1, actualGameSeqResult.getPotQuantity());
        assertEquals(1L, actualGameSeqResult.getID());
        assertEquals(2, actualGameSeqResult.getCurRound());
        assertEquals(5, actualGameSeqResult.getHand().length);
        assertArrayEquals(new long[]{0L, 0L, 0L, 0L}, actualGameSeqResult.getPlayers());
    }

    /**
     * Method under test: {@link GamePlay#GamePlay(Game, Player[])}
     */
    @Test
    void testNewGamePlay() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     GamePlay.curGame
        //     GamePlay.playerList

        // Arrange
        Game game = new Game();
        game.setActivePlayers(1);
        game.setBuyIn(1);
        game.setCurRound(1);
        game.setDifficulty(1);
        game.setHand(new Card[]{new Card()});
        game.setID(1L);
        game.setLuigiFinished(1);
        game.setNumRounds(10);
        game.setPlayers(new long[]{1L, -1L, 1L, -1L});
        game.setPlayersFinished(1);
        game.setPotQuantity(1);
        game.setWinner("Winner");

        // Act
        new GamePlay(game, new Player[]{new Player()});

    }
}
