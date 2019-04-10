package com.alterno.befoot;


public class Player {

    private String namePlayer;
    private String nameTeam;
    private String nbGoals;


    public Player(String namePlayer, String nameTeam, String nbGoals) {
        this.namePlayer = namePlayer;
        this.nameTeam = nameTeam;
        this.nbGoals = nbGoals;
    }


    public String getNamePlayer() {
        return namePlayer;
    }

    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }

    public String getNameTeam() {
        return nameTeam;
    }

    public void setNameTeam(String nameTeam) {
        this.nameTeam = nameTeam;
    }

    public String getNbGoals() {
        return nbGoals;
    }

    public void setNbGoals(String nbGoals) {
        this.nbGoals = nbGoals;
    }

}
