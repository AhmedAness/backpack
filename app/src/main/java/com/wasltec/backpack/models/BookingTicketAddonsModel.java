
package com.wasltec.backpack.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingTicketAddonsModel {

    @SerializedName("Addons_id")
    @Expose
    private String addonsId;
    @SerializedName("count")
    @Expose
    private String count;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BookingTicketAddonsModel() {
    }

    /**
     * 
     * @param addonsId
     */
    public BookingTicketAddonsModel(String addonsId) {
        super();
        this.addonsId = addonsId;
    }

    public String getAddonsId() {
        return addonsId;
    }

    public void setAddonsId(String addonsId) {
        this.addonsId = addonsId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
