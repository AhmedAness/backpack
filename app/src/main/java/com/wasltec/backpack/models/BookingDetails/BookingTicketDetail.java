
package com.wasltec.backpack.models.BookingDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BookingTicketDetail implements Serializable {

    private final static long serialVersionUID = -1200741836220830958L;
    @SerializedName("ticket_Id")
    @Expose
    private Integer ticketId;
    @SerializedName("ticket_number")
    @Expose
    private Long ticketNumber;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("primaryTicket")
    @Expose
    private Boolean primaryTicket;
    @SerializedName("ticket_reviewd")
    @Expose
    private Boolean ticketReviewd;
    @SerializedName("ticket_checked_in")
    @Expose
    private Boolean ticketCheckedIn;
    @SerializedName("ticket_cancelled")
    @Expose
    private Boolean ticketCancelled;
    @SerializedName("user_verified")
    @Expose
    private Boolean userVerified;
    @SerializedName("isGroupTicket")
    @Expose
    private Boolean isGroupTicket;
    @SerializedName("numOfGroup")
    @Expose
    private Integer numOfGroup;
    @SerializedName("nameOfGroup")
    @Expose
    private Object nameOfGroup;
    @SerializedName("booking_Ticket_AddonsDetails")
    @Expose
    private List<BookingTicketAddonsDetail> bookingTicketAddonsDetails = null;

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Long getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(Long ticketNumber) {
        this.ticketNumber = ticketNumber;
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

    public Boolean getPrimaryTicket() {
        return primaryTicket;
    }

    public void setPrimaryTicket(Boolean primaryTicket) {
        this.primaryTicket = primaryTicket;
    }

    public Boolean getTicketReviewd() {
        return ticketReviewd;
    }

    public void setTicketReviewd(Boolean ticketReviewd) {
        this.ticketReviewd = ticketReviewd;
    }

    public Boolean getTicketCheckedIn() {
        return ticketCheckedIn;
    }

    public void setTicketCheckedIn(Boolean ticketCheckedIn) {
        this.ticketCheckedIn = ticketCheckedIn;
    }

    public Boolean getTicketCancelled() {
        return ticketCancelled;
    }

    public void setTicketCancelled(Boolean ticketCancelled) {
        this.ticketCancelled = ticketCancelled;
    }

    public Boolean getUserVerified() {
        return userVerified;
    }

    public void setUserVerified(Boolean userVerified) {
        this.userVerified = userVerified;
    }

    public Boolean getIsGroupTicket() {
        return isGroupTicket;
    }

    public void setIsGroupTicket(Boolean isGroupTicket) {
        this.isGroupTicket = isGroupTicket;
    }

    public Integer getNumOfGroup() {
        return numOfGroup;
    }

    public void setNumOfGroup(Integer numOfGroup) {
        this.numOfGroup = numOfGroup;
    }

    public Object getNameOfGroup() {
        return nameOfGroup;
    }

    public void setNameOfGroup(Object nameOfGroup) {
        this.nameOfGroup = nameOfGroup;
    }

    public List<BookingTicketAddonsDetail> getBookingTicketAddonsDetails() {
        return bookingTicketAddonsDetails;
    }

    public void setBookingTicketAddonsDetails(List<BookingTicketAddonsDetail> bookingTicketAddonsDetails) {
        this.bookingTicketAddonsDetails = bookingTicketAddonsDetails;
    }

}
