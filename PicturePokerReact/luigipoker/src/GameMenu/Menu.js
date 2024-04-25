import "./Menu.css";

import logo from '../resources/menuIcons/luigi poker.png'
import create from '../resources/menuIcons/creategame.png'
import join from '../resources/menuIcons/joingame.png'
import statistics from '../resources/menuIcons/statistics.png'
import exit from '../resources/menuIcons/exit.png'
import backdrop from '../resources/menuIcons/luigisCasino.jpg'

import {Link, useNavigate} from "react-router-dom";
import axios from "axios";
import ColorfulText from "../index";
import {useEffect, useState} from "react";

function joinGame(gid, navigate) {
    //we need to check if the game has started
    if (gid !== 0) {
        async function isInGame() {
            const response = await axios.get(`http://localhost:8080/getByGameID/` + gid)
                .catch(function () {
                    console.log("GetbyGameID didn't work. ");
                });
            if (response.data.players){
                let newPNames = [];
                const filteredPlayers = response.data.players.filter(player => player === 0);
                if (filteredPlayers.length > 0){
                    navigate(`/WaitingRoom`, {state: {gameId: gid}});
                }
            }
        }
        isInGame();

        // this is the case where we're not waiting on players so we just start
        navigate('/game', {state: {gameId: gid}});
    }
}
const ImageComponent = () => {

    const [gid, setGid] = useState(0);
    //quick check to see if the player is in an active game right now
    useEffect(() => {
        const inGame = async () => {
            const response = await axios.get(`http://localhost:8080/getPlayerActiveGame/${sessionStorage.getItem('userID')}`)
                .catch(function(error){
                    console.log("getByPlayerID API call did not work" + sessionStorage.getItem('userID'));
                });
            if(response.status === 200 && response.data !== null && (response.data.curRound <= response.data.numRounds)) {
                setGid(response.data.id);
            }
        }
        inGame();
    }, []);

    const navigate = useNavigate();


    return (
        <div style={{
            display: 'flex', flexDirection: 'column', alignItems: 'flex-end',
            backgroundImage: `url(${backdrop})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            height: '100vh',
            width: '100vw'
        }}>
            <img src={logo} alt="" className="game-logo"/>
            <Link to="/createGame" className="glow button">
                <img src={create} alt="" style={{height: '5vh', marginBottom: '10px'}}/>
            </Link>
            <Link to="/joinGame" className="glow button">
                <img src={join} alt="" style={{height: '5vh', marginBottom: '10px'}}/>
            </Link>
            <Link to="/statistics" className="glow button">
                <img src={statistics} alt="" style={{height: '5vh', marginBottom: '10px'}}/>
            </Link>
            <Link to="/" className="glow button">
                <img src={exit} alt="" style={{height: '5vh', marginBottom: '10px'}}/>
            </Link>
            ))
            {gid !== 0 && //conditional statement to only let player join game if they're already in a game
                <div style={{position: "absolute", left: "5%", bottom: "20%", fontSize: "5vh"}} className={"bordering glow"} onClick={() => { joinGame(gid, navigate) }}>
                    <ColorfulText text={"Rejoin Current Game! "}/>
                </div>
            }

            <div style={{position: "absolute", left: "5%", bottom: "5%", fontSize: "5vh"}} className={"bordering"}>
                <ColorfulText text={"Player: " + sessionStorage.getItem('username')}/>
                <ColorfulText text={"Wealth: $" + sessionStorage.getItem('dollars')}/>
            </div>
        </div>
    );
};

function Menu() {
    return <ImageComponent/>;
}

export default Menu