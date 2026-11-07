import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import keycloak from '../keycloak';
import MapComponent from './MapComponent';

const [locationPopUp, setLocationPopUp] = useState(false);



export default function Navigation() {
  
  
  
  return (
    <nav className='flex-row'>
      <div className="relative flex p-20 flex-nowrap justify-between items-center content-stretch gap-x-20px pbs-10 pbe-10 bg-black text-navbar-500 w-full">
  
      <div className="text-inherit">
        LogoContainer
      </div>
      
      
      <div className='flex gap-5'>
        <div className="text-inherit">
        Location
      </div>
        <div className="text-inherit ms-20">
        Login
      </div>
  
      <div className="text-inherit">
        Registration
      </div></div>
      
    </div>
    <div className='relative flex-space items-center content-stretch bg-navbar-500 w-full h-15 pt-2 pb-2'>
      <div className='relative w-20 h-full bg-black-500'></div>
      <div>navbut</div>

    </div>
    
    

    </nav>


    


      
    
  );
}