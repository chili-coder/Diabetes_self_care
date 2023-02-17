package com.chilicoder.diabetesself_care.activity;

public class PhysicalItem {

    private String activityName;
    private String activityDuration;
    private String dosageSummary;

    public PhysicalItem(String activityName, String activityDuration, String dosageSummary) {
        this.activityName = activityName;
        this.activityDuration = activityDuration;
        this.dosageSummary = dosageSummary;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDuration() {
        return activityDuration;
    }

    public void setActivityDuration(String activityDuration) {
        this.activityDuration = activityDuration;
    }

    public String getDosageSummary() {
        return dosageSummary;
    }

    public void setDosageSummary(String dosageSummary) {
        this.dosageSummary = dosageSummary;
    }
}
