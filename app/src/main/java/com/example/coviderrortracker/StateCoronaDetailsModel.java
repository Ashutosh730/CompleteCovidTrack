package com.example.coviderrortracker;

public class StateCoronaDetailsModel {
    String stateName, confirmedCases, recoveredCases, deaths;

    public StateCoronaDetailsModel(String stateName, String confirmedCases, String recoveredCases, String deaths) {
        this.stateName = stateName;
        this.confirmedCases = confirmedCases;
        this.recoveredCases = recoveredCases;
        this.deaths = deaths;
    }

    public String getStateName() {
        return stateName;
    }

    public String getConfirmedCases() {
        return confirmedCases;
    }

    public String getRecoveredCases() {
        return recoveredCases;
    }

    public String getDeaths() {
        return deaths;
    }

}
