package com.chilicoder.diabetesself_care.med;

public class MedItem {

    private String medicineName;
    private String dosageSummary;

    public MedItem(String medicineName, String dosageSummary) {
        this.medicineName = medicineName;
        this.dosageSummary = dosageSummary;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getDosageSummary() {
        return dosageSummary;
    }
}
