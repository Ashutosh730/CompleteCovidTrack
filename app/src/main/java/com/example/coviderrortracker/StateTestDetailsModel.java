package com.example.coviderrortracker;

public class StateTestDetailsModel {

    private String stateName;
    private long firstDose,secondDose;

    public StateTestDetailsModel(String stateName, long firstDose, long secondDose) {
        this.stateName = stateName;
        this.firstDose = firstDose;
        this.secondDose = secondDose;
    }

    public String getStateName() {
        return stateName;
    }

    public long getFirstDose() {
        return firstDose;
    }

    public long getSecondDose() {
        return secondDose;
    }
}
