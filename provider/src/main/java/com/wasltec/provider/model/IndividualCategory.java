package com.wasltec.provider.model;

public class IndividualCategory
{
    private int id;
    private String name;
    private int price;
    private int priceafterdiscout;
    private int capacity;


    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public int getPrice() { return this.price; }

    public void setPrice(int price) { this.price = price; }


    public int getPriceafterdiscout() { return this.priceafterdiscout; }

    public void setPriceafterdiscout(int priceafterdiscout) { this.priceafterdiscout = priceafterdiscout; }

    public int getCapacity() { return this.capacity; }

    public void setCapacity(int capacity) { this.capacity = capacity; }
}
