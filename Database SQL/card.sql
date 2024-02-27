CREATE TABLE player_card(
    p_id bigint NOT NULL,
    hand_pos int NOT NULL,
    to_change boolean NOT NULL DEFAULT FALSE,
    suit varchar(20) NOT NULL,
    PRIMARY KEY (p_id, hand_pos),
    FOREIGN KEY (p_id) REFERENCES player(p_id) ON DELETE CASCADE,
    FOREIGN KEY (suit) REFERENCES suit(suit) ON DELETE CASCADE
);