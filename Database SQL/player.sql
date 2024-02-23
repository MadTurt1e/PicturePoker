CREATE SEQUENCE player_seq start with 100;

CREATE TABLE player
(
    p_id bigint NOT NULL DEFAULT nextval('player_seq'),
    p_name varchar(50) NOT NULL UNIQUE,
    passcode varchar(50) NOT NULL,
    dollars int NOT NULL DEFAULT 50,

    -- User Statistics
    first_places int NOT NULL DEFAULT 0,
    second_places int NOT NULL DEFAULT 0,
    third_places  int NOT NULL DEFAULT 0,
    fourth_places int NOT NULL DEFAULT 0,

    --Per game info - Zeroed out, and refreshed upon each new game
    tokens int NOT NULL DEFAULT 10,
    bet int NOT NULL DEFAULT 0,
    rounds_won int NOT NULL DEFAULT 0,

    PRIMARY KEY(p_id)
);