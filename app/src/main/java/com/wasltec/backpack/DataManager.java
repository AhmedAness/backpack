package com.wasltec.backpack;

import com.wasltec.backpack.models.ActivityDetails.ActivityDetail;
import com.wasltec.backpack.models.ActivityTrip;
import com.wasltec.backpack.models.ItemsCategory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataManager {
    private static final DataManager ourInstance = new DataManager();
    public List<ActivityTrip> items = new ArrayList<>();
    private List<ItemsCategory> categories = new ArrayList<>();
    private boolean Families = false;
    private boolean females = false;
    private boolean groups = false;
    private boolean male = false;
    private boolean maleAndFemale = false;
    private double minPrice = 0;
    private double maxPrice = 9999999;
    private Date start;
    private Date end;
    private ActivityDetail trip;

    public ActivityDetail getTrip() {
        return trip;
    }

    public void setTrip(ActivityDetail trip) {
        this.trip = trip;
    }

    private DataManager() {
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 10);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -10);
        start = lastYear.getTime();
        end = nextYear.getTime();

    }

    public static DataManager getInstance() {
        return ourInstance;
    }

    public boolean isMaleAndFemale() {
        return maleAndFemale;
    }

    public void setMaleAndFemale(boolean maleAndFemale) {
        this.maleAndFemale = maleAndFemale;
    }

    public boolean isFamilies() {
        return Families;
    }

    public void setFamilies(boolean families) {
        Families = families;
    }

    public boolean isFemales() {
        return females;
    }

    public void setFemales(boolean females) {
        this.females = females;
    }

    public boolean isGroups() {
        return groups;
    }

    public void setGroups(boolean groups) {
        this.groups = groups;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        if (start == null) {
            final Calendar lastYear = Calendar.getInstance();
            lastYear.add(Calendar.YEAR, -10);

            this.start = lastYear.getTime();

        } else
            this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    private void clear() {
        Families = false;
        females = false;
        groups = false;
        male = false;

        minPrice = 0;
        maxPrice = 9999999;
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 10);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -10);
        start = lastYear.getTime();
        end = nextYear.getTime();

    }

    public List<ActivityTrip> getItems() {
        return items;
    }

    public void setItems(List<ActivityTrip> items) {
        this.items = items;
    }

    public List<ActivityTrip> getSpecificItems(List<Integer> categories) {
        List<ActivityTrip> items2 = new ArrayList<>();

        for (ActivityTrip item1 : items) {
            if (categories.contains(item1.getTypeId())) {
                items2.add(item1);
            }
        }
        return items2;
    }

    public List<ActivityTrip> getSpecificItems(int categoryID) {
        List<ActivityTrip> items2 = new ArrayList<>();

        for (ActivityTrip item1 : items) {
            if (item1.getTypeId() == categoryID) {
                items2.add(item1);
            }
        }
        return items2;
    }

    public List<ActivityTrip> filter() {
        List<ActivityTrip> items2 = new ArrayList<>();

        for (ActivityTrip item1 : items) {
            if (Families) {
                if (!item1.isFamily())
                    break;
            }
            if (females) {

            }
            if (groups) {

            }
            if (male) {

            }
            if (!(item1.getPrice() >= minPrice && item1.getPrice() <= maxPrice)) {
                break;
            }
            if (!(item1.getDate().before(end) && item1.getDate().after(start))) {
                break;
            }
            items2.add(item1);

        }

        return items2;
    }

    public List<ItemsCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ItemsCategory> categories) {
        this.categories = categories;
    }

    public ActivityTrip getItem(int item) {
        for (ActivityTrip item1 : items) {
            if (item1.getId() == item)
                return item1;
        }
        return null;
    }
}
