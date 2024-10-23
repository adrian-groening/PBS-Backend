package com.pbs.app.Search.ImpactSearch;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ProductResults {
    
    @SerializedName("Results")
    private List<Result> Results;

    @SuppressWarnings("unused")
    ProductResults() {
    }

    @SuppressWarnings("unused")
    ProductResults(List<Result> results) {
        Results = results;
    }
    
    public List<Result> getResults() {
        return Results;
    }
    public void setResults(List<Result> results) {
        Results = results;
    }

    public class Category {
        @SerializedName("Path")
        private String Path;
        @SerializedName("Name")
        private String Name;
        @SerializedName("SearchUri")
        private String SearchUri;

        @SuppressWarnings("unused")
        Category() {
        }

        @SuppressWarnings("unused")
        Category(String path, String name, String searchUri) {
            Path = path;
            Name = name;
            SearchUri = searchUri;
        }   

        public String getPath() {
            return Path;
        }
        public String getName() {
            return Name;
        }
        public String getSearchUri() {
            return SearchUri;
        }
        public void setPath(String path) {
            Path = path;
        }
        public void setName(String name) {
            Name = name;
        }
        public void setSearchUri(String searchUri) {
            SearchUri = searchUri;
        }
    }

    public class Manufacturer {
        @SerializedName("Name")
        private String Name;

        @SerializedName("SearchUri")
        private String SearchUri;

        @SuppressWarnings("unused")
        Manufacturer() {
        }

        @SuppressWarnings("unused")
        Manufacturer(String name, String searchUri) {
            Name = name;
            SearchUri = searchUri;
        }
  
        public String getName() {
            return Name;
        }

        public String getSearchUri() {
            return SearchUri;
        }

        public void setName(String name) {
            Name = name;
        }

        public void setSearchUri(String searchUri) {
            SearchUri = searchUri;
        }
    }


    public class Offer {
        @SerializedName("RelationshipIndicator")
        private String relationshipIndicator;
        @SerializedName("Program")
        private Program program;
        @SerializedName("CatalogId")
        private String catalogId;
        @SerializedName("catelogItemId")
        private String catalogItemId;
        @SerializedName("Sku")
        private String sku;
        @SerializedName("Gtin")
        private String gtin;
        @SerializedName("LastUpdated")
        private String lastUpdated;
        @SerializedName("Url")
        public String url;
        @SerializedName("OriginalUrl")
        private String originalUrl;
        @SerializedName("RelationshipStatusUri")
        private String relationshipStatusUri;
        @SerializedName("ContractApplyUri")
        private String contractApplyUri;
        @SerializedName("AcceptContractUri")
        private String acceptContractUri;
        @SerializedName("GenerateShortUrlUri")
        private String generateShortUrlUri;
        @SerializedName("Name")
        private String name;
        @SerializedName("Description")
        public String description;
        @SerializedName("ImageUrl")
        private String imageUrl;
        @SerializedName("AdditionalImageUrls")
        private List<String> additionalImageUrls;
        @SerializedName("CurrentPrice")
        public String currentPrice;
        @SerializedName("OriginalPrice")
        public String originalPrice;
        @SerializedName("DiscountPercentage")
        private String discountPercentage;
        @SerializedName("Currency")
        private String currency;
        @SerializedName("MinCommissionPercentage")
        private String minCommissionPercentage;
        @SerializedName("MaxCommissionPercentage")
        private String maxCommissionPercentage;
        @SerializedName("ProgramEarningsPerClick")
        private String programEarningsPerClick;
        @SerializedName("TotalCommission")
        private String totalCommission;
        @SerializedName("TotalSales")
        private String totalSales;
        @SerializedName("TotalSalesVolume")
        private String totalSalesVolume;
        @SerializedName("StockAvailability")
        private String stockAvailability;
        @SerializedName("Condition")
        private String condition = "";    
       
        public boolean isNew() {
            if (condition != null) {
                return condition.equals("New");
            } else {
                return false;
            }
        }
        
        public String getRelationshipIndicator() {
            return relationshipIndicator;
        }
        public Program getProgram() {
            return program;
        }
        public String getCatalogId() {
            return catalogId;
        }
        public String getCatalogItemId() {
            return catalogItemId;
        }
        public String getSku() {
            return sku;
        }
        public String getGtin() {
            return gtin;
        }
        public String getLastUpdated() {
            return lastUpdated;
        }
        public String getUrl() {
            return url;
        }
        public String getOriginalUrl() {
            return originalUrl;
        }
        public String getRelationshipStatusUri() {
            return relationshipStatusUri;
        }
        public String getContractApplyUri() {
            return contractApplyUri;
        }
        public String getAcceptContractUri() {
            return acceptContractUri;
        }
        public String getGenerateShortUrlUri() {
            return generateShortUrlUri;
        }
        public String getName() {
            return name;
        }
        public String getDescription() {
            return description;
        }
        public String getImageUrl() {
            return imageUrl;
        }
        public List<String> getAdditionalImageUrls() {
            return additionalImageUrls;
        }
        public String getCurrentPrice() {
            return currentPrice;
        }
        public String getOriginalPrice() {
            return originalPrice;
        }
        public String getDiscountPercentage() {
            return discountPercentage;
        }
        public String getCurrency() {
            return currency;
        }
        public String getMinCommissionPercentage() {
            return minCommissionPercentage;
        }
        public String getMaxCommissionPercentage() {
            return maxCommissionPercentage;
        }
        public String getProgramEarningsPerClick() {
            return programEarningsPerClick;
        }
        public String getTotalCommission() {
            return totalCommission;
        }
        public String getTotalSales() {
            return totalSales;
        }
        public String getTotalSalesVolume() {
            return totalSalesVolume;
        }
        public String getStockAvailability() {
            return stockAvailability;
        }
        public String getCondition() {
            return condition;
        }
        public void setRelationshipIndicator(String relationshipIndicator) {
            this.relationshipIndicator = relationshipIndicator;
        }
        public void setProgram(Program program) {
            this.program = program;
        }
        public void setCatalogId(String catalogId) {
            this.catalogId = catalogId;
        }
        public void setCatalogItemId(String catalogItemId) {
            this.catalogItemId = catalogItemId;
        }
        public void setSku(String sku) {
            this.sku = sku;
        }
        public void setGtin(String gtin) {
            this.gtin = gtin;
        }
        public void setLastUpdated(String lastUpdated) {
            this.lastUpdated = lastUpdated;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        public void setOriginalUrl(String originalUrl) {
            this.originalUrl = originalUrl;
        }
        public void setRelationshipStatusUri(String relationshipStatusUri) {
            this.relationshipStatusUri = relationshipStatusUri;
        }
        public void setContractApplyUri(String contractApplyUri) {
            this.contractApplyUri = contractApplyUri;
        }
        public void setAcceptContractUri(String acceptContractUri) {
            this.acceptContractUri = acceptContractUri;
        }
        public void setGenerateShortUrlUri(String generateShortUrlUri) {
            this.generateShortUrlUri = generateShortUrlUri;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
        public void setAdditionalImageUrls(List<String> additionalImageUrls) {
            this.additionalImageUrls = additionalImageUrls;
        }
        public void setCurrentPrice(String currentPrice) {
            this.currentPrice = currentPrice;
        }
        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }
        public void setDiscountPercentage(String discountPercentage) {
            this.discountPercentage = discountPercentage;
        }
        public void setCurrency(String currency) {
            this.currency = currency;
        }
        public void setMinCommissionPercentage(String minCommissionPercentage) {
            this.minCommissionPercentage = minCommissionPercentage;
        }
        public void setMaxCommissionPercentage(String maxCommissionPercentage) {
            this.maxCommissionPercentage = maxCommissionPercentage;
        }
        public void setProgramEarningsPerClick(String programEarningsPerClick) {
            this.programEarningsPerClick = programEarningsPerClick;
        }
        public void setTotalCommission(String totalCommission) {
            this.totalCommission = totalCommission;
        }
        public void setTotalSales(String totalSales) {
            this.totalSales = totalSales;
        }
        public void setTotalSalesVolume(String totalSalesVolume) {
            this.totalSalesVolume = totalSalesVolume;
        }
        public void setStockAvailability(String stockAvailability) {
            this.stockAvailability = stockAvailability;
        }
        public void setCondition(String condition) {
            this.condition = condition;
        }
        
    }

    public class Program {
        @SerializedName("Id")
        private String id;
        @SerializedName("Name")
        private String name;
        @SerializedName("LogoUri")
        private String logoUri;
        @SerializedName("SearchUri")
        private String searchUri;
        @SerializedName("AdvertiserId")
        private String advertiserId;
        @SuppressWarnings("unused")
        Program() {
        }
        @SuppressWarnings("unused")
        Program(String id, String name, String logoUri, String searchUri, String advertiserId) {
            this.id = id;
            this.name = name;
            this.logoUri = logoUri;
            this.searchUri = searchUri;
            this.advertiserId = advertiserId;
        }
        public String getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public String getLogoUri() {
            return logoUri;
        }
        public String getSearchUri() {
            return searchUri;
        }
        public String getAdvertiserId() {
            return advertiserId;
        }
        public void setId(String id) {
            this.id = id;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setLogoUri(String logoUri) {
            this.logoUri = logoUri;
        }
        public void setSearchUri(String searchUri) {
            this.searchUri = searchUri;
        }
        public void setAdvertiserId(String advertiserId) {
            this.advertiserId = advertiserId;
        }

    }

    public class Result {
        @SerializedName("Id")
        private String id;

        @SerializedName("GlobalIdentifiers")
        private List<String> globalIdentifiers;

        @SerializedName("Name")
        private String name;

        @SerializedName("ImageUrl")
        private String imageUrl;

        @SerializedName("Category")
        private Category category;

        @SerializedName("Labels")
        private List<String> labels;

        @SerializedName("Manufacturer")
        private Manufacturer manufacturer;

        @SerializedName("ContainsOfferInStock")
        private boolean containsOfferInStock;

        @SerializedName("ContainsOfferOnSale")
        private boolean containsOfferOnSale;

        @SerializedName("ContainsOfferOnPromotion")
        private boolean containsOfferOnPromotion;

        @SerializedName("ContainsOfferPreQualified")
        private boolean containsOfferPreQualified;

        @SerializedName("BestPrice")
        private String bestPrice;

        @SerializedName("Currency")
        private String currency;

        @SerializedName("BestCommissionPercentage")
        private String bestCommissionPercentage;

        @SerializedName("BestEarningsPerClick")
        private String bestEarningsPerClick;

        @SerializedName("TotalCommission")
        private String totalCommission;

        @SerializedName("TotalSales")
        private String totalSales;

        @SerializedName("TotalSalesVolume")
        private String totalSalesVolume;

        @SerializedName("Offers")
        private List<Offer> offers;

        
        public String getId() {
            return id;
        }
        public List<String> getGlobalIdentifiers() {
            return globalIdentifiers;
        }
        public String getName() {
            return name;
        }
        public String getImageUrl() {
            return imageUrl;
        }
        public Category getCategory() {
            return category;
        }
        public List<String> getLabels() {
            return labels;
        }
        public Manufacturer getManufacturer() {
            return manufacturer;
        }
        public boolean containsOfferInStock() {
            return containsOfferInStock;
        }
        public boolean containsOfferOnSale() {
            return containsOfferOnSale;
        }
        public boolean containsOfferOnPromotion() {
            return containsOfferOnPromotion;
        }
        public boolean containsOfferPreQualified() {
            return containsOfferPreQualified;
        }
        public String getBestPrice() {
            return bestPrice;
        }
        public String getCurrency() {
            return currency;
        }
        public String getBestCommissionPercentage() {
            return bestCommissionPercentage;
        }
        public String getBestEarningsPerClick() {
            return bestEarningsPerClick;
        }
        public String getTotalCommission() {
            return totalCommission;
        }
        public String getTotalSales() {
            return totalSales;
        }
        public String getTotalSalesVolume() {
            return totalSalesVolume;
        }
        public List<Offer> getOffers() {
            return offers;
        }
        public void setId(String id) {
            this.id = id;
        }
        public void setGlobalIdentifiers(List<String> globalIdentifiers) {
            this.globalIdentifiers = globalIdentifiers;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
        public void setCategory(Category category) {
            this.category = category;
        }
        public void setLabels(List<String> labels) {
            this.labels = labels;
        }
        public void setManufacturer(Manufacturer manufacturer) {
            this.manufacturer = manufacturer;
        }
        public void setContainsOfferInStock(boolean containsOfferInStock) {
            this.containsOfferInStock = containsOfferInStock;
        }
        public void setContainsOfferOnSale(boolean containsOfferOnSale) {
            this.containsOfferOnSale = containsOfferOnSale;
        }
        public void setContainsOfferOnPromotion(boolean containsOfferOnPromotion) {
            this.containsOfferOnPromotion = containsOfferOnPromotion;
        }
        public void setContainsOfferPreQualified(boolean containsOfferPreQualified) {
            this.containsOfferPreQualified = containsOfferPreQualified;
        }
        public void setBestPrice(String bestPrice) {
            this.bestPrice = bestPrice;
        }
        public void setCurrency(String currency) {
            this.currency = currency;
        }
        public void setBestCommissionPercentage(String bestCommissionPercentage) {
            this.bestCommissionPercentage = bestCommissionPercentage;
        }
        public void setBestEarningsPerClick(String bestEarningsPerClick) {
            this.bestEarningsPerClick = bestEarningsPerClick;
        }
        public void setTotalCommission(String totalCommission) {
            this.totalCommission = totalCommission;
        }
        public void setTotalSales(String totalSales) {
            this.totalSales = totalSales;
        }
        public void setTotalSalesVolume(String totalSalesVolume) {
            this.totalSalesVolume = totalSalesVolume;
        }
        public void setOffers(List<Offer> offers) {
            this.offers = offers;
        }

    }

}
