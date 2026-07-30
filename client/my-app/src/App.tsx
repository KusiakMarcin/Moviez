import React from "react";
import {BrowserRouter, Routes, Route, Link} from 'react-router-dom';


import LoginPage from "./components/Pages/LoginPage";
import HomePage from "./components/Pages/HomePage";

const App = () =>
    {



        return (
                <body>
                    
                    <style>@import url('https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100..900;1,100..900&display=swap');</style>
                
                    <BrowserRouter>
                    <nav>
                        <Link to ="/"> Home</Link>
                        <Link to ="/login">Login</Link>

                    </nav>

                    <Routes>
                        
                        



                        <Route path="/" element ={<HomePage/>}/>
                        <Route path="/login" element={<LoginPage/>}/>

                    </Routes>                    
                    
                    </BrowserRouter>
                    
                </body>
                
                
                    
                    
                
            
        )
    };


export default App;