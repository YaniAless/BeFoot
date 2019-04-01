package com.alterno.befoot;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeagueActivity extends AppCompatActivity {
    private ListView rankingView;
    private ImageView logoTeam;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_leagues:
                    return true;
                case R.id.navigation_matches:
                    startActivity(new Intent(LeagueActivity.this, MatchActivity.class));
                    return true;
                case R.id.navigation_teams:
                    //mTextMessage.setText(R.string.title_teams);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        GetRanking("FL1"); // à faire : choisir son championnat
        setContentView(R.layout.activity_league);

        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        rankingView = findViewById(R.id.ranking);
        logoTeam= findViewById(R.id.logoTeam);
    }

    private void DisplayRanking(List<League> ranking)
    {
            LeagueAdapter adapter = new LeagueAdapter(LeagueActivity.this,ranking);
            rankingView.setAdapter(adapter);
            Log.i("TESTS", "Matches list : " + ranking.size());
    }

    private void GetRanking(String idLeague){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.football-data.org/v2/competitions/"+ idLeague +"/standings";


        JsonObjectRequest matchesReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    List<League> ranking = new ArrayList<>();

                    //JSONObject jsonObject = response.getJSONObject("competition"); // on se positionne dans l'objet competition
                    //String nameCompetition = jsonObject.getString("name"); // on récupère le nom de la competition
                    //team.setText(nameCompetition);

                    JSONArray jsonArray = response.getJSONArray("standings"); // on se positionne dans l'array standings (classement)


                    for (int i = 0; i < 20; i++) {
                        JSONObject standings = jsonArray.getJSONObject(0); //on se positionne au 1er objet (classement général)
                        JSONArray table = standings.getJSONArray("table");
                        JSONObject team = table.getJSONObject(i);
                        JSONObject team1 = team.getJSONObject("team");
                        String nameTeam = team1.getString("name");
                        String points = team.getString("points");
                        String points2 = points + " pts"; // A REFAIRE

                        String logoTeam = team1.getString("crestUrl");



                        League league = new League(nameTeam, points2);
                        ranking.add(league);

                    }
                    DisplayRanking(ranking);


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
