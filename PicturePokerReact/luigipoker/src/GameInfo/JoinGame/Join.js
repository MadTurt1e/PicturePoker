import "../CreateGame/Create.css";

import join from "../../resources/menuIcons/joingame.png";
import backdrop from "../../resources/menuIcons/luigisCasino.jpg";

import {Link} from "react-router-dom";
import React, { useState } from "react";

import ColorfulText from "../../index";

function JoinGame() {
    const [inputValue, setInputValue] = useState('');
    const [message, setMessage] = useState('Enter your friend\'s gameID, then press enter. ');
    const handleInputChange = (event) => {
        const value = event.target.value;
        if (/^\d*$/.test(value)) { // Check if the value is all digits
            setInputValue(value);
        }

        console.log(inputValue)
    };

    //do something when a key is pressed (check if #1: it's a valid game, #2, if the game lets the player in) then enter player in game.
    const handleKeyPress = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault(); // Prevent form submission

            const value = event.target.value;
            //we do not want bad values anywhere near our API
            if (/^\d*$/.test(value)) {
                setInputValue(value);
                setMessage("Game ID: " + value);
            }

            // Call your function here
            console.log('Enter key pressed! ' + value + " in form. ");
        }
    };


    return (
        <div style={{
            backgroundImage: `url(${backdrop})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            height: '100vh',
            width: '100vw'
        }}>
            <Link to="/">
                <img src={join} alt="" style={{width: '60%', marginBottom: '10vh'}}/>
            </Link>
            <br/>
            <div style={{fontSize: '5vh'}} className="bordering">
                <ColorfulText text={message} />
            </div>
            <br/>
            <div style={{fontSize: '5vh'}} className="bordering">
                <ColorfulText text= "Game ID"/>
                <form>
                    <input type="number" value={inputValue} onChange={handleInputChange} onKeyDown={handleKeyPress}
                           style={{fontFamily: "MarioFont", fontSize: "5vh", color: "red"}} pattern="\d*"/>
                </form>
            </div>
            <br/>
        </div>
    );
}

export default JoinGame