package com.alterno.befoot;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
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

public class LeagueActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
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
                    startActivity(new Intent(LeagueActivity.this,TeamActivity.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //GetRanking("FL1"); // à faire : choisir son championnat
        setContentView(R.layout.activity_league);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_leagues);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        leagueSelector2 = findViewById(R.id.leagueSelector2);
        goalScorersButton = findViewById(R.id.buttonGoalScorers);
        placeTeam = findViewById(R.id.placeTeam);
        titleLeague = findViewById(R.id.titleLeagues);

        rankingView = findViewById(R.id.ranking);
        logoTeam= findViewById(R.id.logoTeam);

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


        private void DisplayGoalScorersRanking(List<Player> goalScorersRanking)
    {
        PlayerAdapter adapter = new PlayerAdapter(LeagueActivity.this,goalScorersRanking);
        rankingView.setAdapter(adapter);
        Log.i("TESTS", "Matches list : " + goalScorersRanking.size());
    }



    private void GetRanking(final String idLeague){
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = String.format("https://api.football-data.org/v2/competitions/%s/standings",idLeague);

        JsonObjectRequest matchesReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    List<League> ranking = new ArrayList<>();

                    //JSONObject jsonObject = response.getJSONObject("competition"); // on se positionne dans l'objet competition
                    //String nameCompetition = jsonObject.getString("name"); // on récupère le nom de la competition
                    //team.setText(nameCompetition);

                    JSONArray jsonArray = response.getJSONArray("standings"); // on se positionne dans l'array standings (classement)

                    if(idLeague == "BL1")
                    {
                        for (int i = 0; i < 18; i++) {
                            JSONObject standings = jsonArray.getJSONObject(0); //on se positionne au 1er objet (classement général)
                            JSONArray table = standings.getJSONArray("table");
                            JSONObject team = table.getJSONObject(i);
                            JSONObject team1 = team.getJSONObject("team");
                            String nameTeam = team1.getString("name");
                            String points = team.getString("points");
                            String points2 = points + " pts"; // A REFAIRE
                            String placeTeam = String.valueOf(i + 1);

                            String logoTeam = team1.getString("crestUrl");

                            League league = new League(nameTeam, points2, placeTeam, logoTeam);
                            ranking.add(league);

                        }
                    }
                    else

                    for (int i = 0; i < 20; i++) {
                        JSONObject standings = jsonArray.getJSONObject(0); //on se positionne au 1er objet (classement général)
                        JSONArray table = standings.getJSONArray("table");
                        JSONObject team = table.getJSONObject(i);
                        JSONObject team1 = team.getJSONObject("team");
                        String nameTeam = team1.getString("name");
                        String points = team.getString("points");
                        String points2 = points + " pts"; // A REFAIRE
                        String placeTeam = String.valueOf(i + 1);

                        String logoTeam = team1.getString("crestUrl");

                        League league = new League(nameTeam, points2, placeTeam, logoTeam);
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

    private void GetGoalScorersRanking(String idLeague)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = String.format("https://api.football-data.org/v2/competitions/%s/scorers?limit=30",idLeague);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<Player> goalScorersRanking = new ArrayList<>();
                    JSONArray jsonArray = response.getJSONArray("scorers");


                    for(int i = 0; i < 30; i++)
                    {
                        JSONObject buteurs = jsonArray.getJSONObject(i);
                        JSONObject player = buteurs.getJSONObject("player");
                        String namePlayer = player.getString("name");
                        JSONObject team = buteurs.getJSONObject("team");
                        String nameTeam = team.getString("name");
                        int nbGoals = buteurs.getInt("numberOfGoals");
                        String nbGoals2 = String.valueOf(nbGoals) + " buts";
                        Player goalScorer = new Player(namePlayer, nameTeam, nbGoals2);
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
        })

        {
            //Methode permettant de mettre la clé d'api dans le header
            public Map getHeaders() throws AuthFailureError
            {
                HashMap headers = new HashMap();
                String apiKey = BuildConfig.ApiKey; // securisation de la clé d'API, ajoutée dans gradle.properties et build.gradle
                headers.put("X-Auth-Token", apiKey);
                return headers;

            }
        };

        queue.add(request);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String pos = String.valueOf(parent.getItemIdAtPosition(position));
        String leagueName = "";

        // Here we check what the is ID of the value in the Spinner which indicates what league is selected
        switch (pos){
            case "0":
                leagueName = "FL1";
                GetRanking(leagueName);

                final String finalLeagueName = leagueName;
                goalScorersButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetGoalScorersRanking(finalLeagueName);
                    }
                });
                break;

                case "1":
                leagueName = "PL";
                GetRanking(leagueName);

                final String finalLeagueName2 = leagueName;
                goalScorersButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetGoalScorersRanking(finalLeagueName2);
                    }
                });
                break;

                case "2":
                leagueName = "PD";
                GetRanking(leagueName);

                final String finalLeagueName3 = leagueName;
                goalScorersButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GetGoalScorersRanking(finalLeagueName3);
                        }
                });
                break;

                case "3":
                leagueName = "BL1";
                GetRanking(leagueName);

                    final String finalLeagueName4 = leagueName;
                    goalScorersButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GetGoalScorersRanking(finalLeagueName4);
                        }
                    });
                break;

                case "4":
                leagueName = "SA";
                GetRanking(leagueName);

                    final String finalLeagueName5 = leagueName;
                    goalScorersButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GetGoalScorersRanking(finalLeagueName5);
                        }
                    });
                break;

                case "5":
                Toast.makeText(this, "Pas encore disponible !", Toast.LENGTH_LONG).show();


        }
    }
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
