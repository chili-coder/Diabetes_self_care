package com.chilicoder.diabetesself_care.followup;

public class FollowupItem {

    private String followupName;
    private String hospital;
    private String location;
    private String mTime;
    private String mDate;

    public FollowupItem(String followupName, String hospital, String location, String mTime,String mDate) {
        this.followupName = followupName;
        this.hospital = hospital;
        this.location = location;
        this.mTime = mTime;
        this.mDate = mDate;
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

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }
}