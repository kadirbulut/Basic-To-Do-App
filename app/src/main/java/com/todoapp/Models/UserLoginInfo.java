package com.todoapp.Models;

import com.google.gson.annotations.SerializedName;

public class UserLoginInfo {

    @SerializedName("Email")
    private String userEmail;

    @SerializedName("Password")
    private String userPassword;


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
