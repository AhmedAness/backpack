
package com.wasltec.provider.model.booking;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingTicketDetail {

    @SerializedName("ticket_Id")
    @Expose
    private int ticketId;
    @SerializedName("ticket_number")
    @Expose
    private String ticketNumber;
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
    @SerializedName("user_verified")
    @Expose
    private boolean userVerified;
    @SerializedName("isGroupTicket")
    @Expose
    private boolean isGroupTicket;
    @SerializedName("numOfGroup")
    @Expose
    private int numOfGroup;
    @SerializedName("nameOfGroup")
    @Expose
    private Object nameOfGroup;
    @SerializedName("booking_Ticket_AddonsDetailsGroups")
    @Expose
    private List<BookingTicketAddonsDetail> bookingTicketAddonsDetails = null;
    @SerializedName("booking_Ticket_AddonsDetails")
    @Expose
    private List<BookingTicketAddonsDetail> bookingTicketAddonsDetails_individual = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BookingTicketDetail() {
    }

    /**
     * 
     * @param mail
     * @param nameOfGroup
     * @param primaryTicket
     * @param ticketNumber
     * @param isGroupTicket
     * @param ticketCancelled
     * @param bookingTicketAddonsDetails
     * @param ticketReviewd
     * @param numOfGroup
     * @param ticketId
     * @param name
     * @param ticketCheckedIn
     * @param userVerified
     * @param mobile
     */
    public BookingTicketDetail(int ticketId, String ticketNumber, String name, String mail, String mobile, boolean primaryTicket, boolean ticketReviewd, boolean ticketCheckedIn, boolean ticketCancelled, boolean userVerified, boolean isGroupTicket, int numOfGroup, Object nameOfGroup, List<BookingTicketAddonsDetail> bookingTicketAddonsDetails, List<BookingTicketAddonsDetail> bookingTicketAddonsDetails_individual) {
        super();
        this.ticketId = ticketId;
        this.ticketNumber = ticketNumber;
        this.name = name;
        this.mail = mail;
        this.mobile = mobile;
        this.primaryTicket = primaryTicket;
        this.ticketReviewd = ticketReviewd;
        this.ticketCheckedIn = ticketCheckedIn;
        this.ticketCancelled = ticketCancelled;
        this.userVerified = userVerified;
        this.isGroupTicket = isGroupTicket;
        this.numOfGroup = numOfGroup;
        this.nameOfGroup = nameOfGroup;
        this.bookingTicketAddonsDetails = bookingTicketAddonsDetails;
        this.bookingTicketAddonsDetails_individual = bookingTicketAddonsDetails_individual;
    }

    public List<BookingTicketAddonsDetail> getBookingTicketAddonsDetails_individual() {
        return bookingTicketAddonsDetails_individual;
    }

    public void setBookingTicketAddonsDetails_individual(List<BookingTicketAddonsDetail> bookingTicketAddonsDetails_individual) {
        this.bookingTicketAddonsDetails_individual = bookingTicketAddonsDetails_individual;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String  getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
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

    public boolean isUserVerified() {
        return userVerified;
    }

    public void setUserVerified(boolean userVerified) {
        this.userVerified = userVerified;
    }

    public boolean isIsGroupTicket() {
        return isGroupTicket;
    }

    public void setIsGroupTicket(boolean isGroupTicket) {
        this.isGroupTicket = isGroupTicket;
    }

    public int getNumOfGroup() {
        return numOfGroup;
    }

    public void setNumOfGroup(int numOfGroup) {
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
