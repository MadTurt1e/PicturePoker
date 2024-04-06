import "./Create.css"
import React, { useState } from 'react';
import create from "../../resources/menuIcons/creategame.png";
import backdrop from "../../resources/menuIcons/luigisCasino.jpg";

import buyin from "../../resources/menuIcons/buy-in-4-3-2024.png"
import rounds from "../../resources/menuIcons/number-of-rounds-4-3-2024.png"

import arrow from "../../resources/incdec/Arrow_Sign_SMB3.webp";

import "../../GameMenu/Menu.css";
import {Link} from "react-router-dom";
import axios from "axios";

import ColorfulText from "../../index";

function gameCreation(rounds, buyin){

    //TODO: Check to make sure the game creator can actually join the game
    console.log("Game created - " + rounds + " rounds, and " + buyin + " buyin. ");

    return null;
}

function CreateGame(){
    // counter which counts
    const [counter, setCounter] = useState(0);
    const [counter10, setCounter10] = useState(0);

    return (
        <div style = {{
            backgroundImage: `url(${backdrop})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            height: '100vh',
            width: '100vw'
        }}>
            <Link to="/">
                <img src={create} alt="" style={{width: '60%', marginBottom: '10vh'}}/>
            </Link>

            <br />
            <div className="RoundCount" style={{display: 'flex'}}>
                <img src={rounds} style={{height: '10vh'}} alt={"Round Count"}/>
                <button
                    onClick={() => counter > 0 && setCounter(counter - 1)}
                    className= "glow">
                    <img src={arrow} alt="arrow pointing upwards" className = "rotate90"/>
                </button>
                <div style={{fontSize:'10vh'}} className = "bordering">
                    <ColorfulText text={counter} />
                </div>
                <button
                    onClick={() => counter < 11 && setCounter(counter + 1)}
                    className= "glow">
                    <img src={arrow} alt="arrow pointing downwards" className= "rotateneg90"/>
                </button>
            </div>
            <br />
            <div className="Buy In" style={{display: 'flex'}}>
                <img src={buyin} style={{height: '10vh'}} alt={"buyin"}/>
                <button
                    onClick={() => counter10 > 0 && setCounter10(counter10 - 10)}
                    className= "glow">
                    <img src={arrow} alt="arrow pointing upwards" className="rotate90"/>
                </button>
                <span style={{fontSize:'10vh', fontFamily: "MarioFont", color: "green"}} className = "bordering">
                    <ColorfulText text={counter10} />
                </span>
                <button
                    onClick={() => setCounter10(counter10 + 10)}
                    className= "glow">
                    <img src={arrow} alt="arrow pointing downwards" className="rotateneg90"/>
                </button>
            </div>

            <br />
            <div>
                <button type={"button"} style={{height:'10vh', width:'20hh', border: "black", borderWidth: "10px"}} className = "glow" onClick ={() => gameCreation(counter, counter10)}>
                    <div style={{fontSize:'5vh'}} className = "bordering">
                        <ColorfulText text="Create Game! " />
                    </div>
                </button>
            </div>
        </div>
);
}

export default CreateGame