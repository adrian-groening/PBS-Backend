package com.pbs.app.Repositories;
import java.util.List;

public class AnalyticsData {
    String totalScans, totalShares;
    List<String> scannedProducts, favourateProducts, sharedProducts, scannedBrands, favouriteBrands, sharedBrands, scannedCategories,favourateCategories, sharedCategories;
   


    public AnalyticsData() {
    }
   
   //Constructor 
   public AnalyticsData( String totalScans, String totalShares, List<String> scannedProducts, List<String> favourateProducts, List<String> sharedProducts, List<String> scannedBrands, List<String> favouriteBrands, 
    List<String> sharedBrands, List<String> scannedCategories, List<String> favourateCategories, List<String> sharedCategories)
    {
      this.totalScans =totalScans;
      this.totalShares = totalShares;
      this.scannedProducts = scannedProducts;
      this.favourateProducts = favourateProducts;
      this.sharedProducts = sharedProducts;
      this.scannedBrands = scannedBrands;
      this.favouriteBrands = favouriteBrands;
      this.sharedBrands = sharedBrands;
      this.scannedCategories = scannedCategories;
      this.favourateCategories = favourateCategories;
      this.sharedCategories= sharedCategories;
    }
    
    //Get and Set Methods
    
    
    //totalScans
    public String getTotalScans() {
        return totalScans;
    }

    public void setTotalScans(String totalScans) {
        this.totalScans = totalScans;
    }


    //totalShares
    public String getTotalShares() {
        return totalShares;
    }

    public void setTotalShares(String totalShares) {
        this.totalShares = totalShares;
    }


    //scannedProducts
    public List<String> getScannedProducts() {
        return scannedProducts;
    }

    public void setScannedProducts(List<String> scannedProducts) {
        this.scannedProducts = scannedProducts;
    }


    //favourateProducts
    public List<String> getFavourateProducts() {
        return favourateProducts;
    }

    public void setFavourateProducts(List<String> favourateProducts) {
        this.favourateProducts = favourateProducts;
    }
    
    
    //sharedProducts
    public List<String> getSharedProducts() {
        return sharedProducts;
    }

    public void setSharedProducts(List<String> sharedProducts) {
        this.sharedProducts = sharedProducts;
    }


    //scannedBrands
    public List<String> getScannedBrands() {
        return scannedBrands;
    }

    public void setScannedBrands(List<String> scannedBrands) {
        this.scannedBrands = scannedBrands;
    }


    //favouriteBrands
    public List<String> getFavouriteBrands() {
        return favouriteBrands;
    }

    public void setFavouriteBrands(List<String> favouriteBrands) {
        this.favouriteBrands = favouriteBrands;
    }


    //sharedBrands
    public List<String> getSharedBrands() {
        return sharedBrands;
    }

    public void setSharedBrands(List<String> sharedBrands) {
        this.sharedBrands = sharedBrands;
    }


    //scannedCategories
    public List<String> getScannedCategories() {
        return scannedCategories;
    }

    public void setScannedCategories(List<String> scannedCategories) {
        this.scannedCategories = scannedCategories;
    }

    //favourateCategoties
    public List<String> getFavourateCategories() {
        return favourateCategories;
    }

    public void setFavourateCategories(List<String> favourateCategories) {
        this.favourateCategories = favourateCategories;
    }


    //sharedCategories
    public List<String> getSharedCategories() {
        return sharedCategories;
    }

    public void setSharedCategories(List<String> sharedCategories) {
        this.sharedCategories = sharedCategories;
    }
}