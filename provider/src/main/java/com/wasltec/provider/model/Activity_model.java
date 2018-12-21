package com.wasltec.provider.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.wasltec.provider.Utils.App_Context.TAG;

public class Activity_model {

    int id  ,max_length,min_length,rate, total_tickets,remaining_tickets;
    String title , description ,location ,meeting_point,rules,requirements,type;
    ArrayList<ActivityAddOn> activityAdd_on_list = new ArrayList<>();
    boolean status;
    String date = "14 apr 2018";

    public Activity_model() {
    }

    ArrayList<String> images = new ArrayList<>();
    public Activity_model(int id, String type, int max_length, int min_length, int rate, int total_tickets,
                          int remaining_tickets, String title, String description,String location,
                          String meeting_point, String rules, String requirements, boolean status) {
        this.id = id;
        this.type = type;
        this.max_length = max_length;
        this.min_length = min_length;
        this.rate = rate;
        this.total_tickets = total_tickets;
        this.remaining_tickets = remaining_tickets;
        this.title = title;
        this.description = description;
        this.location = location;
        this.meeting_point = meeting_point;
        this.rules = rules;
        this.requirements = requirements;
        this.status = status;
    }


    public Activity_model(JSONObject jsonObject) {
        if (jsonObject.has("id")) {
            try {
                id = jsonObject.getInt("id");
            } catch (JSONException e) {
                id=-1;
                Log.d(TAG, "User: id  "+e.getMessage());
                e.printStackTrace();
            }
        }

        if (jsonObject.has("type")) {
            try {
                type = jsonObject.getString("type");
            } catch (JSONException e) {
                type="Land";
                Log.d(TAG, "User: type_id  "+e.getMessage());
                e.printStackTrace();
            }
        }


        if (jsonObject.has("max_length")) {
            try {
                max_length = jsonObject.getInt("max_length");
            } catch (JSONException e) {
                max_length=0;
                Log.d(TAG, "User: max_length  "+e.getMessage());
                e.printStackTrace();
            }
        }
        if (jsonObject.has("min_length")) {
            try {
                min_length = jsonObject.getInt("min_length");
            } catch (JSONException e) {
                min_length=0;
                Log.d(TAG, "User: min_length  "+e.getMessage());
                e.printStackTrace();
            }
        }
        if (jsonObject.has("rate")) {
            try {
                rate = jsonObject.getInt("rate");
            } catch (JSONException e) {
                rate=0;
                Log.d(TAG, "User: rate  "+e.getMessage());
                e.printStackTrace();
            }
        }

        if (jsonObject.has("total_tickets")) {
            try {
                total_tickets = jsonObject.getInt("total_tickets");
            } catch (JSONException e) {
                total_tickets=0;
                Log.d(TAG, "User: total_tickets  "+e.getMessage());
                e.printStackTrace();
            }
        }

        if (jsonObject.has("remaining_tickets")) {
            try {
                remaining_tickets = jsonObject.getInt("remaining_tickets");
            } catch (JSONException e) {
                remaining_tickets=0;
                Log.d(TAG, "User: remaining_tickets  "+e.getMessage());
                e.printStackTrace();
            }
        }



        if (jsonObject.has("title")) {
            try {
                title = jsonObject.getString("title");
            } catch (JSONException e) {
                title="";
                Log.d(TAG, "User: title  "+e.getMessage());
                e.printStackTrace();
            }
        }

        if (jsonObject.has("description")) {
            try {
                description = jsonObject.getString("description");
            } catch (JSONException e) {
                description ="";
                Log.d(TAG, "User: description  "+e.getMessage());
                e.printStackTrace();
            }
        }

        if (jsonObject.has("location")) {
            try {
                location = jsonObject.getString("location");
            } catch (JSONException e) {
                location ="";
                Log.d(TAG, "User: location  "+e.getMessage());
                e.printStackTrace();
            }
        }


        if (jsonObject.has("meeting_point")) {
            try {
                meeting_point = jsonObject.getString("meeting_point");
            } catch (JSONException e) {
                meeting_point ="";
                Log.d(TAG, "User: meeting_point  "+e.getMessage());
                e.printStackTrace();
            }
        }

        if (jsonObject.has("rules")) {
            try {
                rules = jsonObject.getString("rules");
            } catch (JSONException e) {
                rules ="";
                Log.d(TAG, "User: rules  "+e.getMessage());
                e.printStackTrace();
            }
        }

        if (jsonObject.has("requirements")) {
            try {
                requirements = jsonObject.getString("requirements");
            } catch (JSONException e) {
                requirements ="";
                Log.d(TAG, "User: requirements  "+e.getMessage());
                e.printStackTrace();
            }
        }




        if (jsonObject.has("status")) {
            try {
                status = jsonObject.getBoolean("status");
            } catch (JSONException e) {
                status=false;
                Log.d(TAG, "User: status  "+e.getMessage());
                e.printStackTrace();
            }
        }

    }
    JSONObject   TojsonObject(){
        JSONObject jsonObject= new JSONObject();
        try {



            jsonObject.put("id",id);
            jsonObject.put("type_id",type);
            jsonObject.put("max_length",max_length);
            jsonObject.put("min_length",min_length);
            jsonObject.put("rate",rate);
            jsonObject.put("total_tickets",total_tickets);
            jsonObject.put("remaining_tickets",remaining_tickets);

            jsonObject.put("title",title);
            jsonObject.put("description",description);
            jsonObject.put("location",location);
            jsonObject.put("meeting_point",meeting_point);
            jsonObject.put("rules",rules);
            jsonObject.put("requirements",requirements);

            jsonObject.put("status",status);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getMax_length() {
        return max_length;
    }

    public void setMax_length(int max_length) {
        this.max_length = max_length;
    }

    public int getMin_length() {
        return min_length;
    }

    public void setMin_length(int min_length) {
        this.min_length = min_length;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getTotal_tickets() {
        return total_tickets;
    }

    public void setTotal_tickets(int total_tickets) {
        this.total_tickets = total_tickets;
    }

    public int getRemaining_tickets() {
        return remaining_tickets;
    }

    public void setRemaining_tickets(int remaining_tickets) {
        this.remaining_tickets = remaining_tickets;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<ActivityAddOn> getActivityAdd_on_list() {
        return activityAdd_on_list;
    }

    public void setActivityAdd_on_list(ArrayList<ActivityAddOn> activityAdd_on_list) {
        this.activityAdd_on_list = activityAdd_on_list;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMeeting_point() {
        return meeting_point;
    }

    public void setMeeting_point(String meeting_point) {
        this.meeting_point = meeting_point;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
