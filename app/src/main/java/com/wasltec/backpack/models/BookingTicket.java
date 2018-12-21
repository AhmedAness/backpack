
package com.wasltec.backpack.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingTicket {

    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("nameOfGroup")
    @Expose
    private String nameOfGroup;

    @SerializedName("numOfGroup")
    @Expose
    private String numOfGroup;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("primaryTicket")
    @Expose
    private boolean primaryTicket;
    @SerializedName("ticket_reviewd")
    @Expose
    private boolean ticketReviewd;
    @SerializedName("ticket_checked_in")
    @Expose
    private boolean ticketCheckedIn;
    @SerializedName("ticket_cancelled")
    @Expose
    private boolean ticketCancelled;
    @SerializedName("category_id")
    @Expose
    private int categoryId;
    @SerializedName("Booking_Ticket_AddonsModel")
    @Expose
    private List<BookingTicketAddonsModel> bookingTicketAddonsModel = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BookingTicket() {
    }

    /**
     * 
     * @param ticketReviewd
     * @param mail
     * @param categoryId
     * @param ticketCheckedIn
     * @param name
     * @param primaryTicket
     * @param ticketCancelled
     * @param bookingTicketAddonsModel
     * @param mobile
     */
    public BookingTicket(String name, String mail, String mobile, boolean primaryTicket, boolean ticketReviewd, boolean ticketCheckedIn, boolean ticketCancelled, int categoryId, List<BookingTicketAddonsModel> bookingTicketAddonsModel) {
        super();
        this.name = name;
        this.mail = mail;
        this.mobile = mobile;
        this.primaryTicket = primaryTicket;
        this.ticketReviewd = ticketReviewd;
        this.ticketCheckedIn = ticketCheckedIn;
        this.ticketCancelled = ticketCancelled;
        this.categoryId = categoryId;
        this.bookingTicketAddonsModel = bookingTicketAddonsModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isPrimaryTicket() {
        return primaryTicket;
    }

    public void setPrimaryTicket(boolean primaryTicket) {
        this.primaryTicket = primaryTicket;
    }

    public boolean isTicketReviewd() {
        return ticketReviewd;
    }

    public void setTicketReviewd(boolean ticketReviewd) {
        this.ticketReviewd = ticketReviewd;
    }

    public boolean isTicketCheckedIn() {
        return ticketCheckedIn;
    }

    public void setTicketCheckedIn(boolean ticketCheckedIn) {
        this.ticketCheckedIn = ticketCheckedIn;
    }

    public boolean isTicketCancelled() {
        return ticketCancelled;
    }

    public void setTicketCancelled(boolean ticketCancelled) {
        this.ticketCancelled = ticketCancelled;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public List<BookingTicketAddonsModel> getBookingTicketAddonsModel() {
        return bookingTicketAddonsModel;
    }

    public void setBookingTicketAddonsModel(List<BookingTicketAddonsModel> bookingTicketAddonsModel) {
        this.bookingTicketAddonsModel = bookingTicketAddonsModel;
    }

    public String getNameOfGroup() {
        return nameOfGroup;
    }

    public void setNameOfGroup(String nameOfGroup) {
        this.nameOfGroup = nameOfGroup;
    }

    public String getNumOfGroup() {
        return numOfGroup;
    }

    public void setNumOfGroup(String numOfGroup) {
        this.numOfGroup = numOfGroup;
    }
}
