package com.example.myapplication.Calendar;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {

    private long id;
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public Event(long id,LocalDate date, String title, String description, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    // Getter and setter for id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
