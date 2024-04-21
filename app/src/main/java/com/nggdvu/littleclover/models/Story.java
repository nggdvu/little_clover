package com.nggdvu.littleclover.models;

public class Story {
    Story() {
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getHashtag() {
        return hashtag;
    }
    public void getHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public Story(String photo, String hashtag) {
        this.photo = photo;
        this.hashtag = hashtag;
    }
    String photo, hashtag;
}
