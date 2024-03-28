package com.fsb.linkedin.entities;

import java.time.LocalDate;

public class Experience extends Skill{
    String type;
    String mission;


    public Experience(String title, String technology, LocalDate dateStart, LocalDate dateFinish, String description, String type, String mission) {
        super(title, dateStart, dateFinish, description,technology);
        this.type = type;
        this.mission = mission;
    }

    public Experience() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    @Override
    public String toString() {
        return  "type: " + type +
                ", mission: " + mission + super.toString();
    }
}
