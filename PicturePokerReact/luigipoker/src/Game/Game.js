import React, { useState, useEffect } from 'react';
import {useLocation} from 'react-router-dom';
import './Game.css'; 

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

import luigiDefault from "../resources/luigi/soulread.png";
import luigiShuffling  from "../resources/luigi/shuffling.gif";

import token from "../resources/pokerSprites/token.png";

import backdrop from "../resources/pokerSprites/table.png";

import axios from "axios";
import ColorfulText from "../index";

function Game() {
    const images = [cloud, mushroom, fireflower, luigi, mario, star, normalCard, proCard];
    const [cards, setCards] = useState([6, 6, 6, 6 , 6]);
    const [dealerCards, setDealerCards] = useState([7, 7, 7, 7, 7]);
    const [selectedCards, setSelectedCards] = useState([]); // Change to an array

    //set luigi animation based on what's happening.
    const [luigiState, setLuigiState] = useState(0);
    const [data, setData] = useState(null);

    const location = useLocation();
    const gid = location.state.gameId;

    //do stuff when the bet button is clicked
    const [bet, setBet] = useState(0);
    const [tokens, setTokens] = useState(10);

    const pid = sessionStorage.getItem('userID');

    const getPlayerData = async () => {
        const response = await axios.get('http://localhost:8080/getByPlayerID/' + pid)
            .catch(function (error) {
                console.log("getByPlayerID API call didn't work. ");
            });
        setData(response.data);
        //try to update as much about the player as reasonable
        if (response.status === 200) {
            const hand = response.data.hand.map(card => card.suit);
            setCards(hand); // Update cards state here
            setTokens(response.data.tokens);
            setBet(response.data.bet);
        }

    };

    const finishRound = async (pid) => {
        const response = await axios.put('http://localhost:8080/finishRound/' + pid)
            .catch(function (error) {
                console.log("finishRound API call didn't work. ");
            });
        setData(response.data);


    };

    const setToChange = async(index) => {
        //just set something to be changed out depending on if it has been clicked or not
        const response = await axios.put(`http://localhost:8080/changeCard/` + pid + '/' + index)
            .catch(function(error){
                console.log("changeCard API call didn't work. Card " + index);
            });
    }

    const handleCardClick = (index) => {
        const newCards = [...cards];
        //set to change here, via that one API call. .
        setToChange(index);

        setCards(newCards);

        // Add or remove the index from the selectedCards array
        if (selectedCards.includes(index)) {
            setSelectedCards(selectedCards.filter(i => i !== index));
        } else {
            setSelectedCards([...selectedCards, index]);
        }
    };


    const handleBetClick = async() => {
        if (bet < 5) {
            const response = await axios.put("http://localhost:8080/raise/" + pid)
                .catch(function (error) {
                    console.log("raise API call didn't work. ")
                });
            console.log(response);
            setBet(response.data.bet);
            setTokens(response.data.tokens);
            setLuigiState(0);
        }
    };

    const endTurnProcedure = async() => {
        const newCards = [...cards];
        selectedCards.map((index) =>
            newCards[index] = Math.floor(Math.random() * 6)
        )
        setCards(newCards);
        setBet(0);

        finishRound(pid);

        getPlayerData(pid);
        setLuigiState(1);
        //pseudo animations - we don't have the budget to go further. I'm going to screencap these images eventually.
        setTimeout(() => {
            setLuigiState(0);
        }, 2000);

        //show luigi's hand
        const response = await axios.get("http://localhost:8080/getByGameID/" + gid)
            .catch(function (error) {
                console.log("getByGameID API call didn't work. ")
            });
        const hand = response.data.hand.map(
            //we are probably going to need to do a conversion from suit to ints - to be tested.
            hand => hand.Suit
        );
    }


    let tokenMessage = "Tokens: " + tokens;
    return (
        <div style={{
            backgroundImage: `url(${backdrop})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            height: '100vh',
            width: '100vw'
        }}>
            <div>
                <ColorfulText text = {gid} />
            </div>
            <div>
                {luigiState === 0 && <img src={luigiDefault} alt="luigistare" className="luigi crispImages"/>}
                {luigiState === 1 && <img src={luigiShuffling} alt="luigishuffle" className="luigi crispImages"/>}
            </div>
            <div className="cards" style={{
                top: '5%'
            }}>
                {cards.map((_, index) => (
                    <div key={index} className={'dealercard'}>
                        <img src={images[dealerCards[index]]} alt={`Dealer Card ${index + 1}`} className='crispImages'/>
                    </div>
                ))}
            </div>
            <div className = "tokenCount bordering">
                <ColorfulText text={tokenMessage} />
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