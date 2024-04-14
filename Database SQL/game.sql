CREATE SEQUENCE game_seq start with 100;

CREATE TABLE game (
    g_id bigint NOT NULL DEFAULT nextval('game_seq'),
    cur_round int NOT NULL DEFAULT 1,
    num_rounds int NOT NULL,
    active_players int NOT NULL,
    buy_in int NOT NULL,
    pot_quantity int NOT NULL DEFAULT 0, -- Calculated on game initialization once players join
    difficulty int NOT NULL,
    players_finished int NOT NULL default 0, -- Perform showdown and advance round once this equals 4
    PRIMARY KEY (g_id)
);
