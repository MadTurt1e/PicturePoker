INSERT INTO player (p_id, p_name, passcode) VALUES (1, 'MarioMario', 'AhSpaghetti');
INSERT INTO player (p_id, p_name, passcode) VALUES (2, 'WarioMan', 'MoneyMoneyMoney');
INSERT INTO player (p_id, p_name, passcode) VALUES (3, 'JeffreyW', 'securepassword');
INSERT INTO player (p_id, p_name, passcode) VALUES (4, 'Richard', 'insecurepassword');
INSERT INTO player (p_id, p_name, passcode) VALUES (5, 'Malek', 'password123');
INSERT INTO player (p_id, p_name, passcode) VALUES (6, 'Annie', 'passwordinkcherry');
INSERT INTO player (p_id, p_name, passcode) VALUES (7, 'Chris', 'passwordthaiicedtea');
INSERT INTO player (p_id, p_name, passcode) VALUES (8, 'Bowser', 'kingofkoopas');

INSERT INTO game (g_id, num_rounds, active_players, buy_in, pot_quantity, difficulty) VALUES (14, 5, 4, 10, 100, 1);
INSERT INTO game (g_id, cur_round, num_rounds, active_players, buy_in, pot_quantity, difficulty) VALUES (45, 7, 7, 4, 100, 20, 0);

INSERT INTO player_in_game (p_id, g_id) VALUES (1, 14), (2, 14), (4, 14), (6, 14), (3, 45), (5, 45), (7, 45), (8, 45);

INSERT INTO player_card (p_id, hand_pos, suit) VALUES
(1, 0, 'MARIO'), (1, 1, 'CLOUD'), (1, 2, 'FIRE_FLOWER'), (1, 3, 'MARIO'), (1, 4, 'STAR'),
(2, 0, 'CLOUD'), (2, 1, 'MARIO'), (2, 2, 'CLOUD'), (2, 3, 'MARIO'), (2, 4, 'CLOUD'),
(3, 0, 'LUIGI'), (3, 1, 'FIRE_FLOWER'), (3, 2, 'CLOUD'), (3, 3, 'MUSHROOM'), (3, 4, 'CLOUD'),
(4, 0, 'FIRE_FLOWER'), (4, 1, 'STAR'), (4, 2, 'LUIGI'), (4, 3, 'STAR'), (4, 4, 'MUSHROOM'),
(5, 0, 'MUSHROOM'), (5, 1, 'MUSHROOM'), (5, 2, 'LUIGI'), (5, 3, 'CLOUD'), (5, 4, 'MARIO'),
(6, 0, 'LUIGI'), (6, 1, 'MUSHROOM'), (6, 2, 'STAR'), (6, 3, 'STAR'), (6, 4, 'STAR'),
(7, 0, 'MARIO'), (7, 1, 'MARIO'), (7, 2, 'STAR'), (7, 3, 'FIRE_FLOWER'), (7, 4, 'MUSHROOM'),
(8, 0, 'FIRE_FLOWER'), (8, 1, 'FIRE_FLOWER'), (8, 2, 'MARIO'), (8, 3, 'STAR'), (8, 4, 'FIRE_FLOWER');

INSERT INTO dealer_card (g_id, hand_pos, suit) VALUES
(14, 0, 'FIRE_FLOWER'), (14, 1, 'CLOUD'), (14, 2, 'LUIGI'), (14, 3, 'FIRE_FLOWER'), (14, 4, 'FIRE_FLOWER'),
(45, 0, 'MUSHROOM'), (45, 1, 'STAR'), (45, 2, 'MUSHROOM'), (45, 3, 'MARIO'), (45, 4, 'LUIGI');