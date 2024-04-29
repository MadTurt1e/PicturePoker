import React, {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate, useLocation} from "react-router-dom";

import ColorfulText from "../index"
import backdrop from "../resources/menuIcons/luigisCasino.jpg";
import "./WaitingRoom.css";

function PlayerList() {
    const [players, setPlayers] = useState(null);
    const [pNames, setPNames] = useState([]);
    const location = useLocation();
    const gid = location.state.gameId;
    const navigate = useNavigate();

    useEffect(() => {
        //this should be on an interval - it runs once, then it runs every 10 seconds.
        async function doStuff () {
            const response = await axios.get(`http://localhost:8080/getByGameID/` + gid)
                .catch(function () {
                    console.log("GetbyGameID didn't work. ");
                });
            setPlayers(response.data.players);

            if (players) {
                const filteredPlayers = players.filter(player => player !== 0);
                let newPNames = [];
                for (let i = 0; i < filteredPlayers.length; i++) {
                    let response = await axios.get(`http://localhost:8080/getByPlayerID/${filteredPlayers[i]}`)
                        .catch(function (error) {
                            console.log('getByPlayerID didn\'t work');
                        });
                    newPNames.push(response.data.playerName);
                }
                setPNames(newPNames);
            }
        }
        doStuff();
        const interval = setInterval(async () => {
            doStuff();
        }, 10000);
        //TIME: for debugging purposes. Set to 10 seconds in real life.

        // Leave the waiting room immediately after we hit 4 players.
        if (pNames.length === 4) {
            navigate(`/Game`, { state: { gameId: gid } });
        }

        // Cleanup interval on unmount
        return () => clearInterval(interval);

    }, []);


    return (
        <div>
            <div style={{fontSize:"5vh"}} className={"bordering3"}>
                <ColorfulText text={"Game  ID: " + gid.toString() + " Players required: " + (4 - pNames.length)} />
            </div>
            <div style={{fontSize:"3vh"}} className={"bordering3"}>
                {pNames.map((value) =>
                    <ColorfulText text={value}/>
                )}
            </div>
        </div>
    );
}

function WaitingRoom(){
    const navigate = useNavigate();
    const [message, setMessage] = useState("Welcome to the Waiting Room. Enjoy your stay!");
    //we have a function to check if a player is allowed to leave.
    const exitGame = async() => {
        //leave game
        let response = await axios.delete(`http://localhost:8080/leaveCurrentGame/${sessionStorage.getItem('userID')}`)
            .catch(function(error){
                console.log("leaveCurrentGame didn\'t work. ");
            });
        if (response.status === 200){
            //if we actually leave, go to the menu
            let response2 = await axios.get(`http://localhost:8080/getPlayerActiveGame/${sessionStorage.getItem('userID')}`)
                .catch(function(error){
                    console.log("GetPlayerActiveGame didn\'t work. ");
                });
            if (response2.data === "" || response2.data.players.includes(parseInt(sessionStorage.getItem("userID"))))
                navigate('/menu');
        }
        setMessage("You cannot leave. ")
    }

    return (
        <div style={{
            backgroundImage: `url(${backdrop})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            height: '100vh',
            width: '100vw'
        }}>
            <div style={{fontSize: "4vh"}} className={"header2"}>
                <ColorfulText text={message}/>
            </div>
                <PlayerList/>
            <button className="escapeGame1 bordering3 glow" style={{fontSize:"3vh"}}onClick={() => exitGame()}>
                <ColorfulText text={"Leave Game?  "}/>
            </button>

            <div style={{position: "absolute", right: "5%", bottom: "5%", fontSize: "5vh"}} className={ "bordering"}>
                <ColorfulText text={"Player: " + sessionStorage.getItem('username')}/>
            </div>
        </div>
    )
}

export default WaitingRoom;