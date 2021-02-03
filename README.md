# My Personal Project

## *Visualization of Shots and Goals in a NHL Game*

### Introduction

This project will take use the event data of a hockey match retrieved from NHL's API and visual represent of the scoring event that occured in the match. 
The program will also run additional analysis (time to goal after giveaway, faceoffs, etc..).

One of the ongoing project that I wanted to work on is a visualization of events from a hockey game.  
This personal project will be the phase of a bigger project that I am interested in continuing after CPSC 210.  

This program will hopefully give users insight on the defensive style that a team is playing (via visalization where teams are allows shots to go through and where they focus on blocking shots).
With additional stats included in the API call, I want to be able to add more analysis in the project further on.  

Due to the restriction in the scope of the project, the program at the current state ***will not*** call NHL's API to  retrieve live or past games.  
The program at its current state will call an offline, cached game played on January 13, 2021 with the Edmonton Oilers hosting the Vancouver Canucks.
If you wish to retrieve the game via the API, the game id is 2020020004.

I choose this game as the test game for this project because:

- This game was an unusually high-scoring and high pase game, which will give me plenty of events to work with.
- I am a die hard Canucks fan, and the Canucks won the game (no bias)

Other cached games will be included in the project closer to the project's completion.

The end goal of the project will be a visualization that:

- Updates and displays all the shots, blockshots, and goals along with their locations, render on a image of an ice rink
- "Boxscores" with
    -Faceoffs Won (good faceoff percentage is an offensive/defensive indicator)
    -Shots on Goal
    -Goals so far
    -Block Shots so far
- A descriptive update of the event that occur

Since a normal game will take at least 60 minutes to complete, the events will be updated in an accelerated rate (to be determined).



### Data and Objects of this Project

Detail instruction can be found here, provided by Drew Haynes:
- https://gitlab.com/dword4/nhlapi/-/blob/master/stats-api.md

NHL's API returns calls in a JSON format.  
The JSON contains multiple nested objects, but this program (as of now) is only interested in data from:

- Object > gameData > teams 

    - To retrieve information on the home and away team (to determine where to place the events on the visualization)
    
- Object > liveData > plays > allPlays

    - allPlays contains all the event objects in a game.
    

NHL records events with a key/id to indicate what kinda of event that occured.  For this project, I will be only interested in events with the following key:

- BLOCKED_SHOT 
- FACEOFF 
- GAME_END 
- GIVEAWAY
- GOAL
- PERIOD_END
- PERIOD_OFFICIAL
- PERIOD_READY
- PERIOD_START



## ***User Stories***

*For Phase 1 of the Project:*
- As a user, I want to be able to build a Match by adding in just shots, shot blocked, and goals events and the metadata that makes up the game.
    - Further on in the project, I want to be able to build a match with the above events from the JSON file retrieved from NHL's API.  
- As a user, I want to be able to take a Match and print out in detail description of the events of interest
- As a user, I want to be able to run a Match and return, as the match progresses, stats such as shot on goals and blocked shots.

