package com.example.friendpie.OtherClasses;

import java.util.ArrayList;

public class UserHelperClass {

    private String UserEmail, UserPhone, UserPassword, UserBirthdate, UserName, UserGender;
    private ArrayList<String> UserHobbies, UserMusicGenres;

    public UserHelperClass(){} // For Firebase

    public UserHelperClass(String UserEmail, String UserPhone, String UserPassword, String UserBirthdate, String UserName,
                           String UserGender, ArrayList<String> UserHobbies, ArrayList<String> UserMusicGenres) {
        this.UserEmail = UserEmail;
        this.UserPhone = UserPhone;
        this.UserPassword = UserPassword;
        this.UserBirthdate = UserBirthdate;
        this.UserName = UserName;
        this.UserGender = UserGender;
        this.UserHobbies = UserHobbies;
        this.UserMusicGenres = UserMusicGenres;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getUserBirthdate() {
        return UserBirthdate;
    }

    public void setUserBirthdate(String userBirthdate) {
        UserBirthdate = userBirthdate;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserGender() {
        return UserGender;
    }

    public void setUserGender(String userGender) {
        UserGender = userGender;
    }

    public ArrayList<String> getUserHobbies() {
        return UserHobbies;
    }

    public void setUserHobbies(ArrayList<String> userHobbies) {
        UserHobbies = userHobbies;
    }

    public ArrayList<String> getUserMusicGenres() {
        return UserMusicGenres;
    }

    public void setUserMusicGenres(ArrayList<String> userMusicGenres) {
        UserMusicGenres = userMusicGenres;
    }
}
