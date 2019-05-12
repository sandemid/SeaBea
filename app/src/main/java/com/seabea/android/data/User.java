package com.seabea.android.data;

public class User {

    private static User user = null; //singleton instance
    public static final int TYPE_CLIENT = 1;
    public static final int TYPE_BUSINESS = 2;
    private int userID;
    private int userType;
    private int userSocialID;
    private String sessionID;
    private String socialNetworkName;
    private String userFirstName;
    private String userLastName;
    private String userDateBirth;
    private int userSex;
    private String userCity;
    private String userCountry;
    private String userEmail;

    private User() {
    }

    public static User getUser() {
        if(user == null) {
            user = new User();
        }
        return user;
    }

    public boolean setUserType(int userType) {
        if (userType == TYPE_BUSINESS || userType == TYPE_CLIENT || userType == 0) {
            this.userType = userType;
            return true;
        } else {
            return false;
        }
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public void setUserSocialID(int userSocialID) {
        this.userSocialID = userSocialID;
    }

    public void setSocialNetworkName(String socialNetworkName) {
        this.socialNetworkName = socialNetworkName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public void setUserDateBirth(String userDateBirth) {
        this.userDateBirth = userDateBirth;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getUserID() {
        return userID;
    }

    public int getUserType() {
        return userType;
    }

    public int getUserSocialID() {
        return userSocialID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getSocialNetworkName() {
        return socialNetworkName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getUserDateBirth() {
        return userDateBirth;
    }

    public int getUserSex() {
        return userSex;
    }

    public String getUserCity() {
        return userCity;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void clearUserFields () {
        setUserType(0);
        setSessionID(null);
        setSocialNetworkName(null);
        setUserCity(null);
        setUserCountry(null);
        setUserDateBirth(null);
        setUserEmail(null);
        setUserFirstName(null);
        setUserID(0);
        setUserLastName(null);
        setUserSex(0);
        setUserSocialID(0);
    }
}
