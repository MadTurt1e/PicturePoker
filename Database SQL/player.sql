CREATE SEQUENCE player_seq start with 0;

CREATE TABLE player
(
    p_ID int NOT NULL DEFAULT nextval('player_seq'),
    name varchar(50) NOT NULL UNIQUE,
    password varchar(50) NOT NULL,
    gamesPlayed int NOT NULL,
    firsts int NOT NULL,
    seconds int NOT NULL,
    thirds  int NOT NULL,
    fourths int NOT NULL,
    dollars int NOT NULL,

    --Per game info - Zeroed out, and refreshed upon each new game
    tokens int NOT NULL,
    bet int NOT NULL,
    rounds_won int NOT NULL,

    PRIMARY KEY(p_ID)
);