package com.alterno.befoot;

public class League {

    private String nameLeague;
    private String nameTeam;
    private String nbPoints;

    public League(String nameLeague, String nameTeam, String nbPoints) {
        this.nameLeague = nameLeague;
        this.nameTeam = nameTeam;
        this.nbPoints = nbPoints;

    }

    public League(String nameTeam, String nbPoints) {
        this.nameTeam = nameTeam;
        this.nbPoints = nbPoints;

    }

    public String getNameLeague() {
        return nameLeague;
    }

    public void setNameLeague(String nameLeague) {
        this.nameLeague = nameLeague;
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
}
