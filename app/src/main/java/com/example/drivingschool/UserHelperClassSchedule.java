package com.example.drivingschool;

public class UserHelperClassSchedule {
    private String name;
    private String trainer;
    private String car;
    private String date;
    private String time;
    private String totalAttended;

    public UserHelperClassSchedule() {
    }

    public UserHelperClassSchedule(String name, String trainer, String car, String date, String time, String totalAttended) {
        this.name = name;
        this.trainer = trainer;
        this.car = car;
        this.date = date;
        this.time = time;
        this.totalAttended = totalAttended;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAttended() {
        return totalAttended;
    }

    public void setTotalAttended(String totalAttended) {
        this.totalAttended = totalAttended;
    }
}
