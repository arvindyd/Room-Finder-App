package com.radhe.roomfinder.Models;

public class Users {

    private String userProfile,userName,email,password,location;
    private long phNumber;
    private String userId;

    public Users(String userId) {
        this.userId = userId;
    }

    public Users(String userProfile, String userName, String email, String password, String location, long phNumber) {
        this.userProfile = userProfile;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.location = location;
        this.phNumber = phNumber;
    }

    public Users(String userName, String email, String password, String location, long phNumber) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.location = location;
        this.phNumber = phNumber;
    }

    public Users() {
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(long phNumber) {
        this.phNumber = phNumber;
    }
}
