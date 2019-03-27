package com.alterno.befoot;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class LeagueActivity extends AppCompatActivity {
    private TextView teamRanking;
    private Button buttonSA, buttonL1, buttonLIGA, buttonPL, buttonBL;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league);
        //GetRanking("FL1"); // à faire : choisir son championnat

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        teamRanking = findViewById(R.id.team);
        buttonL1 = findViewById(R.id.buttonL1);
        buttonSA = findViewById(R.id.buttonSA);
        buttonLIGA = findViewById(R.id.buttonLIGA);
        buttonBL = findViewById(R.id.buttonBL);
        buttonPL = findViewById(R.id.buttonPL);


        buttonL1.setOnClickListener(L1Listener);
        buttonSA.setOnClickListener(SAListener);
        buttonLIGA.setOnClickListener(LIGAListener);
        buttonBL.setOnClickListener(BLListener);
        buttonPL.setOnClickListener(PLListener);
    }

    private View.OnClickListener L1Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttonL1.setVisibility(View.GONE);
            buttonSA.setVisibility(View.GONE);
            buttonPL.setVisibility(View.GONE);
            buttonLIGA.setVisibility(View.GONE);
            buttonBL.setVisibility(View.GONE);
            teamRanking.setText(" "); //vider le textView
            GetRanking("FL1");

        }
    };
    private View.OnClickListener SAListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttonL1.setVisibility(View.GONE);
            buttonSA.setVisibility(View.GONE);
            buttonPL.setVisibility(View.GONE);
            buttonLIGA.setVisibility(View.GONE);
            buttonBL.setVisibility(View.GONE);
            teamRanking.setText(" ");
            GetRanking("SA");

        }
    };
    private View.OnClickListener BLListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            teamRanking.setText(" ");
            buttonL1.setVisibility(View.GONE);
            buttonSA.setVisibility(View.GONE);
            buttonPL.setVisibility(View.GONE);
            buttonLIGA.setVisibility(View.GONE);
            buttonBL.setVisibility(View.GONE);
            GetRanking("BL1");

        }
    };
    private View.OnClickListener PLListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttonL1.setVisibility(View.GONE);
            buttonSA.setVisibility(View.GONE);
            buttonPL.setVisibility(View.GONE);
            buttonLIGA.setVisibility(View.GONE);
            buttonBL.setVisibility(View.GONE);
            teamRanking.setText(" ");
            GetRanking("PL");

        }
    };
    private View.OnClickListener LIGAListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttonL1.setVisibility(View.GONE);
            buttonSA.setVisibility(View.GONE);
            buttonPL.setVisibility(View.GONE);
            buttonLIGA.setVisibility(View.GONE);
            buttonBL.setVisibility(View.GONE);
            teamRanking.setText(" ");
            GetRanking("PD");

        }
    };


    private void GetRanking(String idLeague){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.football-data.org/v2/competitions/"+ idLeague +"/standings";


        JsonObjectRequest matchesReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

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
                        String goalDifference = team.getString("goalDifference");

                        if(i==0)
                        {
                            teamRanking.append("     Equipe            NbPoints        Diff de but\n\n");
                        }
                        teamRanking.append(i+1+") "+ nameTeam + "  " +points + " pts" + "      " + goalDifference+ "\n");

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
