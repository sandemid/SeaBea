package com.seabea.android.rest.entities;

import com.google.gson.annotations.SerializedName;

public class CheckAuthRequestRestModel {
    @SerializedName("userid") public int userid;
    @SerializedName("name") public NameRestModel name;
    @SerializedName("sessionid") public String sessionID;
    @SerializedName("usertype") public int userType;
    @SerializedName("datebirth") public String dateBirth;
    @SerializedName("sex") public int sex;
    @SerializedName("city") public String city;
    @SerializedName("country") public String country;
    @SerializedName("email") public String email;
    @SerializedName("social") public SocialRestModel social;
}
