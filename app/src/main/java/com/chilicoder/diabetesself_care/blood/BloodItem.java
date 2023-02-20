package com.chilicoder.diabetesself_care.blood;

public class BloodItem {


    private String bloodName;
    private String report;
    private String mTime;
    private String mDate;

    public BloodItem(String bloodName, String report, String mTime, String mDate) {
        this.bloodName = bloodName;
        this.report = report;
        this.mTime = mTime;
        this.mDate = mDate;
    }

    public String getBloodName() {
        return bloodName;
    }

    public void setBloodName(String bloodName) {
        this.bloodName = bloodName;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
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
