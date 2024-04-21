package com.nggdvu.littleclover.models;

public class Campaign {
    //19:33
    Campaign() {
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAiming() {
        return aiming;
    }
    public void setAiming(String aiming) {
        this.aiming = aiming;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getSort() {
        return sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    private boolean liked;
    public boolean isLiked() {
        return liked;
    }
    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public Campaign(String image, String title, String aiming, String location, String sort, String description, String time) {
        //this.image = image;
        this.image = image;
        this.title = title;
        this.aiming = aiming;
        this.location = location;
        this.sort = sort;
        this.description = description;
        this.time = time;
    }
    String image, title, aiming, location, sort, description, time;
}
