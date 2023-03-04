package lk.ijse.dep10.app.util;

import lk.ijse.dep10.app.enums.Sex;

import java.io.Serializable;

public class Student implements Serializable {
    private String id;
    private String name;
    private String address;
    private Sex sex;
    private String race;
    private String mobileNumber;
    private String fixedNumber;
    private  String email;
    private Guardian guardian;

    public Student(String id, String name, String address,
                   Sex sex, String race, String mobileNumber, String fixedNumber,
                   String email, Guardian guardian) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.sex = sex;
        this.race = race;
        this.mobileNumber = mobileNumber;
        this.fixedNumber = fixedNumber;
        this.email = email;
        this.guardian = guardian;
    }

    public Student() {}



    /*getters*/
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Sex getSex() {
        return sex;
    }

    public String getRace() {
        return race;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getFixedNumber() {
        return fixedNumber;
    }

    public String getEmail() {
        return email;
    }

    public Guardian getGuardian() {
        return guardian;
    }



    /*Setters*/
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setFixedNumber(String fixedNumber) {
        this.fixedNumber = fixedNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGuardian(Guardian guardian) {
        this.guardian = guardian;
    }
}
