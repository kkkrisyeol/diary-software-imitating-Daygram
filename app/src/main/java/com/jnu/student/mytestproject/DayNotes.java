package com.jnu.student.mytestproject;


import java.io.Serializable;

public class DayNotes implements Serializable {


    public int year;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int month;

    public String getTxt() {return txt;}
    public void setTxt(String txt) {this.txt = txt;}

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    private String week;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    private int day;
    private String txt=null;

}
