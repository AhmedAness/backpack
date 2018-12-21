
package com.wasltec.provider.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
@Parcel
public class Reservation
{

    @SerializedName("booking_id")

    @Expose
    public int booking_id;
    @SerializedName("activity_id")
    @Expose
    public int activityId;
    @SerializedName("booking_type")
    @Expose
    public int bookingType;
    @SerializedName("full_group")
    @Expose
    public boolean fullGroup;
    @SerializedName("activity_Start")
    @Expose
    public String activityStart;
    @SerializedName("activity_End")
    @Expose
    public String activityEnd;
    @SerializedName("payment_method")
    @Expose
    public String paymentMethod;
    @SerializedName("user_name")
    @Expose
    public String name;
    @SerializedName("booking_amount")
    @Expose
    public int bookingAmount;
    @SerializedName("is_paid")
    @Expose
    public boolean isPaid;
    @SerializedName("remainingTickets")
    @Expose
    public int remainingTickets;
    @SerializedName("reservationTicketDetails")
    @Expose
    public ArrayList<BookingTicketDetail> bookingTicketDetails = null;



    /**
     * No args constructor for use in serialization
     * 
     */
    public Reservation() {
    }

    /**
     * 
     * @param activityId
     * @param id
     * @param bookingAmount
     * @param fullGroup
     * @param bookingTicketDetails
     * @param bookingType
     * @param name
     * @param activityEnd
     * @param isPaid
     * @param paymentMethod
     * @param activityStart
     */
    public Reservation(int id, int activityId, int bookingType, boolean fullGroup, String activityStart, String activityEnd, String paymentMethod, String name, int bookingAmount, boolean isPaid,int remainingTickets, ArrayList<BookingTicketDetail> bookingTicketDetails) {
        super();
        this.booking_id = id;
        this.activityId = activityId;
        this.bookingType = bookingType;
        this.fullGroup = fullGroup;
        this.activityStart = activityStart;
        this.activityEnd = activityEnd;
        this.paymentMethod = paymentMethod;
        this.name = name;
        this.bookingAmount = bookingAmount;
        this.isPaid = isPaid;
        this.remainingTickets = remainingTickets;
        this.bookingTicketDetails = bookingTicketDetails;
    }

    public int getRemainingTickets() {
        return remainingTickets;
    }

    public void setRemainingTickets(int remainingTickets) {
        this.remainingTickets = remainingTickets;
    }

    public int getId() {
        return booking_id;
    }

    public void setId(int id) {
        this.booking_id = id;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getBookingType() {
        return bookingType;
    }

    public void setBookingType(int bookingType) {
        this.bookingType = bookingType;
    }

    public boolean isFullGroup() {
        return fullGroup;
    }

    public void setFullGroup(boolean fullGroup) {
        this.fullGroup = fullGroup;
    }

    public String getActivityStart() {

        try {

            if (activityStart.contains("T"))
              return activityStart.replace("T"," ");
            else
                return activityStart;
        }
        catch (Exception e){
            return "";
        }

    }

    public void setActivityStart(String activityStart) {
        this.activityStart = activityStart;
    }

    public String getActivityEnd() {
        return activityEnd.replace("T"," ");
    }

    public void setActivityEnd(String activityEnd) {
        this.activityEnd = activityEnd;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public int getBookingAmount() {
        return bookingAmount;
    }

    public void setBookingAmount(int bookingAmount) {
        this.bookingAmount = bookingAmount;
    }

    public boolean isIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public ArrayList<BookingTicketDetail> getBookingTicketDetails() {
        return bookingTicketDetails;
    }

    public void setBookingTicketDetails(ArrayList<BookingTicketDetail> bookingTicketDetails) {
        this.bookingTicketDetails = bookingTicketDetails;
    }



}
