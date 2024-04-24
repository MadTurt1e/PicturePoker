import "./CreateGame/Create.css";

import stats from "../resources/menuIcons/statistics.png";
import backdrop from "../resources/menuIcons/luigisCasino.jpg";

import {Link} from "react-router-dom";
import React, {useEffect, useState} from "react";
import ColorfulText from "../index";
import axios from "axios";

function Stats(){
    const [data, setData] = useState(null);

    //the call to get the game info
    useEffect(() => {
        const loadGame = async () => {
            // Replace this with the updated API call
            const response = await axios.get(`http://localhost:8080/getByPlayerID/` + sessionStorage.getItem('userID'))
                .catch(function(){
                    console.log("getByPlayerID didn't work. ");
                });
            setData(response.data);
        }
        loadGame();
    }, []);


    if (data) {
        sessionStorage.setItem('dollars', data.dollars);
        return (
            <div style={{fontSize: "3vh", height: '80%', overflow: 'scroll'}} className={"bordering"}>
                <ColorfulText text={"Username: " + data.playerName}/>
                <ColorfulText text={"Dollars: " + data.dollars}/>
                <br/>
                <ColorfulText text={"Firsts: " + data.firstPlaces}/>
                <ColorfulText text={"Seconds: " + data.secondPlaces}/>
                <ColorfulText text={"Thirds: " + data.thirdPlaces}/>
                <ColorfulText text={"Fourths: " + data.fourthPlaces}/>
                <br/>
                <ColorfulText text={"Games Played: " + data.gamesPlayed}/>
                <ColorfulText text={"Hands Played: " + data.handsPlayed}/>
                <br/>
                <ColorfulText text={"Average Bet: " + Math.round(data.avgBet*100)/100}/>
                <ColorfulText text={"Cards Changed: " + data.cardsChanged}/>
                <ColorfulText text={"Average Cards Changed: " + Math.round(data.avgCardsChanged*100)/100}/>
                <ColorfulText text={"Average Lifetime Tokens: " + Math.round(data.avgLifetimeTokens*10)/10}/>
                <br/>
                <ColorfulText text={"Lifetime Rounds Won: " + data.lifetimeRoundsWon}/>
                <ColorfulText text={"Lifetime Tokens: " + data.lifetimeTokens}/>
                <ColorfulText text={"Lifetime Total Bet: " + data.lifetimeTotalBet}/>
                <br/>
                <ColorfulText text={"High Cards: " + data.highCards}/>
                <ColorfulText text={"One Pairs: " + data.onePairs}/>
                <ColorfulText text={"Two Pairs: " + data.twoPairs}/>
                <ColorfulText text={"Three of a Kind: " + data.triples}/>
                <ColorfulText text={"Full Houses: " + data.fullHouses}/>
                <ColorfulText text={"Quads: " + data.quads}/>
                <ColorfulText text={"Flushes: " + data.flushes}/>
                <br/>
                <ColorfulText text={"Rounds Won: " + data.roundsWon}/>
                <ColorfulText text={"Round Winrate: " + Math.round(data.roundWinrate) + "%"}/>
            </div>
        );
    }
}

function Statistics() {
    //this is where we'd call the API to pull the player statistics


    return (
        <div style={{
            backgroundImage: `url(${backdrop})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            height: '100vh',
            width: '100vw'
        }}>
            <Link to="/menu">
                <img src={stats} alt="stats" style={{width: '20%', marginBottom: '10vh'}}/>
            </Link>
            <Stats/>
            <div style={{position: "absolute", right: "5%", bottom: "5%", fontSize: "5vh"}} className={"bordering"}>
                <ColorfulText text={"Player: " + sessionStorage.getItem('username')}/>
            </div>
        </div>
    );
}

export default Statistics