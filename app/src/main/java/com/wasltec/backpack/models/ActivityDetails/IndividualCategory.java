
package com.wasltec.backpack.models.ActivityDetails;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IndividualCategory implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("priceafterdiscout")
    @Expose
    private Integer priceafterdiscout;
    @SerializedName("capacity")
    @Expose
    private Integer capacity;
    private final static long serialVersionUID = 4974559515792859676L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPriceafterdiscout() {
        return priceafterdiscout;
    }

    public void setPriceafterdiscout(Integer priceafterdiscout) {
        this.priceafterdiscout = priceafterdiscout;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

}
