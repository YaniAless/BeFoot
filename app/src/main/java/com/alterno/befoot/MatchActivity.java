package com.alterno.befoot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.Map;

public class MatchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ListView listMatches;
    private TextView matchDate;
    private TextView noMatchesMsg;
    private Spinner leagueSelector;
    private ImageButton btn_prev;
    private ImageButton btn_next;
    private Date today = new Date();

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

        listMatches = findViewById(R.id.listMatches);
        leagueSelector = (Spinner) findViewById(R.id.leagueSelector);
        matchDate = (TextView) findViewById(R.id.matchesDate);
        matchDate.setText(GetLocalDate());

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.leagueSelectorArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        leagueSelector.setAdapter(adapter);
        leagueSelector.setOnItemSelectedListener(this);

        btn_prev = (ImageButton) findViewById(R.id.btn_prev);
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CLICK","Prev date clicked !");
                BuildNewDate('p');
            }
        });

        btn_next = (ImageButton) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CLICK","Next date clicked !");
                BuildNewDate('n');
            }
        });
    }

    private void BuildNewDate(Character action){
        //Date dt = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        String fDate = null;
        String apiDate = null;
        String leagueId = null;

        switch (action){
            case 'p':
                // We subtract one day to the current displayed date
                calendar.add(Calendar.DATE, -1);
                today = calendar.getTime();
                fDate = new SimpleDateFormat("dd MMM YYYY").format(today);
                matchDate.setText(fDate);

                apiDate = new SimpleDateFormat("yyyy-MM-dd").format(today);
                leagueId = GetLeagueIdWithPos(String.valueOf(leagueSelector.getSelectedItemId()));
                GetMatchOfTheDayByLeague(leagueId,apiDate);

                //Log.i("DATE", fDate);
                //Log.i("DATE", leagueId);

                break;
            case 'n':
                // We add one day to the current displayed date
                calendar.add(Calendar.DATE, 1);
                today = calendar.getTime();
                fDate = new SimpleDateFormat("dd MMM YYYY").format(today);
                matchDate.setText(fDate);

                apiDate = new SimpleDateFormat("yyyy-MM-dd").format(today);
                leagueId = GetLeagueIdWithPos(String.valueOf(leagueSelector.getSelectedItemId()));
                GetMatchOfTheDayByLeague(leagueId,apiDate);

                //Log.i("DATE", fDate);
                //Log.i("DATE", leagueId);

                break;
        }
    }


    private void DisplayMatchesOfTheDay(List<Match> matchesList){
        noMatchesMsg = (TextView) findViewById(R.id.noMatchesMsg);
        if(matchesList.size() > 0){
            if(noMatchesMsg.getVisibility() == View.VISIBLE)
                noMatchesMsg.setVisibility(View.INVISIBLE);

            MatchAdapter adapter = new MatchAdapter(MatchActivity.this,matchesList);
            listMatches.setAdapter(adapter);
        }
        else{
            matchesList.clear();
            listMatches.setAdapter(null);
            noMatchesMsg.setVisibility(View.VISIBLE);
            noMatchesMsg.setText(R.string.nomatch_msg);
        }
    }

    private void GetMatchOfTheDayByLeague(String leagueId, String myDate){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = String.format("https://api.football-data.org/v2/competitions/%s/matches?dateFrom=%s&dateTo=%s",leagueId,myDate,myDate);

        JsonObjectRequest matchesReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    List<Match> matchesList = new ArrayList<>();

                    JSONArray arrMatches = response.getJSONArray("matches");

                    for (int i = 0; i < arrMatches.length();i++){
                        JSONObject match = arrMatches.getJSONObject(i);

                        String homeTeam = match.getJSONObject("homeTeam").getString("name");
                        String awayTeam = match.getJSONObject("awayTeam").getString("name");
                        String homeTeamScore = match.getJSONObject("score").getJSONObject("fullTime").getString("homeTeam");
                        String awayTeamScore = match.getJSONObject("score").getJSONObject("fullTime").getString("awayTeam");

                        if(homeTeamScore == "null" || awayTeamScore == "null"){
                            homeTeamScore = "-";
                            awayTeamScore = "-";
                        }

                        String matchStatus = match.getString("status");
                        String matchDay = match.getString("matchday");
                        String matchDate = match.getString("utcDate");

                        Match matchOfTheDay = new Match(homeTeam,awayTeam,homeTeamScore,awayTeamScore,matchStatus,matchDay,matchDate);
                        matchesList.add(matchOfTheDay);
                    }
                    DisplayMatchesOfTheDay(matchesList);
                }
                catch (JSONException e){
                    Log.i("API ERROR",e.getMessage());
                    e.printStackTrace();
                    e.getCause();
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

    private String BuildDateForApi(){
        Date cDate = new Date();
        String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

        return fDate;
    }

    private String GetLocalDate(){
        //Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(today);

        return currentDate;
    }

    private String GetLeagueIdWithPos(String pos){
        switch (pos){
            case "0":
                return "FL1";
            case "1":
                return "PL";
            case "2":
                return "PD";
            case "3":
                return "BL1";
            case "4":
                return "SA";
            case "5":
                return "CL";
        }
        return "Can't get league ID";
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String pos = String.valueOf(parent.getItemIdAtPosition(position));
        String leagueName = null;

        // Here we check what the is ID of the value in the Spinner which indicates what league is selected
        switch (pos){
            case "0":
                leagueName = "FL1";
                GetMatchOfTheDayByLeague(leagueName, BuildDateForApi());

                break;
            case "1":
                leagueName = "PL";
                GetMatchOfTheDayByLeague(leagueName, BuildDateForApi());
                break;
            case "2":
                leagueName = "PD";
                GetMatchOfTheDayByLeague(leagueName, BuildDateForApi());
                break;
            case "3":
                leagueName = "BL1";

                GetMatchOfTheDayByLeague(leagueName, BuildDateForApi());
                break;
            case "4":
                leagueName = "SA";
                GetMatchOfTheDayByLeague(leagueName, BuildDateForApi());
                break;

            case "5":
                leagueName= "CL";
                GetMatchOfTheDayByLeague(leagueName, BuildDateForApi());
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
