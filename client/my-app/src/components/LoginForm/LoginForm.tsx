import React from "react";
import GoogleLoginButton from "../GoogleLoginButton/GoogleLoginButton";
import "./LoginForm.css"

const LoginForm = () => {









    return(
        <div className="LoginForm">
            <h1>Login to your account</h1>
            
            <div className="form-content">
                <input 
                    type="text" 
                    autoComplete="off" 
                    name="username" 
                    className="input" 
                    placeholder="Username"
                />
                
                <input 
                    type="password" 
                    autoComplete="off" 
                    name="password" 
                    className="input" 
                    placeholder="Password"
                />

                <button className="LoginButton">Login</button>
            </div>

            
            <div className="OptionsWrapper">
                <span>Don't have an account? <a href="">Sign In!</a></span>
                
                <GoogleLoginButton /></div>
            
        </div>
    )
};

export default LoginForm;