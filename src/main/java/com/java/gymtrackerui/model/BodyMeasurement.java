package com.java.gymtrackerui.model;

public class BodyMeasurement {

    private Long measurementId;
    private String measuredOn;
    private Double weightKg;
    private Double chestCm;
    private Double waistCm;
    private Double hipsCm;
    private Double armCm;
    private Double thighCm;
    private Double bmi;
    private String bmiCategory;

    public BodyMeasurement() {}

    public Long getMeasurementId() { return measurementId; }
    public void setMeasurementId(Long measurementId) { this.measurementId = measurementId; }

    public String getMeasuredOn() { return measuredOn; }
    public void setMeasuredOn(String measuredOn) { this.measuredOn = measuredOn; }

    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }

    public Double getChestCm() { return chestCm; }
    public void setChestCm(Double chestCm) { this.chestCm = chestCm; }

    public Double getWaistCm() { return waistCm; }
    public void setWaistCm(Double waistCm) { this.waistCm = waistCm; }

    public Double getHipsCm() { return hipsCm; }
    public void setHipsCm(Double hipsCm) { this.hipsCm = hipsCm; }

    public Double getArmCm() { return armCm; }
    public void setArmCm(Double armCm) { this.armCm = armCm; }

    public Double getThighCm() { return thighCm; }
    public void setThighCm(Double thighCm) { this.thighCm = thighCm; }

    public Double getBmi() { return bmi; }
    public void setBmi(Double bmi) { this.bmi = bmi; }

    public String getBmiCategory() { return bmiCategory; }
    public void setBmiCategory(String bmiCategory) { this.bmiCategory = bmiCategory; }

    @Override
    public String toString() {
        return measuredOn + " - " + weightKg + "kg" +
                (bmi != null ? " (BMI: " + bmi + ", " + bmiCategory + ")" : "");
    }
}