package model;

import java.time.LocalTime;
import java.util.List;


//Parse event from the JSON.
public class LiveData {

    //player#:name of the player involved in the event
    //player#Type: type of even they were involved in (verb of the person doing the event, and being acted on)
    private String player0;
    private String player0Type;
    private String player1;
    private String player1Type;
    //Team that initiated the event
    //team also indicates who did/won the event; the team that took the shot, won the faceoff, hit the other player
    private String team;
    //Description of event
    private String detail;
    //The type of even that occured
    private String event;
    //Shorthand code for the event
    private String eventType;
    //Period the event happened in
    private int period;
    //Period type the even happened in (Regular, Overtime)
    private String periodType;
    // Time when the event happened
    private LocalTime periodTime;
    //The next two fields are coordinates.  if coorX == coorY == 999, the LiveData has no
    //coordinates (i.e Period Start, Period End)
    //from https://community.rapidminer.com/discussion/44904/using-the-nhl-api-to-analyze-pro-ice-hockey-data-part-1
    // Range of coorX: -42.5 to 42.5
    // Range of coorY: -100  to 100
    private Integer coorX;
    private Integer coorY;



    //REQUIRE: coorX [-42.5, 42.5] coorY [-100,100]
    public LiveData(String player0, String player0Type, String player1, String player1Type, String team,
                    String detail, String event, String eventType, int period, String periodType, LocalTime periodTime,
                    int coorX, int coorY) {

        this.player0 = player0;
        this.player0Type = player0Type;
        this.player1 = player1;
        this.player1Type  = player1Type;
        this.team = team;
        this.detail  = detail;
        this.event  = event;
        this.eventType  = eventType;
        this.period  = period;
        this.periodType  = periodType;
        this.periodTime  = periodTime;
        this.coorX  = coorX;
        this.coorY  = coorY;

    }

    public String getPlayer0() {
        return player0;
    }

    public String getPlayer0Type() {
        return player0Type;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer1Type() {
        return player1Type;
    }

    public String getTeam() {
        return team;
    }

    public String getDetail() {
        return detail;
    }

    public String getEvent() {
        return event;
    }

    public String getEventType() {
        return eventType;
    }

    public int getPeriod() {
        return period;
    }

    public String getPeriodType() {
        return periodType;
    }

    public LocalTime getPeriodTime() {
        return periodTime;
    }

    public Integer getCoorX() {
        return coorX;
    }

    public Integer getCoorY() {
        return coorY;
    }

    //MODIFIES: None
    //EFFECT: one part of a method overloading; return true if EventType equals s
    public boolean filterEvent(String s) {

        return team.equals("VAN") && eventType.equals(s);
    }

    //MODIFIES: None
    //EFFECT: one part of a method overloading; returns true if EventType is in los
    public boolean filterEvent(List<String> los) {

        return team.equals("VAN") && los.contains(eventType);
    }
}
