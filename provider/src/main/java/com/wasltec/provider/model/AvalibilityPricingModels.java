package com.wasltec.provider.model;

public class AvalibilityPricingModels {

    public int individualCategoryId
            ,price
            ,priceAfterDiscount;

    public AvalibilityPricingModels(int individualCategoryId, int price, int priceAfterDiscount) {
        this.individualCategoryId = individualCategoryId;
        this.price = price;
        this.priceAfterDiscount = priceAfterDiscount;
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
