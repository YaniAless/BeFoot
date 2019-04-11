package com.alterno.befoot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class LeagueAdapter extends ArrayAdapter<League> {

    public LeagueAdapter(Context context, List<League> leagues){
        super(context,0,leagues);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_ranking,parent,false);
        }

        LeagueViewHolder viewHolder = (LeagueViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new LeagueViewHolder();
            viewHolder.nameTeam = (TextView) convertView.findViewById(R.id.nameTeam);
            viewHolder.nbPoints = (TextView) convertView.findViewById(R.id.nbPoints);
            viewHolder.placeTeam = (TextView) convertView.findViewById(R.id.placeTeam);
            viewHolder.logoTeam = convertView.findViewById(R.id.logoTeam);
            convertView.setTag(viewHolder);
        }

        League league = getItem(position);

        String url = league.getLogoTeam();
        //url.replace("http", "https");
        Picasso.get().load(url).resize(120,120).into(viewHolder.logoTeam);
        viewHolder.nameTeam.setText(league.getNameTeam());
        viewHolder.nbPoints.setText(league.getNbPoints());
        viewHolder.placeTeam.setText(league.getPlaceTeam());



        return convertView;
    }

    private class LeagueViewHolder{
        public TextView nameTeam;
        public TextView nbPoints;
        public TextView placeTeam;
        public ImageView logoTeam;
    }
}
