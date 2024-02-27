import subprocess
import time

subprocess.run(["psql", "-h", "localhost", "-U", "postgres", "-f", "databaseCreation.sql"])
time.sleep(1)
subprocess.run(["psql", "-h", "localhost", "-U", "postgres", "-d", "picturepoker", "-f", "player.sql"])
time.sleep(1)
subprocess.run(["psql", "-h", "localhost", "-U", "postgres", "-d", "picturepoker", "-f", "game.sql"])
time.sleep(1)
subprocess.run(["psql", "-h", "localhost", "-U", "postgres", "-d", "picturepoker", "-f", "player_in_game.sql"])
time.sleep(1)
subprocess.run(["psql", "-h", "localhost", "-U", "postgres", "-d", "picturepoker", "-f", "suits.sql"])
time.sleep(1)
subprocess.run(["psql", "-h", "localhost", "-U", "postgres", "-d", "picturepoker", "-f", "card.sql"])
time.sleep(1)
subprocess.run(["psql", "-h", "localhost", "-U", "postgres", "-d", "picturepoker", "-f", "setup.sql"])