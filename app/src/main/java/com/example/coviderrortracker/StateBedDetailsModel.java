package com.example.coviderrortracker;

public class StateBedDetailsModel {
    private String stateName;
    private long totalHospitals,totalBeds;

    public StateBedDetailsModel(String stateName, long totalHospitals, long totalBeds) {
        this.stateName = stateName;
        this.totalHospitals = totalHospitals;
        this.totalBeds = totalBeds;
    }

    public String getStateName() {
        return stateName;
    }

    public long getTotalHospitals() {
        return totalHospitals;
    }

    public long getTotalBeds() {
        return totalBeds;
    }
}
