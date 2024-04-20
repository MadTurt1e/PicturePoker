import React, { useState, useEffect } from 'react';
import { Link, useLocation } from 'react-router-dom';
import './Game.css';


import backdrop from "../resources/menuIcons/luigisCasino.jpg"
import leave from "../resources/incdec/Arrow_Sign_SMB3.webp";

import axios from "axios";
import ColorfulText from "../index";

function PlayerList({ gid }) {
    const [players, setPlayers] = useState(null);
    const [pNames, setPNames] = useState([]);
    const [pTokens, setPTokens] = useState([]);
    const [buyIn, setBuyIn] = useState([]);
    const [pWaiting, setPWaiting] = useState([]);
    const location = useLocation();

    useEffect(() => {
        const loadGame = async () => {
            const response = await axios.get(`http://localhost:8080/getGameEndDetails/${gid}`)
                .catch(function () {
                    console.log("getGameEndDetails didn't work. " + gid);
                });
            console.log(gid);
            setPlayers(response.data.players);
            setBuyIn(response.data.buyIn);
        }
        //if we are in the round end, jump to the end screen
        loadGame();

    }, [gid]);


    useEffect(() => {
        if (players) {
            const filteredPlayers = players.filter(player => player !== 0);

            async function getPlayerNames() {
                const names = [];
                const tokens = [];
                const bet = [];
                const waiting = [];

                for (let i = 0; i < filteredPlayers.length; i++) {
                    let response = await axios.get(`http://localhost:8080/getByPlayerID/${filteredPlayers[i]}`)
                        .catch(function (error) {
                            console.log('getByPlayerID didn\'t work ' + filteredPlayers[i]);
                        });
                    names.push(response.data.playerName);
                    tokens.push(response.data.tokens);
                }
                setPNames(names);
                setPTokens(tokens);
            }
            getPlayerNames();
        }
    }, [players]); // Use a separate useEffect hook to update pNames when players changes

    const winnings = [0.55, 0.3, 0.15, 0];
    return (
        <div style={{ fontSize: "3vh" }} className={"bordering"}>
            {pNames.map((value, i) => (
                <React.Fragment key={i}>
                    <ColorfulText text={(i+1) + ": \t\t" + pNames[i]} />
                    <div style={{ display: 'flex', alignItems: 'center' }}>
                        <ColorfulText text={"Tokens: \t" + pTokens[i]} />
                    </div>
                    <ColorfulText text={"Winnings: $" + (winnings[i] * (buyIn*4))} />
                    <br/>
                </React.Fragment>
            ))}
        </div>
    );
}

function GameEnd() {
    const location = useLocation();
    const gid = (location.state.gameId);
    return (
        <div style={{
            backgroundImage: `url(${backdrop})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            height: '100vh',
            width: '100vw'
        }}>
            <div style={{fontSize: "4vh"}} className= {'bordering'}>
                <ColorfulText text={"Game over! Placements: "} />
            </div>
            <br />
            
            <PlayerList gid={gid} />
            <Link to="/menu" className="glow">
                <div className={"bordering"} style={{fontSize: "3vh"}}>
                    <ColorfulText text={"Go to Menu!"} />
                </div>
                <img src={leave} alt="" style={{ transform: [{ rotateY: '90deg' }], height: '10vh', width:"10vh", marginBottom: '10px' }} />
            </Link>
        </div>
    );
}

export default GameEnd;