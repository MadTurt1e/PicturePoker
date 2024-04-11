import "./Create.css"
import React, { useEffect, useState } from 'react';
import create from "../../resources/menuIcons/creategame.png";
import backdrop from "../../resources/menuIcons/luigisCasino.jpg";

import buyin from "../../resources/menuIcons/buy-in-4-3-2024.png"
import rounds from "../../resources/menuIcons/number-of-rounds-4-3-2024.png"

import arrow from "../../resources/incdec/Arrow_Sign_SMB3.webp";

import "../../GameMenu/Menu.css";
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";

import ColorfulText from "../../index";

function gameCreation(rounds, buyin, navigate){
    //TODO: Check to make sure the game creator can actually join the game
    const makeGame = async () => {
        const gameDetails = {
            "rounds": rounds.toString(), // number of rounds
            "buyIn": buyin.toString(), // buy-in amount
            "difficulty": "1" // difficulty level
        };

        const response = await axios.post('http://localhost:8080/createNewGame', gameDetails)
            .catch(function(error){
                console.log("Error with createNewGame");
                return (
                    <div>
                        Game could not be created.
                    </div>
                )
            });
        //use navigate with additional parameters
        navigate(`/WaitingRoom`, { state: { gameId: response.data.id } });
    }
    makeGame();

    return;
}

function CreateGame(){
    // counter which counts
    const [counter, setCounter] = useState(0);
    const [counter10, setCounter10] = useState(0);

    const [message, setMessage] = useState(null);
    const navigate = useNavigate();

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
                    onClick={() => counter > 0 && setCounter(counter - 1)}
                    className="glow">
                    <img src={arrow} alt="arrow pointing upwards" className="rotate90"/>
                </button>
                <div style={{fontSize: '10vh'}} className="bordering">
                    <ColorfulText text={counter}/>
                </div>
                <button
                    onClick={() => counter < 11 && setCounter(counter + 1)}
                    className="glow">
                    <img src={arrow} alt="arrow pointing downwards" className="rotateneg90"/>
                </button>
            </div>
            <br/>
            <div className="Buy In" style={{display: 'flex'}}>
                <img src={buyin} style={{height: '10vh'}} alt={"buyin"}/>
                <button
                    onClick={() => counter10 > 0 && setCounter10(counter10 - 10)}
                    className="glow">
                    <img src={arrow} alt="arrow pointing upwards" className="rotate90"/>
                </button>
                <span style={{fontSize: '10vh', fontFamily: "MarioFont", color: "green"}} className="bordering">
                    <ColorfulText text={counter10}/>
                </span>
                <button
                    onClick={() => setCounter10(counter10 + 10)}
                    className="glow">
                    <img src={arrow} alt="arrow pointing downwards" className="rotateneg90"/>
                </button>
            </div>

            <br/>
            <div>
                <button type={"button"} style={{height: '10vh', width: '20hh', border: "black", borderWidth: "10px"}}
                        className="glow" onClick={() => gameCreation(counter, counter10, navigate)}>
                    <div style={{fontSize: '5vh'}} className="bordering">
                        <ColorfulText text="Create Game! "/>
                    </div>
                </button>
            </div>
            <div style={{position: "absolute", right: "5%", bottom: "5%", fontSize: "5vh"}} className={"bordering"}>
                <ColorfulText text={"Player: " + sessionStorage.getItem('username')}/>
            </div>
        </div>
    );
}

export default CreateGame