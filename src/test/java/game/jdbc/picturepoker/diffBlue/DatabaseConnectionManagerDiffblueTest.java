package game.jdbc.picturepoker.diffBlue;

import java.sql.SQLException;

import game.jdbc.picturepoker.DatabaseConnectionManager;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class DatabaseConnectionManagerDiffblueTest {
    /**
     * Method under test: {@link DatabaseConnectionManager#getConnection()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetConnection() throws SQLException {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Sandboxing policy violation.
        //   Diffblue Cover ran code in your project that tried
        //     to access the network.
        //   Diffblue Cover's default sandboxing policy disallows this in order to prevent
        //   your code from damaging your system environment.
        //   See https://diff.blue/R011 to resolve this issue.

        // Arrange and Act
        (new DatabaseConnectionManager("localhost", "Database Name", "janedoe", "iloveyou")).getConnection();
    }

    /**
     * Method under test:
     * {@link DatabaseConnectionManager#DatabaseConnectionManager(String, String, String, String)}
     */
    @Test
    void testNewDatabaseConnectionManager() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange and Act
        new DatabaseConnectionManager("localhost", "Database Name", "janedoe", "iloveyou");

    }
}
