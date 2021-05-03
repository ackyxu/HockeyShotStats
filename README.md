# My Personal Project

## *SHOOT THE PUCK!  Visualization of Hockey Shots, and other Stats For the Canucks*

### Introduction

Any long time Canucks fans knows this phrase by heart: "SHOOT THE PUCK ALREADY!".  It is frustrating watching the team just passing around the puck for the "perfect" play instead of shoot the puck when there is a high scoring chance right infront of the player!

Anyways, rant aside, I want to explore if there is a reason why the Canucks did not shoot the puck by visualization all the shot attempts on a per game and game-to-date basis to see if there is any underlying pattern with who the team is shooting.
While I am at it (since the API calls includes infomation about the opposing team also), I will also visualize where the opposing teams are taking their shots, and compare to how the Canucks are doing.
As the project progresses, I am planing on adding other features that explores faceoff rates of a team, and test the theory on the importance of a faceoff win for a team.




### Data and Objects of this Project

Detail instruction can be found here, provided by Drew Haynes:
- https://gitlab.com/dword4/nhlapi/-/blob/master/stats-api.md

NHL's API returns calls in a JSON format.  
The JSON contains multiple nested objects, but this program (as of now) is only interested in data from:

- Object > gameData > teams 

    - To retrieve information on the home and away team (to determine where to place the events on the visualization)
    
- Object > liveData > plays > allPlays

    - allPlays contains all the event objects in a game.
    



