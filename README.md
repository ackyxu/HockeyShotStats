# My Personal Project

## *Play by Play of Hockey Shots, Blocks, and Goals*

### Introduction

This project will take use the event data of a hockey match retrived from NHL's API and visual represent a hockey match,
similar to what you hear in a play by play broadcast of a hockey game over the radio or TV.

Due to the restriction in the scope of the project, the program at the current state ***will not*** call NHL's API to  retrive live or past games.  Instead the program at it's current state will call an offline, pre-retrived game, played on January 13, 2021 with the Edminton Oilers hosting the Vacnouver Canucks.
If you wish to retive the game via the API, the game id is 2020020004.



I chose this game as the test game for this project because:

- This game was an unusally high-scoring and high pase game, which will give me plenty of events to work with.
- I am a die hard Cancuks fan, and the Canucks won the game (no bias)

Other cached games will be included in the project closer to the project's completion.

The end goal of the project will be a visualization that:

- Updates and disaplay all the shots, blockshos, and goals along with their locations, render on a image of an ice rink
- "Boxscores" with
    - Faceoff Percentages
    - Penalties Taken, and by whom
    - Total Shots on Goal for both team
    - Descriptive details of all goals scored.
- Descriptive update of the event that occur

All of the above will be rendered on a graphic image event by event, similar to a play by play given by radio annoucers.
Since a normal game will take at least 60 minutes to complete, the events will be updated in an acclerated rate (to be determined).



### Data and Objects of this Project

Detail instruction can be found here, provided by Drew Haynes:
- https://gitlab.com/dword4/nhlapi/-/blob/master/stats-api.md

NHL's API returns calls in a JSON format.  The JSON failes contains multiple nested objects, but this program (as of now) is only intersted in data from:

- Object > gameData > teams 

    - To retrive information on the home and away team (to determine where to place the events on the visualization)
    
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

Player passes are not recorded by NHL.  Unfortunately, descriptive updates of passes made by players will be one of the main things that will differ from an actual play by play done by radio or TV broadcasts.





