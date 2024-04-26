import "./Create.css"
import React, {useEffect, useState} from 'react';
import create from "../../resources/menuIcons/creategame.png";
import back from "../../resources/misIcons/back.png";

import buyin from "../../resources/menuIcons/buy-in-4-3-2024.png"
import rounds from "../../resources/menuIcons/number-of-rounds-4-3-2024.png"

import arrow from "../../resources/incdec/Arrow_Sign_SMB3.webp";

import "../../GameMenu/Menu.css";
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";

import ColorfulText from "../../index";

function gameCreation(rounds, buyin, navigate, setReason){

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
            if (response2.data.players[i] === parseInt(sessionStorage.getItem('userID'))){
                navigate(`/WaitingRoom`, { state: { gameId: response2.data.id } });
            }
        }
    }
    makeGame();

    setReason( "Player couldn't join the game");
}

function CreateGame(){
    // counter which counts
    const [roundCount, setRoundCount] = useState(1);
    const [buyIn, setBuyIn] = useState(5);

    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    const [reason, setReason] = useState("");

    useEffect(() => {
        if (sessionStorage.getItem('userID') === null)
            navigate('/')
    }, []);
    const checkIfGood = async () => {
        const response = await axios.get(`http://localhost:8080/getByPlayerID/${sessionStorage.getItem('userID')}`)
            .catch(function(){
                console.log("Error with getByPlayerID");
            });

        // two checks - if the player can actually join a game, and if a player has enough cash to join
        if (response.status === 200) {
            const response2 = await axios.get(`http://localhost:8080/getPlayerActiveGame/${sessionStorage.getItem('userID')}`)
                .catch(function () {
                    console.log("Error with getByPlayerID");
                });
            //only if we pass all these checks do we let the player create a game.
            if (response.status === 200)
                if (response.data.dollars < buyIn) {
                    setReason("Too broke. Your funds: $" + response.data.dollars);
                }
                else if (response2.data === null){
                    gameCreation(roundCount, buyIn, navigate, setReason);
                }
                else if(response2.data.curRound <= response2.data.numRounds) {
                    setReason("In a game already. ");
                }
                else {
                    console.log(response2);
                    await axios.delete(`http://localhost:8080/leaveCurrentGame/${sessionStorage.getItem('userID')}`)
                        .catch(function() {
                            console.log("Error with leaveCurrentGame API call");
                    });
                    gameCreation(roundCount, buyIn, navigate, setReason);
                }
        }
        //we only get here if we couldn't actually make the game.
        setMessage("You cannot join the game. Reason: " + reason);
    }

    return (
        <div className="create-background">
            <div className="create-header">
                <img src={create} alt="Create Game" className="create-title"/>
                <Link to="/menu" className="back-button-container">
                    <img src={back} alt="Back" className="back-button glow"/>
                </Link>
            </div>
            <div className="config-container">
                <div className="config-section" >
                    <img src={rounds} style={{height: '22vh'}} alt={"Round Count"}/>
                    <button
                        onClick={() => roundCount > 1 && setRoundCount(roundCount - 1)}
                        className="glow">
                        <img src={arrow} alt="arrow pointing upwards" className="rotate90 arrow"/>
                    </button>
                    <div style={{fontSize: '17vh'}} className="bordering">
                        <ColorfulText text={roundCount}/>
                    </div>
                    <button
                        onClick={() => roundCount < 11 && setRoundCount(roundCount + 1)}
                        className="glow">
                        <img src={arrow} alt="arrow pointing downwards" className="rotateneg90 arrow"/>
                    </button>
                </div>
                <br/>
                <div className="config-section">
                    <img src={buyin} style={{height: '22vh'}} alt={"buyin"}/>
                    <button
                        onClick={() => buyIn > 0 && setBuyIn(buyIn - 5)}
                        className="glow">
                        <img src={arrow} alt="arrow pointing upwards" className="rotate90 arrow"/>
                    </button>
                    <span style={{fontSize: '17vh'}} className="bordering">
                        <ColorfulText text={"$" + buyIn}/>
                    </span>
                    <button
                        onClick={() => setBuyIn(buyIn + 5)}
                        className="glow">
                        <img src={arrow} alt="arrow pointing downwards" className="rotateneg90 arrow"/>
                    </button>
                </div>
            </div>

            <br/>
            <div className="config-section">
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
                <ColorfulText text={"Wealth: $" + sessionStorage.getItem('dollars')}/>
            </div>
        </div>
    );
}

export default CreateGame