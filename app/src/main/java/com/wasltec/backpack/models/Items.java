package com.wasltec.backpack.models;

public class Items {
    String text;
    float price;

    public Items(String text, float price) {
        this.text = text;
        this.price = price;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getPrice() {
        return Float.valueOf(price);
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
