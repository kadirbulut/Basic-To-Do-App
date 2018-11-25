package com.todoapp.Models;

import com.google.gson.annotations.SerializedName;

public class ItemObjectModel {

    @SerializedName("Id")
    private int Id;

    @SerializedName("Content")
    private String Content;

    @SerializedName("ItemType")
    private int ItemType;

    @SerializedName("Checked")
    private boolean Checked;

    @SerializedName("ProjectId")
    private int ProjectId;

    @SerializedName("ProjectId")
    private int ParentId;

}
