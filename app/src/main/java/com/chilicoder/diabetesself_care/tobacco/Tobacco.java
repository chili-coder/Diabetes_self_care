package com.chilicoder.diabetesself_care.tobacco;

public class Tobacco {
    private String tobaccoName;
    private String dosageSummary;

    public Tobacco(String tobaccoName, String dosageSummary) {
        this.tobaccoName = tobaccoName;
        this.dosageSummary = dosageSummary;
    }

    public String getTobaccoName() {
        return tobaccoName;
    }

    public void setTobaccoName(String tobaccoName) {
        this.tobaccoName = tobaccoName;
    }

    public String getDosageSummary() {
        return dosageSummary;
    }

    public void setDosageSummary(String dosageSummary) {
        this.dosageSummary = dosageSummary;
    }
}
