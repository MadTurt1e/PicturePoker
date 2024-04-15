package game.jdbc.picturepoker.diffBlue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import game.jdbc.picturepoker.Player;
import game.jdbc.picturepoker.PlayerDAO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlayerDAODiffblueTest {
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
