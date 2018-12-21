package com.wasltec.provider.model;

public class Price_per_individual {
    String name;
    double price;
    int id=0;
    double price_after_discount;
    int capacity;

    public Price_per_individual(String name, double price, int id, double price_after_discount, int capacity) {
        this.name = name;
        this.price = price;
        this.id = id;
        this.price_after_discount = price_after_discount;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice_after_discount() {
        return price_after_discount;
    }

    public void setPrice_after_discount(double price_after_discount) {
        this.price_after_discount = price_after_discount;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
