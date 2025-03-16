package com.cse681;

import com.fasterxml.jackson.databind.JsonNode;
// Spring Components
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.lang.Exception;
import java.lang.String;


@RestController
@RequestMapping("/api")
public class SportsStatsController {
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
            JsonNode jsonResponse = ParseJson.parse(response);
            return ResponseEntity.ok(jsonResponse);
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error fetching stats: "+ e.getMessage());
        }

    }


}
