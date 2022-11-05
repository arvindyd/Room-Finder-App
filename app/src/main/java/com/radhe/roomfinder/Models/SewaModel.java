package com.radhe.roomfinder.Models;

public class SewaModel {

    private String sewa;
    private String title;
    private int image;
    private double km;

    public SewaModel(String sewa, String title, int image, double km) {
        this.sewa = sewa;
        this.title = title;
        this.image = image;
        this.km = km;
    }

    public String getSewa() {
        return sewa;
    }

    public void setSewa(String sewa) {
        this.sewa = sewa;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }
}
