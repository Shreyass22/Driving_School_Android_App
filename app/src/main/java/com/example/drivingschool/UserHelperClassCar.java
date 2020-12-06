package com.example.drivingschool;

public class UserHelperClassCar {

    String name, company, model, color, image;

    public UserHelperClassCar() {
    }

    public UserHelperClassCar(String name, String company, String model, String color, String image) {
        this.name = name;
        this.company = company;
        this.model = model;
        this.color = color;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
