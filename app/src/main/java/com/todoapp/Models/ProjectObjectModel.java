package com.todoapp.Models;

import com.google.gson.annotations.SerializedName;

public class ProjectObjectModel {

    @SerializedName("Content")
    private String Content;

    @SerializedName("Icon")
    private int Icon;


    public String getContent() {
        return Content;
    }
    public void setContent(String Content){
        this.Content=Content;
    }

    public int getIcon() {
        return Icon;
    }
    public void setIcon(int Icon){
        this.Icon=Icon;
    }
}
