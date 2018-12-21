
package com.wasltec.backpack.models.BookingDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BookingTicketAddonsDetail implements Serializable {

    private final static long serialVersionUID = 8432264348318004386L;
    @SerializedName("ticket_Id")
    @Expose
    private Integer ticketId;
    @SerializedName("addon_id")
    @Expose
    private Integer addonId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("note")
    @Expose
    private Object note;
    @SerializedName("provider_Username")
    @Expose
    private String providerUsername;
    @SerializedName("addons_number")
    @Expose
    private Integer addonsNumber;

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getAddonId() {
        return addonId;
    }

    public void setAddonId(Integer addonId) {
        this.addonId = addonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
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

}
