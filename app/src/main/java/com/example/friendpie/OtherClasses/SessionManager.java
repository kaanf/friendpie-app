package com.example.friendpie.OtherClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class SessionManager {

    SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context context;


    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_FULLNAME = "UserName";
    public static final String KEY_PHONE = "UserPhone";
    public static final String KEY_PASSWORD = "UserPassword";
    public static final String KEY_EMAIL = "UserEmail";
    public static final String KEY_GENDER = "UserGender";
    public static final String KEY_BIRTHDATE = "UserBirthdate";
    public static final String KEY_HOBBIES = "UserHobbies";
    public static final String KEY_MUSICGENRES = "UserMusicGenres";

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context _context) {
        context = _context;
        usersSession = _context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        editor = usersSession.edit();
    }

    public void createLoginSession(String UserName, String UserPhone, String UserPassword, String UserEmail, String UserGender,
                                   String UserBirthdate, ArrayList<String> UserHobbies, ArrayList<String> UserMusicGenres){
        Gson gson = new Gson();
        String userHobby = gson.toJson(UserHobbies);
        String userMusicGenre = gson.toJson(UserMusicGenres);
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_FULLNAME, UserName);
        editor.putString(KEY_PHONE, UserPhone);
        editor.putString(KEY_PASSWORD, UserPassword);
        editor.putString(KEY_EMAIL, UserEmail);
        editor.putString(KEY_GENDER, UserGender);
        editor.putString(KEY_BIRTHDATE, UserBirthdate);
        editor.putString(KEY_HOBBIES, userHobby);
        editor.putString(KEY_MUSICGENRES, userMusicGenre);
        editor.commit();
    }

    public HashMap<String, String> getUsersDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();
        userData.put(KEY_MUSICGENRES, usersSession.getString(KEY_MUSICGENRES, null));
        userData.put(KEY_HOBBIES, usersSession.getString(KEY_HOBBIES, null));
        userData.put(KEY_FULLNAME, usersSession.getString(KEY_FULLNAME, null));
        userData.put(KEY_PHONE, usersSession.getString(KEY_PHONE, null));
        userData.put(KEY_PASSWORD, usersSession.getString(KEY_PASSWORD, null));
        userData.put(KEY_EMAIL, usersSession.getString(KEY_EMAIL, null));
        userData.put(KEY_GENDER, usersSession.getString(KEY_GENDER, null));
        userData.put(KEY_BIRTHDATE, usersSession.getString(KEY_BIRTHDATE, null));
        return userData;
    }

    public boolean checkLogin() {
        return usersSession.getBoolean(IS_LOGIN, true);
    }

    public void logoutUserFromSession() {
        editor.clear();
        editor.commit();
    }

    public static Object fromJson(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
    }

    public ArrayList<String> getUserHobbies() {
        ArrayList<String> userHobbies= (ArrayList<String>) fromJson(getUsersDetailFromSession().get(SessionManager.KEY_HOBBIES),
                new TypeToken<ArrayList<String>>() {
                }.getType());
        return userHobbies;
    }

    public ArrayList<String> getUserMusicGenres() {
        ArrayList<String> musicGenres= (ArrayList<String>) fromJson(getUsersDetailFromSession().get(SessionManager.KEY_MUSICGENRES),
                new TypeToken<ArrayList<String>>() {
                }.getType());
        return musicGenres;
    }

}
