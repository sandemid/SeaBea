package com.seabea.android.rest;


import com.seabea.android.rest.entities.CheckAuthRequestRestModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IOSeaBea {

    @GET("data/2.5/weather")
    Call<CheckAuthRequestRestModel> checkAuthorize(@Query("userid") String userID,
                                                   @Query("sessionid") String sessionID,
                                                   @Query("accounttype") String accountType);

    @POST("data/2.5/weather")
    Call<CheckAuthRequestRestModel> socialEntry(@Query("accountType") String accountType,
                                                @Query("userSocialID") String userSocialID,
                                                @Query("socialNetworkName") String socialNetworkName,
                                                @Query("userFirstName") String userFirstName,
                                                @Query("userLastName") String userLastName,
                                                @Query("userDateBirth") String userDateBirth,
                                                @Query("userSex") String userSex,
                                                @Query("userCity") String userCity,
                                                @Query("userCountry") String userCountry,
                                                @Query("userEmail") String userEmail);

    @GET("data/2.5/weather")
    Call<CheckAuthRequestRestModel> ordinaryEntry(@Query("email") String email,
                                                @Query("password") String password,
                                                @Query("accountType") String accountType);

    @POST("data/2.5/weather")
    Call<CheckAuthRequestRestModel> userRegister(@Query("userEmail") String userEmail,
                                                 @Query("password") String password,
                                                 @Query("accountType") String accountType,
                                                 @Query("userFirstName") String userFirstName,
                                                 @Query("userLastName") String userLastName,
                                                 @Query("userSex") String userSex);
}
