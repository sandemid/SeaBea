package com.seabea.android.rest.entities;

import com.google.gson.annotations.SerializedName;

public class NameRestModel {
    @SerializedName("firstname") public String firstName;
    @SerializedName("lastname") public String lastName;
}