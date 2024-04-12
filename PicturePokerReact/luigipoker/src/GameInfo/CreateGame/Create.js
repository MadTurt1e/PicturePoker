import "./Create.css"
import React, {useState } from 'react';
import create from "../../resources/menuIcons/creategame.png";
import backdrop from "../../resources/menuIcons/luigisCasino.jpg";

import buyin from "../../resources/menuIcons/buy-in-4-3-2024.png"
import rounds from "../../resources/menuIcons/number-of-rounds-4-3-2024.png"

import arrow from "../../resources/incdec/Arrow_Sign_SMB3.webp";

import "../../GameMenu/Menu.css";
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";

import ColorfulText from "../../index";

function gameCreation(rounds, buyin, navigate, state){
    //TODO: Check to make sure the game creator can actually join the game
    // game check implemented here

    const makeGame = async () => {
        const gameDetails = {
            "rounds": rounds.toString(), // number of rounds
            "buyIn": buyin.toString(), // buy-in amount
            "difficulty": "1" // difficulty level
        };

        const response = await axios.post('http://localhost:8080/createNewGame', gameDetails)
            .catch(function(){
                console.log("Error with createNewGame");
            });

        //try joining the game we just made
        let response2 = await axios.put(`http://localhost:8080/joinGame/${response.data.id}/${sessionStorage.getItem('userID')}`)
            .catch(function () {
                console.log("joinGame didn't work. ");
            });
        //a quick check to see if the player was able to join by scanning the player list. Only than do we let them in.
        for (let i=0; i < response2.data.players.length; i++){
            if (response2.data.players[i] === sessionStorage.getItem('userID')){
                navigate(`/WaitingRoom`, { state: { gameId: response2.data.id } });
            }
        }
        state = "Player couldn't join the game";
    }
    makeGame();
}

function CreateGame(){
    // counter which counts
    const [roundCount, setRoundCount] = useState(0);
    const [buyIn, setCounter10] = useState(0);

    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    const checkIfGood = async () => {
        const response = await axios.get(`http://localhost:8080/getByPlayerID/${sessionStorage.getItem('userID')}`)
            .catch(function(){
                console.log("Error with getByPlayerID");
            });

        let state = "Server problems. ";
        // two checks - if the player can actually join a game, and if a player has enough cash to join
        if (response.status === 200) {
            const response2 = await axios.get(`http://localhost:8080/getPlayerActiveGame/${sessionStorage.getItem('userID')}`)
                .catch(function () {
                    console.log("Error with getByPlayerID");
                });
            //only if we pass all these checks do we let the player create a game.
            if (response.status === 200)
                if (response.data.dollars < buyIn) {
                    state = "Too broke. Your funds: $" + response.data.dollars;
                }
                else if (response2.data === null){
                    gameCreation(roundCount, buyIn, navigate, state);
                }
                else if(response2.data.curRound !== response2.data.numRounds) {
                    state = "In a game already. ";
                }
                else {
                    gameCreation(roundCount, buyIn, navigate, state);
                }
        }
        //we only get here if we couldn't actually make the game.
        setMessage("You cannot join the game. Reason: " + state);
    }

    return (
        <div style={{
            backgroundImage: `url(${backdrop})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            height: '100vh',
            width: '100vw'
        }}>
            <Link to="/menu">
                <img src={create} alt="" style={{width: '60%', marginBottom: '10vh'}}/>
            </Link>

            <br/>
            <div className="RoundCount" style={{display: 'flex'}}>
                <img src={rounds} style={{height: '10vh'}} alt={"Round Count"}/>
                <button
                    onClick={() => roundCount > 0 && setRoundCount(roundCount - 1)}
                    className="glow">
                    <img src={arrow} alt="arrow pointing upwards" className="rotate90"/>
                </button>
                <div style={{fontSize: '10vh'}} className="bordering">
                    <ColorfulText text={roundCount}/>
                </div>
                <button
                    onClick={() => roundCount < 11 && setRoundCount(roundCount + 1)}
                    className="glow">
                    <img src={arrow} alt="arrow pointing downwards" className="rotateneg90"/>
                </button>
            </div>
            <br/>
            <div className="Buy In" style={{display: 'flex'}}>
                <img src={buyin} style={{height: '10vh'}} alt={"buyin"}/>
                <button
                    onClick={() => buyIn > 0 && setCounter10(buyIn - 10)}
                    className="glow">
                    <img src={arrow} alt="arrow pointing upwards" className="rotate90"/>
                </button>
                <span style={{fontSize: '10vh', fontFamily: "MarioFont", color: "green"}} className="bordering">
                    <ColorfulText text={buyIn}/>
                </span>
                <button
                    onClick={() => setCounter10(buyIn + 10)}
                    className="glow">
                    <img src={arrow} alt="arrow pointing downwards" className="rotateneg90"/>
                </button>
            </div>

            <br/>
            <div>
                <button type={"button"} style={{height: '10vh', width: '20hh', border: "black", borderWidth: "10px"}}
                        className="glow" onClick={() => checkIfGood()}>
                    <div style={{fontSize: '5vh'}} className="bordering">
                        <ColorfulText text="Create Game! "/>
                    </div>
                </button>
            </div>
            <div style={{fontSize: "4vh"}} className={"bordering"}>
                <ColorfulText text={message}/>
            </div>
            <div style={{position: "absolute", right: "5%", bottom: "5%", fontSize: "5vh"}} className={"bordering"}>
                <ColorfulText text={"Player: " + sessionStorage.getItem('username')}/>
            </div>
        </div>
    );
}

export default CreateGame