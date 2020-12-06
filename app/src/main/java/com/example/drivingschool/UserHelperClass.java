package com.example.drivingschool;

public class UserHelperClass {

    String userId, name, email, phone, password, gender, image, type, addr, msg;

    public UserHelperClass() {
    }

    public UserHelperClass(String userId, String name, String email, String phone, String password,
                           String gender, String type, String image, String addr, String msg) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.type = type;
        this.addr = addr;
        this.password = password;
        this.image = image;
        this.gender = gender;
        this.msg = msg;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
