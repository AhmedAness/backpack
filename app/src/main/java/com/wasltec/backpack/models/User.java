package com.wasltec.backpack.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("isProvider")
    @Expose
    private boolean isProvider;
    @SerializedName("userPhoto_Url")
    @Expose
    private String userPhotoUrl;


    /**
     * No args constructor for use in serialization
     *
     */
    public User() {
    }

    /**
     *
     * @param userPhotoUrl
     * @param id
     * @param mail
     * @param lastName
     * @param isProvider
     * @param name
     * @param firstName
     * @param mobile
     */
    public User(int id, Object name, String firstName, String lastName, String mobile, String mail, boolean isProvider, String userPhotoUrl) {
        super();
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.mail = mail;
        this.isProvider = isProvider;
        this.userPhotoUrl = userPhotoUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getname() {
        return name;
    }

    public void setname(Object name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isIsProvider() {
        return isProvider;
    }

    public void setIsProvider(boolean isProvider) {
        this.isProvider = isProvider;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }
    public String getName(){
        return String.format("%s %s", firstName, lastName);
    }


}
