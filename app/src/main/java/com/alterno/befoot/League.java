package com.alterno.befoot;

import android.media.Image;

public class League {


    private String nameTeam;
    private String nbPoints;
    private Image logoteam;

    public League(String nameTeam, String nbPoints, Image logoteam) {
        this.nameTeam = nameTeam;
        this.nbPoints = nbPoints;
        this.logoteam = logoteam;

    }

    public League(String nameTeam, String nbPoints) {
        this.nameTeam = nameTeam;
        this.nbPoints = nbPoints;
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


    public Image getLogoteam() {
        return logoteam;
    }

    public void setLogoteam(Image logoteam) {
        this.logoteam = logoteam;
    }

}
