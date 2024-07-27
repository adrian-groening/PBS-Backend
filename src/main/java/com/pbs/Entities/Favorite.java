package com.pbs.Entities;

public class Favorite {
    String favoriteID, creatorID, productID;
    public Favorite() {
    }
    public Favorite(String favoriteID, String creatorID, String productID) {
        this.favoriteID = favoriteID;
        this.creatorID = creatorID;
        this.productID = productID;
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
}
