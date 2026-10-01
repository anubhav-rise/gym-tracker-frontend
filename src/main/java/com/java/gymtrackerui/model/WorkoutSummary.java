package com.java.gymtrackerui.model;

public class WorkoutSummary {

    private Long workoutId;
    private String title;
    private String workoutDate;
    private Integer durationMinutes;

    public WorkoutSummary() {}

    public Long getWorkoutId() { return workoutId; }
    public void setWorkoutId(Long workoutId) { this.workoutId = workoutId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getWorkoutDate() { return workoutDate; }
    public void setWorkoutDate(String workoutDate) { this.workoutDate = workoutDate; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
}