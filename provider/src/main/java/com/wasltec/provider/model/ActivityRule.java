package com.wasltec.provider.model;

public class ActivityRule {

    boolean status ;
    boolean selected;
    boolean intial =true;



    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ActivityRule(String id, String description,boolean selected) {
        this.id = id;
        this.description = description;
        this.selected = selected;
    }
    public ActivityRule(String id, String description,boolean selected,boolean intial) {
        this.id = id;
        this.description = description;
        this.selected = selected;
        this.intial = intial;
    }


    private String id;
    private String description;

    public String getDescription() { return this.description; }

    public void setDescription(String description) { this.description = description; }

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isIntial() {
        return intial;
    }

    public void setIntial(boolean intial) {
        this.intial = intial;
    }
}
