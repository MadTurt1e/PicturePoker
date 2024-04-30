package game.jdbc.picturepoker.diffBlue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
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
import java.sql.SQLException;
import java.util.InputMismatchException;

import game.jdbc.picturepoker.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GamePlayDiffblueTest {
    /**
     * Method under test: {@link GamePlay#executeLuigi()}
     */
    @Test
    void testExecuteLuigi() {
        // Arrange
        Game game = new Game();
        game.setHand(new Card[]{new Card()});
        GamePlay gamePlay = new GamePlay(game, new Player[]{new Player()});

        // Act
        gamePlay.executeLuigi();

        // Assert
        Game curGame = gamePlay.getCurGame();
        assertEquals(1, curGame.getLuigiFinished());
        assertFalse((curGame.getHand()[0]).getToChange());
    }

    /**
     * Method under test: {@link GamePlay#executeLuigi()}
     */
    @Test
    void testExecuteLuigi2() throws SQLException {
        // Arrange
        Game game = new Game();
        game.setHand(new Card[]{new Card()});
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
        PlayerDAO playerDAO = new PlayerDAO(connection2);

        GamePlay gamePlay = new GamePlay(game, new Player[]{new Player()});
        gamePlay.gameEndResolution(gameDAO, playerDAO);

        // Act
        gamePlay.executeLuigi();

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
        Game curGame = gamePlay.getCurGame();
        assertEquals(1, curGame.getLuigiFinished());
        assertFalse((curGame.getHand()[0]).getToChange());
    }

    /**
     * Method under test: {@link GamePlay#executeLuigi()}
     */
    @Test
    void testExecuteLuigi3() throws SQLException {
        // Arrange
        Game game = new Game();
        game.setHand(new Card[]{new Card(Card.Suit.CLOUD)});
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
        PlayerDAO playerDAO = new PlayerDAO(connection2);

        GamePlay gamePlay = new GamePlay(game, new Player[]{new Player()});
        gamePlay.gameEndResolution(gameDAO, playerDAO);

        // Act
        gamePlay.executeLuigi();

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
        Game curGame = gamePlay.getCurGame();
        assertEquals(1, curGame.getLuigiFinished());
        assertFalse((curGame.getHand()[0]).getToChange());
    }

    /**
     * Method under test: {@link GamePlay#executeLuigi()}
     */
    @Test
    void testExecuteLuigi4() throws SQLException {
        // Arrange
        Game game = new Game();
        Card card = new Card();
        game.setHand(new Card[]{card, new Card()});
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
        PlayerDAO playerDAO = new PlayerDAO(connection2);

        GamePlay gamePlay = new GamePlay(game, new Player[]{new Player()});
        gamePlay.gameEndResolution(gameDAO, playerDAO);

        // Act
        gamePlay.executeLuigi();

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
        Game curGame = gamePlay.getCurGame();
        assertEquals(1, curGame.getLuigiFinished());
        Card[] hand = curGame.getHand();
        assertFalse((hand[0]).getToChange());
        assertFalse((hand[1]).getToChange());
    }

    /**
     * Method under test: {@link GamePlay#executeLuigi()}
     */
    @Test
    void testExecuteLuigi5() throws SQLException {
        // Arrange
        Game game = new Game();
        Card card = new Card();
        Card card2 = new Card();
        game.setHand(new Card[]{card, card2, new Card()});
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
        PlayerDAO playerDAO = new PlayerDAO(connection2);

        GamePlay gamePlay = new GamePlay(game, new Player[]{new Player()});
        gamePlay.gameEndResolution(gameDAO, playerDAO);

        // Act
        gamePlay.executeLuigi();

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
        Game curGame = gamePlay.getCurGame();
        assertEquals(1, curGame.getLuigiFinished());
        Card[] hand = curGame.getHand();
        assertFalse((hand[0]).getToChange());
        assertFalse((hand[1]).getToChange());
        assertFalse((hand[2]).getToChange());
    }

    /**
     * Method under test: {@link GamePlay#executeLuigi()}
     */
    @Test
    void testExecuteLuigi6() throws SQLException {
        // Arrange
        Game game = new Game();
        Card card = new Card(Card.Suit.CLOUD);
        game.setHand(new Card[]{card, new Card()});
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
        PlayerDAO playerDAO = new PlayerDAO(connection2);

        GamePlay gamePlay = new GamePlay(game, new Player[]{new Player()});
        gamePlay.gameEndResolution(gameDAO, playerDAO);

        // Act
        gamePlay.executeLuigi();

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
        Game curGame = gamePlay.getCurGame();
        assertEquals(1, curGame.getLuigiFinished());
        Card[] hand = curGame.getHand();
        assertFalse((hand[0]).getToChange());
        assertFalse((hand[1]).getToChange());
    }

    /**
     * Method under test: {@link GamePlay#executeLuigi()}
     */
    @Test
    void testExecuteLuigi7() throws SQLException {
        // Arrange
        Game game = new Game();
        Card card = new Card();
        Card card2 = new Card();
        game.setHand(new Card[]{card, card2, new Card()});

        Player player = new Player();
        player.setCardsChanged(3);
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
        PlayerDAO playerDAO = new PlayerDAO(connection2);

        GamePlay gamePlay = new GamePlay(game, new Player[]{player});
        gamePlay.gameEndResolution(gameDAO, playerDAO);

        // Act
        gamePlay.executeLuigi();

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
        Game curGame = gamePlay.getCurGame();
        assertEquals(1, curGame.getLuigiFinished());
        Card[] hand = curGame.getHand();
        assertFalse((hand[0]).getToChange());
        assertFalse((hand[1]).getToChange());
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
    void testShowdownResolution() {
        // Arrange
        Game game = new Game();
        game.setHand(new Card[]{new Card()});
        GamePlay gamePlay = new GamePlay(game, new Player[]{});
        GameDAO gameDAO = new GameDAO(mock(Connection.class));

        // Act
        ShowdownInfo actualShowdownResolutionResult = gamePlay.showdownResolution(gameDAO,
                new PlayerDAO(mock(Connection.class)), false);

        // Assert
        assertEquals("High Card", actualShowdownResolutionResult.getLuigiHandType());
        Game curGame = gamePlay.getCurGame();
        assertEquals(0, curGame.getPlayersFinished());
        assertEquals(1, curGame.getCurRound());
        assertFalse((actualShowdownResolutionResult.getLuigiHand()[0]).getToChange());
        assertTrue(actualShowdownResolutionResult.getPlayerShowdownInfos().isEmpty());
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
        assertNull(gamePlay.getCurGame().getWinner());
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
        assertNull(gamePlay.getCurGame().getWinner());
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
        assertNull(gamePlay.getCurGame().getWinner());
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
        assertNull(gamePlay.getCurGame().getWinner());
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
        assertNull(gamePlay.getCurGame().getWinner());
    }

    /**
     * Method under test: {@link GamePlay#gameSeq(GameDAO, PlayerDAO)}
     */
    @Test
    void testGameSeq() {
        // Arrange
        Game game = mock(Game.class);
        when(game.getID()).thenReturn(1L);
        GamePlay gamePlay = new GamePlay(game, new Player[]{});
        Game game2 = mock(Game.class);
        doThrow(new InputMismatchException("0123456789ABCDEF")).when(game2).setHand(Mockito.<Card[]>any());
        when(game2.getHand()).thenReturn(new Card[]{new Card()});
        when(game2.getCurRound()).thenReturn(1);
        when(game2.getNumRounds()).thenReturn(10);
        when(game2.getPlayers()).thenReturn(new long[]{1L, -1L, 1L, -1L});
        doNothing().when(game2).setPlayers(Mockito.<long[]>any());
        GameDAO gamedao = mock(GameDAO.class);
        when(gamedao.findById(anyLong())).thenReturn(game2);
        when(gamedao.getPIDsByGame(Mockito.<Game>any())).thenReturn(new long[]{1L, 2L, 1L, 2L});

        // Act and Assert
        assertThrows(InputMismatchException.class, () -> gamePlay.gameSeq(gamedao, new PlayerDAO(mock(Connection.class))));
        verify(game2, atLeast(1)).getCurRound();
        verify(game2).getHand();
        verify(game).getID();
        verify(game2).getNumRounds();
        verify(game2).getPlayers();
        verify(game2).setHand(isA(Card[].class));
        verify(game2).setPlayers(isA(long[].class));
        verify(gamedao).findById(eq(1L));
        verify(gamedao).getPIDsByGame(isA(Game.class));
    }

    /**
     * Method under test: {@link GamePlay#gameSeq(GameDAO, PlayerDAO)}
     */
    @Test
    void testGameSeq2() {
        // Arrange
        Game game = mock(Game.class);
        when(game.getID()).thenReturn(1L);
        GamePlay gamePlay = new GamePlay(game, new Player[]{});
        Game game2 = mock(Game.class);
        when(game2.getCurRound()).thenReturn(120);
        when(game2.getNumRounds()).thenReturn(10);
        when(game2.getPlayers()).thenReturn(new long[]{1L, -1L, 1L, -1L});
        doNothing().when(game2).setPlayers(Mockito.<long[]>any());
        GameDAO gamedao = mock(GameDAO.class);
        when(gamedao.update_all(Mockito.<Game>any())).thenReturn(new Game());
        when(gamedao.findById(anyLong())).thenReturn(game2);
        when(gamedao.getPIDsByGame(Mockito.<Game>any())).thenReturn(new long[]{1L, 2L, 1L, 2L});

        // Act
        Game actualGameSeqResult = gamePlay.gameSeq(gamedao, new PlayerDAO(mock(Connection.class)));

        // Assert
        verify(game2, atLeast(1)).getCurRound();
        verify(game).getID();
        verify(game2).getNumRounds();
        verify(game2).getPlayers();
        verify(game2).setPlayers(isA(long[].class));
        verify(gamedao).findById(eq(1L));
        verify(gamedao).getPIDsByGame(isA(Game.class));
        verify(gamedao).update_all(isA(Game.class));
        assertSame(actualGameSeqResult, gamePlay.getCurGame());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link GamePlay#GamePlay(Game, Player[])}
     *   <li>{@link GamePlay#getCurGame()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange
        Game game = new Game();

        // Act and Assert
        assertSame(game, (new GamePlay(game, new Player[]{new Player()})).getCurGame());
    }
}
