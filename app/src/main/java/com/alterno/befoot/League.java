package com.alterno.befoot;

import android.media.Image;

public class League {


    private String nameTeam;
    private String nbPoints;
    private String logoTeam;
    private String placeTeam;


    public League(String nameTeam, String nbPoints, String placeTeam, String logoTeam) {
        this.nameTeam = nameTeam;
        this.nbPoints = nbPoints;
        this.placeTeam = placeTeam;
        this.logoTeam = logoTeam;
    }

    public String getPlaceTeam() {
        return placeTeam;
    }

    public void setPlaceTeam(String placeTeam) {
        this.placeTeam = placeTeam;
    }

    public String getNameTeam() {
        return nameTeam;
    }

    public void setNameTeam(String nameTeam) {
        this.nameTeam = nameTeam;
    }

    public String getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(String nbPoints) {
        this.nbPoints = nbPoints;
    }


    public String getLogoTeam() {
        return logoTeam;
    }

    public void setLogoTeam(String logoTeam) {
        this.logoTeam = logoTeam;
    }

}
