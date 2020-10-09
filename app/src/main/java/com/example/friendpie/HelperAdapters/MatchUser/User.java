package com.example.friendpie.HelperAdapters.MatchUser;

public class User {

    private String userUID = "";
    private String name = "";
    private String age = "";
    private String imageURL = "";

    User(){}

    public User(String userUID, String name, String age, String imageURL) {
        this.userUID = userUID;
        this.name = name;
        this.age = age;
        this.imageURL = imageURL;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
