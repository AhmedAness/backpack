package com.wasltec.backpack.models;

import java.util.List;

public class SectionItem<T> {
    String label;
    List<T> activities;
    int category;
    int more;

    public SectionItem(String label, List<T> items, int category) {
        this.label = label;
        this.activities = items;
        this.category = category;
    }

    public SectionItem(String label, List<T> items) {
        this.label = label;
        this.activities = items;
    }

    public int getCategory() {
        return category;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<T> getActivities() {
        return activities;
    }

    public void setActivities(List<T> activities) {
        this.activities = activities;
    }

    public int getMore() {
        return more;
    }

    public void setMore(int more) {
        this.more = more;
    }
}
