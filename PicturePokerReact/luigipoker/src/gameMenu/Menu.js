import "./Menu.css";

import logo from '../resources/menuIcons/luigi poker.png'
import create from '../resources/menuIcons/creategame.png'
import join from '../resources/menuIcons/joingame.png'
import statistics from '../resources/menuIcons/statistics.png'
import exit from '../resources/menuIcons/exit.png'
import backdrop from '../resources/menuIcons/luigisCasino.jpg'

import {Link} from "react-router-dom";

const ImageComponent = () => {

    //closes tab
    const closeTab = () => {
        window.opener = null;
        window.open("", "_self");
        window.close();
    };

    return (
        <div style={{
            display: 'flex', flexDirection: 'column', alignItems: 'flex-end',
            backgroundImage: `url(${backdrop})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            height: '100vh',
            width: '100vw'
        }}>
            <img src={logo} alt="" style={{width: '100hh', marginBottom: '10px'}}/>
            <Link to="/createGame" class = "glow">
                <img src={create} alt="" style={{height: '5vh', marginBottom: '10px'}}/>
            </Link>
            <Link to="/joinGame" class = "glow">
                <img src={join} alt="" style={{height: '5vh', marginBottom: '10px'}}/>
            </Link>
            <Link to="/statistics" class = "glow">
                <img src={statistics} alt="" style={{height: '5vh', marginBottom: '10px'}}/>
            </Link>
            <button className="glow" onClick={closeTab}>
                <img src={exit} alt="" style={{height: '5vh', marginBottom: '10px'}}/>
            </button>
            ))
        </div>
    );
};

function Menu() {
    return <ImageComponent/>;
}

export default Menu