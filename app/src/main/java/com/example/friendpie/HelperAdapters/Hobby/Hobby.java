package com.example.friendpie.HelperAdapters.Hobby;

public class Hobby{

    private int imageId;
    private String hobbyTitle;
    private boolean isSelected;

    public Hobby(String hobbyTitle, int imageId) {
        this.hobbyTitle = hobbyTitle;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getHobbyTitle() {
        return hobbyTitle;
    }

    public void setHobbyTitle(String hobbyTitle) {
        this.hobbyTitle = hobbyTitle;
    }

    public boolean isSelected() { return isSelected; }

    public void setSelected(boolean selected) { isSelected = selected; }
}