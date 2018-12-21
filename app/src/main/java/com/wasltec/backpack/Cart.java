package com.wasltec.backpack;

import com.wasltec.backpack.activities.DetailsActivity;
import com.wasltec.backpack.models.ActivityDetails.Avaliability;
import com.wasltec.backpack.models.BookingTicket;
import com.wasltec.backpack.models.Category;
import com.wasltec.backpack.models.KeyValue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private static final com.wasltec.backpack.Cart ourInstance = new com.wasltec.backpack.Cart();
    int adultCount, childCount;
    int bookingid;
    Date Start;
    Date end;
    String total_date;
    Avaliability avaliablityID;
    int avaliabilitiesCount=0;
    List<BookingTicket> tickets;
    boolean isgroub = false;
    float total_price;
    ArrayList<KeyValue> categores = new ArrayList<>();
    Map<Integer, Integer> group_addons;

    public List<BookingTicket> getTickets() {
        return tickets;
    }

    public void setTickets(List<BookingTicket> tickets) {
        this.tickets = tickets;
    }

    public int getAvaliabilitiesCount() {
        return avaliabilitiesCount;
    }

    public void setAvaliabilitiesCount(int avaliabilitiesCount) {
        this.avaliabilitiesCount = avaliabilitiesCount;
    }

    public Avaliability getAvaliablityID() {
        return avaliablityID;
    }

    public void setAvaliablityID(Avaliability avaliablityID) {
        this.avaliablityID = avaliablityID;
    }

    public static Cart getOurInstance() {
        return ourInstance;
    }

    public String getTotal_date() {
        return total_date;
    }

    public void setTotal_date(String total_date) {
        this.total_date = total_date;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    private Cart() {
        group_addons = new HashMap<>();
    }

    public static com.wasltec.backpack.Cart getInstance() {
        return ourInstance;
    }

    public int getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public Date getStart() {
        return Start;
    }

    public void setStart(Date start) {
        Start = start;
    }


    public boolean isIsgroub() {
        return isgroub;
    }

    public void setIsgroub(boolean isgroub) {
        this.isgroub = isgroub;
    }

    public Map<Integer, Integer> getGroup_addons() {
        return group_addons;
    }

    public void setGroup_addons(Map<Integer, Integer> group_addons) {
        this.group_addons = group_addons;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }


    public ArrayList<KeyValue> getCategories() {
        categores = new ArrayList<>();
        for (int i = 0; i < Cart.getInstance().getTickets().size(); i++) {
            int id = Cart.getInstance().getTickets().get(i).getCategoryId();
                int j;
                for(j=0; j<categores.size(); j++)
                    if(categores.get(j).getKey() == id)
                        break;
            if(j != categores.size()) {
                int tmp = categores.get(j).getValue();
                tmp++;
                categores.get(j).setValue(tmp);
            } else {
                KeyValue keyValue = new KeyValue();
                keyValue.setKey(id);
                keyValue.setValue(1);
                categores.add(keyValue);
            }
        }
        return categores;

//        categores.clear();
//        for (int i = 0; i < Cart.getInstance().getTickets().size(); i++) {
//            int id = Cart.getInstance().getTickets().get(i).getCategoryId();
//            if (categores.containsKey(id)) {
//                int tmp = categores.get(id);
//                tmp++;
//                categores.put(id, tmp);
//            } else
//                categores.put(id, 1);
//        }
//        return categores;
    }

    public int getBookingid() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }
}
