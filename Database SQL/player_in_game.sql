CREATE TABLE player_in_game(
    p_id bigint NOT NULL UNIQUE,
    g_id bigint NOT NULL,
    PRIMARY KEY (p_id, g_id),
    FOREIGN KEY (p_id) references player(p_id) ON DELETE CASCADE,
    FOREIGN KEY (g_id) references game(g_id) ON DELETE CASCADE
);