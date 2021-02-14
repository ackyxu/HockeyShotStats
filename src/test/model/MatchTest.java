package model;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatchTest {
    // delete or rename this class!
    Team nucks;
    Team sen;
    Team oil;

    GameData gameData1;
    GameData gameData2;


    List<LiveData> liveData;
    StoredMatchData storedMatchData;

    MatchData matchData1;
    MatchData matchData2;

    @BeforeEach
    void setup() {
        nucks = new Team("Cancuks", "VAN");
        sen = new Team("Senator", "OTT");
        oil = new Team("Oilers", "EDM");

        gameData1 = new GameData(nucks, oil);
        gameData2 = new GameData(sen, nucks);

        //Since my Class method will not directly call or modify LiveData, I will leave it null
        //LiveData are parsed in the ConsoleInterface Method, and it contains a lot of fields
        //Since I will not be testing them, I will leave it as a empty list
        liveData = new ArrayList<>();
        storedMatchData = new StoredMatchData();

        matchData1 = new MatchData(gameData1, liveData, 2020020004, "2021-01-14");
        matchData2 = new MatchData(gameData2, liveData, 2020020117, "2021-01-29");


    }

    @Test
    void testAddMatch1() {
        storedMatchData.addMatchData(matchData1);


        assertEquals(storedMatchData.storedSize(), 1);
        assertTrue(storedMatchData.checkContainMatchID(2020020004));

        //Test if the match is in the stored matches
        assertTrue(this.storedMatchData.checkContainMatchID(this.matchData1.getMatchID()));
    }

    @Test
    void testAddMatch2() {
        storedMatchData.addMatchData(matchData2);


        assertEquals(storedMatchData.storedSize(), 1);
        assertTrue(storedMatchData.checkContainMatchID(2020020117));

        //Test if the match is in the stored matches
        assertTrue(this.storedMatchData.checkContainMatchID(this.matchData2.getMatchID()));
    }
}
