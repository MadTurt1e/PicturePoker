import React, { useEffect, useState } from "react";
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";
import ColorfulText from "../index";
import statsIcon from "../resources/menuIcons/statistics.png";
import backdrop from "../resources/menuIcons/luigisCasino.jpg";
import "./Statistics.css";
import back from "../resources/misIcons/back.png";

function Stats() {
  const [data, setData] = useState(null);
  const navigate = useNavigate();

  // the call to get the game info
  useEffect(() => {
      if (sessionStorage.getItem('userID') === null)
          navigate('/')

        const loadGame = async () => {
            // Replace this with the updated API call
            const response = await axios.get(`${sessionStorage.getItem('host')}/getByPlayerID/` + sessionStorage.getItem('userID'))
                .catch(function(){
                    console.log("getByPlayerID didn't work. ");
                });
            if (response !== undefined)
                setData(response.data);
        }
        loadGame();
    }, []);

  if (!data) {
    return <div>Loading...</div>;
  }

  console.log(data);

  return (
    <div className="stats-container bordering">
      <div className="player-info">
            <ColorfulText text={"Username: " + data.playerName} />
            <ColorfulText text={"Dollars: " + data.dollars} />
      </div>
      <div className="game-stats">
            <ColorfulText text={"Firsts: " + data.firstPlaces} />
            <ColorfulText text={"Seconds: " + data.secondPlaces} />
            <ColorfulText text={"Thirds: " + data.thirdPlaces}/>
            <ColorfulText text={"Fourths: " + data.fourthPlaces}/>
            <ColorfulText text={"Games Played: " + data.gamesPlayed}/>
            <ColorfulText text={"Hands Played: " + data.handsPlayed}/>
      </div>
      <div className="bet-stats">
            <ColorfulText text={"Average Bet: " + Math.round(data.avgBet*100)/100}/>
            <ColorfulText text={"Cards Changed: " + data.cardsChanged}/>
            <ColorfulText text={"Average Cards Changed: " + Math.round(data.avgCardsChanged*100)/100}/>
            <ColorfulText text={"Average Lifetime Tokens: " + Math.round(data.avgLifetimeTokens*10)/10}/>
            <ColorfulText text={"Lifetime Rounds Won: " + data.lifetimeRoundsWon}/>
            <ColorfulText text={"Lifetime Tokens: " + data.lifetimeTokens}/>
            <ColorfulText text={"Lifetime Total Bet: " + data.lifetimeTotalBet}/>
      </div>
      <div className="hand-stats">
            <ColorfulText text={"High Cards: " + data.highCards}/>
            <ColorfulText text={"One Pairs: " + data.onePairs}/>
            <ColorfulText text={"Two Pairs: " + data.twoPairs}/>
            <ColorfulText text={"Three of a Kind: " + data.triples}/>
            <ColorfulText text={"Full Houses: " + data.fullHouses}/>
            <ColorfulText text={"Quads: " + data.quads}/>
            <ColorfulText text={"Flushes: " + data.flushes}/>
      </div>
      <div className="round-stats">
            <ColorfulText text={"Rounds Won: " + data.roundsWon}/>
            <ColorfulText text={"Round Winrate: " + Math.round(data.roundWinrate) + "%"}/>
      </div>
    </div>
  );
}

function Statistics() {
    return (
    <div className="statistics-page">
      <div className="statistics-header">
        <img src={statsIcon} alt="stats" />
        </div>
      <div className="back-button-container">
        <Link to="/menu">
          <img src={back} alt="Back" className="back-button" />
        </Link>
      </div>
      <Stats />
      <div className="player-footer bordering">
        <ColorfulText text={"Player: " + sessionStorage.getItem('username')} />
      </div>
    </div>
  );
}

export default Statistics;
