package com.alterno.befoot;

import android.media.Image;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Match {

    private String homeTeam;
    private String awayTeam;
    private String homeTeamScore;
    private String awayTeamScore;

    private League matchLeague;
    private String matchStatus;
    private String matchDay;
    private String matchDate;

    private ImageView homeTeamImg;
    private ImageView awayTeamImg;

    public Match(String homeTeam, String awayTeam, String homeTeamScore, String awayTeamScore) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    public Match(String homeTeam, String awayTeam, String homeTeamScore, String awayTeamScore, String matchStatus, String matchDay, String matchDate) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
        this.matchStatus = matchStatus;
        this.matchDay = matchDay;
        this.matchDate = matchDate;
    }

    public Match(String homeTeam, String awayTeam, String homeTeamScore, String awayTeamScore, ImageView homeTeamImg, ImageView awayTeamImg, League matchLeague) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
        this.homeTeamImg = homeTeamImg;
        this.awayTeamImg = awayTeamImg;
        this.matchLeague = matchLeague;
    }

    public Match(String homeTeam, String awayTeam, String homeTeamScore, String awayTeamScore, League matchLeague, String matchStatus, String matchDay, String matchDate, ImageView homeTeamImg, ImageView awayTeamImg) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
        this.matchLeague = matchLeague;
        this.matchStatus = matchStatus;
        this.matchDay = matchDay;
        this.matchDate = matchDate;
        this.homeTeamImg = homeTeamImg;
        this.awayTeamImg = awayTeamImg;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String getMatchDay() {
        return matchDay;
    }

    public void setMatchDay(String matchDay) {
        this.matchDay = matchDay;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
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

    public ImageView getHomeTeamImg() {
        return homeTeamImg;
    }

    public void setHomeTeamImg(ImageView homeTeamImg) {
        this.homeTeamImg = homeTeamImg;
    }

    public ImageView getAwayTeamImg() {
        return awayTeamImg;
    }

    public void setAwayTeamImg(ImageView awayTeamImg) {
        this.awayTeamImg = awayTeamImg;
    }

    public League getMatchLeague() {
        return matchLeague;
    }

    public void setMatchLeague(League matchLeague) {
        this.matchLeague = matchLeague;
    }
}
