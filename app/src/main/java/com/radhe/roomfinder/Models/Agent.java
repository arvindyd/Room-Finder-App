package com.radhe.roomfinder.Models;

public class Agent {

    private String profileImg,name,email,passport,adress;
    private long phoneNum;

    public Agent(String profileImg, String name, String email, String passport, String adress, long phoneNum) {
        this.profileImg = profileImg;
        this.name = name;
        this.email = email;
        this.passport = passport;
        this.adress = adress;
        this.phoneNum = phoneNum;
    }

    public Agent() {
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
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

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }
}
