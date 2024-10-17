package com.pbs.app;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pbs.app.Repositories.AnalyticsData;

class AnalyticsDataTests {

    private AnalyticsData analyticsData;
    private String totalScans;
    private String totalShares;
    private List<String> scannedProducts;
    private List<String> favouriteProducts;
    private List<String> sharedProducts;
    private List<String> scannedBrands;
    private List<String> favouriteBrands;
    private List<String> sharedBrands;
    private List<String> scannedCategories;
    private List<String> favouriteCategories;
    private List<String> sharedCategories;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        totalScans = "1000";
        totalShares = "500";
        scannedProducts = Arrays.asList("Product1", "Product2");
        favouriteProducts = Arrays.asList("FavProduct1", "FavProduct2");
        sharedProducts = Arrays.asList("SharedProduct1", "SharedProduct2");
        scannedBrands = Arrays.asList("Brand1", "Brand2");
        favouriteBrands = Arrays.asList("FavBrand1", "FavBrand2");
        sharedBrands = Arrays.asList("SharedBrand1", "SharedBrand2");
        scannedCategories = Arrays.asList("Category1", "Category2");
        favouriteCategories = Arrays.asList("FavCategory1", "FavCategory2");
        sharedCategories = Arrays.asList("SharedCategory1", "SharedCategory2");

        analyticsData = new AnalyticsData(totalScans, totalShares, scannedProducts, favouriteProducts, sharedProducts,
                scannedBrands, favouriteBrands, sharedBrands, scannedCategories, favouriteCategories, sharedCategories);
    }

    @Test
    void testConstructor() {
        assertEquals(totalScans, analyticsData.getTotalScans());
        assertEquals(totalShares, analyticsData.getTotalShares());
        assertEquals(scannedProducts, analyticsData.getScannedProducts());
        assertEquals(favouriteProducts, analyticsData.getFavourateProducts());
        assertEquals(sharedProducts, analyticsData.getSharedProducts());
        assertEquals(scannedBrands, analyticsData.getScannedBrands());
        assertEquals(favouriteBrands, analyticsData.getFavouriteBrands());
        assertEquals(sharedBrands, analyticsData.getSharedBrands());
        assertEquals(scannedCategories, analyticsData.getScannedCategories());
        assertEquals(favouriteCategories, analyticsData.getFavourateCategories());
        assertEquals(sharedCategories, analyticsData.getSharedCategories());
    }

    @Test
    void testSettersAndGetters() {
        // Modify and check totalScans
        analyticsData.setTotalScans("2000");
        assertEquals("2000", analyticsData.getTotalScans());

        // Modify and check totalShares
        analyticsData.setTotalShares("1000");
        assertEquals("1000", analyticsData.getTotalShares());

        // Modify and check scannedProducts
        List<String> newScannedProducts = Arrays.asList("Product3", "Product4");
        analyticsData.setScannedProducts(newScannedProducts);
        assertEquals(newScannedProducts, analyticsData.getScannedProducts());

        // Modify and check favouriteProducts
        List<String> newFavouriteProducts = Arrays.asList("FavProduct3", "FavProduct4");
        analyticsData.setFavourateProducts(newFavouriteProducts);
        assertEquals(newFavouriteProducts, analyticsData.getFavourateProducts());

        // Modify and check sharedProducts
        List<String> newSharedProducts = Arrays.asList("SharedProduct3", "SharedProduct4");
        analyticsData.setSharedProducts(newSharedProducts);
        assertEquals(newSharedProducts, analyticsData.getSharedProducts());

        // Modify and check scannedBrands
        List<String> newScannedBrands = Arrays.asList("Brand3", "Brand4");
        analyticsData.setScannedBrands(newScannedBrands);
        assertEquals(newScannedBrands, analyticsData.getScannedBrands());

        // Modify and check favouriteBrands
        List<String> newFavouriteBrands = Arrays.asList("FavBrand3", "FavBrand4");
        analyticsData.setFavouriteBrands(newFavouriteBrands);
        assertEquals(newFavouriteBrands, analyticsData.getFavouriteBrands());

        // Modify and check sharedBrands
        List<String> newSharedBrands = Arrays.asList("SharedBrand3", "SharedBrand4");
        analyticsData.setSharedBrands(newSharedBrands);
        assertEquals(newSharedBrands, analyticsData.getSharedBrands());

        // Modify and check scannedCategories
        List<String> newScannedCategories = Arrays.asList("Category3", "Category4");
        analyticsData.setScannedCategories(newScannedCategories);
        assertEquals(newScannedCategories, analyticsData.getScannedCategories());

        // Modify and check favouriteCategories
        List<String> newFavouriteCategories = Arrays.asList("FavCategory3", "FavCategory4");
        analyticsData.setFavourateCategories(newFavouriteCategories);
        assertEquals(newFavouriteCategories, analyticsData.getFavourateCategories());

        // Modify and check sharedCategories
        List<String> newSharedCategories = Arrays.asList("SharedCategory3", "SharedCategory4");
        analyticsData.setSharedCategories(newSharedCategories);
        assertEquals(newSharedCategories, analyticsData.getSharedCategories());
    }
}
