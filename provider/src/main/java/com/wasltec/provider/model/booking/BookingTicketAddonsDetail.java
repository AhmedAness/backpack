
package com.wasltec.provider.model.booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingTicketAddonsDetail {

    @SerializedName("ticket_Id")
    @Expose
    private int ticketId;
    @SerializedName("addon_id")
    @Expose
    private int addonId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("note")
    @Expose
    private Object note;
    @SerializedName("provider_name")
    @Expose
    private String providername;
    @SerializedName("addons_number")
    @Expose
    private int addonsNumber;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BookingTicketAddonsDetail() {
    }

    /**
     * 
     * @param icon
     * @param price
     * @param ticketId
     * @param providername
     * @param name
     * @param addonsNumber
     * @param note
     * @param addonId
     */
    public BookingTicketAddonsDetail(int ticketId, int addonId, String name, String icon, int price, Object note, String providername, int addonsNumber) {
        super();
        this.ticketId = ticketId;
        this.addonId = addonId;
        this.name = name;
        this.icon = icon;
        this.price = price;
        this.note = note;
        this.providername = providername;
        this.addonsNumber = addonsNumber;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getAddonId() {
        return addonId;
    }

    public void setAddonId(int addonId) {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
        this.note = note;
    }

    public String getProvidername() {
        return providername;
    }

    public void setProvidername(String providername) {
        this.providername = providername;
    }

    public int getAddonsNumber() {
        return addonsNumber;
    }

    public void setAddonsNumber(int addonsNumber) {
        this.addonsNumber = addonsNumber;
    }

}
