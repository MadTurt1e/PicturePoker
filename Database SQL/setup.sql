INSERT INTO player (p_id, p_name, password)
VALUES
(0, NULL, "password")
(1, "MarioMario", "AhSpaghetti"),
(2, "WarioMan", "MoneyMoneyMoney"),
(3, "JeffreyW", "securepassword"),
(4, "Richard", "insecurepassword"),
(5, "Malek", "password123");

INSERT INTO game (p1_id, p2_id, p3_id, p4_id, num_rounds, active_players, pot_quantity, difficulty)
VALUES
(1, 3, 4, 5, 5, 4, 100, 1),
(1, 2, 4, 5, 7, 4, 20, 0);