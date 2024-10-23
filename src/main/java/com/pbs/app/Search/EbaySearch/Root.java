package com.pbs.app.Search.EbaySearch;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Root {
    @SerializedName("href")
    private String href;
    @SerializedName("total")
    private int total;
    @SerializedName("next")
    private String next;
    @SerializedName("limit")
    private int limit;
    @SerializedName("offset")
    private int offset;
    @SerializedName("itemSummaries")
    public List<itemSummary> itemSummaries;

    public Root() {
    }

    public Root(String href, int total, String next, int limit, int offset, List<itemSummary> itemSummaries) {
        this.href = href;
        this.total = total;
        this.next = next;
        this.limit = limit;
        this.offset = offset;
        this.itemSummaries = itemSummaries;
    }

    public String getHref() {
        return href;
    }

    public int getTotal() {
        return total;
    }

    public String getNext() {
        return next;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public List<itemSummary> getItemSummaries() {
        return itemSummaries;
    }
    

    public class itemSummary {
        @SerializedName("itemId")
        private String itemId;
        @SerializedName("title")
        public String title;
        @SerializedName("leafCategoryIds")
        private List<String> leafCategoryIds;
        @SerializedName("categories")
        private List<Category> categories;
        @SerializedName("image")
        public Image image;
        @SerializedName("price")
        public Price price;
        @SerializedName("itemHref")
        private String itemHref;
        @SerializedName("seller")
        public Seller seller;
        @SerializedName("condition")
        private String condition;
        @SerializedName("conditionId")
        private String conditionId;
        @SerializedName("thumbnailImages")
        private List<Image> thumbnailImages;
        @SerializedName("shippingOptions")
        private List<ShippingOption> shippingOptions;
        @SerializedName("buyingOptions")
        private List<String> buyingOptions;
        @SerializedName("itemAffiliateWebUrl")
        private String itemAffiliateWebUrl;
        @SerializedName("itemWebUrl")
        private String itemWebUrl;
        @SerializedName("itemLocation")
        private Location itemLocation;
        @SerializedName("additionalImages")
        private List<Image> additionalImages;
        @SerializedName("adultOnly")
        private boolean adultOnly;
        @SerializedName("legacyItemId")
        private String legacyItemId;
        @SerializedName("availableCoupons")
        private boolean availableCoupons;
        @SerializedName("itemCreationDate")
        private String itemCreationDate;
        @SerializedName("topRatedBuyingExperience")
        private boolean topRatedBuyingExperience;
        @SerializedName("priorityListing")
        private boolean priorityListing;
        @SerializedName("listingMarketplaceId")
        private String listingMarketplaceId;
        @SerializedName("commission")
        public String commission;

        public boolean isNew() {
            return condition.equals("New") || condition.equals("new") || condition.equals("New");
        }   

        public String getItemId() {
            return itemId;
        }
        public String getTitle() {
            return title;
        }
        public List<String> getLeafCategoryIds() {
            return leafCategoryIds;
        }
        public List<Category> getCategories() {
            return categories;
        }
        public Image getImage() {
            return image;
        }
        public Price getPrice() {
            return price;
        }
        public String getItemHref() {
            return itemHref;
        }
        public Seller getSeller() {
            return seller;
        }
        public String getCondition() {
            return condition;
        }
        public String getConditionId() {
            return conditionId;
        }
        public List<Image> getThumbnailImages() {
            return thumbnailImages;
        }
        public List<ShippingOption> getShippingOptions() {
            return shippingOptions;
        }

        public List<String> getBuyingOptions() {
            return buyingOptions;
        }

        public String getItemAffiliateWebUrl() {
            return itemAffiliateWebUrl;
        }

        public String getItemWebUrl() {
            return itemWebUrl;
        }


        public Location getItemLocation() {
            return itemLocation;
        }


        public List<Image> getAdditionalImages() {
            return additionalImages;
        }


        public boolean isAdultOnly() {
            return adultOnly;
        }

        public String getLegacyItemId() {
            return legacyItemId;
        }


        public boolean hasAvailableCoupons() {
            return availableCoupons;
        }

        public String getItemCreationDate() {
            return itemCreationDate;
        }

        public boolean isTopRatedBuyingExperience() {
            return topRatedBuyingExperience;
        }


        public boolean isPriorityListing() {
            return priorityListing;
        }


        public String getListingMarketplaceId() {
            return listingMarketplaceId;
        }

        public String getCommission() {
            return commission;
        }

        public void calculateCommission() {
            if (categories != null) {
                for (Category category : categories) {
                    String categoryId = category.getCategoryId();
                    switch (categoryId) {
                        case "12576" -> commission = "2.5"; // Business & Industrial
                        case "20081" -> commission = "3.0"; // Art & Antiques
                        case "11116" -> commission = "3.0"; // Coins & Paper Money
                        case "14339" -> commission = "3.0"; // Crafts
                        case "237" -> commission = "3.0"; // Dolls & Bears
                        case "45100" -> commission = "3.0"; // Entertainment Memorabilia
                        case "1" -> commission = "3.0"; // Miscellaneous Collectibles
                        case "870" -> commission = "3.0"; // Pottery, Glass
                        case "64482" -> commission = "3.0"; // Sports & Leisure
                        case "260" -> commission = "3.0"; // Stamps
                        case "220" -> commission = "3.0"; // Toys, Hobbies, and Games
                        case "625" -> commission = "2.0"; // Cameras & Photo
                        case "15032" -> commission = "2.0"; // Cell Phones & Accessories
                        case "293" -> commission = "2.0"; // Consumer Electronics/TV, Video & Audio
                        case "1249" -> commission = "2.0"; // Video Games & Consoles
                        case "58058" -> commission = "1.5"; // Computers/Tablets & Networking
                        case "11450" -> commission = "4.0"; // Clothing, Shoes & Accessories
                        case "26395" -> commission = "4.0"; // Health & Beauty
                        case "281" -> commission = "4.0"; // Jewelry & Watches
                        case "14308" -> commission = "3.0"; // Alcohol & Food
                        case "2984" -> commission = "3.0"; // Baby
                        case "40005" -> commission = "3.0"; // Miscellaneous Home & Garden/Appliances
                        case "1281" -> commission = "3.0"; // Pet Supplies
                        case "172008" -> commission = "3.0"; // Gift Cards & Coupons
                        case "3252" -> commission = "3.0"; // Miscellaneous Lifestyle
                        case "619" -> commission = "3.0"; // Musical Instruments
                        case "888" -> commission = "3.0"; // Sports
                        case "1305" -> commission = "3.0"; // Tickets & Events
                        case "267" -> commission = "3.0"; // Books, Comics & Magazines
                        case "11232" -> commission = "3.0"; // DVDs & Movies
                        case "11233" -> commission = "3.0"; // Music
                        case "9800" -> commission = "4.0"; // eBay Motors
                        case "6000" -> commission = "4.0"; // US Motors
                        case "131090" -> commission = "3.0"; // Vehicle Parts & Accessories
                        case "10542" -> commission = "1.0"; // Real Estate
                        default -> {
                        }
                    }
                    //commission = "default commission"; // Default commission for any unspecified categories
                                        
                }
            }
            

        }


    }

    public class Category {
        @SerializedName("categoryId")
        private String categoryId;
        @SerializedName("categoryName")
        public String categoryName;
        public String getCategoryId() {
            return categoryId;
        }
        public String getCategoryName() {
            return categoryName;
        }
        
    }

    public class Image {
        @SerializedName("imageUrl")
        public String imageUrl;
        @SerializedName("imageAltText")
        public String getImageUrl() {
            return imageUrl;
        }
    }

    public class Price {
        @SerializedName("value")
        public String value;
        @SerializedName("currency")
        public String currency;

        public String getCurrency() {
            return currency;
        }
        public String getValue() {
            return value;
        }
    }

    public class Seller {
        @SerializedName("username")
        public String username;
        @SerializedName("feedbackPercentage")
        private double feedbackPercentage;
        @SerializedName("feedbackScore")
        private int feedbackScore;

        public double getFeedbackPercentage() {
            return feedbackPercentage;
        }
        public int getFeedbackScore() {
            return feedbackScore;
        }

        public String getUsername() {
            return username;
        }
    }

    public class Location {
        @SerializedName("postalCode")
        private String postalCode;
        @SerializedName("country")
        private String country;

        public String getCountry() {
            return country;
        }

        public String getPostalCode() {
            return postalCode;
        }

    }

    public class ShippingOption {
        @SerializedName("shippingCostType")
        private String shippingCostType;
        @SerializedName("shippingCost")
        private Price shippingCost; // Optional, depends on type

        public Price getShippingCost() {
            return shippingCost;
        }

        public String getShippingCostType() {
            return shippingCostType;
        }
    }

    

}


