package com.example.drivingschool;

public class UserHelperClassCar {

    String userId, name, company, model, color;

    public UserHelperClassCar() {
    }

    public UserHelperClassCar(String userId, String name, String company, String model, String color) {
        this.userId = userId;
        this.name = name;
        this.company = company;
        this.model = model;
        this.color = color;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
