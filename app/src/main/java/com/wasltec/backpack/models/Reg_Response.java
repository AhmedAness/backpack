package com.wasltec.backpack.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reg_Response {

    @Expose
    @SerializedName("nextpage")
    public String nextpage;
    @Expose
    @SerializedName("verified")
    public boolean verified;
    @Expose
    @SerializedName("verificationcode")
    public String verificationcode;
    @Expose
    @SerializedName("message")
    public String message;
    @Expose
    @SerializedName("status")
    public int status;

    public Reg_Response() {
    }

    public String getNextpage() {
        return nextpage;
    }

    public void setNextpage(String nextpage) {
        this.nextpage = nextpage;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getVerificationcode() {
        return verificationcode;
    }

    public void setVerificationcode(String verificationcode) {
        this.verificationcode = verificationcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
