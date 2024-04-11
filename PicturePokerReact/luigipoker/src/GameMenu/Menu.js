import "./Menu.css";

import logo from '../resources/menuIcons/luigi poker.png'
import create from '../resources/menuIcons/creategame.png'
import join from '../resources/menuIcons/joingame.png'
import statistics from '../resources/menuIcons/statistics.png'
import exit from '../resources/menuIcons/exit.png'
import backdrop from '../resources/menuIcons/luigisCasino.jpg'

import {Link} from "react-router-dom";
import ColorfulText from "../index";

const ImageComponent = () => {


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
            <Link to="/createGame" className = "glow">
                <img src={create} alt="" style={{height: '5vh', marginBottom: '10px'}}/>
            </Link>
            <Link to="/joinGame" className = "glow">
                <img src={join} alt="" style={{height: '5vh', marginBottom: '10px'}}/>
            </Link>
            <Link to="/statistics" className = "glow">
                <img src={statistics} alt="" style={{height: '5vh', marginBottom: '10px'}}/>
            </Link>
            <Link to="/" className = "glow">
                <img src={exit} alt="" style={{height: '5vh', marginBottom: '10px'}}/>
            </Link>
            ))
            <div style={{position: "absolute", left: "5%", bottom: "5%", fontSize: "5vh"}} className={"bordering"}>
                <ColorfulText text={"Player: " + sessionStorage.getItem('username')} />
            </div>
        </div>
    );
};

function Menu() {
    return <ImageComponent/>;
}

export default Menu