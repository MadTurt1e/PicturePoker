import React from 'react';
import ReactDOM from 'react-dom/client';

import {
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";

import './index.css';

import Menu from './GameMenu/Menu';
import Create from './GameInfo/CreateGame/Create';
import Join from './GameInfo/JoinGame/Join';
import Statistics from './GameInfo/Statistics';
import WaitingRoom from './Game/WaitingRoom';
import Game from './Game/Game';
import GameEnd from './Game/GameEnd';
import Login from './Login/Login';

import reportWebVitals from './reportWebVitals';

const axios = require('axios').default;

//useful block that colorizes text to make it look like the mario font
const colors = ['#009CDA', '#FCD000', '#E71E07', '#42B132', '#FCD000', '#E71E07', '#42B132', '#FCD000', '#009CDA', '#42B132'];

const ColorfulText = ({text}) => {
    return (
        <div>
            {text.toString().split('').map((char, i) => (
                <span key={i} style={{ color: colors[i % colors.length], fontFamily: "MarioFont"}}>
          {char}
        </span>
            ))}
        </div>
    );
};

const root = ReactDOM.createRoot(document.getElementById('root'));



/// This now sends the user to the login page first, and only after logging in can they access the menu
root.render(
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<Login />} />
            <Route path="/menu" element={<Menu />} />
            <Route path="/createGame" element={<Create />} />
            <Route path="/joinGame" element={<Join />} />
            <Route path="/statistics" element={<Statistics />} />
            <Route path="/game" element={<Game />} />
            <Route path="/WaitingRoom" element={<WaitingRoom />} />
            <Route path="/gameEnd" element={<GameEnd />} />
        </Routes>
    </BrowserRouter>,
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();

export default ColorfulText;