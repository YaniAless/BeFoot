package com.alterno.befoot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PlayerAdapter extends ArrayAdapter<Player> {

    public PlayerAdapter(Context context, List<Player> players){
        super(context,0,players);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_scorers,parent,false);
        }

        LeagueViewHolder viewHolder = (LeagueViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new LeagueViewHolder();
            viewHolder.placeScorer = convertView.findViewById(R.id.placeScorer);
            viewHolder.namePlayer = convertView.findViewById(R.id.namePlayer);
            viewHolder.nameTeam =  convertView.findViewById(R.id.nameTeam);
            viewHolder.nbGoals =  convertView.findViewById(R.id.nbGoals);
            convertView.setTag(viewHolder);
        }

        Player player = getItem(position);

        viewHolder.placeScorer.setText(player.getPlaceScorer());
        viewHolder.namePlayer.setText(player.getNamePlayer());
        viewHolder.nameTeam.setText(player.getNameTeam());
        viewHolder.nbGoals.setText(player.getNbGoals());


        return convertView;
    }

    private class LeagueViewHolder{
        public TextView placeScorer;
        public  TextView namePlayer;
        public TextView nameTeam;
        public TextView nbGoals;
    }
}