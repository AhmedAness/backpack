
package com.wasltec.backpack.models.ActivityDetails;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivityAddOn implements Serializable
{

    @SerializedName("addons_id")
    @Expose
    private Integer addonsId;
    @SerializedName("activityAddons_id")
    @Expose
    private Integer activityAddonsId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private float price;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("provider_Username")
    @Expose
    private String providerUsername;
    @SerializedName("addons_number")
    @Expose
    private Integer addonsNumber;
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("icon")
    @Expose
    private String icon;

    boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private final static long serialVersionUID = -8135464137298026046L;

    public Integer getAddonsId() {
        return addonsId;
    }

    public void setAddonsId(Integer addonsId) {
        this.addonsId = addonsId;
    }

    public Integer getActivityAddonsId() {
        return activityAddonsId;
    }

    public void setActivityAddonsId(Integer activityAddonsId) {
        this.activityAddonsId = activityAddonsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProviderUsername() {
        return providerUsername;
    }

    public void setProviderUsername(String providerUsername) {
        this.providerUsername = providerUsername;
    }

    public Integer getAddonsNumber() {
        return addonsNumber;
    }

    public void setAddonsNumber(Integer addonsNumber) {
        this.addonsNumber = addonsNumber;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
