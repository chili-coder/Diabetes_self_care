package com.chilicoder.diabetesself_care.followup;

public class FollowupItem {

    private String followupName;
    private String hospital;
    private String location;


    public FollowupItem(String followupName, String hospital, String location) {
        this.followupName = followupName;
        this.hospital = hospital;
        this.location = location;

    }

    public String getFollowupName() {
        return followupName;
    }

    public void setFollowupName(String followupName) {
        this.followupName = followupName;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}