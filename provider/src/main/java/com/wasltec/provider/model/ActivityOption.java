package com.wasltec.provider.model;

public class ActivityOption
{
    private int id;

    private String name;
    private  int fromAge ;
    private  int  toAge ;

    public ActivityOption(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }


    public int getFromAge() {
        return fromAge;
    }

    public void setFromAge(int fromAge) {
        this.fromAge = fromAge;
    }

    public int getToAge() {
        return toAge;
    }

    public void setToAge(int toAge) {
        this.toAge = toAge;
    }
}