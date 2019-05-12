package com.seabea.android.rest.entities;

import com.google.gson.annotations.SerializedName;

public class SocialRestModel {
    @SerializedName("socialnetworkid") public int socialNetworkID;
    @SerializedName("socialnetworkname") public String socialNetworkName;
    @SerializedName("usersocialid") public int userSocialID;
}
