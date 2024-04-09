import React from 'react';
import ReactDOM from 'react-dom/client';

import {
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";

import './index.css';

import Menu from './gameMenu/Menu';
import Create from './GameInfo/CreateGame/Create';
import Join from './GameInfo/JoinGame/Join';
import Statistics from './GameInfo/Statistics'
import Login from './Login/Login';

import reportWebVitals from './reportWebVitals';

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
