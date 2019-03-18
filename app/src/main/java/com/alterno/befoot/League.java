package com.alterno.befoot;

public class League {

    private String idLeague;
    private String nameLeague;

    public League(String idLeague, String nameLeague) {
        this.idLeague = idLeague;
        this.nameLeague = nameLeague;
    }

    public String getIdLeague() {
        return idLeague;
    }

    public void setIdLeague(String idLeague) {
        this.idLeague = idLeague;
    }

    public String getNameLeague() {
        return nameLeague;
    }

    public void setNameLeague(String nameLeague) {
        this.nameLeague = nameLeague;
    }

}
