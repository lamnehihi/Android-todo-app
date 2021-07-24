package com.frsarker.todotask;

public class ScreenItem {

    String Title, Description;
    int scrImg;
    int logo;

    public ScreenItem(String title, String description, int scrImg, int logo) {
        Title = title;
        Description = description;
        this.scrImg = scrImg;
        this.logo = logo;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getScrImg() {
        return scrImg;
    }

    public void setScrImg(int scrImg) {
        this.scrImg = scrImg;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
