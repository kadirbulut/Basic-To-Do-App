package com.todoapp.Models;

import com.google.gson.annotations.SerializedName;

public class UserResponseModel {
    @SerializedName("Id")
    private int userId;

    @SerializedName("Email")
    private String userEmail;

    @SerializedName("FullName")
    private String userFullName;

    @SerializedName("TimeZone")
    private int TimeZone;

    @SerializedName("IsProUser")
    private boolean IsProUser;

    @SerializedName("DefaultProjectId")
    private int DefaultProjectId;

    @SerializedName("AddItemMoreExpanded")
    private boolean AddItemMoreExpanded;

    @SerializedName("EditDueDateMoreExpanded")
    private boolean EditDueDateMoreExpanded;

    @SerializedName("ListSortType")
    private int ListSortType;

    @SerializedName("FirstDayOfWeek")
    private int FirstDayOfWeek;

    @SerializedName("NewTaskDueDate")
    private int NewTaskDueDate;


    public void setUserId(int id){
        this.userId=id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserEmail(String Email){
        this.userEmail = Email;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserFullName(String userFullName){
        this.userFullName=userFullName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setTimeZone(int timeZone){
        this.TimeZone=timeZone;
    }

    public int getTimeZone(){
        return this.TimeZone;
    }
}
