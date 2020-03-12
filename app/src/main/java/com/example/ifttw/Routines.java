package com.example.ifttw;

import androidx.annotation.ColorLong;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Routines {

    @PrimaryKey
    long idRoutine;

    @ColumnInfo(name = "triggerType")
    private int triggerType;

    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "month")
    private int month;

    @ColumnInfo(name = "day")
    private int day;

    @ColumnInfo(name = "hour")
    private int hour;

    @ColumnInfo(name = "minute")
    private int minute;

    @ColumnInfo(name = "actionType")
    private int actionType;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "status")
    private int status;

    public long getIdRoutine() {
        return idRoutine;
    }
    public void setIdRoutine(long idRoutine) {
        this.idRoutine = idRoutine;
    }
    public int getTriggerType() {
        return triggerType;
    }
    public void setTriggerType(int triggerType) {
        this.triggerType = triggerType;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public int getDay() {
        return day;
    }
    public void setDay(int Day) {
        this.day = Day;
    }
    public int getHour() {
        return  hour;
    }
    public void setHour(int Hour) {
        this.hour = Hour;
    }
    public int getMinute() {
        return minute;
    }
    public void setMinute(int Minute) {
        this.minute = Minute;
    }
    public int getActionType() {
        return actionType;
    }

}