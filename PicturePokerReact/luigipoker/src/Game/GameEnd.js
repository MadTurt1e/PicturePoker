import React, { useState, useEffect } from 'react';
import {Link, useLocation, useNavigate} from 'react-router-dom';
import './Game.css';


import backdrop from "../resources/menuIcons/luigisCasinoBright.jpg"
import leave from "../resources/incdec/Arrow_Sign_SMB3.webp";

import axios from "axios";
import ColorfulText from "../index";

function PlayerList({ gid }) {
    const [players, setPlayers] = useState(null);
    const [pNames, setPNames] = useState([]);
    const [pTokens, setPTokens] = useState([]);
    const [pRoundsWon, setPRoundsWon] = useState([]);
    const [buyIn, setBuyIn] = useState([]);
    useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        //check to ensure the user isn't doing weird link hopping
        if (gid === 0)
            navigate('/');

        const loadGame = async () => {
            const response = await axios.get(`${sessionStorage.getItem('host')}/getGameEndDetails/${gid}`)
                .catch(function () {
                    console.log("getGameEndDetails didn't work. " + gid);
                });
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
                const rounds_won = [];

                for (let i = 0; i < filteredPlayers.length; i++) {
                    let response = await axios.get(`${sessionStorage.getItem('host')}/getByPlayerID/${filteredPlayers[i]}`)
                        .catch(function () {
                            console.log('getByPlayerID didn\'t work ' + filteredPlayers[i]);
                        });
                    //update this thing while we're here
                    if (filteredPlayers[i] === sessionStorage.getItem("userID")){
                        sessionStorage.setItem('dollars', response.data.dollars);
                    }
                    names.push(response.data.playerName);
                    tokens.push(response.data.tokens);
                    rounds_won.push(response.data.roundsWon);
                }
                setPNames(names);
                setPTokens(tokens);
                setPRoundsWon(rounds_won);
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
                    <ColorfulText text={"Rounds Won: \t" + pRoundsWon[i]} />
                    <ColorfulText text={"Winnings: $" + (winnings[i] * (buyIn*4))} />
                    <br/>
                </React.Fragment>
            ))}
        </div>
    );
}

function GameEnd() {
    let gid = 0;
    const location = useLocation();

    if (location.state !== null)
        gid = (location.state.gameId);
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