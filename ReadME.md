## Introduction
An android Weather app using  weatherSdk
  
## User Example
First opent the app, will direct user to current location if current location not available will pop up dialoge to require the permission. It will use the fusedlocation service to keep your location updated.
User can enter the city name and click the button, then it will pull weather data for that city.<br/>

After user click go to map, it will start the mapActicity from weatherSdk,when a user click on the map, it will show a weather icon to that location,  after click the weather icon it will show more weather deatails for that location(Right now only shows **Location name, weather, and temperature**). 
The user would be able to interact with the map (zoom, pan, etc),  app work in both portrait and landscape mode.<br/>
![alt tag](https://raw.githubusercontent.com/louisli1989/MyWeather/master/screenshot3.png)
![alt tag](https://raw.githubusercontent.com/louisli1989/weatherMap/master/screenshot1.png)
![alt tag](https://raw.githubusercontent.com/louisli1989/weatherMap/master/screenshot2.png)

## API Reference
need support Google Maps Android API v2
target API 23
