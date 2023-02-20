package com.chilicoder.diabetesself_care.footcare;

public class FootItem {

    private String footName;
    private String dosageSummary;

    public FootItem(String footName, String dosageSummary) {
        this.footName = footName;
        this.dosageSummary = dosageSummary;
    }

    public String getFootName() {
        return footName;
    }

    public void setFootName(String footName) {
        this.footName = footName;
    }

    public String getDosageSummary() {
        return dosageSummary;
    }

    public void setDosageSummary(String dosageSummary) {
        this.dosageSummary = dosageSummary;
    }
}
