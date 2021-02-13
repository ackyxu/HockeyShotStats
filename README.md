# My Personal Project

## *SHOOT THE PUCK!  Visualization of Hockey Shots, and other Stats*

### Introduction

Any long time Canucks fans knows this phrase by heart: "SHOOT THE PUCK ALREADY!".  It is frustrating watching the team just passing around the puck for the "perfect" play instead of shoot the puck when there is a high scoring chance right infront of the player!

Anyways, rant aside, I want to explore if there is a reason why the Canucks did not shoot the puck by visualization all the shot attempts on a per game and game-to-date basis to see if there is any underlying pattern with who the team is shooting.
While I am at it (since the API calls includes infomation about the opposing team also), I will also visualize where the opposing teams are taking their shots, and compare to how the Canucks are doing.
As the project progresses, I am planing on adding other features that explores faceoff rates of a team, and test the theory on the importance of a faceoff win for a team.

Since this project was something I always wanted to start on and never go around to it, I want to use this project (for CPSC210 assignment) as a base for further improvement to explore more hockey analytic by evolving the program even after this term. 


* For the Scope of the CPSC210 Project*
Due to the restriction in the scope of the project, the program at the current state ***will not*** call NHL's API to  retrieve live or past games.  
The program at its current state will call an offline, cached game played on January 13, 2021 with the Edmonton Oilers hosting the Vancouver Canucks.
If you wish to retrieve the game via the API, the game id is 2020020004.

For Phase 1, I have prepare a couple of games in text format to test out the feature of users importing games to the program to parse and create into the classes I defined for the program.
Since NHL's API calls are in JSON, I will implement a JSON parser to parse the cache calls for the match in the later Phases.  


### Data and Objects of this Project

Detail instruction can be found here, provided by Drew Haynes:
- https://gitlab.com/dword4/nhlapi/-/blob/master/stats-api.md

NHL's API returns calls in a JSON format.  
The JSON contains multiple nested objects, but this program (as of now) is only interested in data from:

- Object > gameData > teams 

    - To retrieve information on the home and away team (to determine where to place the events on the visualization)
    
- Object > liveData > plays > allPlays

    - allPlays contains all the event objects in a game.
    



## ***User Stories***

*For Phase 1 of the Project:*
- As a user, I want to be able to add a match to the program and be able to analysis it through the program
- As a user, I want to be able to retrieve all the shot events by both the Canucks and the opposing team from the matches I stored in the program
- As a user, I want to be able to retrieve the shot events from multiple games
- As a user, I want to be able to retrieve information about the team's faceoff stats.
