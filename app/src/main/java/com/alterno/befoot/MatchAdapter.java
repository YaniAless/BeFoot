package com.alterno.befoot;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MatchAdapter extends ArrayAdapter<Match> {

    public MatchAdapter(Context context, List<Match> matches){
        super(context,0,matches);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_match,parent,false);
        }

        MatchViewHolder viewHolder = (MatchViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new MatchViewHolder();
            viewHolder.homeTeam = (TextView) convertView.findViewById(R.id.homeTeamTxt);
            viewHolder.awayTeam = (TextView) convertView.findViewById(R.id.awayTeamTxt);
            viewHolder.homeTeamScore = (TextView) convertView.findViewById(R.id.homeTeamScore);
            viewHolder.awayTeamScore = (TextView) convertView.findViewById(R.id.awayTeamScore);
            convertView.setTag(viewHolder);
        }

        Match match = getItem(position);

        viewHolder.homeTeam.setText(match.getHomeTeam());
        viewHolder.awayTeam.setText(match.getAwayTeam());
        viewHolder.homeTeamScore.setText(match.getHomeTeamScore());
        viewHolder.awayTeamScore.setText(match.getAwayTeamScore());

        return convertView;
    }

    private class MatchViewHolder{
        public TextView homeTeam;
        public TextView awayTeam;
        public TextView homeTeamScore;
        public TextView awayTeamScore;
    }

}
