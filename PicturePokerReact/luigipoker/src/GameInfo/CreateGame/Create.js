import "./Create.css"
import React, { useState } from 'react';
import create from "../../resources/menuIcons/creategame.png";
import backdrop from "../../resources/menuIcons/luigisCasino.jpg";

import buyin from "../../resources/menuIcons/buy-in-4-3-2024.png"
import rounds from "../../resources/menuIcons/number-of-rounds-4-3-2024.png"

import arrow from "../../resources/incdec/Arrow_Sign_SMB3.webp";

import "../../gameMenu/Menu.css";
import {Link} from "react-router-dom";

function gameCreation(rounds, buyin){
    return null;
    console.log("Game created - " + rounds + " rounds, and " + buyin + " buyin. ");
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
            <Link to="/menu">
                <img src={create} alt="" style={{height: '10vh', marginBottom: '10vh'}}/>
            </Link>

            <br />
            <div className="RoundCount">
                <img src={rounds} style={{height: '10vh'}} alt={"Round Count"}/>
                <button
                    className="arrow-up"
                    onClick={() => counter > 0 && setCounter(counter - 1)}
                    class = "glow">
                    <img src={arrow} alt="arrow pointing upwards" className = "rotate90"/>
                </button>
                <span style={{fontSize:'10vh', fontFamily: "MarioFont", color: "green"}} className = "bordering">
                    {counter}
                </span>
                <button
                    className="arrow-down"
                    onClick={() => counter < 11 && setCounter(counter + 1)}
                    class = "glow">
                    <img src={arrow} alt="arrow pointing downwards" class = "rotateneg90"/>
                </button>
            </div>
            <br />
            <div className="Buy In">
                <img src={buyin} style={{height: '10vh'}}/>
                <button
                    className="arrow-up"
                    onClick={() => counter10 > 0 && setCounter10(counter10 - 10)}
                    class = "glow">
                    <img src={arrow} alt="arrow pointing upwards" class="rotate90"/>
                </button>
                <span style={{fontSize:'10vh', fontFamily: "MarioFont", color: "green"}} className = "bordering">
                    {counter10}
                </span>
                <button
                    className="arrow-down"
                    onClick={() => setCounter10(counter10 + 10)}
                    class = "glow">
                    <img src={arrow} alt="arrow pointing downwards" class="rotateneg90"/>
                </button>
            </div>

            <br />
            <div>
                <button type={"button"} style={{height:'10vh', width:'20hh'}} className = "glow" onClick ={gameCreation(counter, counter10)}>
                    <div style={{fontSize:'5vh', fontFamily:"MarioFont", color:"red"}} className = "bordering">
                        Create Game
                    </div>
                </button>
            </div>
        </div>
);
}

export default CreateGame