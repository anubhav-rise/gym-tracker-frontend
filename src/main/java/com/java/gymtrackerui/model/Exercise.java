package com.java.gymtrackerui.model;

public class Exercise {

    private Long exerciseId;
    private String name;
    private String equipment;
    private String description;
    private Boolean isCustom;

    public Exercise() {}

    public Long getExerciseId() { return exerciseId; }
    public void setExerciseId(Long exerciseId) { this.exerciseId = exerciseId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEquipment() { return equipment; }
    public void setEquipment(String equipment) { this.equipment = equipment; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getIsCustom() { return isCustom; }
    public void setIsCustom(Boolean isCustom) { this.isCustom = isCustom; }

    @Override
    public String toString() {
        return name + (equipment != null && !equipment.isBlank() ? " (" + equipment + ")" : "")
                + (Boolean.TRUE.equals(isCustom) ? " [Custom]" : " [Global]");
    }
}