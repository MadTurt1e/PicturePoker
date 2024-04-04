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

import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));
/// This is the line you'd change to change the page the react website loads first
root.render(
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<Menu />} />
            <Route path="/createGame" element={<Create />} />
            <Route path="/joinGame" element={<Join />} />
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
