import React, {useState, useEffect} from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import './Game.css'; 

// various card symbols
import cloud from "../resources/pokerSprites/cloud.png";
import mushroom from "../resources/pokerSprites/mushroom.png";
import fireflower from "../resources/pokerSprites/fireflower.png";
import luigi from "../resources/pokerSprites/luigi.png";
import mario from "../resources/pokerSprites/mario.png";
import star from "../resources/pokerSprites/star.png";
import normalCard from "../resources/pokerSprites/normalCard.png";
import proCard from "../resources/pokerSprites/proCards.png";

// various buttons
import betButton from "../resources/pokerSprites/bet.png";
import draw from "../resources/pokerSprites/draw.png";
import hold from "../resources/pokerSprites/hold.png";

// various luigi sprites
import luigiDefault from "../resources/luigi/soulread.jpg";
import luigiShuffling  from "../resources/luigi/shuffling.gif";

// game information images
import handPowers from "../resources/misIcons/handPowers.png";
import picturePowers from "../resources/misIcons/picturePowers.png";

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
    const [pRoundsWon, setPRoundsWon] = useState([]);
    useLocation();

    useEffect(() => {
        const loadGame = async () => {
            const response = await axios.get(`http://localhost:8080/getByGameID/` + gid)
                .catch(function () {
                    console.log("GetbyGameID didn't work. " + gid);
                });
            setPlayers(response.data.players);
        }

        //if we are in the round end, jump to the end screen

        // Call loadGame immediately and then every X milliseconds
        loadGame();
        const intervalId = setInterval(loadGame, 5000); // 5000 ms = 5 seconds
        //TODO: This is how we update the players, which really isn't that great a strategy.

        // Clear interval on unmount
        return () => clearInterval(intervalId);
    }, [gid]);


    useEffect(() => {
        if(players) {
            const filteredPlayers = players.filter(player => player !== 0);

            async function getPlayerNames() {
                const names = [];
                const tokens = [];
                const bet = [];
                const waiting = [];
                const roundsWon = [];

                for(let i=0; i < filteredPlayers.length; i++) {
                    let response = await axios.get(`http://localhost:8080/getByPlayerID/${filteredPlayers[i]}`)
                        .catch(function(){
                            console.log('getByPlayerID didn\'t work ' + filteredPlayers[i]);
                        });
                    names.push(response.data.playerName);
                    tokens.push(response.data.tokens);
                    bet.push(response.data.bet);
                    waiting.push(response.data.finishedRound);
                    roundsWon.push(response.data.roundsWon);
                }
                setPNames(names);
                setPTokens(tokens);
                setPBet(bet);
                setPWaiting(waiting);
                setPRoundsWon(roundsWon);
            }
            getPlayerNames();
        }
    }, [players]); // Use a separate useEffect hook to update pNames when players changes

    return (
        <div style={{fontSize: "2.3vh"}} className={"bordering"}>
            {pNames.map((value, i) => (
                <React.Fragment key={i}>
                    <ColorfulText text={pNames[i] + (pNames[i] === sessionStorage.getItem("username") ? " (You!)" : "") }/>
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
                    <div style={{display: 'flex', alignItems: 'center'}}>
                        <ColorfulText text={"Wins against Luigi: " + pRoundsWon[i]} />
                    </div>
                    {pWaiting[i] === 0 && <ColorfulText text={"Tell this person to hurry up! "}/>}
                    <br/>
                </React.Fragment>
                ))}
        </div>
    );
}


function RoundCount({gid, gameUpdate}) {
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
    }, [gid, gameUpdate]); // Remove players from the dependency array

    //navigate away in the event we're done with the game
    const navigate = useNavigate();
    if (curRound > rounds) {
        navigate("/gameEnd", { state: { gameId: gid } })
    }

    return(
        <div style = {{fontSize: "2vh"}} className={"bordering"}>
            <ColorfulText text={"Round " + curRound + " of " + rounds} />
        </div>
    )
}

function WaitingOnTurn(turnEnd) {
    const [finished, setFinished] = useState(0); // Use state to track 'finished'

    useEffect(() => {
        if (turnEnd.turn === true)
            setFinished(1);
        else
            setFinished(0);
    }, [turnEnd]);

    // Conditionally render based on 'finished'
    if (finished === 1) {
        return (
            <div style={{ fontSize: "2vh" }} className={"bordering"}>
                <ColorfulText text={"Waiting for round to finish.  "} />
            </div>
        );
    } else {
        return (
            <div style={{ fontSize: "2vh" }} className={"bordering"}>
                <ColorfulText text={"Waiting on YOU to finish! "} />
            </div>
        )
    }
}

function cards2Int(hand){
    if (hand === "luigi")
        return [7, 7, 7, 7, 7];
    if (hand !== undefined) {
        const cardMapping = {
            "CLOUD": 0,
            "MUSHROOM": 1,
            "FIRE_FLOWER": 2,
            "LUIGI": 3,
            "MARIO": 4,
            "STAR": 5,
            "REG": 6,
            "PRO": 7
        };
        // Use the mapping to convert card suits to their corresponding values
        return hand.map(card => cardMapping[card.suit]);
    }
    return [6, 6, 6, 6, 6];

}

function EndOfRoundList({info}) {
    const [players, setPlayers] = useState([]);

    useEffect(() => {
        async function getPlayerNames() {
            const names = [];

            for(let i=0; i < info.length; i++) {
                let response = await axios.get(`http://localhost:8080/getByPlayerID/${info[i].pID}`)
                    .catch(function(){
                        console.log('getByPlayerID didn\'t work ' + info[i].pID);
                    });
                names.push(response.data.playerName);
            }
            setPlayers(names);
        }
        getPlayerNames();
    }, [info]);

    return (
        <div>
            {players.map((value, i) => (
                <React.Fragment key={i}>
                    <ColorfulText text={players[i] + ": " + info[i].newTokens + " token" + (info[i].newTokens > 1 ? "s" : "") +" ("+ (info[i].winLossAmount > 0 ? "+" : "")+info[i].winLossAmount +")"}/>
                </React.Fragment>
            ))}
        </div>
    )
}

function nextRound(gid){
    const endRound = async () => {
        // api call once and done
        await axios.get(`http://localhost:8080/getEndOfRoundInformation/${gid}/${true}`)
            .catch(function () {
                console.log("GetEndOfRoundInformation didn't work. " + gid);
            });
    }
    endRound();
}
function EndOfRound({gid, turnEnd, showCards, gameUpdate}){
    const [data, setData] = useState(null);
    const [dispResults, setDispResults] = useState(false);

    useEffect(() => {
        const endRound = async () => {
            //we only start API calling when our turn ends.
            if (turnEnd) {
                const response = await axios.get(`http://localhost:8080/getEndOfRoundInformation/${gid}/${false}`)
                    .catch(function () {
                        console.log("GetEndOfRoundInformation didn't work. " + gid);
                    });
                setData(response.data);
            }
        }
        endRound();

        const intervalId = setInterval(endRound, 5000); // 5000 ms = 5 seconds
        //TODO: Intervals are bad, and we have a lot of them. At this state, we live with it.

        // Clear interval on unmount
        return () => clearInterval(intervalId);
    }, [gid, turnEnd]);

    //this is the case where our game is now over
    if (turnEnd && data !== null && data.playerShowdownInfos !== null && data.playerShowdownInfos.length !== 0){
        //scan through for our player ID
        for (let i = 0; i < data.playerShowdownInfos.length; ++i){
            if (data.playerShowdownInfos[i].pID === parseInt(sessionStorage.getItem("userID"))){
                // With this information, we can do stuff.

                //First, we showcase the results of the drawing
                showCards(data, i);

                //Set a timer to wait for a bit before we display results
                setTimeout(() => {
                    setDispResults(true);
                }, 7000); // give it like 7 seconds

                //Set a timer to go on to the next round after a while
                setTimeout(() => {
                    nextRound(gid);
                    setData(null); //this is so that we don't call this thing again
                    setDispResults(false);
                    gameUpdate(true);
                }, 20000); // let the user bask in the results for like 20 seconds

                //Next, we return a splash screen of the results
                return(
                    <div>
                        {dispResults &&
                            <div className="splashScreen bordering">
                                <ColorfulText text = {"You drew: " + (data.playerShowdownInfos[i].handType)}/>
                                <ColorfulText text = {"Luigi drew: " + data.luigiHandType}></ColorfulText>
                                <ColorfulText text = {"You are " + (data.playerShowdownInfos[i].winLossAmount > 0 ? "up " : "down ") + Math.abs(data.playerShowdownInfos[i].winLossAmount) + " tokens"} />
                                <ColorfulText text={"Current standings: "}/>
                                <EndOfRoundList info = {data.playerShowdownInfos} />
                            </div>
                        }
                    </div>
                );
            }
        }
    }

    //return nothing if we don't have anything
    return (
        <div>

        </div>
    )
}

function Game() {
    const images = [cloud, mushroom, fireflower, luigi, mario, star, normalCard, proCard];
    const [cards, setCards] = useState([6, 6, 6, 6, 6]);
    const [hand, setHand] = useState(undefined);
    const [dealerCards, setDealerCards] = useState([7, 7, 7, 7, 7]);
    const [dealerHand, setDealerHand] = useState("luigi");
    const [selectedCards, setSelectedCards] = useState([]); // Change to an array

    //set luigi animation based on what's happening.
    const [luigiState, setLuigiState] = useState(0);

    const [gid, setGID] = useState(0);
    const location = useLocation();

    const [turnEnd, setTurnEnd] = useState(false);

    //toggle this whenever we wanna update a bunch of stuff
    const [gameUpdate, setGameUpdate] = useState(false);

    const pid = sessionStorage.getItem('userID');

    const navigate = useNavigate();

    //startup stuff - we first get the game id and stuff
    useEffect(() => {
        //boot players out if they haven't logged in.
        if (sessionStorage.getItem('userID') === null)
            navigate('/');
        if (location.state !== null)
            setGID(location.state.gameId);

        setGameUpdate(true);

        // set turn end to false for when we update it later
        setTurnEnd(false);
        if (gid === 0 || gid !== null) {
            const inGame = async () => {
                const response = await axios.get(`http://localhost:8080/getPlayerActiveGame/${pid}`)
                    .catch(function () {
                        console.log("getByPlayerID API call did not work");
                    });
                if (response !== undefined && response.status === 200) {
                    setGID(response.data.id);
                }
            }
            inGame()
        }
    }, []);


    //do stuff when the bet button is clicked
    const [bet, setBet] = useState(0);
    const [tokens, setTokens] = useState(10);

    const getPlayerData = async () => {
        const response = await axios.get('http://localhost:8080/getByPlayerID/' + pid)
            .catch(function () {
                console.log("getByPlayerID API call didn't work. ");
            });
        //try to update as much about the player as reasonable
        if (response.status === 200){
            if (response.data.finishedRound === 1){
                setTurnEnd(true);
            }
            else
                setTurnEnd(false);

            setHand(response.data.hand);
            setTokens(response.data.tokens);
            setBet(response.data.bet);

            //cycle through the hand, and set any cards which are to be changed.
            let tmpSelection = [];
            for (let i = 0; i < response.data.hand.length; ++i){
                if (response.data.hand[i].toChange){
                    tmpSelection.push(i);
                }
            }
            setSelectedCards(tmpSelection);
        }
    };

    //update the player's hand whenever we activate game update
    useEffect(() => {
        if (gameUpdate) {
            getPlayerData();
            setGameUpdate(false);
        }
    }, [gameUpdate]);

    //update the player's cards whenever necessary.
    useEffect(() => {
        setCards(cards2Int(hand));
    }, [hand]);

    //update the dealer's cards whenever necessary
    useEffect(() => {
        if (turnEnd)
            setDealerCards(cards2Int(dealerHand));
        else
            setDealerCards(cards2Int("luigi"));
    }, [dealerHand]);


    const finishRound = async (pid) => {
        await axios.put('http://localhost:8080/finishRound/' + pid)
            .catch(function () {
                console.log("finishRound API call didn't work. ");
            });

    };

    const setToChange = async(index) => {
        //just set something to be changed out depending on if it has been clicked or not
        await axios.put(`http://localhost:8080/changeCard/` + pid + '/' + index)
            .catch(function(){
                console.log("changeCard API call didn't work. Card " + index);
            });
    }

    const handleCardClick = (index) => {
        if (!turnEnd) {
            setToChange(index);
            // Add or remove the index from the selectedCards array
            if (selectedCards.includes(index)) {
                setSelectedCards(selectedCards.filter(i => i !== index));
            } else {
                setSelectedCards([...selectedCards, index]);
            }
        }
    };


    const handleBetClick = async() => {
        if (bet < 5) {
            const response = await axios.put("http://localhost:8080/raise/" + pid)
                .catch(function () {
                    console.log("raise API call didn't work. ")
                });
            setBet(response.data.bet);
            setTokens(response.data.tokens);
            setLuigiState(0);
        }
    };

    const endTurnProcedure = async() => {
        if (bet !== 0)
        {
            //only animate if we haven't finished our turn.
            if (turnEnd === false)
                setLuigiState(1);

            await finishRound(pid);

            //note: turnEnd only gets updated in this section
            getPlayerData(pid);

            //pseudo animations - we don't have the budget to go further.
            setTimeout(() => {
                setLuigiState(0);
            }, 5310);

            //show luigi's hand (as of the turn end)
            const response = await axios.get("http://localhost:8080/getByGameID/" + gid)
                .catch(function () {
                    console.log("getByGameID API call didn't work. ")
                });
            if (response.status === 200) {
                setDealerHand(response.data.hand);
            } else {
                setDealerHand("luigi")
            }
        }
    }

    //run the end of turn stuff whenever turnEnd is true (or set to true)
    useEffect(() => {
        if (turnEnd === true){
            endTurnProcedure();
        }
        else
            setDealerHand("luigi"); //if we're not at the turn end, hide the dealer's hand.
    }, [turnEnd]);

    //function that goes through a sequence to display card data to the user
    const showCards = (eorData, idx) => {
        //update hand
        setHand(eorData.playerShowdownInfos[idx].hand);

        //reset the cards selected to nothing
        setTimeout(() => {
            if (selectedCards.length !== 0)
                setSelectedCards([]);
        }, 3000);

        //let the player stare at their cards for a bit before updating Luigi's hand
        setTimeout(() => {
            setDealerHand(eorData.luigiHand);
        }, 4000);
    }

    const [hoverState, setHoverState] = useState(false);

    const handleMouseOver = () => {
        setHoverState(true);
    }

    const handleMouseOut = () => {
        setHoverState(false);
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
            <RoundCount gid = {gid} gameUpdate = {gameUpdate}/>
            <WaitingOnTurn turn={turnEnd} />
            <EndOfRound gid = {gid} turnEnd={turnEnd} showCards={showCards} gameUpdate = {setGameUpdate}/>
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
                className="endTurnButton glow"
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

            <div>
                <div
                    className={`info bordering ${hoverState ? 'hover' : ''}`}
                    onMouseOver={handleMouseOver}
                    onMouseOut={handleMouseOut}
                >
                    <ColorfulText text={"?"}/>
                </div>
                <div className={`tooltip ${hoverState ? 'hover' : ''}`}>
                    <div className={"bordering"}>
                        <ColorfulText text={"Suits / Multipliers"}/>
                    </div>
                    <img src={picturePowers} alt="Powers of Pictures" className={"crispImages"}/>
                    <img src={handPowers} alt="Powers of Hands" className={"crispImages"}/>
                </div>
            </div>
        </div>
    );
}

export default Game;