package com.alterno.befoot;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class MatchActivity extends AppCompatActivity {

    private ListView listMatches;
    private TextView matchDate;
    private TextView noMatchesMsg;
    private Spinner leagueSelector;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_matches:
                    startActivity(new Intent(MatchActivity.this,MatchActivity.class));
                    return true;
                case R.id.navigation_leagues:
                    startActivity(new Intent(MatchActivity.this,LeagueActivity.class));
                    return true;
                case R.id.navigation_teams:
                    startActivity(new Intent(MatchActivity.this,TeamActivity.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        matchDate = (TextView) findViewById(R.id.matchesDate);
        matchDate.setText(DisplayLocalDate());

        leagueSelector = (Spinner) findViewById(R.id.leagueSelector);

        listMatches = (ListView) findViewById(R.id.listMatches);

        GetMatchOfTheDay();

    }

    private String BuildDateForApi(){
        Date cDate = new Date();
        String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

        return fDate;
    }

    private String DisplayLocalDate(){
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());

        return currentDate;
    }

    private void DisplayMatchesOfTheDay(List<Match> matchesList){
        noMatchesMsg = (TextView) findViewById(R.id.noMatchesMsg);
        if(matchesList.size() > 0){
            if(noMatchesMsg.getVisibility() == View.VISIBLE)
                noMatchesMsg.setVisibility(View.INVISIBLE);
            MatchAdapter adapter = new MatchAdapter(MatchActivity.this,matchesList);
            listMatches.setAdapter(adapter);
            Log.i("TESTS", "Matches list : " + matchesList.size());
        }
        else{
            noMatchesMsg.setVisibility(View.VISIBLE);
            noMatchesMsg.setText(R.string.noMatchesFound);
        }
    }

    private void GetMatchOfTheDay(){

        RequestQueue queue = Volley.newRequestQueue(this);

        String selectedLeague = getResources().getStringArray(R.array.leagueSelectorArray)[leagueSelector.getSelectedItemPosition()];
        Log.i("LEAGUE",selectedLeague);

        String myDate = BuildDateForApi();

        String baseurl = "http://api.football-data.org/v2/competitions/";

        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(baseurl);
        sbUrl.append(selectedLeague);
        sbUrl.append("/matches?");
        sbUrl.append("dateFrom=" + myDate);
        sbUrl.append("&dateTo=" + myDate);

        JsonObjectRequest matchesReq = new JsonObjectRequest(Request.Method.GET, sbUrl.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    List<Match> matchesList = new ArrayList<>();

                    JSONArray arrMatches = response.getJSONArray("matches");
                    //String league = response.getString("name");
                    //Log.i("TESTS","Response : " + response.toString());

                    for (int i = 0; i < arrMatches.length();i++){
                        JSONObject match = arrMatches.getJSONObject(i);

                        String homeTeam = match.getJSONObject("homeTeam").getString("name");
                        String awayTeam = match.getJSONObject("awayTeam").getString("name");
                        String homeTeamScore = match.getJSONObject("score").getJSONObject("fullTime").getString("homeTeam");
                        String awayTeamScore = match.getJSONObject("score").getJSONObject("fullTime").getString("awayTeam");

                        if(homeTeamScore == "null" || awayTeamScore == "null"){
                            homeTeamScore = "0";
                            awayTeamScore = "0";
                        }

                        String matchStatus = match.getString("status");
                        String matchDay = match.getString("matchday");
                        String matchDate = match.getString("utcDate");

                        //League matchLeague = league;
                        //ImageView homeTeamImg = match.getString();
                        //ImageView awayTeamImg = match.getString();

                        Match matchOfTheDay = new Match(homeTeam,awayTeam,homeTeamScore,awayTeamScore,matchStatus,matchDay,matchDate);
                        matchesList.add(matchOfTheDay);
                    }
                    DisplayMatchesOfTheDay(matchesList);
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
            String key = BuildConfig.ApiKey;
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("X-Auth-Token",key);
                return params;
            }
        };
        queue.add(matchesReq);
    }

}
