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
import Statistics from './GameInfo/Statistics'

import Game from './Game/Game'

import reportWebVitals from './reportWebVitals';

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
/// This is the line you'd change to change the page the react website loads first
root.render(
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<Menu />} />
            <Route path="/createGame" element={<Create />} />
            <Route path="/joinGame" element={<Join />} />
            <Route path="/statistics" element={<Statistics />} />
            <Route path="/game" element={<Game />} />
        </Routes>
    </BrowserRouter>,
  <React.StrictMode>
    <Menu />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();

export default ColorfulText;