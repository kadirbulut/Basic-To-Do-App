package com.todoapp.Models;

import com.google.gson.annotations.SerializedName;

public class ProjectResponseModel {

    @SerializedName("Id")
    private int Id;

    @SerializedName("Content")
    private String Content;

    @SerializedName("ItemsCount")
    private int ItemsCount;

    @SerializedName("Icon")
    private int Icon;

    @SerializedName("ItemType")
    private int ItemType;

    @SerializedName("ParentId")
    private int ParentId;

    @SerializedName("Collapsed")
    private boolean Collapsed;

    public String getContent(){
        return this.Content;
    }
    public void setContent(String Content){
        this.Content=Content;
    }
    public int getId(){
        return this.Id;
    }
    public void setId(int Id){
        this.Id=Id;
    }
}
