CREATE SEQUENCE lp_game_seq START WITH 1;

CREATE TABLE game(
    gid bigint NOT NULL DEFAULT nextval(lp_game_seq),
    player1 varchar(50) NOT NULL,
    player2 varchar(50) NOT NULL,
    player3 varchar(50) NOT NULL,
    player4 varchar(50) NOT NULL,
    -- Game options
    cur_round int NOT NULL DEFAULT 1,
    num_rounds int NOT NULL,
    pot int NOT NULL,
    difficulty varchar(50) DEFAULT NULL,
    PRIMARY KEY(gid),
    FOREIGN KEY (player1) NOT NULL REFERENCES player(pname),
    FOREIGN KEY (player2) NOT NULL REFERENCES player(pname),
    FOREIGN KEY (player3) NOT NULL REFERENCES player(pname),
    FOREIGN KEY (player4) NOT NULL REFERENCES player(pname)
);