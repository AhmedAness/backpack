
package com.wasltec.provider.model.booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryModel {

    @SerializedName("category_id")
    @Expose
    private int categoryId;
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private int price;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CategoryModel() {
    }

    /**
     * 
     * @param price
     * @param count
     * @param name
     * @param categoryId
     */
    public CategoryModel(int categoryId, int count, String name, int price) {
        super();
        this.categoryId = categoryId;
        this.count = count;
        this.name = name;
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
