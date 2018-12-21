package com.wasltec.provider.model;

public class Price {
    int id ;
    String category;
    String price;

    public Price(String category, String price) {
        id =0;
        this.category = category;
        this.price = price;
    }
    public Price(String category, String price,int id ) {
        this.category = category;
        this.price = price;
        this.id= id ;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
