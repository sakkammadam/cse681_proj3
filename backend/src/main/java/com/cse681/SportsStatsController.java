package com.cse681;

// JSON dependencies
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

// Spring Components
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Standard JAVA components
import java.lang.Exception;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class SportsStatsController {

    // method to return a JSON Node enriched with necessary information
    private static JsonNode buildStats(JsonNode teamStats1, JsonNode teamStats2, String matchup, String home, String away){
        // modify
        ObjectNode teamStatObj = (ObjectNode) teamStats1;
        // add match up element -
        teamStatObj.put("matchup", matchup);
        // enrich with opponent name
        if (matchup.equals("home team")){
            teamStatObj.put("opponent", away);
        } else {
            teamStatObj.put("opponent", home);
        }
        // add opponent score
        teamStatObj.put("opponentScore", teamStats2.get("score").asInt());
        // add matchResult
        String matchResult;
        if (teamStats1.get("score").asInt() > teamStats2.get("score").asInt()){
            matchResult = "Team Win";
        } else {
            matchResult = "Team Loss";
        }
        // add match result
        teamStatObj.put("matchResult", matchResult);
        // return
        return teamStatObj;
    }

    private final TeamDataService teamLookup;

    public SportsStatsController(TeamDataService teamLookup){
        this.teamLookup = teamLookup;
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getTeamStats(@RequestParam("teamName") String teamName){
        // Validate team name
        if (!teamLookup.isValidTeam(teamName)){
            return ResponseEntity.badRequest().body("Invalid team name: " + teamName);
        }
        // Retrieve corresponding team id
        int teamId = teamLookup.getTeamId(teamName);

        // Construct URL
        String url = "https://sports.snoozle.net/search/nfl/searchHandler?" +
                "fileType=inline&statType=teamStats&season=2020&teamName=" + teamId;

        // Fetch data
        try{
            String response = InvokeHttpClient.sendRequest(url);
            // Leverage JSON and parse results
            JsonNode jsonResponse = ParseJson.parse(response);
            JsonNode matchUpStats = jsonResponse.get("matchUpStats");
            List<JsonNode> allTeamStatsArray = new ArrayList<>();

            // Parse response and save it to allTeamStatsArray
            if(matchUpStats.isArray()){
                for(JsonNode stat: matchUpStats){
                    String homeTeam = String.valueOf(stat.get("homeTeamName").asText());
                    String visitTeam = String.valueOf(stat.get("visTeamName").asText());
                    JsonNode homeStats = stat.get("homeStats");
                    JsonNode visStats = stat.get("visStats");
                    // add stats to allTeamStatsArray
                    if (homeStats != null && homeStats.get("teamCode").asInt() == teamId) {
                        allTeamStatsArray.add(buildStats(homeStats, visStats, "home team", homeTeam, visitTeam));
                    }
                    if (visStats != null && visStats.get("teamCode").asInt() == teamId) {
                        allTeamStatsArray.add(buildStats(visStats, homeStats, "away team", visitTeam, homeTeam));
                    }
                }
            }
            // send parsed response back
            return ResponseEntity.ok(allTeamStatsArray);
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error fetching stats: "+ e.getMessage());
        }

    }


}
