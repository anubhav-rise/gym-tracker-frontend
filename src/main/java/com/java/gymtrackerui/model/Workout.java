package com.java.gymtrackerui.model;

public class Workout {

    private Long workoutId;
    private String workoutDate;
    private String title;
    private String notes;
    private Integer durationMinutes;

    public Workout() {}

    public Long getWorkoutId() { return workoutId; }
    public void setWorkoutId(Long workoutId) { this.workoutId = workoutId; }

    public String getWorkoutDate() { return workoutDate; }
    public void setWorkoutDate(String workoutDate) { this.workoutDate = workoutDate; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    @Override
    public String toString() {
        // Controls how this object displays in a ListView
        return workoutDate + " - " + (title != null ? title : "Untitled") +
                (durationMinutes != null ? " (" + durationMinutes + " min)" : "");
    }
}