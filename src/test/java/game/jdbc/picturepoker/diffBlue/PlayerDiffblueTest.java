package game.jdbc.picturepoker.diffBlue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import game.jdbc.picturepoker.Card;
import game.jdbc.picturepoker.Player;
import org.junit.jupiter.api.Test;

class PlayerDiffblueTest {
  /**
   * Method under test: {@link Player#compareTo(Player)}
   */
  @Test
  void testCompareTo() {
    // Arrange
    Player player = new Player();

    // Act and Assert
    assertEquals(0, player.compareTo(new Player()));
  }

  /**
   * Method under test: {@link Player#compareTo(Player)}
   */
  @Test
  void testCompareTo2() {
    // Arrange
    Player player = new Player();
    player.setTokens(1);

    // Act and Assert
    assertEquals(-1, player.compareTo(new Player()));
  }

  /**
   * Method under test: {@link Player#compareTo(Player)}
   */
  @Test
  void testCompareTo3() {
    // Arrange
    Player player = new Player();
    player.setRoundsWon(1);

    // Act and Assert
    assertEquals(-1, player.compareTo(new Player()));
  }

  /**
   * Method under test: {@link Player#raise()}
   */
  @Test
  void testRaise() {
    // Arrange
    Player player = new Player();

    // Act and Assert
    assertEquals(-1, player.raise());
    assertEquals(0, player.getBet());
  }

  /**
   * Method under test: {@link Player#raise()}
   */
  @Test
  void testRaise2() {
    // Arrange
    Player player = new Player();
    player.setBet(5);
    player.setTokens(1);

    // Act and Assert
    assertEquals(-1, player.raise());
    assertEquals(5, player.getBet());
  }

  /**
   * Method under test: {@link Player#raise()}
   */
  @Test
  void testRaise3() {
    // Arrange
    Player player = new Player();
    player.setBet(4);
    player.setTokens(1);

    // Act
    int actualRaiseResult = player.raise();

    // Assert
    assertEquals(0, player.getTokens());
    assertEquals(0, actualRaiseResult);
    assertEquals(5, player.getBet());
  }

  /**
   * Method under test: {@link Player#getGamesPlayed()}
   */
  @Test
  void testGetGamesPlayed() {
    // Arrange, Act and Assert
    assertEquals(0, (new Player()).getGamesPlayed());
  }

  /**
   * Method under test: {@link Player#getHandsPlayed()}
   */
  @Test
  void testGetHandsPlayed() {
    // Arrange, Act and Assert
    assertEquals(0, (new Player()).getHandsPlayed());
  }

  /**
   * Method under test: {@link Player#getAvgLifetimeTokens()}
   */
  @Test
  void testGetAvgLifetimeTokens() {
    // Arrange, Act and Assert
    assertEquals(Double.NaN, (new Player()).getAvgLifetimeTokens());
  }

  /**
   * Method under test: {@link Player#getAvgBet()}
   */
  @Test
  void testGetAvgBet() {
    // Arrange, Act and Assert
    assertEquals(Double.NaN, (new Player()).getAvgBet());
  }

  /**
   * Method under test: {@link Player#getAvgCardsChanged()}
   */
  @Test
  void testGetAvgCardsChanged() {
    // Arrange, Act and Assert
    assertEquals(Double.NaN, (new Player()).getAvgCardsChanged());
  }

  /**
   * Method under test: {@link Player#getRoundWinrate()}
   */
  @Test
  void testGetRoundWinrate() {
    // Arrange, Act and Assert
    assertEquals(Double.NaN, (new Player()).getRoundWinrate());
  }

  /**
   * Methods under test:
   *
   * <ul>
   *   <li>{@link Player#setBet(int)}
   *   <li>{@link Player#setCardsChanged(int)}
   *   <li>{@link Player#setDollars(int)}
   *   <li>{@link Player#setFinishedRound(int)}
   *   <li>{@link Player#setFirstPlaces(int)}
   *   <li>{@link Player#setFlushes(int)}
   *   <li>{@link Player#setFourthPlaces(int)}
   *   <li>{@link Player#setFullHouses(int)}
   *   <li>{@link Player#setHand(Card[])}
   *   <li>{@link Player#setHighCards(int)}
   *   <li>{@link Player#setID(long)}
   *   <li>{@link Player#setLifetimeRoundsWon(int)}
   *   <li>{@link Player#setLifetimeTokens(int)}
   *   <li>{@link Player#setLifetimeTotalBet(int)}
   *   <li>{@link Player#setOnePairs(int)}
   *   <li>{@link Player#setPasscode(String)}
   *   <li>{@link Player#setPlayerName(String)}
   *   <li>{@link Player#setQuads(int)}
   *   <li>{@link Player#setRoundsWon(int)}
   *   <li>{@link Player#setSecondPlaces(int)}
   *   <li>{@link Player#setThirdPlaces(int)}
   *   <li>{@link Player#setTokens(int)}
   *   <li>{@link Player#setTriples(int)}
   *   <li>{@link Player#setTwoPairs(int)}
   *   <li>{@link Player#toString()}
   *   <li>{@link Player#getBet()}
   *   <li>{@link Player#getCardsChanged()}
   *   <li>{@link Player#getDollars()}
   *   <li>{@link Player#getFinishedRound()}
   *   <li>{@link Player#getFirstPlaces()}
   *   <li>{@link Player#getFlushes()}
   *   <li>{@link Player#getFourthPlaces()}
   *   <li>{@link Player#getFullHouses()}
   *   <li>{@link Player#getHand()}
   *   <li>{@link Player#getHighCards()}
   *   <li>{@link Player#getID()}
   *   <li>{@link Player#getLifetimeRoundsWon()}
   *   <li>{@link Player#getLifetimeTokens()}
   *   <li>{@link Player#getLifetimeTotalBet()}
   *   <li>{@link Player#getOnePairs()}
   *   <li>{@link Player#getPasscode()}
   *   <li>{@link Player#getPlayerName()}
   *   <li>{@link Player#getQuads()}
   *   <li>{@link Player#getRoundsWon()}
   *   <li>{@link Player#getSecondPlaces()}
   *   <li>{@link Player#getThirdPlaces()}
   *   <li>{@link Player#getTokens()}
   *   <li>{@link Player#getTriples()}
   *   <li>{@link Player#getTwoPairs()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    Player player = new Player();

    // Act
    player.setBet(1);
    player.setCardsChanged(1);
    player.setDollars(1);
    player.setFinishedRound(1);
    player.setFirstPlaces(1);
    player.setFlushes(1);
    player.setFourthPlaces(1);
    player.setFullHouses(1);
    Card[] hand = new Card[]{new Card()};
    player.setHand(hand);
    player.setHighCards(1);
    player.setID(1L);
    player.setLifetimeRoundsWon(1);
    player.setLifetimeTokens(1);
    player.setLifetimeTotalBet(1);
    player.setOnePairs(1);
    player.setPasscode("Passcode");
    player.setPlayerName("Name");
    player.setQuads(1);
    player.setRoundsWon(1);
    player.setSecondPlaces(1);
    player.setThirdPlaces(1);
    player.setTokens(1);
    player.setTriples(1);
    player.setTwoPairs(1);
    String actualToStringResult = player.toString();
    int actualBet = player.getBet();
    int actualCardsChanged = player.getCardsChanged();
    int actualDollars = player.getDollars();
    int actualFinishedRound = player.getFinishedRound();
    int actualFirstPlaces = player.getFirstPlaces();
    int actualFlushes = player.getFlushes();
    int actualFourthPlaces = player.getFourthPlaces();
    int actualFullHouses = player.getFullHouses();
    Card[] actualHand = player.getHand();
    int actualHighCards = player.getHighCards();
    long actualID = player.getID();
    int actualLifetimeRoundsWon = player.getLifetimeRoundsWon();
    int actualLifetimeTokens = player.getLifetimeTokens();
    int actualLifetimeTotalBet = player.getLifetimeTotalBet();
    int actualOnePairs = player.getOnePairs();
    String actualPasscode = player.getPasscode();
    String actualPlayerName = player.getPlayerName();
    int actualQuads = player.getQuads();
    int actualRoundsWon = player.getRoundsWon();
    int actualSecondPlaces = player.getSecondPlaces();
    int actualThirdPlaces = player.getThirdPlaces();
    int actualTokens = player.getTokens();
    int actualTriples = player.getTriples();

    // Assert that nothing has changed
    assertEquals("Name", actualPlayerName);
    assertEquals("Passcode", actualPasscode);
    assertEquals(
            "Player{p_ID=1, name='Name', passcode='Passcode', dollars=1, first_places=1, second_places=1, third_places=1,"
                    + " fourth_places=1, lifetime_tokens=1, lifetime_total_bet=1, flushes=1, four_of_a_kinds=1, full_houses=1,"
                    + " three_of_a_kinds=1, two_pair=1, one_pair=1, high_card=1, cards_changed=1, lifetime_rounds_won=1,"
                    + " tokens=1, bet=1, rounds_won=1, finished_round=1}",
            actualToStringResult);
    assertEquals(1, actualBet);
    assertEquals(1, actualCardsChanged);
    assertEquals(1, actualDollars);
    assertEquals(1, actualFinishedRound);
    assertEquals(1, actualFirstPlaces);
    assertEquals(1, actualFlushes);
    assertEquals(1, actualFourthPlaces);
    assertEquals(1, actualFullHouses);
    assertEquals(1, actualHighCards);
    assertEquals(1, actualLifetimeRoundsWon);
    assertEquals(1, actualLifetimeTokens);
    assertEquals(1, actualLifetimeTotalBet);
    assertEquals(1, actualOnePairs);
    assertEquals(1, actualQuads);
    assertEquals(1, actualRoundsWon);
    assertEquals(1, actualSecondPlaces);
    assertEquals(1, actualThirdPlaces);
    assertEquals(1, actualTokens);
    assertEquals(1, actualTriples);
    assertEquals(1, player.getTwoPairs());
    assertEquals(1L, actualID);
    assertSame(hand, actualHand);
  }
}
