import React from "react";
import {APIProvider, Map,AdvancedMarker,type MapCameraChangedEvent,Pin,useMap} from '@vis.gl/react-google-maps';

  type Poi = {key: string, location: {lat:number,lng:number}}
    const locations: Poi[]=[
        {key:'some', location:{lat:52.23480319338484, lng:20.90553315522474}}
    ]
const Markers = (props: {pois:Poi[]})=>
        {
            return (
                <>
                {props.pois.map( (poi: Poi) => (
                    
                    <AdvancedMarker
                    key={poi.key}
                    position={poi.location}>

                    <Pin background={'#FBBC04'} glyphColor={'#000'} borderColor={'#000'} />
                    
                    </AdvancedMarker>
                ))}
                </>
            )
        }

export default function MapComponent()
{
  
    
    

return(

   <APIProvider apiKey={'AIzaSyAlwSeoiI_H8PMGTgYIEkCQFUaDmHzFbik'} onLoad={() => console.log('Maps API has loaded.')}>
    <Map
    style={{ width: '100%', height: '500px' }}
        defaultZoom ={13}
        defaultCenter={{lat:52.23306794341422, lng:21.00895019173}}
        onCameraChanged={()=>console.log("changed")}    
    >
       
    </Map>
    </APIProvider>
)


}