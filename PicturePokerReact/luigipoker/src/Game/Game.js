import React, { useState } from 'react';
import './Game.css'; // make sure to create this file

import cloud from "../resources/pokerSprites/cloud.png";
import mushroom from "../resources/pokerSprites/mushroom.png";
import fireflower from "../resources/pokerSprites/fireflower.png";
import luigi from "../resources/pokerSprites/luigi.png";
import mario from "../resources/pokerSprites/mario.png";
import star from "../resources/pokerSprites/star.png";
import normalCard from "../resources/pokerSprites/normalCard.png";
import proCard from "../resources/pokerSprites/proCards.png";

import betButton from "../resources/pokerSprites/bet.png";
import draw from "../resources/pokerSprites/draw.png";
import hold from "../resources/pokerSprites/hold.png";

import token from "../resources/pokerSprites/token.png";

import backdrop from "../resources/pokerSprites/table.png";

import axios from "axios";

function Game() {
    const images = [cloud, mushroom, fireflower, luigi, mario, star, normalCard, proCard];
    const [cards, setCards] = useState([6, 6, 6, 6 , 6]);
    const [dealerCards, setDealerCards] = useState([7, 7, 7, 7, 7]);

    const [selectedCards, setSelectedCards] = useState([]); // Change to an array

    const handleCardClick = (index) => {
        const newCards = [...cards];
        console.log(index);
        newCards[index] = Math.floor(Math.random() * 6); // THIS IS WHERE THE CARD VALUE (WOULD) CHANGE AND HOW TO CHANGE IT
        setCards(newCards);

        // Add or remove the index from the selectedCards array
        if (selectedCards.includes(index)) {
            setSelectedCards(selectedCards.filter(i => i !== index));
        } else {
            setSelectedCards([...selectedCards, index]);
        }
    };

    //do stuff when the bet button is clicked
    const [bet, setBet] = useState(0);
    const handleBetClick = () => {
        if (bet < 5)
            setBet(bet + 1);
    };

    const endTurnProcedure = () => {
        const newCards = [...cards];
        selectedCards.map((index) =>
            newCards[index] = Math.floor(Math.random() * 6)

        )
        setCards(newCards);
    }

    return (
        <div style={{
            backgroundImage: `url(${backdrop})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            height: '100vh',
            width: '100vw'
        }}>
            <div className="cards" style={{
                top: '5%'
            }}>
                {cards.map((_, index) => (
                    <div key={index} className={'dealercard'}>
                        <img src={images[dealerCards[index]]} alt={`Dealer Card ${index + 1}`} className='crispImages'/>
                    </div>
                ))}
            </div>
            <div
                className="bet"
                onClick={() => handleBetClick()}
            >
                <img src={betButton} alt={"Bet Button"} style={{width: '4vw'}} className={"crispImages"}/>
                <div>
                    {bet > 0 && <img src={token} alt="token1" className="token crispImages"/>}
                    {bet > 1 && <img src={token} alt="token2" className="token crispImages"/>}
                    {bet > 2 && <img src={token} alt="token3" className="token crispImages"/>}
                    {bet > 3 && <img src={token} alt="token4" className="token crispImages"/>}
                    {bet > 4 && <img src={token} alt="token5" className="token crispImages"/>}
                </div>
            </div>

            <div
                className="endTurnButton"
                onClick={() => endTurnProcedure()}
            >
                {selectedCards.some(el => el > -1) &&
                    <img src={draw} alt={"Draw Button"} style={{width: '20vw'}} className={"crispImages"}/> ||
                    <img src={hold} alt={"Hold Button"} style={{width: '20vw'}} className={"crispImages"}/>}
            </div>

            <div className="cards" style={{
                bottom: '5%'
            }}>
                {cards.map((_, index) => (
                    <div
                        key={index}
                        className={`card ${selectedCards.includes(index) ? 'raised' : ''}`} // Check if the card is in the selectedCards array
                        onClick={() => handleCardClick(index)}
                    >
                        <img src={images[cards[index]]} alt={`Card ${index + 1}`} className='crispImages'/>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default Game;