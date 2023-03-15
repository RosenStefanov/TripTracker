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
  
  ### On Start
  After starting the app the main screen will open in which you will have the option to create a new trip or open all of the saved trips.
  ![start_screen - Copy](https://user-images.githubusercontent.com/95367525/225417449-cac35d9a-c003-4577-9779-73c4a32822a9.PNG)
  ### Creating A Trip
  After clicking "CREATE TRIP" the create trip activity will open in which the user will be presented with a map using which they will be able to select their starting and finish locations by clicking on the map or using the text input field. There is a choose date field in which the user has to select the date of the trip. After the user has selected the starting and finish locations and the date of the trip by pressing the "CREATE TRIP" button, a new window opens with the information for the trip and a "SAVE TRIP" button with which the user can save the trip to the database.
  ![create_trip](https://user-images.githubusercontent.com/95367525/225423070-8410a001-3cb2-4c41-81ee-c62eb7cf47b3.PNG) &emsp; &emsp; &emsp;    &emsp; ![new_trip_data](https://user-images.githubusercontent.com/95367525/225423203-ae4cf8a1-19a5-4a2e-a67b-a61a602834d5.PNG)
### Saved Trips
After clicking on the "SAVED TRIPS" button on the starting screen the user will be presented with a list of all of their saved strips. The user can click on a trip to open it and view the information for that trip and will have the options to calculate the cost of the trip, view the current weather on the start and finish locations and delete the trip from the database.

![trips_list](https://user-images.githubusercontent.com/95367525/225426485-f83961bb-9e87-4e9d-80c0-f6f4cf46bb35.PNG) &emsp; &emsp; &emsp;    &emsp; ![trip_info](https://user-images.githubusercontent.com/95367525/225426791-877aac53-abfd-402d-ada4-0c32978a9aa3.PNG)




