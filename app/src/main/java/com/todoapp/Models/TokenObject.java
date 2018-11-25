package com.todoapp.Models;

import com.google.gson.annotations.SerializedName;

public class TokenObject {
    @SerializedName("TokenString")
    private String TokenString;

    @SerializedName("UserEmail")
    private String UserEmail;

    @SerializedName("ExpirationTime")
    private String ExpirationTime;

    public void setTokenString(String TokenString){
        this.TokenString=TokenString;
    }

    public String getTokenString() {
        return TokenString;
    }

    public void setUserEmail(String UserEmail){
        this.UserEmail=UserEmail;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setExpirationTime(String ExpirationTime){
        this.ExpirationTime=ExpirationTime;
    }

    public String getExpirationTime() {
        return ExpirationTime;
    }
}
