package com.alterno.befoot;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ListView listMatches;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_matches:
                    //mTextMessage.setText(R.string.title_matches);
                    return true;
                case R.id.navigation_leagues:
                    //mTextMessage.setText(R.string.title_leagues);
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
        setContentView(R.layout.activity_main);
        //mTextMessage = (TextView) findViewById(R.id.message);

        listMatches = (ListView) findViewById(R.id.listMatches);

        List<Match> matches = genererMatch();

        MatchAdapter adapter = new MatchAdapter(MainActivity.this,matches);
        listMatches.setAdapter(adapter);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
