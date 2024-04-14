import React, {useState, useEffect} from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
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

import luigiDefault from "../resources/luigi/soulread.jpg";
import luigiShuffling  from "../resources/luigi/shuffling.gif";

import token from "../resources/pokerSprites/token.png";

import backdrop from "../resources/pokerSprites/table.png";

import axios from "axios";
import ColorfulText from "../index";

function PlayerList({gid}) {
    const [players, setPlayers] = useState(null);
    const [pNames, setPNames] = useState([]);
    const [pTokens, setPTokens] = useState([]);
    const [pBet, setPBet] = useState([]);
    const [pWaiting, setPWaiting] = useState([]);
    const location = useLocation();

    useEffect(() => {
        const loadGame = async () => {
            const response = await axios.get(`http://localhost:8080/getByGameID/` + gid)
                .catch(function(){
                    console.log("GetbyGameID didn't work. " + gid);
                });
            setPlayers(response.data.players);
        }
        loadGame();
    }, [gid]); // Remove players from the dependency array

    useEffect(() => {
        if(players) {
            const filteredPlayers = players.filter(player => player !== 0);

            async function getPlayerNames() {
                const names = [];
                const tokens = [];
                const bet = [];
                const waiting = [];

                for(let i=0; i < filteredPlayers.length; i++) {
                    let response = await axios.get(`http://localhost:8080/getByPlayerID/${filteredPlayers[i]}`)
                        .catch(function(error){
                            console.log('getByPlayerID didn\'t work ' + filteredPlayers[i]);
                        });
                    names.push(response.data.playerName);
                    tokens.push(response.data.tokens);
                    bet.push(response.data.bet);
                    waiting.push(response.data.finishedRound);

                }
                setPNames(names);
                setPTokens(tokens);
                setPBet(bet);
                setPWaiting(waiting);
            }
            getPlayerNames();
        }
    }, [players]); // Use a separate useEffect hook to update pNames when players changes
    return (
        <div style={{fontSize: "3vh"}} className={"bordering"}>
            {pNames.map((value, i) => (
                <React.Fragment key={i}>
                    <ColorfulText text={pNames[i]}/>
                    <div style={{display: 'flex', alignItems: 'center'}}>
                        <ColorfulText text={"Tokens: " + pTokens[i]}/>
                    </div>
                    <div style={{display: 'flex', alignItems: 'center'}}>
                    <ColorfulText text={"Bet: "}/>
                        <div>
                            <img src={pBet[i] > 0 ? token : null} alt="" className="miniToken crispImages"/>
                            <img src={pBet[i] > 1 ? token : null} alt="" className="miniToken crispImages"/>
                            <img src={pBet[i] > 2 ? token : null} alt="" className="miniToken crispImages"/>
                            <img src={pBet[i] > 3 ? token : null} alt="" className="miniToken crispImages"/>
                            <img src={pBet[i] > 4 ? token : null} alt="" className="miniToken crispImages"/>
                            <img src={pBet[i] > 5 ? token : null} alt="" className="miniToken crispImages"/>
                        </div>
                    </div>
                    {pWaiting[i] === 0 && <ColorfulText text={"Tell this person to hurry up! "}/>}
                    <br/>
                </React.Fragment>
                ))}
        </div>
    );
}


function RoundCount({gid}) {
    const [rounds, setRounds] = useState(null);
    const [curRound, setCurRounds] = useState(null);
    useEffect(() => {
        const loadGame = async () => {
            const response = await axios.get(`http://localhost:8080/getByGameID/` + gid)
                .catch(function(){
                    console.log("GetbyGameID didn't work. " + gid);
                });
            setRounds(response.data.numRounds);
            setCurRounds(response.data.curRound)
        }
        loadGame();
    }, [gid]); // Remove players from the dependency array
    return(
        <div style = {{fontSize: "2vh"}} className={"bordering"}>
            <ColorfulText text={"Round " + curRound + " of " + rounds} />
        </div>
    )
}

function WaitingOnTurn(){
    let waitingOnTurn = false;
    useEffect(() => {
        async function getPlayerNames(){
            let response = await axios.get(`http://localhost:8080/getByPlayerID/${sessionStorage.getItem("userID")}`)
                .catch(function(error){
                    console.log('getByPlayerID didn\'t work. ');
                });
            if (response.status === 200 && response.data.finishedRound === 1){
                waitingOnTurn = true;
            }
        }
    }, []);
    return (
        <div style = {{fontSize: "2vh"}} className={"bordering"}>
            <ColorfulText text={"Waiting for round to finish.  "}/>
        </div>
    )
}
function Game() {
    const images = [cloud, mushroom, fireflower, luigi, mario, star, normalCard, proCard];
    const [cards, setCards] = useState([6, 6, 6, 6, 6]);

    const [dealerCards, setDealerCards] = useState([7, 7, 7, 7, 7]);
    const [selectedCards, setSelectedCards] = useState([]); // Change to an array

    //set luigi animation based on what's happening.
    const [luigiState, setLuigiState] = useState(0);
    const [data, setData] = useState(null);

    const [gid, setGID] = useState(0);
    const location = useLocation();

    const pid = sessionStorage.getItem('userID');
    useEffect(() => {
        //failsafe
        if (location.state !== null){
            setGID(location.state.gameId);
        }
        else {
            const inGame = async () => {
                const response = await axios.get(`http://localhost:8080/getPlayerActiveGame/${pid}`)
                    .catch(function () {
                        console.log("getByPlayerID API call did not work");
                    });
                if (response.status === 200) {
                    setGID(response.data.id);
                }
            }
            inGame()
        }
    }, []);

    //do stuff when the bet button is clicked
    let bet = 0;
    let tokens = 10;

    const getPlayerData = async () => {
        const response = await axios.get('http://localhost:8080/getByPlayerID/' + pid)
            .catch(function () {
                console.log("getByPlayerID API call didn't work. ");
            });
        setData(response.data);
        //try to update as much about the player as reasonable
        if (response.status === 200) {
            // Define the mapping
            const cardMapping = {
                "CLOUD": 0,
                "MUSHROOM": 1,
                "FIRE_FLOWER": 2,
                "LUIGI": 3,
                "MARIO": 4,
                "STAR": 5
            };

            // Use the mapping to convert card suits to their corresponding values
            const hand = response.data.hand.map(card => cardMapping[card.suit]);
            tokens = (response.data.tokens);
            bet = (response.data.bet);
            setCards(hand); // Update cards state here
        }
    };
    useEffect(() => {
        getPlayerData();
    }, []);

    const finishRound = async (pid) => {
        //we don't really care about the return values of this thing.
        await axios.put('http://localhost:8080/finishRound/' + pid)
            .catch(function () {
                console.log("finishRound API call didn't work. ");
            });

    };

    const setToChange = async(index) => {
        //just set something to be changed out depending on if it has been clicked or not
        await axios.put(`http://localhost:8080/changeCard/` + pid + '/' + index)
            .catch(function(error){
                console.log("changeCard API call didn't work. Card " + index);
            });
    }

    const handleCardClick = (index) => {
        setToChange(index);

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
            bet = response.data.bet;
            tokens = response.data.tokens;
            setLuigiState(0);
        }
    };

    const endTurnProcedure = async() => {
        if (bet !== 0)
        {
            bet = 0;

            finishRound(pid);

            getPlayerData(pid);
            setLuigiState(1);
            //pseudo animations - we don't have the budget to go further.
            setTimeout(() => {
                setLuigiState(0);
            }, 5310);

            //show luigi's hand
            const response = await axios.get("http://localhost:8080/getByGameID/" + gid)
                .catch(function (error) {
                    console.log("getByGameID API call didn't work. ")
                });
            if (response.status === 200 && response.data.hand === null) {
                // Define the mapping
                const cardMapping = {
                    "CLOUD": 0,
                    "MUSHROOM": 1,
                    "FIRE_FLOWER": 2,
                    "LUIGI": 3,
                    "MARIO": 4,
                    "STAR": 5
                };

                // Use the mapping to convert card suits to their corresponding values
                const hand = response.data.hand.map(card => cardMapping[card.suit]);
                setDealerCards(hand); // Update cards state here
            } else {
                setDealerCards([7, 7, 7, 7, 7])
            }
        }
    }


    return (
        <div style={{
            backgroundImage: `url(${backdrop})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            height: '100vh',
            width: '100vw'
        }}>
            <div>
                {luigiState === 0 && <img src={luigiDefault} alt="luigistare" className="luigi crispImages"/>}
                {luigiState === 1 && <img src={luigiShuffling} alt="luigishuffle" className="luigi crispImages"/>}
            </div>
            <div style={{fontSize: "1.7vw"}} className={"bordering"}>
                <ColorfulText text={("Current Game ID: " + gid).toString()}/>
            </div>
            <PlayerList gid = {gid}/>
            <RoundCount gid = {gid}/>
            <WaitingOnTurn/>
            <div className="cards" style={{
                top: '5%'
            }}>
                {cards.map((_, index) => (
                    <div key={index} className={'dealercard'}>
                        <img src={images[dealerCards[index]]} alt={`Dealer Card ${index + 1}`} className='crispImages'/>
                    </div>
                ))}
            </div>
            <div className="tokenCount bordering">
                <ColorfulText text={"Tokens: " + tokens}/>
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