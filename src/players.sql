CREATE SEQUENCE lp_pid_seq START WITH 1000;

CREATE TABLE player(
    pid bigint NOT NULL DEFAULT nextval(lp_pid_seq),
    pname varchar(50) DEFAULT NULL UNIQUE,
    passphrase varchar(50) DEFAULT NULL,
    dollars bigint NOT NULL DEFAULT 50,
    -- Per game information, accessed during game flow
    tokens int NOT NULL DEFAULT 10,
    bet int NOT NULL DEFAULT 0,
    rounds_won int NOT NULL DEFAULT 0,
    -- Overall statistics
    first_places int NOT NULL DEFAULT 0,
    second_places int NOT NULL DEFAULT 0,
    third_places int NOT NULL DEFAULT 0,
    last_places int NOT NULL DEFAULT 0,
    lifetime_tokens bigint NOT NULL DEFAULT 0,
    PRIMARY KEY (pid)
);