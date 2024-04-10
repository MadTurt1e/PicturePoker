import "./CreateGame/Create.css";

import stats from "../resources/menuIcons/statistics.png";
import backdrop from "../resources/menuIcons/luigisCasino.jpg";

import {Link} from "react-router-dom";
import React, { useState } from "react";

function Statistics() {
    //this is where we'd call the API to pull the player statistics.
    return (
        <div style={{
            backgroundImage: `url(${backdrop})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            height: '100vh',
            width: '100vw'
        }}>
            <Link to="/menu">
                <img src={stats} alt="stats" style={{height: '10vh', marginBottom: '10vh'}}/>
            </Link>
        </div>
    );
}

export default Statistics