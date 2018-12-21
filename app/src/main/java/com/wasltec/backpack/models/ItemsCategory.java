package com.wasltec.backpack.models;


import com.google.gson.annotations.SerializedName;

public class ItemsCategory {
    @SerializedName("id")
    private int ID;
    @SerializedName("name")
    private String title;
    @SerializedName("url")
    private  String Image;

    public ItemsCategory(int ID, String title, String image) {
        this.ID = ID;
        this.title = title;
        Image = image;
    }

    public ItemsCategory() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
