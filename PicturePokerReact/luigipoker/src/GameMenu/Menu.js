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
    if (gid !== 0) {
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
                    console.log("getByPlayerID API call did not work");
                });
            console.log(response);
            if(response.status === 200 && response.data !== null && (response.data.curRound !== response.data.numRounds)) {
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
            <img src={logo} alt="" style={{width: '70%', marginBottom: '10px'}}/>
            <Link to="/createGame" className="glow">
                <img src={create} alt="" style={{height: '5vh', marginBottom: '10px'}}/>
            </Link>
            <Link to="/joinGame" className="glow">
                <img src={join} alt="" style={{height: '5vh', marginBottom: '10px'}}/>
            </Link>
            <Link to="/statistics" className="glow">
                <img src={statistics} alt="" style={{height: '5vh', marginBottom: '10px'}}/>
            </Link>
            <Link to="/" className="glow">
                <img src={exit} alt="" style={{height: '5vh', marginBottom: '10px'}}/>
            </Link>
            ))
            {gid !== 0 && //conditional statement to only let player join game if they're already in a game
                <div style={{position: "absolute", left: "5%", bottom: "10%", fontSize: "5vh"}} className={"bordering glow"} onClick={() => { joinGame(gid, navigate) }}>
                    <ColorfulText text={"Rejoin Current Game! "}/>
                </div>
            }

            <div style={{position: "absolute", left: "5%", bottom: "5%", fontSize: "5vh"}} className={"bordering"}>
                <ColorfulText text={"Player: " + sessionStorage.getItem('username')}/>
            </div>
        </div>
    );
};

function Menu() {
    return <ImageComponent/>;
}

export default Menu