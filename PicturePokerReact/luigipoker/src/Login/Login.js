import "./Login.css";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

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
    const [error, setError] = useState("");

    const handleSubmission = async (e) => {
        e.preventDefault();
        setError(null);
        
        try {
            const serverHost = 'http://localhost:8080';
            const response = await fetch(`${serverHost}/getByPlayerName/${loginUsername}`);

            if (response.ok) {
                if(response.status === 200){
                // User login info is saved as loggedInUser for future use
                const loggedInUser = await response.json();
                navigate("/menu");
            } else if (response.status === 404){
                setError("Username not found")
            }else {
                setError("An error occured while logging in")
            }
        }  
        } catch (error) {
            console.error(error);
        }
    };

    const handleAccountCreation = async (e) => {
        e.preventDefault();
        // Save the user to the database
        try {
            const serverHost = 'http://localhost:8080';
            const response = await fetch(`${serverHost}/createNewPlayer`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    playerName: registerUsername,
                    password: registerPassword
                })
            });

            if (response.ok) {
                const newUser = await response.json();
                console.log("Successfully created account for", newUser);
                // User login info is saved as newUser for future use
                navigate("/menu");
            } else {
                setError("An error occured while creating your account")
            }
        } catch (error) {
            console.error(error);
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
                        <img src={loginP} alt="" style={{width: '100hh', marginBottom: '50px'}}/>
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
                                style={{ width: '100px', margin: '10px 0' }} 
                            />
                        </form>
                    </div>
                    <div className="register">
                        <img src={registerP} alt="" style={{width: '400px', marginBottom: '50px'}}/>
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
                                style={{ width: '100px', margin: '10px 0' }} 
                            />
                        </form>
                    </div>
                </div>
            </div>
        </div>
      );
};

export default Login;