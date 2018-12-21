package com.wasltec.provider.model;

public class OrganizerType {

    String id;
    String type;
    String description="";

    public OrganizerType(String id, String type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
    }

    public OrganizerType(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
