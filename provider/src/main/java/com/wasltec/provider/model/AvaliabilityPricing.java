package com.wasltec.provider.model;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvaliabilityPricing implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("individualCategoryId")
    @Expose
    private Integer individualCategoryId;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("priceAfterDiscount")
    @Expose
    private Integer priceAfterDiscount;
    public final static Parcelable.Creator<AvaliabilityPricing> CREATOR = new Creator<AvaliabilityPricing>() {


        @SuppressWarnings({
                "unchecked"
        })
        public AvaliabilityPricing createFromParcel(Parcel in) {
            return new AvaliabilityPricing(in);
        }

        public AvaliabilityPricing[] newArray(int size) {
            return (new AvaliabilityPricing[size]);
        }

    }
            ;
    private final static long serialVersionUID = -5935680591165408641L;

    protected AvaliabilityPricing(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.individualCategoryId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.price = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.priceAfterDiscount = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public AvaliabilityPricing() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIndividualCategoryId() {
        return individualCategoryId;
    }

    public void setIndividualCategoryId(Integer individualCategoryId) {
        this.individualCategoryId = individualCategoryId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public void setPriceAfterDiscount(Integer priceAfterDiscount) {
        this.priceAfterDiscount = priceAfterDiscount;
    }




    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(individualCategoryId);
        dest.writeValue(price);
        dest.writeValue(priceAfterDiscount);
    }

    public int describeContents() {
        return 0;
    }

}
