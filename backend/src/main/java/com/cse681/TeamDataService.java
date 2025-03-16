package com.cse681;

import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

@Service
public class TeamDataService {
    private static final Map<String, Integer> teamMap;
    // Load it with NFL teams
    static {
        Map<String, Integer> tempMap = new TreeMap<>();
        tempMap.put("Arizona Cardinals",1);
        tempMap.put("Atlanta Falcons",2);
        tempMap.put("Baltimore Ravens",3);
        tempMap.put("Buffalo Bills",4);
        tempMap.put("Carolina Panthers",5);
        tempMap.put("Chicago Bears",6);
        tempMap.put("Cleveland Browns",7);
        tempMap.put("Dallas Cowboys",8);
        tempMap.put("Denver Broncos",9);
        tempMap.put("Detroit Lions",10);
        tempMap.put("Green Bay Packers",11);
        tempMap.put("New York Giants",12);
        tempMap.put("Indianapolis Colts",13);
        tempMap.put("Jacksonville Jaguars",14);
        tempMap.put("Kansas City Chiefs",15);
        tempMap.put("Miami Dolphins",16);
        tempMap.put("Minnesota Vikings",17);
        tempMap.put("New England Patriots",18);
        tempMap.put("New Orleans Saints",19);
        tempMap.put("New York Jets",20);
        tempMap.put("Las Vegas Raiders",21);
        tempMap.put("Philadelphia Eagles",22);
        tempMap.put("Pittsburgh Steelers",23);
        tempMap.put("Los Angeles Chargers",24);
        tempMap.put("Seattle Seahawks",25);
        tempMap.put("San Francisco 49ers",26);
        tempMap.put("Los Angeles Rams",27);
        tempMap.put("Tampa Bay Buccaneers",28);
        tempMap.put("Tennessee Titans",29);
        tempMap.put("Washington Commanders",30);
        tempMap.put("Cincinnati Bengals",31);
        tempMap.put("Houston Texans",32);

        // Ensure that it can't be modified
        teamMap = Collections.unmodifiableMap(tempMap);
    }

    // Build getters - supporting methods
    public Map<String, Integer> getTeamMap(){
        return teamMap;
    }
    public boolean isValidTeam(String teamName){
        return teamMap.containsKey(teamName);
    }
    public int getTeamId(String teamName){
        return teamMap.getOrDefault(teamName, -1);
    }
}
