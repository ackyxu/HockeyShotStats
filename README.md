# My Personal Project

## *SHOOT THE PUCK!  Visualization of Hockey Shots, and other Stats For the Canucks*

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
- As a user, I want to be able to add a match to the program and parse it into MatchData. Then the parsed match will be stored in StoredMatchData.
    - NOTE: When a user imports a MatchData, the program will construct GameData, LiveData, and Team representative of the MatchData.
- As a user, I want to be able to drop a match if I realize that the match is not representative of the team as a whole, etc.
- As a user, I want to be able to retrieve all the shot events by the Canucks  from the matches I stored in the program, with the option to select just goals or all Shot Events with the coordinate of where the event originated
- As a user, I want to be able to retrieve a boxscore summary of the games in StoredMatchData

*For Phase 2 of the Project:*
- As a user, I want to be able to retrieve all the matches I previous imported in a past session of the program.
- As a user, I want to be able to choose which session I want to import (i.e have a session containing matches from a single season).
- As a user, I want to be able to save all the matches I retrieve in the current session, so I won't need to call to retrieve the matches again in the future sessions.
- As a user, I want to be able to have an option to save my current session of matches or not, and option to load a different session (for different a hockey season)
- As a user, I wamt to be able to parse the JSON API call from NHL Stats into the Objects in my program

*For Phase 3 of the Project:*
- As a user, I want to be able to perform the same action as Phase 2, but with a GUI
- As a user, I want to be able to create a shot related plot overalyed on a ice rink from the MactchData that I imported.

*Phase 4: Task 2*
I created a CanucksNotInImport Exception to prevent a user from importing a game that the Canucks did not played in, as my program at this stage specifically only look at Canucks games.
This is first thrown in JsonImport.parseGameData, which then is rethrown in JsonImport.parseMatchData, which then is rethrown again in JsonImport.read.
From there, the exception is caught in the processImportMatch method in both Interface and override in GraphicalInterface, to prevent a user from importing a game that is not related to the Canucks.


*Phase 4: Task 3:*
- I wish to improve the cohesion with my GraphicInterface class by creating specific classes for the JPanels that I used and for JButton:
- Since all my JPanel on the right side follows a specific design recipe(Gridlayout 4x1 with maximum of 4 JButtons), I think I can abstract out both the JPanel and JButton from GraphicInterface, and then abstract out the creation of the JPanel in a sperate class.
- I should have realized that I could create an Abstract class during phase 1 so I can abstract out more methods during phase 3 for my interfaces.  Since I designed most of the methods for my ConsoleInterface wtih System.out.println, it made it hard to use some of the method in my GraphicInterface.
- While I tried to refactor most of the common methods between phase 1 and phase 3 to the Abstract Class Interface (poor name choice, now when I think about it), there are still many methods that I can redesign so that I can reuse them in both Console and Graphic Interface if I had more time.

