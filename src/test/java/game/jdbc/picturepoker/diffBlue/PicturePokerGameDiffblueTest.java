package game.jdbc.picturepoker.diffBlue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

import game.jdbc.picturepoker.PicturePokerGame;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class PicturePokerGameDiffblueTest {
    /**
     * Method under test: {@link PicturePokerGame#helloWorld()}
     */
    @Test
    void testHelloWorld() {
        // Arrange, Act and Assert
        assertEquals("HELLO WORLD", (new PicturePokerGame()).helloWorld());
    }

    /**
     * Method under test: {@link PicturePokerGame#homepage()}
     */
    @Test
    void testHomepage() {
        // Arrange, Act and Assert
        assertEquals("Welcome to Luigi's Picture Poker", (new PicturePokerGame()).homepage());
    }

    /**
     * Method under test: {@link PicturePokerGame#createNewPlayer(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateNewPlayer() throws JsonProcessingException {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: argument "content" is null
        //       at com.fasterxml.jackson.databind.ObjectMapper._assertNotNull(ObjectMapper.java:4980)
        //       at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:3739)
        //       at game.jdbc.picturepoker.PicturePokerGame.createNewPlayer(PicturePokerGame.java:43)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange and Act
        (new PicturePokerGame()).createNewPlayer(null);
    }

    /**
     * Method under test: {@link PicturePokerGame#createNewPlayer(String)}
     */
    @Test
    void testCreateNewPlayer2() throws JsonProcessingException {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange
        PicturePokerGame picturePokerGame = new PicturePokerGame();

        ObjectMapper objectMapper = new ObjectMapper();

        // Act
        picturePokerGame.createNewPlayer(objectMapper.writeValueAsString(new HashMap<>()));
    }

    /**
     * Method under test: {@link PicturePokerGame#createNewGame(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateNewGame() throws JsonProcessingException {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: argument "content" is null
        //       at com.fasterxml.jackson.databind.ObjectMapper._assertNotNull(ObjectMapper.java:4980)
        //       at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:3739)
        //       at game.jdbc.picturepoker.PicturePokerGame.createNewGame(PicturePokerGame.java:67)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange and Act
        (new PicturePokerGame()).createNewGame(null);
    }

    /**
     * Method under test: {@link PicturePokerGame#createNewGame(String)}
     */
    @Test
    void testCreateNewGame2() throws JsonProcessingException {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange
        PicturePokerGame picturePokerGame = new PicturePokerGame();

        ObjectMapper objectMapper = new ObjectMapper();

        // Act
        picturePokerGame.createNewGame(objectMapper.writeValueAsString(new HashMap<>()));
    }

    /**
     * Method under test: {@link PicturePokerGame#getByPlayerName(String)}
     */
    @Test
    void testGetByPlayerName() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange and Act
        (new PicturePokerGame()).getByPlayerName("Player Name");
    }

    /**
     * Method under test: {@link PicturePokerGame#getByPlayerID(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetByPlayerID() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NumberFormatException: For input string: "P id Str"
        //       at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:67)
        //       at java.base/java.lang.Long.parseLong(Long.java:709)
        //       at java.base/java.lang.Long.parseLong(Long.java:832)
        //       at game.jdbc.picturepoker.PicturePokerGame.getByPlayerID(PicturePokerGame.java:117)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange and Act
        (new PicturePokerGame()).getByPlayerID("P id Str");
    }

    /**
     * Method under test: {@link PicturePokerGame#getByPlayerID(String)}
     */
    @Test
    void testGetByPlayerID2() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange and Act
        (new PicturePokerGame()).getByPlayerID("42");
    }

    /**
     * Method under test: {@link PicturePokerGame#getPlayerActiveGame(long)}
     */
    @Test
    void testGetPlayerActiveGame() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange and Act
        (new PicturePokerGame()).getPlayerActiveGame(1L);
    }

    /**
     * Method under test: {@link PicturePokerGame#getByGameID(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetByGameID() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NumberFormatException: For input string: "G id Str"
        //       at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:67)
        //       at java.base/java.lang.Long.parseLong(Long.java:709)
        //       at java.base/java.lang.Long.parseLong(Long.java:832)
        //       at game.jdbc.picturepoker.PicturePokerGame.getByGameID(PicturePokerGame.java:163)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange and Act
        (new PicturePokerGame()).getByGameID("G id Str");
    }

    /**
     * Method under test: {@link PicturePokerGame#getByGameID(String)}
     */
    @Test
    void testGetByGameID2() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange and Act
        (new PicturePokerGame()).getByGameID("42");
    }

    /**
     * Method under test: {@link PicturePokerGame#getPlayersByGame(String)}
     */
    @Test
    void testGetPlayersByGame() {
        // Arrange, Act and Assert
        assertTrue((new PicturePokerGame()).getPlayersByGame("42").isEmpty());
    }

    /**
     * Method under test: {@link PicturePokerGame#getAllPlayers()}
     */
    @Test
    void testGetAllPlayers() {
        // Arrange, Act and Assert
        assertTrue((new PicturePokerGame()).getAllPlayers().isEmpty());
    }

    /**
     * Method under test: {@link PicturePokerGame#getAllGames()}
     */
    @Test
    void testGetAllGames() {
        // Arrange, Act and Assert
        assertTrue((new PicturePokerGame()).getAllGames().isEmpty());
    }

    /**
     * Method under test: {@link PicturePokerGame#updateByPID(long, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateByPID() throws JsonProcessingException {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: argument "content" is null
        //       at com.fasterxml.jackson.databind.ObjectMapper._assertNotNull(ObjectMapper.java:4980)
        //       at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:3739)
        //       at game.jdbc.picturepoker.PicturePokerGame.updateByPID(PicturePokerGame.java:254)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange and Act
        (new PicturePokerGame()).updateByPID(1L, null);
    }

    /**
     * Method under test: {@link PicturePokerGame#updateByPID(long, String)}
     */
    @Test
    void testUpdateByPID2() throws JsonProcessingException {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange
        PicturePokerGame picturePokerGame = new PicturePokerGame();

        ObjectMapper objectMapper = new ObjectMapper();

        // Act
        picturePokerGame.updateByPID(1L, objectMapper.writeValueAsString(new HashMap<>()));
    }

    /**
     * Method under test: {@link PicturePokerGame#updateByGID(Long, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateByGID() throws JsonProcessingException {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: argument "content" is null
        //       at com.fasterxml.jackson.databind.ObjectMapper._assertNotNull(ObjectMapper.java:4980)
        //       at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:3739)
        //       at game.jdbc.picturepoker.PicturePokerGame.updateByGID(PicturePokerGame.java:302)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange and Act
        (new PicturePokerGame()).updateByGID(1L, null);
    }

    /**
     * Method under test: {@link PicturePokerGame#updateByGID(Long, String)}
     */
    @Test
    void testUpdateByGID2() throws JsonProcessingException {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange
        PicturePokerGame picturePokerGame = new PicturePokerGame();

        ObjectMapper objectMapper = new ObjectMapper();

        // Act
        picturePokerGame.updateByGID(1L, objectMapper.writeValueAsString(new HashMap<>()));
    }

    /**
     * Method under test: {@link PicturePokerGame#raiseBet(long)}
     */
    @Test
    void testRaiseBet() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange and Act
        (new PicturePokerGame()).raiseBet(1L);
    }

    /**
     * Method under test: {@link PicturePokerGame#toggleToChange(long, int)}
     */
    @Test
    void testToggleToChange() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange and Act
        (new PicturePokerGame()).toggleToChange(1L, 1);
    }

    /**
     * Method under test: {@link PicturePokerGame#finishRound(long)}
     */
    @Test
    void testFinishRound() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange and Act
        (new PicturePokerGame()).finishRound(1L);
    }

    /**
     * Method under test: {@link PicturePokerGame#deleteByPID(long)}
     */
    @Test
    void testDeleteByPID() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange and Act
        (new PicturePokerGame()).deleteByPID(1L);
    }

    /**
     * Method under test: {@link PicturePokerGame#deleteByGID(long)}
     */
    @Test
    void testDeleteByGID() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange and Act
        (new PicturePokerGame()).deleteByGID(1L);
    }

    /**
     * Method under test: {@link PicturePokerGame#leaveGame(long)}
     */
    @Test
    void testLeaveGame() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange and Act
        (new PicturePokerGame()).leaveGame(1L);
    }

    /**
     * Method under test: {@link PicturePokerGame#playGame(long)}
     */
    @Test
    void testPlayGame() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange and Act
        (new PicturePokerGame()).playGame(1L);
    }

    /**
     * Method under test: {@link PicturePokerGame#joinGame(long, long)}
     */
    @Test
    void testJoinGame() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange and Act
        (new PicturePokerGame()).joinGame(1L, 1L);
    }
}
