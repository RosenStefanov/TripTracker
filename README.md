# TripTracker
 Android app that allows the user to save a trip between two locations on a given date. The user can use the inbuilt calculator to calculate the cost of the trip and check the current weather at the start and finish locations.
## About The Project
 The idea for this project came while i was thinking about what android app i can build that utilizes some form of an API. I figured that an app that allows me to save trips by using some form of maps API will be what i'm looking to build and at the same time it will be something that i can use in some occasions. After that i decided that it would be useful to have a trip cost calculator integrated into the app and functionality that would show the current weather on the start and finish locations. This is the result of the project.
### Built With
This project uses Google maps API for the maps API and OpenWeatherMap for the weather forecast API.

## Prerequisites and Installation
### Prerequisites
```sh
Generate your own Google maps api key, make shure that the key has Maps SDK for Android and Directions API Enabled 
```
```sh
Generate your own OpenWeatherMap API key
```
### Installation
  
  Clone the repo and open it with an IDE that supports Android.
   ```sh
  git clone https://github.com/RosenStefanov/TripTracker.git
  ```
  Replace {Google Maps Api key} in AndroidManifest and keys files with your own maps API key. Locations of the files:
  ```sh
  /app/src/main/AndroidManifest.xml
  ```
  ```sh
  /app/src/main/res/values/keys.xml
  ```
  Replace {OpenWeatherMap API Key} in the keys.xml file with your own OpenWeatherAPI key. Location of the file:
  ```sh
  /app/src/main/AndroidManifest.xml
  ```
  
  ## Usage
  
