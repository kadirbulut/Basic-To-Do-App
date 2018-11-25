package com.todoapp.Models;

import com.google.gson.annotations.SerializedName;

public class UserCreateModel {

    @SerializedName("Email")
    private String userEmail;
    @SerializedName("FullName")
    private String userFullName;
    @SerializedName("Password")
    private String userPassword;

    public void setUserFullName(String userFullName){
        this.userFullName=userFullName;
    }

    public String getUserFullName() {
        return userFullName;
    }
    public void setUserEmail(String Email){
        this.userEmail = Email;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserPassword(String password){
        this.userPassword=password;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserInfo(){
        return userEmail+":"+userPassword;
    }



}
