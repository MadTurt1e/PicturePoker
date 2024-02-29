CREATE SEQUENCE game_seq start with 100;

CREATE TABLE game (
    g_id bigint NOT NULL DEFAULT nextval('game_seq'),
    p1_id bigint DEFAULT NULL,
    p2_id bigint DEFAULT NULL,
    p3_id bigint DEFAULT NULL,
    p4_id bigint DEFAULT NULL,
    cur_round int NOT NULL DEFAULT 1,
    num_rounds int NOT NULL,
    active_player int NOT NULL,
    buy_in int NOT NULL,
    pot_quantity int NOT NULL DEFAULT 0, -- Calculated on game initialization once players join
    difficulty int NOT NULL,
    winner bigint DEFAULT NULL,
    PRIMARY KEY (g_id),
    FOREIGN KEY (p1_id) references player(p_id) ON DELETE CASCADE,
    FOREIGN KEY (p2_id) references player(p_id) ON DELETE CASCADE,
    FOREIGN KEY (p3_id) references player(p_id) ON DELETE CASCADE,
    FOREIGN KEY (p4_id) references player(p_id) ON DELETE CASCADE,
    FOREIGN KEY (winner) references player(p_id) ON DELETE CASCADE,
    --Check to ensure the ids are not the same - i wish there was a better way for this.
    CHECK (p1_id <> p2_id AND p1_id <> p3_id AND p1_id <> p4_id AND p2_id <> p3_id AND p2_id <> p4_id AND p3_id <> p4_id),
    --check to ensure winner is valid if specified
    CHECK (winner = p1_id OR winner = p2_id OR winner = p3_id OR winner = p4_id OR winner = NULL)
);
