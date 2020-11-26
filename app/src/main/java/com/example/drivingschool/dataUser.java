package com.example.drivingschool;

public class dataUser {
    private String id;
    private String c_name;
    private String trr_name;
    private String carr_name;
    private long datee;
    private String time;

    public dataUser() {
    }

    public dataUser(String id, String c_name, String trr_name, String carr_name, long datee, String time) {
        this.id = id;
        this.c_name = c_name;
        this.trr_name = trr_name;
        this.carr_name = carr_name;
        this.datee = datee;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getTrr_name() {
        return trr_name;
    }

    public void setTrr_name(String trr_name) {
        this.trr_name = trr_name;
    }

    public String getCarr_name() {
        return carr_name;
    }

    public void setCarr_name(String carr_name) {
        this.carr_name = carr_name;
    }

    public long getDatee() {
        return datee;
    }

    public void setDatee(long datee) {
        this.datee = datee;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

