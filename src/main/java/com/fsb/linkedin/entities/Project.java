package com.fsb.linkedin.entities;

import java.time.LocalDate;

public class Project {
    private String title;
    private LocalDate dateStart;
    private LocalDate dateFinish;
    private String Description;

    public Project(String title, LocalDate dateStart, LocalDate dateFinish, String description) {

        this.title = title;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        Description = description;
    }

    public Project() {

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public LocalDate getStartDate() {
        return dateStart;
    }

    public void setStartDate(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getFinishDate() {
        return dateFinish;
    }

    public void setFinishDate(LocalDate dateFinish) {
        this.dateFinish = dateFinish;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return
                ", title: " + title +
                        ", dateStart: " + dateStart +
                        ", dateFinish: " + dateFinish +
                        ", Description: " + Description;
    }
}
