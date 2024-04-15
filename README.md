**LUIGI'S PICTURE POKER - FOR ECE-366**

A game where anybody and everybody can be scammed by Luigi. Multiplayer, so you can see who is the worst or the best at getting scammed by Luigi. 

In this multiplayer version of Picture Poker, players can bet "money" on games and try to accumulate as many tokens as possible by creating strong hands to beat Luigi. The players with the most tokens will receive larger portions of the pot. During each round, players can bet up to five tokens and may select and redraw any and all of the five cards in their hands in an attempt to improve their hand. After each player has bet and redrawn, Luigi selects cards from his hands to redraw using a semi-intelligent algorithm, then each player compares their hand to Luigi's to see if they win big or lose their bet.

This version of Picture Poker also features profiles for players, tracking how you've placed in games and how many tokens you've accumulated over the course of play.

**TO RUN LOCALLY**

Once all files are downloaded, navigate into the directory, and do 

```shell
docker compose up
```

to build the file. As of this version, you may also need to run 
```shell
Database\ SQL/databaseCreation.py
```

to get the database built up. 

Note that as seen in the report, we have achieved the mandatory test coverage - 50% of the lines have been covered by tests, which is more than enough for our purposes. 
![img.png](report.png)