package com.nggdvu.littleclover.models;

public class Story {
    Story() {
    }

    public String getHashtag() {
        return hashtag;
    }
    public void getHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Story(String photo, String hashtag) {
        this.hashtag = hashtag;
        this.photo = photo;

    }
    String hashtag, photo;
}
