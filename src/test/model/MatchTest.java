package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    Team nucks;
    Team sen;
    Team oil;

    GameData gameData1;
    GameData gameData2;


    List<LiveData> liveDataList1;
    List<LiveData> liveDataList2;
    LiveData liveData1;
    LiveData liveData2;
    LiveData liveData3;
    StoredMatchData storedMatchData;

    MatchData matchData1;
    MatchData matchData2;

    List<String> shotEvents = new ArrayList<>();

    @BeforeEach
    void setup() {
        nucks = new Team("Canucks", "VAN");
        sen = new Team("Senator", "OTT");
        oil = new Team("Oilers", "EDM");

        gameData1 = new GameData(nucks, oil);
        gameData2 = new GameData(sen, nucks);


        liveData1 = new LiveData("Elias Pettersson", "Hitter", "Caleb Jones",
                "Hittee", "VAN", "Elias Pettersson hit Caleb Jones", "Hit",
                "HIT", 1, "REGULAR", LocalTime.parse("03:30"), 6, 36);

        liveData2 = new LiveData("Bo Horvat", "Scorer", "Tanner Pearson",
                "Assist", "VAN", "Bo Horvat (1) Wrist Shot assists: Tanner Pearson (1)",
                "Goal", "GOAL", 3,
                "REGULAR", LocalTime.parse("18:47"), 68, 143);

        liveData3 = new LiveData("Colin White", "Shooter", "",
                "", "OTT", "Colin White Wide of Net",
                "Missed Shot", "MISSED_SHOT", 2,
                "REGULAR", LocalTime.parse("01:20"), -44, 26);

        liveDataList1 = new ArrayList<>();
        liveDataList1.add(liveData1);
        liveDataList1.add(liveData2);


        liveDataList2 = new ArrayList<>();
        liveDataList2.add(liveData3);

        storedMatchData = new StoredMatchData();

        matchData1 = new MatchData(gameData1, liveDataList1, 2020020004, "2021-01-14");
        matchData2 = new MatchData(gameData2, liveDataList2, 2020020117, "2021-01-29");


        shotEvents.add("BLOCKED_SHOT");
        shotEvents.add("SHOT");
        shotEvents.add("MISSED_SHOT");
        shotEvents.add("GOAL");


    }

    @Test
    void retrieveGame1Info() {
        assertEquals(matchData1.getGameData().getHome().getTeamName(), "Canucks");
        assertEquals(matchData1.getGameData().getHome().getTeamAbr(), "VAN");
        assertEquals(matchData1.getGameData().getAway().getTeamName(), "Oilers");
        assertEquals(matchData1.getGameData().getAway().getTeamAbr(), "EDM");
    }

    @Test
    void retrieveGame2Info() {
        assertEquals(matchData2.getGameData().getHome().getTeamName(), "Senator");
        assertEquals(matchData2.getGameData().getHome().getTeamAbr(), "OTT");
        assertEquals(matchData2.getGameData().getAway().getTeamName(), "Canucks");
        assertEquals(matchData2.getGameData().getAway().getTeamAbr(), "VAN");
    }

    @Test
    void testLiveData1Gets() {

        assertEquals(liveData1.getPlayer0(), "Elias Pettersson");
        assertEquals(liveData1.getPlayer0Type(), "Hitter");
        assertEquals(liveData1.getPlayer1(), "Caleb Jones");
        assertEquals(liveData1.getPlayer1Type(), "Hittee");
        assertEquals(liveData1.getTeam(), "VAN");
        assertEquals(liveData1.getDetail(), "Elias Pettersson hit Caleb Jones");
        assertEquals(liveData1.getEvent(), "Hit");
        assertEquals(liveData1.getEventType(), "HIT");
        assertEquals(liveData1.getPeriod(), 1);
        assertEquals(liveData1.getPeriodType(), "REGULAR");
        assertEquals(liveData1.getPeriodTime(), LocalTime.parse("03:30"));
        assertEquals(liveData1.getCoorX(), 6);
        assertEquals(liveData1.getCoorY(), 36);


    }

    @Test
    void testLiveData3Gets() {


        assertEquals(liveData3.getPlayer0(), "Colin White");
        assertEquals(liveData3.getPlayer0Type(), "Shooter");
        assertEquals(liveData3.getPlayer1(), "");
        assertEquals(liveData3.getPlayer1Type(), "");
        assertEquals(liveData3.getTeam(), "OTT");
        assertEquals(liveData3.getDetail(), "Colin White Wide of Net");
        assertEquals(liveData3.getEvent(), "Missed Shot");
        assertEquals(liveData3.getEventType(), "MISSED_SHOT");
        assertEquals(liveData3.getPeriod(), 2);
        assertEquals(liveData3.getPeriodType(), "REGULAR");
        assertEquals(liveData3.getPeriodTime(), LocalTime.parse("01:20"));
        assertEquals(liveData3.getCoorX(), -44);
        assertEquals(liveData3.getCoorY(), 26);


    }

    @Test
    void testFilterSingleEvent() {
        assertTrue(liveData2.filterEvent("VAN", "GOAL"));
        assertFalse(liveData2.filterEvent("VAN", "HIT"));
        assertFalse(liveData2.filterEvent("OTT", "GOAL"));

        assertFalse(liveData1.filterEvent("VAN", "GOAL"));
        assertTrue(liveData1.filterEvent("VAN", "HIT"));
        assertFalse(liveData1.filterEvent("EDM", "HIT"));

        assertFalse(liveData3.filterEvent("VAN", "GOAL"));
        assertFalse(liveData3.filterEvent("VAN", "MISSED_SHOT"));
        assertTrue(liveData3.filterEvent("OTT", "MISSED_SHOT"));

    }


    @Test
    void testFilterMultiEvent() {


        assertFalse(liveData1.filterEvent("VAN", shotEvents));
        assertFalse(liveData1.filterEvent("EDM", shotEvents));

        assertTrue(liveData2.filterEvent("VAN", shotEvents));
        assertFalse(liveData2.filterEvent("OTT", shotEvents));

        assertFalse(liveData3.filterEvent("VAN", shotEvents));
        assertTrue(liveData3.filterEvent("OTT", shotEvents));


    }

    @Test
    void testMatchData1() {

        assertEquals(matchData1.getMatchDate(), "2021-01-14");
        List<LiveData> liveDataList = matchData1.getAllLiveData();

        assertTrue(matchData1.compareMatchID(2020020004));
        assertFalse(matchData1.compareMatchID(2020020117));

        assertTrue(liveDataList.contains(liveData1));
        assertTrue(liveDataList.contains(liveData2));
        assertFalse(liveDataList.contains(liveData3));
    }

    @Test
    void testMatchData2() {

        assertEquals(matchData2.getMatchDate(), "2021-01-29");
        List<LiveData> liveDataList = matchData2.getAllLiveData();

        assertFalse(matchData2.compareMatchID(2020020004));
        assertTrue(matchData2.compareMatchID(2020020117));

        assertFalse(liveDataList.contains(liveData1));
        assertFalse(liveDataList.contains(liveData2));
        assertTrue(liveDataList.contains(liveData3));
    }

    @Test
    void testGetFilteredSingleEvent() {

        List<LiveData> filter1 = matchData1.getFilteredEvent("VAN", "GOAL");
        List<LiveData> filter2 = matchData1.getFilteredEvent("EDM", "GOAL");
        List<LiveData> filter3 = matchData1.getFilteredEvent("VAN", "HIT");
        List<LiveData> filter4 = matchData1.getFilteredEvent("EDM", "HIT");

        List<LiveData> filter5 = matchData2.getFilteredEvent("OTT", "HIT");
        List<LiveData> filter6 = matchData2.getFilteredEvent("OTT", "MISSED_SHOT");
        List<LiveData> filter7 = matchData2.getFilteredEvent("VAN", "MISSED_SHOT");


        assertTrue(filter1.contains(liveData2));
        assertFalse(filter1.contains(liveData1));
        assertEquals(filter1.size(), 1);

        assertFalse(filter2.contains(liveData2));
        assertFalse(filter2.contains(liveData1));
        assertEquals(filter2.size(), 0);

        assertFalse(filter3.contains(liveData2));
        assertTrue(filter3.contains(liveData1));
        assertEquals(filter3.size(), 1);

        assertFalse(filter4.contains(liveData2));
        assertFalse(filter4.contains(liveData1));
        assertEquals(filter4.size(), 0);

        assertFalse(filter5.contains(liveData3));
        assertEquals(filter5.size(), 0);

        assertTrue(filter6.contains(liveData3));
        assertEquals(filter6.size(), 1);

        assertFalse(filter7.contains(liveData3));
        assertEquals(filter7.size(), 0);
    }

    @Test
    void testGetFilteredListEvent() {

        List<LiveData> filter1 = matchData1.getFilteredEvent("VAN", shotEvents);
        List<LiveData> filter2 = matchData2.getFilteredEvent("VAN", shotEvents);
        List<LiveData> filter3 = matchData2.getFilteredEvent("OTT", shotEvents);

        assertTrue(filter1.contains(liveData2));
        assertEquals(filter1.size(), 1);

        assertFalse(filter2.contains(liveData3));
        assertEquals(filter2.size(), 0);

        assertTrue(filter3.contains(liveData3));
        assertEquals(filter3.size(), 1);


    }


    @Test
    void testAddMatch1() {
        storedMatchData.addMatchData(matchData1);


        assertEquals(storedMatchData.storedSize(), 1);
        assertTrue(storedMatchData.getStoredMatches().contains(matchData1));
        assertEquals(storedMatchData.getMatchIDs().size(), 1);
        assertTrue(storedMatchData.checkContainMatchID(2020020004));

        //Test if the match is in the stored matches
        assertTrue(this.storedMatchData.checkContainMatchID(this.matchData1.getMatchID()));


    }

    @Test
    void testAddMatch2() {
        storedMatchData.addMatchData(matchData2);


        assertEquals(storedMatchData.storedSize(), 1);
        assertTrue(storedMatchData.getStoredMatches().contains(matchData2));
        assertEquals(storedMatchData.getMatchIDs().size(), 1);
        assertTrue(storedMatchData.checkContainMatchID(2020020117));

        //Test if the match is in the stored matches
        assertTrue(this.storedMatchData.checkContainMatchID(this.matchData2.getMatchID()));
    }

    @Test
    void testAddMultiMatches() {
        storedMatchData.addMatchData(matchData1);
        storedMatchData.addMatchData(matchData2);

        assertEquals(storedMatchData.storedSize(), 2);
        assertTrue(storedMatchData.getStoredMatches().contains(matchData1));
        assertTrue(storedMatchData.getStoredMatches().contains(matchData2));
        assertEquals(storedMatchData.getMatchIDs().size(), 2);
        assertTrue(storedMatchData.checkContainMatchID(2020020004));
        assertTrue(storedMatchData.checkContainMatchID(2020020117));

        //Test if the match is in the stored matches
        assertTrue(this.storedMatchData.checkContainMatchID(this.matchData1.getMatchID()));
        assertTrue(this.storedMatchData.checkContainMatchID(this.matchData2.getMatchID()));

    }

    @Test
    void testDropFirstMatch() {

        storedMatchData.addMatchData(matchData1);
        storedMatchData.addMatchData(matchData2);

        storedMatchData.dropMatchData(matchData1);

        assertEquals(storedMatchData.storedSize(), 1);
        assertTrue(storedMatchData.getStoredMatches().contains(matchData2));
        assertEquals(storedMatchData.getMatchIDs().size(), 1);
        assertTrue(storedMatchData.checkContainMatchID(2020020117));

        //Test if the match is in the stored matches
        assertTrue(this.storedMatchData.checkContainMatchID(this.matchData2.getMatchID()));


    }

    @Test
    void testDropSecondMatch() {

        storedMatchData.addMatchData(matchData1);
        storedMatchData.addMatchData(matchData2);

        storedMatchData.dropMatchData(matchData2);

        assertEquals(storedMatchData.storedSize(), 1);
        assertTrue(storedMatchData.getStoredMatches().contains(matchData1));
        assertEquals(storedMatchData.getMatchIDs().size(), 1);
        assertTrue(storedMatchData.checkContainMatchID(2020020004));

        //Test if the match is in the stored matches
        assertTrue(this.storedMatchData.checkContainMatchID(this.matchData1.getMatchID()));


    }
}
