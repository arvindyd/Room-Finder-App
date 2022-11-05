package com.radhe.roomfinder.Models;

public class SliderModel {

    private String url;
    private String title;

    public SliderModel(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public SliderModel() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
