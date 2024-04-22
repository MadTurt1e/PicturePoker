FROM postgres

# Copy the SQL files to the container
#COPY databaseCreation.sql /docker-entrypoint-initdb.d/0_setup.sql
COPY player.sql /docker-entrypoint-initdb.d/1_player.sql
COPY game.sql /docker-entrypoint-initdb.d/2_game.sql
COPY player_in_game.sql /docker-entrypoint-initdb.d/3_player_in_game.sql
COPY suits.sql /docker-entrypoint-initdb.d/4_suits.sql
COPY card.sql /docker-entrypoint-initdb.d/5_card.sql
COPY dealer_card.sql /docker-entrypoint-initdb.d/6_dealer_card.sql
COPY setup.sql /docker-entrypoint-initdb.d/7_sample_data.sql