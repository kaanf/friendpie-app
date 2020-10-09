package com.example.friendpie.HelperAdapters.MusicGenre;

public class MusicGenre {

    private String musicGenreTitle;
    private boolean isSelected;

    public MusicGenre(String musicGenreTitle) {
        this.musicGenreTitle = musicGenreTitle;
    }

    public String getMusicGenreTitle() {
        return musicGenreTitle;
    }

    public void setMusicGenreTitle(String musicGenreTitle) {
        this.musicGenreTitle = musicGenreTitle;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
