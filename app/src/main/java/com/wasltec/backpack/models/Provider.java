
package com.wasltec.backpack.models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Provider implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_Type")
    @Expose
    private Integer userType;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("tempUser")
    @Expose
    private Boolean tempUser;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("customerphoto64")
    @Expose
    private Object customerphoto64;
    @SerializedName("userPhoto_Url")
    @Expose
    private String userPhotoUrl;
    @SerializedName("bio")
    @Expose
    private Object bio;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("organization_name")
    @Expose
    private Object organizationName;
    @SerializedName("organization_type")
    @Expose
    private Object organizationType;
    @SerializedName("about_organization")
    @Expose
    private Object aboutOrganization;
    @SerializedName("receive_cash_payment")
    @Expose
    private Boolean receiveCashPayment;
    @SerializedName("receive_online_payment")
    @Expose
    private Boolean receiveOnlinePayment;
    @SerializedName("recieve_money_transfer")
    @Expose
    private Boolean recieveMoneyTransfer;
    @SerializedName("bank_id")
    @Expose
    private Object bankId;
    @SerializedName("ibaN_number")
    @Expose
    private Object ibaNNumber;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("password")
    @Expose
    private Object password;
    @SerializedName("isProvider")
    @Expose
    private Boolean isProvider;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("age")
    @Expose
    private Object age;
    @SerializedName("activationcode")
    @Expose
    private Object activationcode;
    @SerializedName("isregistered")
    @Expose
    private Boolean isregistered;
    @SerializedName("isverified")
    @Expose
    private Boolean isverified;
    @SerializedName("banks")
    @Expose
    private Object banks;
    @SerializedName("notifications")
    @Expose
    private Object notifications;
    @SerializedName("userIdentifications")
    @Expose
    private Object userIdentifications;
    @SerializedName("activities")
    @Expose
    private List<Object> activities = null;
    @SerializedName("user_roles")
    @Expose
    private Object userRoles;
    @SerializedName("bookings")
    @Expose
    private List<Object> bookings = null;
    @SerializedName("rules")
    @Expose
    private Object rules;
    @SerializedName("activity_Logs")
    @Expose
    private Object activityLogs;
    @SerializedName("reviews")
    @Expose
    private List<Object> reviews = null;
    @SerializedName("reviewReports")
    @Expose
    private Object reviewReports;
    @SerializedName("ticketMessages")
    @Expose
    private Object ticketMessages;
    @SerializedName("user_Diseases")
    @Expose
    private Object userDiseases;
    @SerializedName("followUpHealths")
    @Expose
    private Object followUpHealths;
    @SerializedName("booking_Tickets")
    @Expose
    private Object bookingTickets;
    @SerializedName("userDevices")
    @Expose
    private Object userDevices;
    @SerializedName("messageReplies")
    @Expose
    private Object messageReplies;
    private final static long serialVersionUID = -6057003392057230783L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getTempUser() {
        return tempUser;
    }

    public void setTempUser(Boolean tempUser) {
        this.tempUser = tempUser;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Object getCustomerphoto64() {
        return customerphoto64;
    }

    public void setCustomerphoto64(Object customerphoto64) {
        this.customerphoto64 = customerphoto64;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public Object getBio() {
        return bio;
    }

    public void setBio(Object bio) {
        this.bio = bio;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Object getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(Object organizationName) {
        this.organizationName = organizationName;
    }

    public Object getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(Object organizationType) {
        this.organizationType = organizationType;
    }

    public Object getAboutOrganization() {
        return aboutOrganization;
    }

    public void setAboutOrganization(Object aboutOrganization) {
        this.aboutOrganization = aboutOrganization;
    }

    public Boolean getReceiveCashPayment() {
        return receiveCashPayment;
    }

    public void setReceiveCashPayment(Boolean receiveCashPayment) {
        this.receiveCashPayment = receiveCashPayment;
    }

    public Boolean getReceiveOnlinePayment() {
        return receiveOnlinePayment;
    }

    public void setReceiveOnlinePayment(Boolean receiveOnlinePayment) {
        this.receiveOnlinePayment = receiveOnlinePayment;
    }

    public Boolean getRecieveMoneyTransfer() {
        return recieveMoneyTransfer;
    }

    public void setRecieveMoneyTransfer(Boolean recieveMoneyTransfer) {
        this.recieveMoneyTransfer = recieveMoneyTransfer;
    }

    public Object getBankId() {
        return bankId;
    }

    public void setBankId(Object bankId) {
        this.bankId = bankId;
    }

    public Object getIbaNNumber() {
        return ibaNNumber;
    }

    public void setIbaNNumber(Object ibaNNumber) {
        this.ibaNNumber = ibaNNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Boolean getIsProvider() {
        return isProvider;
    }

    public void setIsProvider(Boolean isProvider) {
        this.isProvider = isProvider;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Object getAge() {
        return age;
    }

    public void setAge(Object age) {
        this.age = age;
    }

    public Object getActivationcode() {
        return activationcode;
    }

    public void setActivationcode(Object activationcode) {
        this.activationcode = activationcode;
    }

    public Boolean getIsregistered() {
        return isregistered;
    }

    public void setIsregistered(Boolean isregistered) {
        this.isregistered = isregistered;
    }

    public Boolean getIsverified() {
        return isverified;
    }

    public void setIsverified(Boolean isverified) {
        this.isverified = isverified;
    }

    public Object getBanks() {
        return banks;
    }

    public void setBanks(Object banks) {
        this.banks = banks;
    }

    public Object getNotifications() {
        return notifications;
    }

    public void setNotifications(Object notifications) {
        this.notifications = notifications;
    }

    public Object getUserIdentifications() {
        return userIdentifications;
    }

    public void setUserIdentifications(Object userIdentifications) {
        this.userIdentifications = userIdentifications;
    }

    public List<Object> getActivities() {
        return activities;
    }

    public void setActivities(List<Object> activities) {
        this.activities = activities;
    }

    public Object getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Object userRoles) {
        this.userRoles = userRoles;
    }

    public List<Object> getBookings() {
        return bookings;
    }

    public void setBookings(List<Object> bookings) {
        this.bookings = bookings;
    }

    public Object getRules() {
        return rules;
    }

    public void setRules(Object rules) {
        this.rules = rules;
    }

    public Object getActivityLogs() {
        return activityLogs;
    }

    public void setActivityLogs(Object activityLogs) {
        this.activityLogs = activityLogs;
    }

    public List<Object> getReviews() {
        return reviews;
    }

    public void setReviews(List<Object> reviews) {
        this.reviews = reviews;
    }

    public Object getReviewReports() {
        return reviewReports;
    }

    public void setReviewReports(Object reviewReports) {
        this.reviewReports = reviewReports;
    }

    public Object getTicketMessages() {
        return ticketMessages;
    }

    public void setTicketMessages(Object ticketMessages) {
        this.ticketMessages = ticketMessages;
    }

    public Object getUserDiseases() {
        return userDiseases;
    }

    public void setUserDiseases(Object userDiseases) {
        this.userDiseases = userDiseases;
    }

    public Object getFollowUpHealths() {
        return followUpHealths;
    }

    public void setFollowUpHealths(Object followUpHealths) {
        this.followUpHealths = followUpHealths;
    }

    public Object getBookingTickets() {
        return bookingTickets;
    }

    public void setBookingTickets(Object bookingTickets) {
        this.bookingTickets = bookingTickets;
    }

    public Object getUserDevices() {
        return userDevices;
    }

    public void setUserDevices(Object userDevices) {
        this.userDevices = userDevices;
    }

    public Object getMessageReplies() {
        return messageReplies;
    }

    public void setMessageReplies(Object messageReplies) {
        this.messageReplies = messageReplies;
    }

}
