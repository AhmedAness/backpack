
package com.wasltec.backpack.models.ActivityDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wasltec.backpack.models.ActivityPhoto;
import com.wasltec.backpack.models.Category;
import com.wasltec.backpack.models.Provider;
import com.wasltec.backpack.models.Review;

import java.io.Serializable;
import java.util.List;

public class ActivityDetail implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("activity_Location")
    @Expose
    private String activityLocation;
    @SerializedName("meeting_Location")
    @Expose
    private String meetingLocation;
    @SerializedName("activity_Lat")
    @Expose
    private Double activityLat;
    @SerializedName("activity_Lang")
    @Expose
    private Double activityLang;
    @SerializedName("meeting_Lat")
    @Expose
    private Double meetingLat;
    @SerializedName("meeting_Lang")
    @Expose
    private Double meetingLang;
    @SerializedName("activity_length")
    @Expose
    private Integer activityLength;
    @SerializedName("apply_discount")
    @Expose
    private Boolean applyDiscount;
    @SerializedName("bookingAvailableForGroups")
    @Expose
    private Boolean bookingAvailableForGroups;
    @SerializedName("bookingAvailableForIndividuals")
    @Expose
    private Boolean bookingAvailableForIndividuals;
    @SerializedName("isCompleted")
    @Expose
    private Boolean isCompleted;
    @SerializedName("booking_window")
    @Expose
    private int bookingWindow;
    @SerializedName("notice_in_advance")
    @Expose
    private int noticeInAdvance;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("totalCapacity")
    @Expose
    private int totalCapacity;
    @SerializedName("stepNumber")
    @Expose
    private Integer stepNumber;
    @SerializedName("activity_Photos")
    @Expose
    private List<ActivityPhoto> activityPhotos = null;
    @SerializedName("activity_Option")
    @Expose
    private List<ActivityOption> activityOption = null;
    @SerializedName("activity_Add_Ons")
    @Expose
    private List<ActivityAddOn> activityAddOns = null;
    @SerializedName("activity_Rules")
    @Expose
    private List<ActivityRule> activityRules = null;
    @SerializedName("requirements")
    @Expose
    private String requirements;
    @SerializedName("capacityIsUnlimited")
    @Expose
    private Boolean capacityIsUnlimited;
    @SerializedName("max_capacity_group")
    @Expose
    private Integer maxCapacityGroup;
    @SerializedName("min_capacity_group")
    @Expose
    private Integer minCapacityGroup;
    @SerializedName("group_price")
    @Expose
    private Integer groupPrice;
    @SerializedName("activity_Organizer")
    @Expose
    private List<ActivityOrganizer> activityOrganizer = null;
    @SerializedName("individual_Categories")
    @Expose
    private List<IndividualCategory> individualCategories = null;
    @SerializedName("reviewsCount")
    @Expose
    private Integer reviewsCount;
    @SerializedName("rate")
    @Expose
    private float rate;
    @SerializedName("firstIndividualPrice")
    @Expose
    private float firstIndividualPrice;
    @SerializedName("provider")
    @Expose
    private Provider provider;
    @SerializedName("favActivity")
    @Expose
    private Boolean favActivity;
    @SerializedName("reviews")
    @Expose
    private List<Review> reviews = null;
    @SerializedName("avaliabilities")
    @Expose
    private List<Avaliability> avaliabilities = null;
    private final static long serialVersionUID = -5215986090881197264L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivityLocation() {
        return activityLocation;
    }

    public void setActivityLocation(String activityLocation) {
        this.activityLocation = activityLocation;
    }

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }

    public Double getActivityLat() {
        return activityLat;
    }

    public void setActivityLat(Double activityLat) {
        this.activityLat = activityLat;
    }

    public Double getActivityLang() {
        return activityLang;
    }

    public void setActivityLang(Double activityLang) {
        this.activityLang = activityLang;
    }

    public Double getMeetingLat() {
        return meetingLat;
    }

    public void setMeetingLat(Double meetingLat) {
        this.meetingLat = meetingLat;
    }

    public Double getMeetingLang() {
        return meetingLang;
    }

    public void setMeetingLang(Double meetingLang) {
        this.meetingLang = meetingLang;
    }

    public Integer getActivityLength() {
        return activityLength;
    }

    public void setActivityLength(Integer activityLength) {
        this.activityLength = activityLength;
    }

    public Boolean getApplyDiscount() {
        return applyDiscount;
    }

    public void setApplyDiscount(Boolean applyDiscount) {
        this.applyDiscount = applyDiscount;
    }

    public Boolean getBookingAvailableForGroups() {
        return bookingAvailableForGroups;
    }

    public void setBookingAvailableForGroups(Boolean bookingAvailableForGroups) {
        this.bookingAvailableForGroups = bookingAvailableForGroups;
    }

    public Boolean getBookingAvailableForIndividuals() {
        return bookingAvailableForIndividuals;
    }

    public void setBookingAvailableForIndividuals(Boolean bookingAvailableForIndividuals) {
        this.bookingAvailableForIndividuals = bookingAvailableForIndividuals;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Integer getBookingWindow() {
        return bookingWindow;
    }

    public void setBookingWindow(Integer bookingWindow) {
        this.bookingWindow = bookingWindow;
    }

    public Integer getNoticeInAdvance() {
        return noticeInAdvance;
    }

    public void setNoticeInAdvance(Integer noticeInAdvance) {
        this.noticeInAdvance = noticeInAdvance;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public Integer getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(Integer stepNumber) {
        this.stepNumber = stepNumber;
    }

    public List<ActivityPhoto> getActivityPhotos() {
        return activityPhotos;
    }

    public void setActivityPhotos(List<ActivityPhoto> activityPhotos) {
        this.activityPhotos = activityPhotos;
    }

    public List<ActivityOption> getActivityOption() {
        return activityOption;
    }

    public void setActivityOption(List<ActivityOption> activityOption) {
        this.activityOption = activityOption;
    }

    public List<ActivityAddOn> getActivityAddOns() {
        return activityAddOns;
    }

    public void setActivityAddOns(List<ActivityAddOn> activityAddOns) {
        this.activityAddOns = activityAddOns;
    }

    public List<ActivityRule> getActivityRules() {
        return activityRules;
    }

    public void setActivityRules(List<ActivityRule> activityRules) {
        this.activityRules = activityRules;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public Boolean getCapacityIsUnlimited() {
        return capacityIsUnlimited;
    }

    public void setCapacityIsUnlimited(Boolean capacityIsUnlimited) {
        this.capacityIsUnlimited = capacityIsUnlimited;
    }

    public Integer getMaxCapacityGroup() {
        return maxCapacityGroup;
    }

    public void setMaxCapacityGroup(Integer maxCapacityGroup) {
        this.maxCapacityGroup = maxCapacityGroup;
    }

    public Integer getMinCapacityGroup() {
        return minCapacityGroup;
    }

    public void setMinCapacityGroup(Integer minCapacityGroup) {
        this.minCapacityGroup = minCapacityGroup;
    }

    public Integer getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(Integer groupPrice) {
        this.groupPrice = groupPrice;
    }

    public List<ActivityOrganizer> getActivityOrganizer() {
        return activityOrganizer;
    }

    public void setActivityOrganizer(List<ActivityOrganizer> activityOrganizer) {
        this.activityOrganizer = activityOrganizer;
    }

    public List<IndividualCategory> getIndividualCategories() {
        return individualCategories;
    }

    public void setIndividualCategories(List<IndividualCategory> individualCategories) {
        this.individualCategories = individualCategories;
    }

    public Integer getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(Integer reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public float getFirstIndividualPrice() {
        return firstIndividualPrice;
    }

    public void setFirstIndividualPrice(float firstIndividualPrice) {
        this.firstIndividualPrice = firstIndividualPrice;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Boolean getFavActivity() {
        return favActivity;
    }

    public void setFavActivity(Boolean favActivity) {
        this.favActivity = favActivity;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Avaliability> getAvaliabilities() {
        return avaliabilities;
    }

    public void setAvaliabilities(List<Avaliability> avaliabilities) {
        this.avaliabilities = avaliabilities;
    }

}
