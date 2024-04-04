import "./Menu.css";

import logo from '../resources/menuIcons/luigi poker.png'
import create from '../resources/menuIcons/creategame.png'
import join from '../resources/menuIcons/joingame.png'
import statistics from '../resources/menuIcons/statistics.png'
import exit from '../resources/menuIcons/exit.png'
import backdrop from '../resources/menuIcons/luigisCasino.jpg'

import createjs from '../GameInfo/createGame/Create.js'

import React, { useState, useEffect } from 'react';

const ImageComponent = () => {

    // window dimensions are not necessary
    const [windowDimensions, setWindowDimensions] = useState({ width: window.innerWidth, height: window.innerHeight });

    // a little thing I got off the internet to adjust the image size based on the size of the window
    useEffect(() => {
        const handleResize = () => {
            setWindowDimensions({ width: window.innerWidth, height: window.innerHeight });
        };

        window.addEventListener('resize', handleResize);
        return () => window.removeEventListener('resize', handleResize);
    }, []);
    // use backgroundsize: 'cover'
    //so basically we set the image size to the smaller of the image dimensions
    const imageSize = Math.min(windowDimensions.width, windowDimensions.height) * 0.5; // adjust as needed

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
            <img src={logo} alt="" style={{width: imageSize * 2, marginBottom: '10px'}}/>
            <link to={createjs} className="GFG">
                <img src={create} alt="" style={{height: '5vh', marginBottom: '10px'}}/>
            </link>
            <button className="GFG">
                <img src={join} alt="" style={{height: '5vh', marginBottom: '10px'}}/>
            </button>
            <button className="GFG">
                <img src={statistics} alt="" style={{height: '5vh', marginBottom: '10px'}}/>
            </button>
            <button className="GFG" onClick={closeTab}>
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