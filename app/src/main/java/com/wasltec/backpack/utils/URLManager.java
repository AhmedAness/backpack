package com.wasltec.backpack.utils;

public class URLManager {
    private static final URLManager ourInstance = new URLManager();

    //    String base_host= "http://backpackapi.repoteq.com/api/" ;
    String base_host = "http://backpackapis.wasltec.org/api/";

    public static URLManager getInstance() {
        return ourInstance;
    }

    private URLManager() {
    }
    public String get_became_activity_pro="www.google.com";
    String login = "Token";
    String mobileRegister = "customer/MobileRegisteration";
    String customerRegister = "customer/CustomerRegisteration";
    String addToFavourite = "customer/AddActivityToFavorite";
    String removeFromFavourite = "customer/RemoveActivityFromFavorite";
    String getFavourites = "customer/ListCustomerFavoriteActivities?customerid=";
    String getHomeActivities = "customer/ListHomeActivities";
    String ActivitiesByCategory = "Customer/ActivitiesByCategory?categoryId=";
    String editProfile = "customer/EditProfile";
    String ActivitiesType = "Activity/GetActivityTypes";

    String getLIstActivities = "customer/ListActivities?options";
    String ListCustomerFavoriteActivities = "customer/ListCustomerFavoriteActivities?customerid=";
    String CustomerActivitiesDetails = "customer/CustomerActivitiesDetails?activityId=";
    String send_message       =  "User/send_message?" ;

    String CustomerActivities = "customer/CustomerActivities";
    String countryCode = "Home/GetCountryCode";

    String checkVerificationId = "customer/Chk_VerificationId";
    String VerificationId = "customer/VerificationId";
    String checkUserDeclaration = "customer/Chk_UserDeclaration";
    String userDisease = "Customer/UserDisease?mode=";
    String lookUp = "Services/GetLookUp?lookUp=";
    String cust_Chat = "User/cust_Chat?ticketId=";
    String CreateBooking = "Booking/CreateBooking";
    String GetBookingDetails = "Booking/GetBookingDetails?Bookingid=";


    public String getCustomerInbox() {
        return base_host+CustomerInbox;
    }

    String CustomerInbox = "User/CustomerInbox";

    public String getActivitiesType() {
        return base_host + ActivitiesType;
    }

    public String getLogin() {
        return base_host + login;
    }

    public String getMobileRegister() {
        return base_host + mobileRegister;
    }

    public String getCustomerRegister() {
        return base_host + customerRegister;
    }

    public String getAddToFavourite() {
        return base_host + addToFavourite;
    }

    public String getRemoveFromFavourite() {
        return base_host + removeFromFavourite;
    }

    public String getGetFavourites(String customerID) {
        return base_host + getFavourites + customerID;
    }

    public String getGetHomeActivities() {
        return base_host + getHomeActivities;
    }

    public String getGetLIstActivities(String... options) {
        StringBuilder temp = new StringBuilder(base_host + getLIstActivities);
        for (int i = 0; i < options.length; i++) {
            if (i != 0)
                temp.append("," + temp);
            else
                temp.append(temp);
        }

        return temp.toString();
    }

    public String getEditProfile() {
        return base_host + editProfile;
    }

    public String getListCustomerFavoriteActivities(String customerid) {
        return base_host + ListCustomerFavoriteActivities + customerid;
    }

    public String getActivitiesByCategory(String Categoryid) {
        return base_host + ActivitiesByCategory + Categoryid;
    }

    public String getCountryCode() {
        return base_host + countryCode;
    }

    public String getCustomerActivities() {
        return base_host + CustomerActivities;
    }

    public void setListCustomerFavoriteActivities(String listCustomerFavoriteActivities) {
        ListCustomerFavoriteActivities = listCustomerFavoriteActivities;
    }

    public String getCheckVerificationId() {
        return base_host +checkVerificationId;
    }

    public String getVerificationId() {
        return base_host + VerificationId;
    }

    public String getCheckUserDeclaration() {
        return base_host +checkUserDeclaration;
    }

    public String getUserDisease(String mode) {
        return base_host +userDisease+mode;
    }

    public String getLookUp(String param) {
        return base_host + lookUp + param;
    }


    public String getCustomerActivitiesDetails(String activityId) {
        return base_host+CustomerActivitiesDetails+activityId;
    }

    public String getCust_Chat(String ticketId) {
        return base_host+cust_Chat+ticketId;
    }

    public void setCust_Chat(String cust_Chat) {
        this.cust_Chat = cust_Chat;
    }
    public String getSend_message(int chatId,int availabilityId) {
        return base_host+send_message+"chatId="+chatId+"&"+"availabilityId="+availabilityId;
    }

    public String getCreateBooking() {
        return base_host + CreateBooking;
    }

    public String getGetBookingDetails(String Bookingid) {
        return base_host + GetBookingDetails + Bookingid;
    }

    public void setGetBookingDetails(String getBookingDetails) {
        GetBookingDetails = getBookingDetails;
    }
}
