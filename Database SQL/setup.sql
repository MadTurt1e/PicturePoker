INSERT INTO player (p_id, p_name, passcode) VALUES (0, NULL, 'password');
INSERT INTO player (p_id, p_name, passcode) VALUES (1, 'MarioMario', 'AhSpaghetti');
INSERT INTO player (p_id, p_name, passcode) VALUES (2, 'WarioMan', 'MoneyMoneyMoney');
INSERT INTO player (p_id, p_name, passcode) VALUES (3, 'JeffreyW', 'securepassword');
INSERT INTO player (p_id, p_name, passcode) VALUES (4, 'Richard', 'insecurepassword');
INSERT INTO player (p_id, p_name, passcode) VALUES (5, 'Malek', 'password123');

INSERT INTO game (p1_id, p2_id, p3_id, p4_id, num_rounds, active_players, pot_quantity, difficulty)VALUES(1, 3, 4, 5, 5, 4, 100, 1);
INSERT INTO game (p1_id, p2_id, p3_id, p4_id, cur_round, num_rounds, active_players, pot_quantity, difficulty)VALUES(1, 2, 4, 5, 7, 7, 4, 20, 0);