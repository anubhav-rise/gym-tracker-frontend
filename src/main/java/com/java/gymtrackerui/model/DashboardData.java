package com.java.gymtrackerui.model;

public class DashboardData {

    private long totalWorkouts;
    private long totalExercisesPerformed;
    private WorkoutSummary recentWorkout;

    public DashboardData() {}

    public long getTotalWorkouts() { return totalWorkouts; }
    public void setTotalWorkouts(long totalWorkouts) { this.totalWorkouts = totalWorkouts; }

    public long getTotalExercisesPerformed() { return totalExercisesPerformed; }
    public void setTotalExercisesPerformed(long totalExercisesPerformed) { this.totalExercisesPerformed = totalExercisesPerformed; }

    public WorkoutSummary getRecentWorkout() { return recentWorkout; }
    public void setRecentWorkout(WorkoutSummary recentWorkout) { this.recentWorkout = recentWorkout; }
}