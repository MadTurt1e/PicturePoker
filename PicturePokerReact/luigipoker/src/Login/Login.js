import "./Login.css";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

import logo from '../resources/menuIcons/luigi poker.png'
import loginP from '../resources/loginIcons/login.png'
import registerP from '../resources/loginIcons/register.png'
import usernameP from '../resources/loginIcons/username.png'
import passwordP from '../resources/loginIcons/password.png'

const Login = () => {
    const navigate = useNavigate();
    const [loginUsername, setLoginUsername] = useState("");
    const [loginPassword, setLoginPassword] = useState("");
    const [registerUsername, setRegisterUsername] = useState("");
    const [registerPassword, setRegisterPassword] = useState("");
    const [errorMsg, setError] = useState("");

    const handleSubmission = async (e) => {
        e.preventDefault();
        setError(null);

        try {
            const serverHost = sessionStorage.getItem('host');
            const response = await axios.get(`${serverHost}/getByPlayerName/` + loginUsername);

            if (response.status === 200) {
                const data = response.data;
                if(data.id !== 0 && data.passcode === loginPassword){
                    // User login info is saved as loggedInUser for future use
                    sessionStorage.setItem('userID', data.id);
                    sessionStorage.setItem('username', data.playerName);
                    sessionStorage.setItem('dollars', data.dollars);
                    navigate("/menu");
                } else if (data.id === 0){
                    setError("Username not found")
                }else {
                    setError("An error occured while logging in")
                }
            }
        } catch (error) {
            if (error.response && error.response.status === 404) {
                setError("Username not found");
            } else {
                console.error(error);
                setError("An error occured while logging in");
            }
        }
    };

    const handleAccountCreation = async (e) => {
        e.preventDefault();
        // Save the user to the database
        try {
            const serverHost = sessionStorage.getItem('host');
            const response = await axios.post(`${serverHost}/createNewPlayer`, {
                playerName: registerUsername,
                password: registerPassword
            });

            if (response.status === 200) {
                const newUser = response.data;
                console.log("Successfully created account for", newUser.playerName);

                sessionStorage.setItem('userID', response.data.id);
                sessionStorage.setItem('username', response.data.playerName);
                sessionStorage.setItem('dollars', 50);

                // User login info is saved as newUser for future use
                navigate("/menu");
            } else {
                setError("An error occured while creating your account")
            }
        } catch (error) {
            console.error(error);
            setError("An error occured while creating your account");
        }
    };

    return (
        <div className="background">
            <div className="container">
                <div className="logo">
                    <img src={logo} alt="Logo" />
                </div>
                <div className="form-container">
                    <div className="login">
                        <img className="header" src={loginP} alt="" style={{width: '100hh', marginBottom: '50px'}}/>
                        <form onSubmit={handleSubmission}>
                        <label>
                                <img src={usernameP} alt="Username" />
                                <input
                                    type="text"
                                    value={loginUsername}
                                    onChange={(e) => setLoginUsername(e.target.value)}
                                />
                            </label>
                            <label>
                                <img src={passwordP} alt="Password" />
                                <input
                                    type="password"
                                    value={loginPassword}
                                    onChange={(e) => setLoginPassword(e.target.value)}
                                />
                            </label>
                            <input 
                                type="image" 
                                src={loginP} 
                                alt="Login" 
                                className ="glow"
                                style={{width: 'auto', height:'50px', margin: '10px 0'}}
                            />
                        </form>
                    </div>
                    <div className="register">
                        <img className="header" src={registerP} alt="" style={{width: '400px', marginBottom: '50px'}}/>
                        <form onSubmit={handleAccountCreation}>
                        <label>
                                <img src={usernameP} alt="Username" />
                                <input
                                    type="text"
                                    value={registerUsername}
                                    onChange={(e) => setRegisterUsername(e.target.value)}
                                />
                            </label>
                            <label>
                                <img src={passwordP} alt="Password" />
                                <input
                                    type="password"
                                    value={registerPassword}
                                    onChange={(e) => setRegisterPassword(e.target.value)}
                                />
                            </label>
                            <input 
                                type="image" 
                                src={registerP} 
                                alt="Create Account" 
                                className ="glow"
                                style={{width: 'auto', height:'50px', margin: '10px 0'}}
                            />
                        </form>
                    </div>
                </div>
            </div>
        </div>
      );
};

export default Login;
