package com.pbs.app.Entities;

import java.time.LocalDate;
import java.util.Random;

public class Scan {
    private String scanID, creatorID, productBarcode, dateOfScan, name, photo;
    public Scan() {
    }
    public Scan(String scanID, String creatorID, String productBarcode, String dateOfScan, String name, String photo) {
        this.scanID = scanID;
        this.creatorID = creatorID;
        this.productBarcode = productBarcode;
        this.dateOfScan = dateOfScan;
        this.name = name;
        this.photo = photo;
    }
    public String getScanID() {
        return scanID;
    }
    public void setScanID(String scanID) {
        this.scanID = scanID;
    }
    public String getCreatorID() {
        return creatorID;
    }
    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }
    public String getProductBarcode() {
        return productBarcode;
    }
    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }
    public String getDateOfScan() {
        return dateOfScan;
    }
    public void setDateOfScan(String dateOfScan) {
        this.dateOfScan = dateOfScan;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public void generateScanID() {
        Random rand = new Random();
        this.scanID = "SC" + rand.nextInt(1000000);
    }
    public void generateDateOfScan() {
        LocalDate date = LocalDate.now();
        this.dateOfScan = date.toString();
    }
}
