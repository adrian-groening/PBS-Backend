package com.pbs.app.Entities;

import java.time.LocalDate;
import java.util.Random;

public class Favorite {
    private String favoriteID, creatorID, productID, dateOfFavorite;
    public Favorite() {
    }
    public Favorite(String favoriteID, String creatorID, String productID, String dateOfFavorite) {
        this.favoriteID = favoriteID;
        this.creatorID = creatorID;
        this.productID = productID;
        this.dateOfFavorite = dateOfFavorite;
    }
    public String getFavoriteID() {
        return favoriteID;
    }
    public void setFavoriteID(String favoriteID) {
        this.favoriteID = favoriteID;
    }
    public String getCreatorID() {
        return creatorID;
    }
    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }
    public String getProductID() {
        return productID;
    }
    public void setProductID(String productID) {
        this.productID = productID;
    }
    public String getDateofFavorite() {
        return dateOfFavorite;
    }
    public void setDateOfFavorite(String dateOfFavorite) {
        this.dateOfFavorite = dateOfFavorite;
    }
    public void generateFavoriteID() {
        Random rand = new Random();
        this.favoriteID = "F" + rand.nextInt(1000000);
    }
    public void generateDateOfFavorite() {
        LocalDate date = LocalDate.now();
        this.dateOfFavorite = date.toString();
    }
}
