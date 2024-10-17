package com.pbs.app.Entities;

import java.time.LocalDate;
import java.util.Random;

public class Share {
    String shareID, creatorID, productID, dateOfShare;
    public Share() {
    }
    public Share(String shareID, String creatorID, String productID, String dateOfShare) {
        this.shareID = shareID;
        this.creatorID = creatorID;
        this.productID = productID;
        this.dateOfShare = dateOfShare;
    }
    public String getShareID() {
        return shareID;
    }
    public void setShareID(String shareID) {
        this.shareID = shareID;
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
    public String getDateOfShare() {
        return dateOfShare;
    }
    public void setDateOfShare(String dateOfShare) {
        this.dateOfShare = dateOfShare;
    }
    public void generateShareID() {
        Random rand = new Random();
        this.shareID = "S" + rand.nextInt(1000000);
    }
    public void generateDateOfShare() {
        LocalDate date = LocalDate.now();
        this.dateOfShare = date.toString();
    }

    
}
