package com.wasltec.provider.model;


import java.util.ArrayList;

public class ActivityDetailsReturnObj
{




    private int id;

     private  double activity_Lat;
     private  double activity_Lang;
     private  double meeting_Lat;
     private  double meeting_Lang;
    private String title;
    private String description;
    private String activity_Location;
    private String meeting_Location;
    private boolean status;
    private int notice_in_advance;
    private int booking_window;
    private int activity_length;
    private String requirements;
    private int totalCapacity;
    private int min_capacity_group;
    private int max_capacity_group;
    private int group_price;
    private boolean apply_discount;
    private boolean bookingAvailableForIndividuals;
    private boolean bookingAvailableForGroups;
    private int stepNumber;
    private boolean isCompleted;
    public Category category;
    private ArrayList<ActivityPhoto> activity_Photos;
    private ArrayList<ActivityAddOn> activity_Add_Ons;
    private ArrayList<ActivityRule> activity_Rules;
    private ArrayList<ActivityOrganizer> activity_Organizer;////////////////////////////////////////
    private ArrayList<ActivityOption> activity_Option;
    private ArrayList<Avaliability> avaliabilities;
    private ArrayList<IndividualCategory> individual_Categories;












    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return this.title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return this.description; }

    public void setDescription(String description) { this.description = description; }

    public String getActivityLocation() { return this.activity_Location; }

    public void setActivityLocation(String activity_Location) { this.activity_Location = activity_Location; }

    public String getMeetingLocation() { return this.meeting_Location; }

    public void setMeetingLocation(String meeting_Location) { this.meeting_Location = meeting_Location; }

    public boolean getStatus() { return this.status; }

    public void setStatus(boolean status) { this.status = status; }

    public int getNoticeInAdvance() { return this.notice_in_advance; }

    public void setNoticeInAdvance(int notice_in_advance) { this.notice_in_advance = notice_in_advance; }


    public int getBookingWindow() { return this.booking_window; }

    public void setBookingWindow(int booking_window) { this.booking_window = booking_window; }

    public int getActivityLength() { return this.activity_length; }

    public void setActivityLength(int activity_length) { this.activity_length = activity_length; }


    public String getRequirements() { return this.requirements; }

    public void setRequirements(String requirements) { this.requirements = requirements; }


    public int getTotalCapacity() { return this.totalCapacity; }

    public void setTotalCapacity(int totalCapacity) { this.totalCapacity = totalCapacity; }


    public int getMinCapacityGroup() { return this.min_capacity_group; }

    public void setMinCapacityGroup(int min_capacity_group) { this.min_capacity_group = min_capacity_group; }

    public int getMaxCapacityGroup() { return this.max_capacity_group; }

    public void setMaxCapacityGroup(int max_capacity_group) { this.max_capacity_group = max_capacity_group; }



    public int getGroupPrice() { return this.group_price; }

    public void setGroupPrice(int group_price) { this.group_price = group_price; }


    public boolean getApplyDiscount() { return this.apply_discount; }

    public void setApplyDiscount(boolean apply_discount) { this.apply_discount = apply_discount; }

    public boolean getBookingAvailableForIndividuals() { return this.bookingAvailableForIndividuals; }

    public void setBookingAvailableForIndividuals(boolean bookingAvailableForIndividuals) { this.bookingAvailableForIndividuals = bookingAvailableForIndividuals; }


    public boolean getBookingAvailableForGroups() { return this.bookingAvailableForGroups; }

    public void setBookingAvailableForGroups(boolean bookingAvailableForGroups) { this.bookingAvailableForGroups = bookingAvailableForGroups; }

    public int getStepNumber() { return this.stepNumber; }

    public void setStepNumber(int stepNumber) { this.stepNumber = stepNumber; }




    public boolean getIsCompleted() { return this.isCompleted; }

    public void setIsCompleted(boolean isCompleted) { this.isCompleted = isCompleted; }

    public Category getCategory() { return this.category; }

    public void setCategory(Category category) { this.category = category; }

    public ArrayList<ActivityPhoto> getActivityPhotos() { return this.activity_Photos; }

    public void setActivityPhotos(ArrayList<ActivityPhoto> activity_Photos) { this.activity_Photos = activity_Photos; }

    public ArrayList<ActivityAddOn> getActivityAddOns() { return this.activity_Add_Ons; }

    public void setActivityAddOns(ArrayList<ActivityAddOn> activity_Add_Ons) { this.activity_Add_Ons = activity_Add_Ons; }





    public ArrayList<ActivityRule> getActivityRules() { return this.activity_Rules; }

    public void setActivityRules(ArrayList<ActivityRule> activity_Rules) { this.activity_Rules = activity_Rules; }

    public ArrayList<ActivityOrganizer> getActivity_Organizer() {
        return activity_Organizer;
    }

    public void setActivity_Organizer(ArrayList<ActivityOrganizer> activity_Organizer) {
        this.activity_Organizer = activity_Organizer;
    }

    public ArrayList<ActivityOption> getActivityOption() { return this.activity_Option; }

    public void setActivityOption(ArrayList<ActivityOption> activity_Option) { this.activity_Option = activity_Option; }

    public ArrayList<Avaliability> getAvaliabilities() { return this.avaliabilities; }

    public void setAvaliabilities(ArrayList<Avaliability> avaliabilities) { this.avaliabilities = avaliabilities; }

    public ArrayList<IndividualCategory> getIndividualCategories() { return this.individual_Categories; }

    public void setIndividualCategories(ArrayList<IndividualCategory> individual_Categories) { this.individual_Categories = individual_Categories; }

    public double getActivity_Lat() {
        return activity_Lat;
    }

    public void setActivity_Lat(double activity_Lat) {
        this.activity_Lat = activity_Lat;
    }

    public double getActivity_Lang() {
        return activity_Lang;
    }

    public void setActivity_Lang(double activity_Lang) {
        this.activity_Lang = activity_Lang;
    }

    public double getMeeting_Lat() {
        return meeting_Lat;
    }

    public void setMeeting_Lat(double meeting_Lat) {
        this.meeting_Lat = meeting_Lat;
    }

    public double getMeeting_Lang() {
        return meeting_Lang;
    }

    public void setMeeting_Lang(double meeting_Lang) {
        this.meeting_Lang = meeting_Lang;
    }
}