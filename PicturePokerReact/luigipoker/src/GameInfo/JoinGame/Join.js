import './Join.css';

import join from "../../resources/menuIcons/joingame.png";
import back from "../../resources/misIcons/back.png";
import backdrop from "../../resources/menuIcons/luigisCasino.jpg";

import {Link, useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react";
import axios from "axios";

import ColorfulText from "../../index";

function GameList({joinFxn}) {
    const navigate = useNavigate();
    const [data, setData] = useState(null);

    useEffect(() => {
        if (sessionStorage.getItem('userID') === null)
            navigate('/');

        const loadGame = async () => {
            const response = await axios.get(`http://localhost:8080/getAllGames`)
                .catch(function(error){
                    console.log("getAllGames API call did not work");
            });
            setData(response.data)
        }
        loadGame();
    }, []);
    if (data) {
        const games = data.filter(game => game.players.includes(0) && game.curRound === 1);
        return (
            <div style={{height: '30vh', overflow: 'scroll'}}>
                {games.map((value) =>
                    <div onClick={() => joinFxn(value.id)}>
                        {/*TODO: Make the value.buyin thing look a little nicer. */}
                        <ColorfulText text={JSON.stringify(value.id, null, 2) + ": $" + value.buyIn + " ENTRY, \r" + (4 - value.activePlayers) + "P TO START"}/>
                    </div>
                )}
            </div>
        );
    }
    return <ColorfulText text={"Connection not established"}/>;
}

function JoinGame() {
    const [inputValue, setInputValue] = useState();
    const [message, setMessage] = useState('Enter your friend\'s gameID, then press enter. ');
    const handleInputChange = (event) => {
        const value = event.target.value;
        if (/^\d*$/.test(value)) { // Check if the value is all digits
            setInputValue(value);
        }
    };

    const navigate = useNavigate();
    //do something when a key is pressed (check if #1: it's a valid game, #2, if the game lets the player in) then enter player in game.
    const handleKeyPress = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault(); // Prevent form submission

            const value = event.target.value;
            //we do not want bad values anywhere near our API
            if (/^\d*$/.test(value)) {
                setInputValue(value);
                setMessage("Game ID: " + value);

                //try to get the person into the game
                joinGame(value);
            }
        }
    };

    const joinGame = async(gameId) => {
        try {
            await axios.delete(`http://localhost:8080/leaveCurrentGame/${sessionStorage.getItem('userID')}`)
                .catch(function(){
                console.log("leaveCurrentGame didn't work");
            });
            let response = await axios.put(`http://localhost:8080/joinGame/${gameId}/${sessionStorage.getItem('userID')}`);
            handleJoinGameResponse(response, gameId);
        } catch (error) {
            console.log("JoinGame didn't work. " + gameId);
            setMessage("An error occurred while trying to join the game. Refresh the page? ");
        }
    }

    const handleJoinGameResponse = (response, gameId) => {
        if (isUserInGame(response.data.players)) {
            navigate(`/WaitingRoom`, { state: { gameId: gameId } });
        } else {
            console.log(response.data.players);
            setMessage("You can't join this game. Try getting better?");
        }
    }

    const isUserInGame = (players) => {
        const userId = sessionStorage.getItem('userID');
        return players.includes(parseInt(userId));
    }


    return (
        <div className="create-background">
            <div className="create-header">
                <img src={join} alt="Create Game" className="create-title"/>
                <Link to="/menu" className="back-button-container">
                    <img src={back} alt="Back" className="back-button glow"/>
                </Link>
            </div>
            <div className="centered-container bordering">
                <div className="message">
                    <ColorfulText text={message}/>
                </div>

                <div className="input-and-list-container">
                    <div className="input-container">
                        <div className="game-id-title">
                            <ColorfulText text="Game ID"/>
                        </div>
                        <input type="number" value={inputValue} onChange={handleInputChange} onKeyDown={handleKeyPress}
                               className="game-id-input"/>
                    </div>
                    <div className="game-id-list-title">
                        <ColorfulText text="Game ID List:"/>
                    </div>
                    <div className="game-id-list">
                        <GameList joinFxn={joinGame}/>
                    </div>
                </div>
            </div>
            <div style={{position: "absolute", right: "5%", bottom: "5%", fontSize: "5vh"}} className={"bordering"}>
                <ColorfulText text={"Player: " + sessionStorage.getItem('username')}/>
                <ColorfulText text={"Wealth: $" + sessionStorage.getItem('dollars')}/>
            </div>
        </div>
    );
}

export default JoinGame