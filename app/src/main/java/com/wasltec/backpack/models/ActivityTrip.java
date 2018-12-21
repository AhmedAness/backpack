
package com.wasltec.backpack.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wasltec.backpack.DataManager;
import com.wasltec.backpack.models.ActivityDetails.ActivityAddOn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityTrip {


    @SerializedName("id")
    @Expose
    private int id;
     @SerializedName("reviewsCount")
    @Expose
    private int reviewsCount;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("rate")
    @Expose
    private float rate;
    @SerializedName("price")
    @Expose
    private float price;
    @SerializedName("categoryId")
    @Expose
    private int categoryId;

    @SerializedName("group_price")
    @Expose
    private int group_price;

    @SerializedName("activity_Lang")
    @Expose
    private float activity_Lang;

    @SerializedName("activity_Lat")
    @Expose
    private float activity_Lat;





    @SerializedName("activityPhoto_Url")
    @Expose
    private String cover;
    @SerializedName("type_id")
    @Expose
    private int typeId;
    @SerializedName("num_of_ratings")
    @Expose
    private int numOfRatings;
    @SerializedName("provider")
    @Expose
    private String provider;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("location_country")
    @Expose
    private String locationCountry;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("place")
    @Expose
    private Place place;
    @SerializedName("addones")
    @Expose
    private List<ActivityAddOn> addones = new ArrayList<>();
    @SerializedName("provider_img")
    @Expose
    private String providerImg;
    @SerializedName("isFamily")
    @Expose
    private boolean isFamily;


    @SerializedName("maleFemale")
    @Expose
    private boolean maleFemale;

    @SerializedName("favActivity")
    @Expose
    private boolean favActivity;


    @SerializedName("group")
    @Expose
    private boolean group;
    @SerializedName("reviews")
    @Expose

    private List<Review> reviews = new ArrayList<>();
    @SerializedName("similar_acitivity")
    @Expose
    private List<ActivityTrip> similarAcitivity = new ArrayList<>();

    @SerializedName("remaining_tickets")
    @Expose
    private int remaining_tickets;


    @SerializedName("times")
    @Expose
    private List<Times> startTime;

    public List<Times> getStartTime() {
        return startTime;
    }

    public void setStartTime(List<Times> startTime) {
        this.startTime = startTime;
    }

    @SerializedName("date")
    @Expose
    private Date date = new Date();

    public boolean isFamily() {
        return isFamily;
    }

    public void setFamily(boolean family) {
        isFamily = family;
    }


    public int getRemaining_tickets() {
        return remaining_tickets;
    }

    public void setRemaining_tickets(int remaining_tickets) {
        this.remaining_tickets = remaining_tickets;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getNumOfRatings() {
        return numOfRatings;
    }

    public void setNumOfRatings(int numOfRatings) {
        this.numOfRatings = numOfRatings;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationCountry() {
        return locationCountry;
    }

    public void setLocationCountry(String locationCountry) {
        this.locationCountry = locationCountry;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public List<ActivityAddOn> getAddones() {
        return addones;
    }

    public void setAddones(List<ActivityAddOn> addones) {
        this.addones = addones;
    }

    public String getProviderImg() {
        return providerImg;
    }

    public void setProviderImg(String providerImg) {
        this.providerImg = providerImg;
    }

    public boolean isIsFamily() {
        return isFamily;
    }

    public void setIsFamily(boolean isFamily) {
        this.isFamily = isFamily;
    }

    public boolean isMaleFemale() {
        return maleFemale;
    }

    public void setMaleFemale(boolean maleFemale) {
        this.maleFemale = maleFemale;
    }

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<ActivityTrip> getSimilarAcitivity() {
        return DataManager.getInstance().getSpecificItems(typeId);
    }

    public void setSimilarAcitivity(List<ActivityTrip> similarAcitivity) {
        this.similarAcitivity = similarAcitivity;
    }

    public List<String> getRules() {
        List<String> strings = new ArrayList<>();
        strings.add("No Drugs");
        strings.add("Transportation level at 3 Pm");
        strings.add("No Drugs");
        strings.add("meeting Assembly at 3 Pm");
        return strings;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(int reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    public int getGroup_price() {
        return group_price;
    }

    public void setGroup_price(int group_price) {
        this.group_price = group_price;
    }

    public float getActivity_Lang() {
        return activity_Lang;
    }

    public void setActivity_Lang(float activity_Lang) {
        this.activity_Lang = activity_Lang;
    }

    public float getActivity_Lat() {
        return activity_Lat;
    }

    public void setActivity_Lat(float activity_Lat) {
        this.activity_Lat = activity_Lat;
    }

    public boolean isFavActivity() {
        return favActivity;
    }

    public void setFavActivity(boolean favActivity) {
        this.favActivity = favActivity;
    }
}
