CREATE TABLE dealer_card(
    g_id bigint NOT NULL,
    hand_pos int NOT NULL,
    to_change boolean NOT NULL DEFAULT FALSE,
    suit varchar(20) NOT NULL,
    PRIMARY KEY (g_id, hand_pos),
    FOREIGN KEY (g_id) REFERENCES game(g_id) ON DELETE CASCADE,
    FOREIGN KEY (suit) REFERENCES suit(suit) ON DELETE CASCADE
);