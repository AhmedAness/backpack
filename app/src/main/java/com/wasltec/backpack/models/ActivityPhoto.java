
package com.wasltec.backpack.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivityPhoto implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("cover_photo")
    @Expose
    private Boolean coverPhoto;
    private final static long serialVersionUID = -6865583258305238434L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(Boolean coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

}
