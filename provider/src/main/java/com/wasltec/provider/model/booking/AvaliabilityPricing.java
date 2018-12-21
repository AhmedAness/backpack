
package com.wasltec.provider.model.booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvaliabilityPricing {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("individualCategoryId")
    @Expose
    private int individualCategoryId;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("priceAfterDiscount")
    @Expose
    private int priceAfterDiscount;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AvaliabilityPricing() {
    }

    /**
     * 
     * @param id
     * @param priceAfterDiscount
     * @param price
     * @param individualCategoryId
     */
    public AvaliabilityPricing(int id, int individualCategoryId, int price, int priceAfterDiscount) {
        super();
        this.id = id;
        this.individualCategoryId = individualCategoryId;
        this.price = price;
        this.priceAfterDiscount = priceAfterDiscount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndividualCategoryId() {
        return individualCategoryId;
    }

    public void setIndividualCategoryId(int individualCategoryId) {
        this.individualCategoryId = individualCategoryId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public void setPriceAfterDiscount(int priceAfterDiscount) {
        this.priceAfterDiscount = priceAfterDiscount;
    }

}
