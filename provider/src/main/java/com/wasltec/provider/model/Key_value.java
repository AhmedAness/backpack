package com.wasltec.provider.model;

public class Key_value {

    String Key ,VAlue;

    public Key_value(String key, String VAlue) {
        Key = key;
        this.VAlue = VAlue;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getVAlue() {
        return VAlue;
    }

    public void setVAlue(String VAlue) {
        this.VAlue = VAlue;
    }
}
