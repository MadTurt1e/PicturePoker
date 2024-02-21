CREATE SEQUENCE player_seq start with 100;

CREATE TABLE player
(
    p_id int NOT NULL DEFAULT nextval('player_seq'),
    p_name varchar(50) NOT NULL UNIQUE,
    password varchar(50) NOT NULL,
    dollars int NOT NULL DEFAULT 50,

    -- User Statistics
    firsts int NOT NULL DEFAULT 0,
    seconds int NOT NULL DEFAULT 0,
    thirds  int NOT NULL DEFAULT 0,
    fourths int NOT NULL DEFAULT 0,

    --Per game info - Zeroed out, and refreshed upon each new game
    tokens int NOT NULL DEFAULT 10,
    bet int NOT NULL DEFAULT 0,
    rounds_won int NOT NULL DEFAULT 0,

    PRIMARY KEY(p_id)
);