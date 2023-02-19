package com.chilicoder.diabetesself_care.blood;

public class BloodItem {


    private String bloodCenterName;
    private String report;
    private String mDate;

    public BloodItem(String bloodCenterName, String report, String mDate) {
        this.bloodCenterName = bloodCenterName;
        this.report = report;
        this.mDate = mDate;
    }

    public String getBloodCenterName() {
        return bloodCenterName;
    }

    public void setBloodCenterName(String bloodCenterName) {
        this.bloodCenterName = bloodCenterName;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }
}
