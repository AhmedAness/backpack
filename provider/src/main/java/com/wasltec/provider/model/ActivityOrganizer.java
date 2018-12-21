package com.wasltec.provider.model;

public class ActivityOrganizer {

    int id ,type_id;
    String name , email,mobile ,organizerType;
    Object typeDescription;



    public ActivityOrganizer(int id, int type_id, String name, String email, String mobile, String organizerType, Object typeDescription) {
        this.id = id;
        this.type_id = type_id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.organizerType = organizerType;
        this.typeDescription = typeDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrganizerType() {
        return organizerType;
    }

    public void setOrganizerType(String organizerType) {
        this.organizerType = organizerType;
    }

    public Object getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(Object typeDescription) {
        this.typeDescription = typeDescription;
    }
}
