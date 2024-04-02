import "./Create.css"
import React, { useState } from 'react';
import create from "../../resources/menuIcons/creategame.png";
import backdrop from "../../resources/menuIcons/luigisCasino.jpg";

import arrow from "../../resources/incdec/Arrow_Sign_SMB3.webp";

// counter which counts
const [counter, setCounter] = useState(0);
const [counter10, setCounter10] = useState(0);

// a couple functions to do a bit of counting
const incrementCounter = () => {
    setCounter(counter + 1);
};

const decrementCounter = () => {
    if (counter !== 0) {
        setCounter(counter - 1);
    }
};

const increment10Counter = () => {
    setCounter10(counter10 + 10);
};

const decrement10Counter = () => {
    if (counter !== 0) {
        setCounter10(counter10 - 10);
    }
};

function CreateGame(){

    return (
        <div style = {{
            backgroundImage: `url(${backdrop})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            height: '100vh',
            width: '100vw'
        }}>
            <img src={create} alt="" style={{width: '100hh', marginBottom: '10px'}}/>
            <br />
            <div className="RoundCount">
                <button
                    className="arrow-up"
                    onClick={incrementCounter}>
                    <img src={arrow} alt="arrow pointing upwards" class = "rotate90"/>
                </button>
                <span className="number">
                        textContent = {counter}
                    </span>
                <button
                    className="arrow-down"
                    onClick={decrementCounter}>
                    <img src={arrow} alt="arrow pointing downwards" class = "rotate90"/>
                </button>
            </div>
            <br />
            <div className="Buy In">
                <button
                    className="arrow-up"
                    onClick={increment10Counter}>
                    <img src={arrow} alt="arrow pointing upwards" class="rotateneg90"/>
                </button>
                <span className="number">
                        textContent = {counter10}
                    </span>
                <button
                    className="arrow-down"
                    onClick={decrement10Counter}>
                    <img src={arrow} alt="arrow pointing downwards" class="rotateneg90"/>
                </button>
            </div>
        </div>
    );
}

export default CreateGame