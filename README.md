# My Personal Project

## *Play by Play of Hockey Shots, Blocks, and Goals*

### Introduction

This project will take use the event data of a hockey match retrived from NHL's API and visual represent a hockey match,
similar to what you hear in a play by play broadcast of a hockey game over the radio or TV.

One of the ongoing project that I wanted to work on is a visualization of events from a hockey game.  
This personal project will be the phase one of the tools I want to develop to help me visualize hockey plays.  
The main goal of this project is to render all the shots, goals and block shots of a game with their respected teams.  
Those events will be rendered to an image of an ice rink in roughly the location of where the event occured. 

Theoretically, users can use this program to catch back up on a game they missed (not that I recommend it, as NHL posts highlights to their Youtube channel not long after a game ends). 

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
    - Face-off Percentages
    - Penalties Taken, and by whom
    - Total Shots on Goal for both team
    - Descriptive details of all goals scored.
- A descriptive update of the event that occur

All the events above will be rendered on a graphic image event by event, similar to a play by play given by radio announcers.
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
    

NHL records the following events in allPlays:

- BLOCKED_SHOT
- FACEOFF
- GAME_END
- GAME_SCHEDULED
- GIVEAWAY
- GOAL
- HIT
- MISSED_SHOT
- PENALTY
- PERIOD_END
- PERIOD_OFFICIAL
- PERIOD_READY
- PERIOD_START
- SHOT
- STOP
- TAKEAWAY

Unfortunately, descriptive updates of passes made by players will be one of the main things that will differ from an actual play by play done by radio or TV broadcasts.
NHL does not currently record players passes (at least the data is not available to end users).



## ***User Stories***

*For Phase 1 of the Project:*
- As a user, I want to be able to iterate a list of events and print out a descriptive detail of the event
    - The program will print out the next event after a certain time has pass, or with  after a user input, whichever comes first
- As a user, I want to be able to retrieve boxscore statistics after all the program iterates through all the event.
- As a user, I want to be able to retrieve a list of goals and penalties in the game after the program iterates through all the events.  
