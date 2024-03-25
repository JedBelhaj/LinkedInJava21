package com.fsb.linkedin.entities;

import java.time.LocalDate;

public class Qualification extends Skill {
    private String diploma;
    private String institution;

    public Qualification(String diploma, String title, String institution, String technology, LocalDate dateStart, LocalDate dateFinish, String description) {
        super(title,  dateStart, dateFinish, description,technology);
        this.diploma = diploma;
        this.institution = institution;
    }

    public Qualification() {
        super();
    }

    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @Override
    public String toString() {
        return "diploma: " + diploma + ", Institution: " + institution + super.toString();
    }
}
