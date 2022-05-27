package com.example.winecompendium;

import android.net.Uri;

public class wines
{
    private String WineImage;

    private String WineName;
    private String WineDesc;
    private String WineType;
    private String WineSubtype;
    private String WineOrigin;
    private String WineBottleType;

    private String WinePerc;
    private String WineYear;

    private String WineDate;

    private String WineRating;

    public wines()
    {

    }

    public wines(String wineImage)
    {
        WineImage = wineImage;
    }

    public wines(String wineImage, String wineName, String wineDesc, String wineType, String wineSubtype, String wineOrigin, String wineBottleType, String winePerc, String wineYear, String wineDate, String wineRating)
    {
        if (wineName.trim().equals(""))
        {
            wineName = "No Name";
        }

        WineImage = wineImage;
        WineName = wineName;
        WineDesc = wineDesc;
        WineType = wineType;
        WineSubtype = wineSubtype;
        WineOrigin = wineOrigin;
        WineBottleType = wineBottleType;
        WinePerc = winePerc;
        WineYear = wineYear;
        WineDate = wineDate;
        WineRating = wineRating;
    }

    public String getWineImage() {
        return WineImage;
    }

    public void setWineImage(String wineImage) {
        WineImage = wineImage;
    }

    public String getWineName() {
        return WineName;
    }

    public void setWineName(String wineName) {
        WineName = wineName;
    }

    public String getWineDesc() {
        return WineDesc;
    }

    public void setWineDesc(String wineDesc) {
        WineDesc = wineDesc;
    }

    public String getWineType() {
        return WineType;
    }

    public void setWineType(String wineType) {
        WineType = wineType;
    }

    public String getWineSubtype() {
        return WineSubtype;
    }

    public void setWineSubtype(String wineSubtype) {
        WineSubtype = wineSubtype;
    }

    public String getWineOrigin() {
        return WineOrigin;
    }

    public void setWineOrigin(String wineOrigin) {
        WineOrigin = wineOrigin;
    }

    public String getWineBottleType() {
        return WineBottleType;
    }

    public void setWineBottleType(String wineBottleType) {
        WineBottleType = wineBottleType;
    }

    public String getWinePerc() {
        return WinePerc;
    }

    public void setWinePerc(String winePerc) {
        WinePerc = winePerc;
    }

    public String getWineYear() {
        return WineYear;
    }

    public void setWineYear(String wineYear) {
        WineYear = wineYear;
    }

    public String getWineDate() {
        return WineDate;
    }

    public void setWineDate(String wineDate) {
        WineDate = wineDate;
    }

    public String getWineRating() {
        return WineRating;
    }

    public void setWineRating(String wineRating) {
        WineRating = wineRating;
    }
}
