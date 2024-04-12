import React, {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate, useLocation} from "react-router-dom";

import ColorfulText from "../index"
import backdrop from "../resources/menuIcons/luigisCasino.jpg";

function PlayerList() {
    const [data, setData] = useState(null);
    const [players, setPlayers] = useState(null);
    const location = useLocation();
    const gid = location.state.gameId;

    const navigate = useNavigate();

    //the call to get the game info
    useEffect(() => {
        const loadGame = async () => {
            // Replace this with the updated API call
            const response = await axios.get(`http://localhost:8080/getByGameID/` + gid)
                .catch(function(error){
                    console.log("GetbyGameID didn't work. ");
                });
            setData(response.data)
            setPlayers(response.data.players);
        }
        loadGame();

        if(players) {
            const filteredPlayers = players.filter(player => player !== 0);
            const pNames = [];

            async function getPlayerNames() {
                for(let i=0; i < filteredPlayers.length; i++) {
                    let response = await axios.get(`http://localhost:8080/getByPlayerID/${filteredPlayers[i]}`)
                        .catch(function(error){
                            console.log('getByPlayerID didn\'t work');
                        });
                    pNames.push(response.data.playerName);
                }
            }
            getPlayerNames();

            if (filteredPlayers.length === 4) {
                navigate('/game', {gid: gid,});
            }

            return (
                <div>
                    <div style={{fontSize:"5vh"}} className={"bordering"}>
                        <ColorfulText text={"Game  ID: " + gid.toString() + " Players required: " + (4 - pNames.length)} />
                    </div>
                    {pNames.map((value) =>
                        <ColorfulText text={JSON.stringify(value, null, 2)}/>
                    )}
                </div>
            );
        }
    }, []);
    return (
        <ColorfulText text={"Connection not established."}/>
    )
}

function WaitingRoom(){
    const navigate = useNavigate();
    const [message, setMessage] = useState("Welcome to the Waiting Room. Enjoy your stay!");
    const exitGame = async() => {
        //leave game
        let response = await axios.delete(`http://localhost:8080/leaveCurrentGame/${sessionStorage.getItem('userID')}`)
            .catch(function(error){
                console.log("leaveCurrentGame didn\'t work. ");
            });
        if (response.status === 200){
            //if we actually leave, go to the menu
            let response = await axios.get(`http://localhost:8080/getPlayerActiveGame/${sessionStorage.getItem('userID')}`)
                .catch(function(error){
                    console.log("GetPlayerActiveGame didn\'t work. ");
                });
            if (response.data === null)
                navigate("/menu");
        }
        setMessage("You can never leave. ")
    }

    return (
        <div style={{
            backgroundImage: `url(${backdrop})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            height: '100vh',
            width: '100vw'
        }}>
            <div style={{fontSize: "10vh"}} className={"bordering"}>
                <ColorfulText text={message}/>
            </div>
            <PlayerList/>
            <br/>

            <div className="escapeGame" onClick={() => exitGame()}>
                <ColorfulText text={"Leave Game?  "}/>
            </div>

            <div style={{position: "absolute", right: "5%", bottom: "5%", fontSize: "5vh"}} className={"bordering"}>
                <ColorfulText text={"Player: " + sessionStorage.getItem('username')}/>
            </div>
        </div>
    )
}

export default WaitingRoom;