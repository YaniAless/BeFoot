package com.alterno.befoot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class MatchActivity extends AppCompatActivity {

    private ListView listMatches;
    private TextView matchDate;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_matches:
                    return true;
                case R.id.navigation_leagues:
                    startActivity(new Intent(MatchActivity.this,LeagueActivity.class));
                    return true;
                case R.id.navigation_teams:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matchDate = (TextView) findViewById(R.id.matchesDate);
        matchDate.setText(GetCurrentDate());

        listMatches = (ListView) findViewById(R.id.listMatches);
        List<Match> matches = GetMatchOfTheDay();
        if(matches.size() > 0){
            MatchAdapter adapter = new MatchAdapter(MatchActivity.this,matches);
            listMatches.setAdapter(adapter);
        }
        else
            Log.i("TESTS", "No matches found");


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private String GetCurrentDate(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/London"));
        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());

        return currentDate;
    }

    public List<Match> GetMatchOfTheDay(){

        RequestQueue queue = Volley.newRequestQueue(this);

        String date = "2019-04-05";

        String url = "http://api.football-data.org/v2/competitions/FL1/matches?dateFrom="+date+"&dateTo="+date;

        Log.i("TESTS", url);

        final List<Match> matchesList = new ArrayList<>();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray arrMatches = response.getJSONArray("matches");
                    String league = response.getString("name");

                    for (int i = 0; i < arrMatches.length();i++){
                        JSONObject match = arrMatches.getJSONObject(i);


                        String homeTeam = match.getJSONObject("homeTeam").getString("name");
                        String awayTeam = match.getJSONObject("awayTeam").getString("name");
                        String homeTeamScore = match.getJSONObject("score").getJSONObject("fullTime").getString("homeTeam");
                        String awayTeamScore = match.getJSONObject("score").getJSONObject("fullTime").getString("awayTeam");

                        String matchStatus = match.getString("status");
                        String matchDay = match.getString("matchday");
                        String matchDate = match.getString("utcDate");

                        Log.i("TESTS","Date : " + matchDate);


                        //League matchLeague = league;
                        //ImageView homeTeamImg = match.getString();
                        //ImageView awayTeamImg = match.getString();

                        Match matchOfTheDay = new Match(homeTeam,awayTeam,homeTeamScore,awayTeamScore,matchStatus,matchDay,matchDate);
                        matchesList.add(matchOfTheDay);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String,String>();
                headers.put("Content-Type","application/json");
                headers.put("X-Auth-Token","98d4c03b623847c2b648fcb3f8c4b059");
                return headers;
            }
        };
        queue.add(req);
        return matchesList;
    }

    // TODO : Remplacer le core de la méthode par les appels API
    private List<Match> genererMatch(){
        List<Match> matches = new ArrayList<>();

        matches.add(new Match("Dijon","PSG","0","4"));
        matches.add(new Match("Juventus","Atletico Madrid","3","0"));
        matches.add(new Match("Manchester City","Schalke 04","7","0"));

        return matches;
    }

}
