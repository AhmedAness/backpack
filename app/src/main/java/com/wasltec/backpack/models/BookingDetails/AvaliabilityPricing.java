
package com.wasltec.backpack.models.BookingDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AvaliabilityPricing implements Serializable {

    private final static long serialVersionUID = -2065191641815443185L;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("individualCategoryId")
    @Expose
    private Integer individualCategoryId;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("priceAfterDiscount")
    @Expose
    private Integer priceAfterDiscount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIndividualCategoryId() {
        return individualCategoryId;
    }

    public void setIndividualCategoryId(Integer individualCategoryId) {
        this.individualCategoryId = individualCategoryId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public void setPriceAfterDiscount(Integer priceAfterDiscount) {
        this.priceAfterDiscount = priceAfterDiscount;
    }

}
