package com.alterno.befoot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeagueActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ListView rankingView;
    private ListView rankingView2;
    private ImageView logoTeam;
    private Spinner leagueSelector2;
    private Button goalScorersButton;
    private TextView placeTeam;
    private TextView titleLeague;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_matches:
                    startActivity(new Intent(LeagueActivity.this, MatchActivity.class));
                    return true;
                case R.id.navigation_leagues:

                    return true;
                case R.id.navigation_teams:
                    startActivity(new Intent(LeagueActivity.this, TeamActivity.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_leagues);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        leagueSelector2 = findViewById(R.id.leagueSelector2);
        goalScorersButton = findViewById(R.id.buttonGoalScorers);
        placeTeam = findViewById(R.id.placeTeam);
        titleLeague = findViewById(R.id.titleLeagues);

        rankingView = findViewById(R.id.ranking);
        logoTeam = findViewById(R.id.logoTeam);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.leagueSelectorArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        leagueSelector2.setAdapter(adapter);
        leagueSelector2.setOnItemSelectedListener(this);

    }

    private void DisplayRanking(List<League> ranking) {
        LeagueAdapter adapter = new LeagueAdapter(LeagueActivity.this, ranking);
        rankingView.setAdapter(adapter);
        Log.i("TESTS", "Matches list : " + ranking.size());
    }


    private void DisplayGoalScorersRanking(List<Player> goalScorersRanking) {
        PlayerAdapter adapter = new PlayerAdapter(LeagueActivity.this, goalScorersRanking);
        rankingView.setAdapter(adapter);
        Log.i("TESTS", "Matches list : " + goalScorersRanking.size());
    }

    private void GetRanking(final int idLeague) {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = String.format("https://api-football-v1.p.rapidapi.com/v2/leagueTable/%s", idLeague);

        JsonObjectRequest matchesReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<League> ranking = new ArrayList<>();

                    JSONObject jsonObject = response.getJSONObject("api"); // on se positionne dans l'array standings (classement)

                    if (idLeague == 754) {
                        for (int i = 0; i < 18; i++) {
                            JSONArray standings = jsonObject.getJSONArray("standings");
                            JSONArray table = standings.getJSONArray(0);
                            JSONObject team = table.getJSONObject(i);
                            String nameTeam = team.getString("teamName"); // nom de l'équipe
                            String points = team.getString("points"); // nombre de points
                            String points2 = points + " pts"; // A REFAIRE
                            String placeTeam = String.valueOf(i + 1); // place de l'équipe
                            String logoTeam = team.getString("logo"); // url du logo

                            League league = new League(nameTeam, points2, placeTeam, logoTeam);
                            ranking.add(league);

                        }
                    } else {

                        for (int i = 0; i < 20; i++) {
                            JSONArray standings = jsonObject.getJSONArray("standings");
                            JSONArray table = standings.getJSONArray(0);
                            JSONObject team = table.getJSONObject(i);
                            String nameTeam = team.getString("teamName"); // nom de l'équipe
                            String points = team.getString("points"); // nombre de points
                            String points2 = points + " pts"; // A REFAIRE
                            String placeTeam = String.valueOf(i + 1); // place de l'équipe
                            String logoTeam = team.getString("logo"); // url du logo

                            League league = new League(nameTeam, points2, placeTeam, logoTeam);
                            ranking.add(league);
                        }
                    }
                    DisplayRanking(ranking);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String key = BuildConfig.ApiKey;
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("x-rapidapi-key", key);
                return params;
            }
        };
        queue.add(matchesReq);
    }

    private void GetGoalScorersRanking(final int idLeague) {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = String.format("https://api-football-v1.p.rapidapi.com/v2/topscorers/%s", idLeague);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<Player> goalScorersRanking = new ArrayList<>();

                    JSONObject jsonObject = response.getJSONObject("api");

                    for (int i = 0; i < 20; i++) {
                        JSONArray topscorersArray = jsonObject.getJSONArray("topscorers");
                        JSONObject player = topscorersArray.getJSONObject(i);
                        String playerName = player.getString("player_name");
                        String teamName = player.getString("team_name");
                        JSONObject goalsObject = player.getJSONObject("goals");
                        int goals = goalsObject.getInt("total");
                        String goalsFormatted = String.valueOf(goals) + " buts";

                        Player goalScorer = new Player(playerName, teamName, goalsFormatted);
                        goalScorersRanking.add(goalScorer);
                    }
                    DisplayGoalScorersRanking(goalScorersRanking);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }) {
            //Methode permettant de mettre la clé d'api dans le header
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                String apiKey = BuildConfig.ApiKey; // securisation de la clé d'API, ajoutée dans gradle.properties et build.gradle
                headers.put("x-rapidapi-key", apiKey);
                return headers;
            }
        };
        queue.add(request);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String pos = String.valueOf(parent.getItemIdAtPosition(position));
        int idLeague = 0;

        // Here we check what the is ID of the value in the Spinner which indicates what league is selected
        switch (pos) {
            case "0":
                idLeague = 525;
                GetRanking(idLeague);

                final int finalLeagueId = idLeague;
                goalScorersButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetGoalScorersRanking(finalLeagueId);
                    }
                });
                break;

            case "1":
                idLeague = 524;
                GetRanking(idLeague);

                final int finalLeagueId2 = idLeague;
                goalScorersButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetGoalScorersRanking(finalLeagueId2);
                    }
                });
                break;

            case "2":
                idLeague = 775;
                GetRanking(idLeague);

                final int finalLeagueId3 = idLeague;
                goalScorersButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetGoalScorersRanking(finalLeagueId3);
                    }
                });
                break;

            case "3":
                idLeague = 754;
                GetRanking(idLeague);

                final int finalLeagueId4 = idLeague;
                goalScorersButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetGoalScorersRanking(finalLeagueId4);
                    }
                });
                break;

            case "4":
                idLeague = 891;
                GetRanking(idLeague);

                final int finalLeagueId5 = idLeague;
                goalScorersButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetGoalScorersRanking(finalLeagueId5);
                    }
                });
                break;

            case "5":
                idLeague = 530;
                GetGoalScorersRanking(idLeague);
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

}
