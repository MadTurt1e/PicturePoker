import React, {useEffect, useState} from "react";
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";

import ColorfulText from "../index"
import backdrop from "../resources/menuIcons/luigisCasino.jpg";

function WaitingRoom(){
    const [data, setData] = useState(null);
    const [players, setPlayers] = useState(null);

    //TODO: I believe this line actually necessitates an import. THe problem is, I don't know which ones. 
    const {gid} = route.params;

    //the call to get the game info
    useEffect(() => {
        const loadGame = async () => {
            // Replace this with the updated API call
            const response = await axios.get(`http://localhost:8080/getByGID/` + gid);
            setData(response.data)
        }
        loadGame();
        setPlayers(data.player);
    }, []);

    if(players){
        const filter = players.filter(activeList => activeList.p_id.includes(0));
        const pNames = filter.map(activeList => activeList.p_names);
    }
    const playerCount = pNames.length();

    if (playerCount > 4){
        navigate('/game', {gid: gid,});
    }

    return(
        <div style={{
            backgroundImage: `url(${backdrop})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            height: '100vh',
            width: '100vw'
        }}>

            <ColorfulText text = {"Waiting on players - we need " + (4-playerCount) + " more players. "} />
            <br /> 

            <div>
                <ColorfulText text={"Current Players"}/>
            </div>
            <br />
            <div>
                {pNames.map((value) =>
                        <ColorfulText text={JSON.stringify(value, null, 2)}/>
                    )}
            </div>
        </div>
    )
}

export default WaitingRoom