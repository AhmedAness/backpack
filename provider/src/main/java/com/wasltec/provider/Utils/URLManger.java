package com.wasltec.provider.Utils;

import android.util.Log;

import com.wasltec.provider.Activities.Calender;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class URLManger {
    private static final URLManger ourInstance = new URLManger();

//    String base_host= "http://backpackapi.repoteq.com/api/" ;
    String base_host= "http://backpackapis.wasltec.org/api/" ;
    String Login =  "Token" ;

    String addStep1 =  "Activity/Create_Activity?activityid=" ;
    String addStep2 =  "Activity/Create_ActivityPhoto?activityId=" ;
    String addStep3 =  "Activity/Create_ActivityAddons?activityId=" ;
    String addStep4 =  "Activity/Create_ActivityLocation?activityId=" ;
    String addStep5 =  "Activity/Create_ActivityRules?activityId=" ;
    String addStep6 =  "Activity/Create_BookingPrefrence?activityId=" ;
    String addStep7 =  "Activity/Create_ActivityAvailabilty?activityId=" ;
//    String addStep8 =  "Activity/Create_ActivityPricing?activityId=" ;
    String addStep8 =  "Activity/Create_ActivityLength?activityId=" ;
//    String addStep9 =  "Activity/Activity/Create_ActivityPricing?activityId=" ;
    String addStep9 =  "Activity/Activity/Create_Activity?activityid=" ;
    String ReportReview =  "/Reviews/ReportReview?reviewid=" ;
    String ReviewReplies =  "Reviews/ReviewReplies?reviewid=" ;
    String GetAvailablility       =  "Activity/GetAvailablility" ;
    String GetAllActivities =  "Activity/GetAllActivities" ;
    String UpcomingActivity =  "Activity/UpcomingActivity" ;
    String GetActivityTypes =  "Activity/GetActivityTypes" ;
    String GetOptions       =  "Activity/GetOptions" ;
    String ChangeStatus       =  "Activity/ChangeStatus?activityId=" ;
    String sendMessage       =  "Booking/sendMessage?ticketid=" ;
    String Inbox       =  "User/Inbox" ;
    String User_Chat       =  "User/User_Chat?chatId=" ;
    String send_message       =  "User/send_message?" ;
    String EditProfile       =  "customer/EditProfile" ;
    String DeleteActivity       =  "Activity/DeleteActivity?id=" ;


    String createIndividualCategory =  "Activity/createIndividualCategory";
    String createAddone="Add_ons/CreateAdd_Ons";
    String createRule="Rule/CreateRule";

    String GetReservations       =  "Booking/GetReservations" ;
    String CreateBooking       =  "Booking/CreateBooking" ;
    String Checkin_Ticket       =  "Booking/Checkin_Ticket?ticketid=" ;
    String User_Verified_Ticket =  "Booking/User_Verified_Ticket?ticketid=" ;
    String Cancel_Ticket       =  "Booking/Cancel_Ticket?ticketid=" ;
    String BookingPaid       =  "Booking/BookingPaid" ;
    String GetBookingDetails       =  "Booking/GetBookingDetails?Bookingid=" ;



    String Create_ActivityPricing       =  "Activity/Create_ActivityPricing?activityId=" ;


    String GetAllAdd_Ons    =  "add_ons/GetAllAdd_Ons" ;
    String GetOrganizerTypes = "User/GetOrganizerTypes" ;
    String GetActivityReview =  "Activity/GetActivityReview?activityid=" ;
    String GetActivityDetails =  "Activity/GetActivityDetails?activityid=" ;
    String GetAllRules =  "Rule/GetAllRules" ;
    String addOrginizer =  "Activity/Create_ActivityOrganizers?activityId=" ;
    String addAvalibilty="Activity/AddAvailablility";

    String IndividualCategory="Activity/GetIndividualCategory?activityid=";

    String Msg="Booking/sendMessage?activityid=";
    String sendMessageForAll="Booking/sendMessageForAll?availabilityId=";



    String CalenderReservation="Booking/CalenderReservation?activityid=";
    String CalenarAvailablility="Activity/CalenarAvailablility?activityId=";



    String DeleteActivityPhoto="Activity/DeleteActivityPhoto?activityid=";
    String ShowStatistics="User/ShowStatistics?providerId=";

    String DeleteActivity_URL="http://backpackapis.wasltec.org/Api/Activity/DeleteActivity?id=";


    String DeleteAvailablility="Activity/DeleteAvailablility?availablilityId=";
    String DeleteIndividualCategory="Activity/DeleteIndividualCategory?individualCategoryId=";

    String DeleteRule="Rule/DeleteRule?id=";

    String DeleteAddons="add_ons/DeleteAddons?id=";

    private String signup="https://www.google.com";
    private String Help="https://www.google.com";
    private String Feedback="https://www.google.com";
    private String Activities_Settings="https://www.google.com";
    private String  Users_and_Roles="https://www.google.com";

    public String getReportReview(String reviewid) {
        return base_host+ReportReview+reviewid;
    }

    public String getReviewReplies(String reviewid) {
        return base_host+ ReviewReplies+reviewid;
    }

    public String getIndividualCategory(String id) {
        return base_host+IndividualCategory+id;
    }

    public String getAddAvalibilty() {
        return base_host+addAvalibilty;
    }

    public String getCreateRule() {
        return base_host+createRule;
    }

    public String getCreateAddone() {
        return base_host+createAddone;
    }

    public String getCreateIndividualCategory() {
        return base_host+createIndividualCategory;
    }

    public String getDeleteActivity_URL() {
        return DeleteActivity_URL;
    }

    public static URLManger getInstance() {
        return ourInstance;
    }

    private URLManger() {
    }

    public String getMsg(int id) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String s=simpleDateFormat.format(Calendar.getInstance().getTime());
        Log.v("d",base_host+Msg+id+"&date="+s);
        return base_host+Msg+id+"&date="+s;
    }

    public String getGetAllActivities() {
        return base_host+ GetAllActivities;
    }

    public void setGetAllActivities(String getAllActivities) {
        GetAllActivities =  getAllActivities;
    }

    public String getLogin() {
        return  base_host+ Login;
    }
    public String getAllRules() {
        return  base_host+ GetAllRules;
    }

    public void setLogin(String login) {
        Login = login;
    }


    public String getUpcomingActivity() {
        return base_host+UpcomingActivity;
    }

    public void setUpcomingActivity(String upcomingActivity) {
        UpcomingActivity = upcomingActivity;
    }


    public String getGetActivityReview(int id ) {
        return base_host+GetActivityReview+id;
    }


    public String getGetActivityDetails(String id ) {
        return   base_host+GetActivityDetails+id;
    }
    public String setStepOne(int id,int mode ) {
        return base_host+addStep1+id+"&mode="+mode;
    }
    public String setStepTwo(int id,int mode ) {
        return   base_host+addStep2+id+"&mode="+mode;
    }
    public String setStepThree(int id,int mode ) {
        return   base_host+addStep3+id+"&mode="+mode;
    }
    public String setStepFour(int id,int mode ) {
        return   base_host+addStep4+id+"&mode="+mode;
    }
    public String setStepFive(int id,int mode ) {
        return   base_host+addStep5+id+"&mode="+mode;
    }
    public String setStepSix(int id,int mode ) {
        return   base_host+addStep6+id+"&mode="+mode;
    }
    public String setStepSeven(int id,int mode ) {
        return   base_host+addStep7+id+"&mode="+mode;
    }
    public String setStepEight(int id,int mode ) {
        return   base_host+addStep8+id+"&mode="+mode;
    }
    public String setStepNine(int id,int mode ) {
        return   base_host+addStep9+id+"&mode="+mode;
    }
    public String getAddOrginizer(int id,int mode) {
        return base_host+addOrginizer+id+"&mode="+mode;
    }

    public String getDeleteActivity(String id) {
        return base_host+DeleteActivity+id ;
    }

    public String getGetActivityTypes() {
        return base_host+GetActivityTypes;
    }

    public String getGetAllAdd_Ons() {
        return base_host+GetAllAdd_Ons;
    }


    public String getGetOrganizerTypes() {
        return base_host+GetOrganizerTypes;
    }

    public String getGetOptions() {
        return base_host+ GetOptions;
    }


    public String getGetReservations(String Activtity_id ) {
        return base_host+GetReservations+"?activityid="+Activtity_id;
    }
    public String getGetReservations(String Activtity_id ,String date) {
        return base_host+GetReservations+"?activityid="+Activtity_id+"&date="+date;
    }
    public String getGetAvailablility(String ActivityID) {
        return base_host+GetAvailablility+"?activityid=" + ActivityID;
    }

    public String getCalenderReservation(String id) {
        return base_host+CalenderReservation+id;
    }

    public String getCreateBooking() {
        return base_host+CreateBooking;
    }


    public String getCheckin_Ticket(String id) {
        return base_host+Checkin_Ticket+id;
    }

    public String getUser_Verified_Ticket(String id) {
        return base_host+User_Verified_Ticket+id;
    }

    public String getCancel_Ticket(String id) {
        return base_host+Cancel_Ticket+id;
    }
    public String getChangeStatus(String id) {
        return base_host+ChangeStatus+id;
    }
    public String getBookingPaid() {
        return base_host+BookingPaid;
    }

    public String getGetBookingDetails(String Bookingid,String ticketNo) {

        return base_host+GetBookingDetails+Bookingid+"&ticketNo="+ticketNo;
    }
    public String getCalenarAvailablility(String Activity_id) {

        return base_host+CalenarAvailablility+Activity_id;
    }
    public String getDeleteActivityPhoto(String activityid,String photoid) {

        return base_host+DeleteActivityPhoto+activityid+"&photoid="+photoid;
    }
    public String getDeleteAvailablility(String Avalibiltyid) {
        return base_host+DeleteAvailablility+Avalibiltyid;
    }
    public String getDeleteIndividualCategory(String DeleteIndividualCategoryid) {
        return base_host+DeleteIndividualCategory+DeleteIndividualCategoryid;
    }
    public String getsendMessage(String ticketid) {
        return base_host+sendMessage+ticketid;
    }
    public String getsendMessageForAll(String availabilityId) {
        return base_host+sendMessageForAll+availabilityId;
    }

    public String getShowStatistics(String providerId) {
//        https://localhost:44324/api/User/ShowStatistics?providerId=5
        return base_host+ShowStatistics+providerId;

    }

    public void setShowStatistics(String showStatistics) {
        ShowStatistics = showStatistics;
    }

    public String getSignup() {
        return signup;
    }

    public void setSignup(String signup) {
        this.signup = signup;
    }

    public String getHelp() {
        return Help;
    }

    public void setHelp(String help) {
        Help = help;
    }

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String feedback) {
        Feedback = feedback;
    }

    public String getActivities_Settings() {
        return Activities_Settings;
    }

    public void setActivities_Settings(String activities_Settings) {
        Activities_Settings = activities_Settings;
    }

    public String getInbox() {
        return base_host+Inbox;
    }

    public void setInbox(String inbox) {
        Inbox = inbox;
    }

    public String getUsers_and_Roles() {
        return Users_and_Roles;
    }

    public void setUsers_and_Roles(String users_and_Roles) {
        Users_and_Roles = users_and_Roles;
    }

    public String getUser_Chat(int chatId) {
        return base_host+ User_Chat+chatId;
    }

    public void setUser_Chat(String user_Chat) {
        User_Chat = user_Chat;
    }


    public String getSend_message(int chatId,int availabilityId) {
        return base_host+send_message+"chatId="+chatId+"&"+"availabilityId="+availabilityId;
    }

    public String getEditProfile() {
        return base_host+EditProfile;
    }

    public void setEditProfile(String editProfile) {
        EditProfile = editProfile;
    }

    public void setSend_message(String send_message) {
        this.send_message = send_message;
    }

    public String getDeleteRule(String id) {
        return base_host+DeleteRule+id;
    }
    public String getDeleteAddons(String id) {
        return base_host+DeleteAddons+id;
    }
    public String getCreate_ActivityPricing(int id,int mode ) {
        return base_host+Create_ActivityPricing+id+"&mode="+mode;

    }



}
