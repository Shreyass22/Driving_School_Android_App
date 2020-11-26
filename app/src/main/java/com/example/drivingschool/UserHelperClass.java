package com.example.drivingschool;

public class UserHelperClass {

    String userId, name, email, phone, password, gender, imageUrl, type, addr;

    public UserHelperClass() {
    }

    public UserHelperClass(String userId, String name, String email, String phone, String password, String gender, String imageUrl, String type, String addr) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.type = type;
        this.addr = addr;
        this.password = password;
        this.gender = gender;
        this.imageUrl = imageUrl;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
