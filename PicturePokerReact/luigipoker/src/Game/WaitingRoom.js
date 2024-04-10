import React, {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";

import ColorfulText from "../index"
import backdrop from "../resources/menuIcons/luigisCasino.jpg";

function PlayerList() {
    const [data, setData] = useState(null);
    const [players, setPlayers] = useState(null);

    const {gid} = useParams();

    //the call to get the game info
    useEffect(() => {

        const loadGame = async () => {

            // Replace this with the updated API call
            const response = await axios.get(`http://localhost:8080/getByGID/` + gid)
                .catch(function(error){
                    return (<div>
                        connection not established.
                    </div>)
                });
            setData(response.data)
        }
        loadGame();
        setPlayers(data.player);
    }, []);

    const navigate = useNavigate();
    if(players) {
        const filter = players.filter(activeList => activeList.p_id.includes(0));
        const pNames = filter.map(activeList => activeList.p_names);
        if (pNames.length() === 4) {
            navigate('/game', {gid: gid,});
        }

        return (
            <div>
                Game ID: {gid.toString()}, Players required: {4 - pNames.length()}
                {pNames.map((value) =>
                    <ColorfulText text={JSON.stringify(value, null, 2)}/>
                )}
            </div>
        );
    }
    return (
        <ColorfulText text={"Connection not established."}/>
    )
}

function WaitingRoom(){


    const [gid, setGID] = useState(null);

    return (
        <div style={{
            backgroundImage: `url(${backdrop})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            height: '100vh',
            width: '100vw'
        }}>
            <PlayerList/>
            <br />

        </div>
    )
}

export default WaitingRoom