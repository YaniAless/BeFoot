package com.alterno.befoot;

import android.media.Image;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Match {

    private String homeTeam;
    private String awayTeam;
    private String homeTeamScore;
    private String awayTeamScore;
    private Image homeTeamImg;
    private Image awayTeamImg;
    private League matchLeague;

    public Match(String homeTeam, String awayTeam, String homeTeamScore, String awayTeamScore) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    public Match(String homeTeam, String awayTeam, String homeTeamScore, String awayTeamScore, Image homeTeamImg, Image awayTeamImg, League matchLeague) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
        this.homeTeamImg = homeTeamImg;
        this.awayTeamImg = awayTeamImg;
        this.matchLeague = matchLeague;
    }

    public List<Match> GetMatchOfTheDay(){

        String url = "";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray listMatch = response.getJSONArray("matches");

                    for(int i = 0; i < listMatch.length();i++){

                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        })
    }



    // GETTER SETTER

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(String homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public String getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(String awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    public Image getHomeTeamImg() {
        return homeTeamImg;
    }

    public void setHomeTeamImg(Image homeTeamImg) {
        this.homeTeamImg = homeTeamImg;
    }

    public Image getAwayTeamImg() {
        return awayTeamImg;
    }

    public void setAwayTeamImg(Image awayTeamImg) {
        this.awayTeamImg = awayTeamImg;
    }

    public League getMatchLeague() {
        return matchLeague;
    }

    public void setMatchLeague(League matchLeague) {
        this.matchLeague = matchLeague;
    }
}
