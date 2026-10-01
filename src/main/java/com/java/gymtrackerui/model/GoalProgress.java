package com.java.gymtrackerui.model;

public class GoalProgress {

    private Long progressId;
    private String recordedOn;
    private Double currentValue;
    private Double percentComplete;

    public GoalProgress() {}

    public Long getProgressId() { return progressId; }
    public void setProgressId(Long progressId) { this.progressId = progressId; }

    public String getRecordedOn() { return recordedOn; }
    public void setRecordedOn(String recordedOn) { this.recordedOn = recordedOn; }

    public Double getCurrentValue() { return currentValue; }
    public void setCurrentValue(Double currentValue) { this.currentValue = currentValue; }

    public Double getPercentComplete() { return percentComplete; }
    public void setPercentComplete(Double percentComplete) { this.percentComplete = percentComplete; }

    @Override
    public String toString() {
        return recordedOn + " - Value: " + currentValue +
                (percentComplete != null ? " (" + percentComplete + "% complete)" : "");
    }
}