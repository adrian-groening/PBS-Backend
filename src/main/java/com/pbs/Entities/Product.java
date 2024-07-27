package com.pbs.Entities;




public class Product {
    String productID, name, price, barcode, currency, category, brand, commissionRate, affiliate, webURL, affiliateWebURL, description, earningsPerClick, totalSalesVolume, imageURL;
    boolean couponsAvailable, topRated, isNew, isPriorityListing, onSale, onPromotion;
    public int points;


    public Product() {
    }
    public Product(String productID, String name, String price, String barcode, String currency, String imageURL, String category, String brand, String commissionRate,
                   String affiliate, String webURL, String affiliateWebURL, String description, String earningsPerClick,
                   String totalSalesVolume, boolean couponsAvailable, boolean topRated, boolean isNew, boolean isPriorityListing,
                   boolean onSale, boolean onPromotion) {
        this.productID= productID;
        this.name = name;
        this.price = price;
        this.barcode = barcode;
        this.currency = currency;
        this.imageURL = imageURL;
        this.category = category;
        this.brand = brand;
        this.commissionRate = commissionRate;
        this.affiliate = affiliate;
        this.webURL = webURL;
        this.affiliateWebURL = affiliateWebURL;
        this.description = description;
        this.earningsPerClick = earningsPerClick;
        this.totalSalesVolume = totalSalesVolume;
        this.couponsAvailable = couponsAvailable;
        this.topRated = topRated;
        this.isNew = isNew;
        this.isPriorityListing = isPriorityListing;
        this.onSale = onSale;
        this.onPromotion = onPromotion;
        points = 0;
    }
    public String getProductID() {
        return productID;
    }
    public void setProductID(String productID) {
        this.productID = productID;
    }
    public String getName() {
        return name;
    }
    public String getPrice() {
        return price;
    }
    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public String getCurrency() {
        return currency;
    }
    public String getImageURL() {
        return imageURL;
    }
    public String getCategory() {
        return category;
    }
    public String getBrand() {
        return brand;
    }
    public String getCommissionRate() {
        return commissionRate;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setCommissionRate(String commissionRate) {
        this.commissionRate = commissionRate;
    }
    public String getAffiliate() {
        return affiliate;
    }
    public void setAffiliate(String affiliate) {
        this.affiliate = affiliate;
    }
    public String getWebURL() {
        return webURL;
    }
    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }
    public String getAffiliateWebURL() {
        return affiliateWebURL;
    }
    public void setAffiliateWebURL(String affiliateWebURL) {
        this.affiliateWebURL = affiliateWebURL;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getEarningsPerClick() {
        return earningsPerClick;
    }
    public void setEarningsPerClick(String earningsPerClick) {
        this.earningsPerClick = earningsPerClick;
    }
    public String getTotalSalesVolume() {
        return totalSalesVolume;
    }
    public void setTotalSalesVolume(String totalSalesVolume) {
        this.totalSalesVolume = totalSalesVolume;
    }
    public boolean hasCouponsAvailable() {
        return couponsAvailable;
    }
    public void setCouponsAvailable(boolean couponsAvailable) {
        this.couponsAvailable = couponsAvailable;
    }
    public boolean isTopRated() {
        return topRated;
    }
    public void setTopRated(boolean topRated) {
        this.topRated = topRated;
    }
    public boolean isNew() {
        return isNew;
    }
    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }
    public boolean isPriorityListing() {
        return isPriorityListing;
    }
    public void setPriorityListing(boolean priorityListing) {
        isPriorityListing = priorityListing;
    }
    public boolean isOnSale() {
        return onSale;
    }
    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }
    public boolean isOnPromotion() {
        return onPromotion;
    }
    public void setOnPromotion(boolean onPromotion) {
        this.onPromotion = onPromotion;
    }
    public void tallyPoints() {
        if (isNew) {
            points += 1;
        }
        if (onSale) {
            points += 1;
        }
        if (onPromotion) {
            points += 1;
        }
        if (topRated) {
            points += 1;
        }
        if (isPriorityListing) {
            points += 1;
        }
        if (couponsAvailable) {
            points += 1;
        }
    }


   
}
