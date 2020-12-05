package com.example.drivingschool;

public class UserHelperClassSchedule {
    private String c_name;
    private String t_name;
    private String car_name;
    private String datee;
    private String timee;

    public UserHelperClassSchedule() {
    }

    public UserHelperClassSchedule(String c_name, String t_name, String car_name, String datee, String timee) {
        this.c_name = c_name;
        this.t_name = t_name;
        this.car_name = car_name;
        this.datee = datee;
        this.timee = timee;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }

    public String getTimee() {
        return timee;
    }

    public void setTimee(String timee) {
        this.timee = timee;
    }
}
