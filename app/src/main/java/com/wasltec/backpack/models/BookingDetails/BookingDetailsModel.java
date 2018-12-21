
package com.wasltec.backpack.models.BookingDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BookingDetailsModel implements Serializable {

    private final static long serialVersionUID = 6719932685605725260L;
    @SerializedName("booking_id")
    @Expose
    private Integer bookingId;
    @SerializedName("activity_id")
    @Expose
    private Integer activityId;
    @SerializedName("activityName")
    @Expose
    private String activityName;
    @SerializedName("booking_type")
    @Expose
    private Integer bookingType;
    @SerializedName("full_group")
    @Expose
    private Boolean fullGroup;
    @SerializedName("activity_Start")
    @Expose
    private String activityStart;
    @SerializedName("activity_End")
    @Expose
    private String activityEnd;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("user_name")
    @Expose
    private Object userName;
    @SerializedName("booking_amount")
    @Expose
    private Integer bookingAmount;
    @SerializedName("is_paid")
    @Expose
    private Boolean isPaid;
    @SerializedName("bookingTicketDetails")
    @Expose
    private List<BookingTicketDetail> bookingTicketDetails = null;
    @SerializedName("bookingTicketDetailsGroups")
    @Expose
    private List<BookingTicketDetail> bookingTicketDetailsGroups = null;
    @SerializedName("avaliabilityPricing")
    @Expose
    private List<AvaliabilityPricing> avaliabilityPricing = null;
    @SerializedName("categoryModel")
    @Expose
    private List<CategoryModel> categoryModel = null;

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getBookingType() {
        return bookingType;
    }

    public void setBookingType(Integer bookingType) {
        this.bookingType = bookingType;
    }

    public Boolean getFullGroup() {
        return fullGroup;
    }

    public void setFullGroup(Boolean fullGroup) {
        this.fullGroup = fullGroup;
    }

    public String getActivityStart() {
        return activityStart;
    }

    public void setActivityStart(String activityStart) {
        this.activityStart = activityStart;
    }

    public String getActivityEnd() {
        return activityEnd;
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

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public Integer getBookingAmount() {
        return bookingAmount;
    }

    public void setBookingAmount(Integer bookingAmount) {
        this.bookingAmount = bookingAmount;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public List<BookingTicketDetail> getBookingTicketDetails() {
        return bookingTicketDetails;
    }

    public void setBookingTicketDetails(List<BookingTicketDetail> bookingTicketDetails) {
        this.bookingTicketDetails = bookingTicketDetails;
    }

    public List<BookingTicketDetail> getBookingTicketDetailsGroups() {
        return bookingTicketDetailsGroups;
    }

    public void setBookingTicketDetailsGroups(List<BookingTicketDetail> bookingTicketDetailsGroups) {
        this.bookingTicketDetailsGroups = bookingTicketDetailsGroups;
    }

    public List<AvaliabilityPricing> getAvaliabilityPricing() {
        return avaliabilityPricing;
    }

    public void setAvaliabilityPricing(List<AvaliabilityPricing> avaliabilityPricing) {
        this.avaliabilityPricing = avaliabilityPricing;
    }

    public List<CategoryModel> getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(List<CategoryModel> categoryModel) {
        this.categoryModel = categoryModel;
    }

}
