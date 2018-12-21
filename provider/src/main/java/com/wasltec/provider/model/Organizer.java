package com.wasltec.provider.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Organizer {
    private int id;
    private String name;
    private String type;
    private String mobile;
    private String phone;
    private String mail;

    /**
     * No args constructor for use in serialization
     *
     */
    public Organizer() {
    }

    /**
     *
     * @param id
     * @param mail
     * @param name
     * @param type
     * @param mobile
     */
    public Organizer(int id, String name, String type, String mobile, String mail) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
        this.mobile = mobile;
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
      public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}

