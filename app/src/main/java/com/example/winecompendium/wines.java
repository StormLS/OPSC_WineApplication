package com.example.winecompendium;

public class wines
{
    private String wineImage;
    private String wineName;
    private String wineType;
    private String wineSubtype;
    private String wineOrigin;
    private String wineBottleType;
    private String wineAlcoPerc;
    private String wineYear;
    private String wineDateAcquired;

    //default constructor
    public wines()
    {

    }

    //constructor with full parameters
    public wines (String wineImage, String wineName, String wineType, String wineSubtype, String wineOrigin, String wineBottleType, String wineAlcoPerc,String wineYear, String wineDateAcquired)
    {
        this.wineImage = wineImage;
        this.wineName = wineImage;
        this.wineType = wineImage;
        this.wineSubtype = wineImage;
        this.wineOrigin = wineImage;
        this.wineBottleType = wineImage;
        this.wineAlcoPerc = wineImage;
        this.wineYear = wineImage;
        this.wineDateAcquired = wineImage;
    }

    public void setWineImage(String wineImage) {
        this.wineImage = wineImage;
    }

    public void setWineName(String wineName) {
        this.wineName = wineName;
    }

    public void setWineType(String wineType) {
        this.wineType = wineType;
    }

    public void setWineSubtype(String wineSubtype) {
        this.wineSubtype = wineSubtype;
    }

    public void setWineOrigin(String wineOrigin) {
        this.wineOrigin = wineOrigin;
    }

    public void setWineBottleType(String wineBottleType) {
        this.wineBottleType = wineBottleType;
    }

    public void setWineAlcoPerc(String wineAlcoPerc) {
        this.wineAlcoPerc = wineAlcoPerc;
    }

    public void setWineYear(String wineYear) {
        this.wineYear = wineYear;
    }

    public void setWineDateAcquired(String wineDateAcquired) {
        this.wineDateAcquired = wineDateAcquired;
    }

    public String getWineImage() {
        return wineImage;
    }

    public String getWineName() {
        return wineName;
    }

    public String getWineType() {
        return wineType;
    }

    public String getWineSubtype() {
        return wineSubtype;
    }

    public String getWineOrigin() {
        return wineOrigin;
    }

    public String getWineBottleType() {
        return wineBottleType;
    }

    public String getWineAlcoPerc() {
        return wineAlcoPerc;
    }

    public String getWineYear() {
        return wineYear;
    }

    public String getWineDateAcquired() {
        return wineDateAcquired;
    }
}
