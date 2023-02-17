package com.chilicoder.diabetesself_care.diet;

public class DietItem {
    private String dietName;
    private String dosageSummary;

    public DietItem(String dietName, String dosageSummary) {
        this.dietName = dietName;
        this.dosageSummary = dosageSummary;
    }

    public String getDietName() {
        return dietName;
    }

    public String getDosageSummary() {
        return dosageSummary;
    }
}
