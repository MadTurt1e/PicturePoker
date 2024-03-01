INSERT INTO player (p_id, p_name, passcode) VALUES (1, 'MarioMario', 'AhSpaghetti');
INSERT INTO player (p_id, p_name, passcode) VALUES (2, 'WarioMan', 'MoneyMoneyMoney');
INSERT INTO player (p_id, p_name, passcode) VALUES (3, 'JeffreyW', 'securepassword');
INSERT INTO player (p_id, p_name, passcode) VALUES (4, 'Richard', 'insecurepassword');
INSERT INTO player (p_id, p_name, passcode) VALUES (5, 'Malek', 'password123');
INSERT INTO player (p_id, p_name, passcode) VALUES (6, 'Annie', 'passwordinkcherry');
INSERT INTO player (p_id, p_name, passcode) VALUES (7, 'Chris', 'passwordthaiicedtea');
INSERT INTO player (p_id, p_name, passcode) VALUES (8, 'Smithy', 'ilikemurder');

INSERT INTO game (g_id, num_rounds, active_players, buy_in, pot_quantity, difficulty) VALUES (14, 5, 4, 10, 100, 1);
INSERT INTO game (g_id, cur_round, num_rounds, active_players, buy_in, pot_quantity, difficulty) VALUES (645, 7, 7, 4, 100, 20, 0);

INSERT INTO player_in_game (p_id, g_id) VALUES (1, 14), (2, 14), (4, 14), (6, 14), (3, 645), (5, 645), (7, 645), (8, 645);