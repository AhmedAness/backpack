
package com.wasltec.provider.model.booking;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingDetail {

    @SerializedName("booking_id")
    @Expose
    private int bookingId;
    @SerializedName("activity_id")
    @Expose
    private int activityId;
    @SerializedName("booking_type")
    @Expose
    private int bookingType;
    @SerializedName("full_group")
    @Expose
    private boolean fullGroup;
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
    private Object name;
    @SerializedName("booking_amount")
    @Expose
    private int bookingAmount;
    @SerializedName("is_paid")
    @Expose
    private boolean isPaid;
    @SerializedName("bookingTicketDetailsGroups")
    @Expose
    private List<BookingTicketDetail> bookingTicketDetails = null;
    @SerializedName("bookingTicketDetails")
    @Expose
    private List<BookingTicketDetail> bookingTicketDetails_individual = null;
    @SerializedName("avaliabilityPricing")
    @Expose
    private List<AvaliabilityPricing> avaliabilityPricing = null;
    @SerializedName("categoryModel")
    @Expose
    private List<CategoryModel> categoryModel = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BookingDetail() {
    }

    /**
     * 
     * @param avaliabilityPricing
     * @param activityId
     * @param bookingId
     * @param categoryModel
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
    public BookingDetail(int bookingId, int activityId, int bookingType, boolean fullGroup, String activityStart, String activityEnd, String paymentMethod, Object name, int bookingAmount, boolean isPaid, List<BookingTicketDetail> bookingTicketDetails,List<BookingTicketDetail> bookingTicketDetails_individual, List<AvaliabilityPricing> avaliabilityPricing, List<CategoryModel> categoryModel) {
        super();
        this.bookingId = bookingId;
        this.activityId = activityId;
        this.bookingType = bookingType;
        this.fullGroup = fullGroup;
        this.activityStart = activityStart;
        this.activityEnd = activityEnd;
        this.paymentMethod = paymentMethod;
        this.name = name;
        this.bookingAmount = bookingAmount;
        this.isPaid = isPaid;
        this.bookingTicketDetails = bookingTicketDetails;
        this.bookingTicketDetails_individual = bookingTicketDetails_individual;
        this.avaliabilityPricing = avaliabilityPricing;
        this.categoryModel = categoryModel;
    }

    public List<BookingTicketDetail> getBookingTicketDetails_individual() {
        return bookingTicketDetails_individual;
    }

    public void setBookingTicketDetails_individual(List<BookingTicketDetail> bookingTicketDetails_individual) {
        this.bookingTicketDetails_individual = bookingTicketDetails_individual;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
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

    public Object getname() {
        return name;
    }

    public void setname(Object name) {
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

    public List<BookingTicketDetail> getBookingTicketDetails() {
        return bookingTicketDetails;
    }

    public void setBookingTicketDetails(List<BookingTicketDetail> bookingTicketDetails) {
        this.bookingTicketDetails = bookingTicketDetails;
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
