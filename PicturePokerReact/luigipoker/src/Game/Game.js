import React, { useState } from 'react';
import './Game.css'; // make sure to create this file

import cloud from "../resources/pokerSprites/cloud.png";
import mushroom from "../resources/pokerSprites/mushroom.png";
import fireflower from "../resources/pokerSprites/fireflower.png";
import luigi from "../resources/pokerSprites/luigi.png";
import mario from "../resources/pokerSprites/mario.png";
import star from "../resources/pokerSprites/star.png";

import background from "../resources/pokerSprites/table.png";
import backdrop from "../resources/pokerSprites/table.png";

function Game() {
    const images = [cloud, mushroom, fireflower, luigi, mario, star];
    const [cards, setCards] = useState([0,0,0,0,0]);
    const [selectedCards, setSelectedCards] = useState([]); // Change to an array

    const handleCardClick = (index) => {
        const newCards = [...cards];
        console.log(index);
        newCards[index] = Math.floor(Math.random() * 6); // THIS IS WHERE THE CARD VALUE (WOULD) CHANGE!
        setCards(newCards);

        // Add or remove the index from the selectedCards array
        if (selectedCards.includes(index)) {
            setSelectedCards(selectedCards.filter(i => i !== index));
        } else {
            setSelectedCards([...selectedCards, index]);
        }
    };

    return (
        <div className="Game" style = {{
            backgroundImage: `url(${backdrop})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            height: '100vh',
            width: '100vw'
        }}>
            {cards.map((_, index) => (
                <div
                    key={index}
                    className={`card ${selectedCards.includes(index) ? 'raised' : ''}`} // Check if the card is in the selectedCards array
                    onClick={() => handleCardClick(index)}
                >
                    <img src={images[cards[index]]} alt={`Card ${index + 1}`} />
                </div>
            ))}
        </div>
    );
}

export default Game;