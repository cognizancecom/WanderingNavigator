package com.example.d308vacationplanner.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursion_table")
public class Excursion {
    @PrimaryKey(autoGenerate = true)
    private int excursionId;
    private int vacationId;
    private String title;
    private String date;

    // Constructor WITHOUT ID for insertion
    @Ignore
    public Excursion(int vacationId, String title, String date) {
        this.vacationId = vacationId;
        this.title = title;
        this.date = date;
    }

    // Constructor WITH ID for updates
    public Excursion(int excursionId, int vacationId, String title, String date) {
        this.excursionId = excursionId;
        this.vacationId = vacationId;
        this.title = title;
        this.date = date;
    }


    public int getExcursionId() {
        return excursionId;
    }

    public void setExcursionId(int excursionId) {
        this.excursionId = excursionId;
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
