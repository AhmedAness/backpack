package com.wasltec.provider.model;

public class ActivityAddOn {
    int  id ;
    int activityAddons_id ;
    int addons_id ;
    String name , icon ;
    private int price;
    private String note;

    public int getActivityAddons_id() {
        return activityAddons_id;
    }

    public void setActivityAddons_id(int activityAddons_id) {
        this.activityAddons_id = activityAddons_id;
    }

    public int getAddons_id() {
        return addons_id;
    }

    public void setAddons_id(int addons_id) {
        this.addons_id = addons_id;
    }

    private String provider_name;
    private int addons_number;
    boolean isSelected;

    boolean intial = true;

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public int getAddons_number() {
        return addons_number;
    }

    public void setAddons_number(int addons_number) {
        this.addons_number = addons_number;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ActivityAddOn(int id, String name, String icon_url) {
        this.id = id;
        this.name = name;
        this.icon = icon_url;
    }



    public int getAddonsNumber() { return this.addons_number; }

    public void setAddonsNumber(int addons_number) { this.addons_number = addons_number; }

    public String getProvidername() { return this.provider_name; }

    public void setProvidername(String provider_name) { this.provider_name = provider_name; }

    public int getPrice() { return this.price; }

    public void setPrice(int price) { this.price = price; }

    public String getNote() { return this.note; }

    public void setNote(String note) { this.note = note; }


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

    public String getIcon_url() {
        return icon;
    }

    public void setIcon_url(String icon_url) {
        this.icon = icon_url;
    }


    public boolean isIntial() {
        return intial;
    }

    public void setIntial(boolean intial) {
        this.intial = intial;
    }
}


