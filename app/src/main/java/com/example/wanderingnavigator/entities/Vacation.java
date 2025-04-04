package com.example.wanderingnavigator.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;


@Entity(tableName = "vacations")
public class Vacation extends BaseEntity {
    @PrimaryKey(autoGenerate = true)
    private int vacationId;
    private String vacationTitle;
    private String vacationHotel;
    private String startDate;
    private String endDate;

    // Constructor WITHOUT ID for insertion
    @Ignore
    public Vacation(String vacationTitle, String vacationHotel, String startDate, String endDate) {
        super();
        this.vacationTitle = vacationTitle;
        this.vacationHotel = vacationHotel;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDateStr = getCreatedDateFormatted();
        this.modifiedDateStr = getModifiedDateFormatted();
    }

    private String getModifiedDateFormatted() {
        return "";
    }

    private String getCreatedDateFormatted() {
        return "";
    }

    // Constructor WITH ID for updates
    public Vacation(int vacationId, String vacationTitle, String vacationHotel, String startDate, String endDate) {
        super();
        this.vacationId = vacationId;
        this.vacationTitle = vacationTitle;
        this.vacationHotel = vacationHotel;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDateStr = getCreatedDateFormatted();
        this.modifiedDateStr = getModifiedDateFormatted();
    }

    @ColumnInfo(name = "created_date")
    private String createdDateStr;

    @ColumnInfo(name = "modified_date")
    private String modifiedDateStr;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getVacationHotel() {
        return vacationHotel;
    }

    public void setVacationHotel(String vacationHotel) {
        this.vacationHotel = vacationHotel;
    }

    public String getVacationTitle() {
        return vacationTitle;
    }

    @Override
    public String toString() {
        return vacationTitle;
    }

    public void setVacationTitle(String vacationTitle) {
        this.vacationTitle = vacationTitle;
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getModifiedDateStr() {
        return modifiedDateStr;
    }

    public void setModifiedDateStr(String modifiedDateStr) {
        this.modifiedDateStr = modifiedDateStr;
    }


    @Override
    public boolean validate() {
        return false;
    }
}
