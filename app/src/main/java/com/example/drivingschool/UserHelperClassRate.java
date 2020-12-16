package com.example.drivingschool;

public class UserHelperClassRate {

    String name, message, type, level, mail;

    public UserHelperClassRate() {
    }

    public UserHelperClassRate(String name, String message, String type, String level, String mail) {
        this.name = name;
        this.message = message;
        this.type = type;
        this.level = level;
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
