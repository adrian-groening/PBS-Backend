package com.pbs.app.Search.EbaySearch;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.io.IOException;
import com.google.gson.Gson;
import com.pbs.app.Entities.Product;
import com.pbs.app.Search.EbaySearch.Root.itemSummary;
import com.pbs.app.Search.ImpactSearch.ProductResults.Result;
import com.pbs.app.Services.Data;

public class EbaySearch {

    private HttpClient client = HttpClient.newHttpClient();
    private final String url = "https://api.ebay.com/buy/browse/v1/item_summary/search";
    private Root Root;
    String gtin = "";
    String token = "v^1.1#i^1#r^0#I^3#f^0#p^1#t^H4sIAAAAAAAAAOVYXWwUVRTev7K0tEAA/yrqdtAEi7s7s9Od3Zl0F7fdAiulW7pLC42muTNzp512d2Yz9y7tBjGbikhiNbxICEKCaJQoAj6oSAwSfTABTfEnBH/iA0bRIA8i2BAUnZluy7YSaOjGNHFfNnPuued+33fPuffMkPlZ5bVbV20dqbI6bXvzZN5mtVJzyPJZZcvm2m3VZRayyMG6N/9g3jFo/7kegXQqw7VBlFEVBF0D6ZSCONMYIrKawqkAyYhTQBoiDgtcIrKmmfN5SC6jqVgV1BThikVDBKBoUmQpiRVJAfhoqFuVsZhJNURIIBigJZ5hGSkokiyjjyOUhTEFYaDgEOEjfXVuinRT/iRVx5EsR1MeimQ7CVc71JCsKrqLhyTCJlzOnKsVYb05VIAQ1LAehAjHIisS8Ugs2tSSrPcWxQoXdEhggLNo4lOjKkJXO0hl4c2XQaY3l8gKAkSI8IZHV5gYlIuMgbkN+KbUPl/AD0hKFAM+SEJGLImUK1QtDfDNcRgWWXRLpisHFSzj3K0U1dXge6GAC08teohY1GX8rc2ClCzJUAsRTQ2RDZHWViIcETUZKCs1d2tDwt3aFnUDIFAMDwXBLTESTfFisLDGaKCCwpMWaVQVUTb0Qq4WFTdAHTCcKAvN+Ytk0Z3iSlyLSNgAU+zHjMtHdxr7ObqBWdyjGFsK07oGLvPx1uKPz8ZYk/kshuMRJg+Y6ugVlcnIIjF50EzDQuYMoBDRg3GG83r7+/s9/bRH1bq9PpKkvOvXNCeEHpgGxJivUetIvvUEt2xSEfQKHkAyh3MZHcuAnqY6AKWbCNexfjpIFXSfCCs82fovQxFn78RiKFVx8AzDQoqR+CBkKJ4NlKI4woX89Bo4IA9y7jTQ+iDOpIAA3YKeZ9k01GSRo/2Sjw5K0C0yrOSuYyXJzftFxk1JUK9UyPMCG/yf1MhUszwBBQ3i0qV5KVJ8Q98yWZLW92odAQZlJV5YzQQA29Ye7+5NgmXkQFQIRNVAQ9NAcl1oqoVwQ/KNKVlXJqmvX1oBjFqfrgirVIShOC16CUHNwFY1JQu5mbXBtCa2Ag3nEjCV0g3TIhnJZGIlPKZLQW/qJ8TtUS7xzfTf30o3ZIWMbJ1ZrIz5SA8AMrLHuHc8gpr2qkBvOAxTl4nY7OGnw1vWW9UZxVonOcpWFkd7TI9J2YM2Ch4NIjWr6e21J270XUm1Dyr6VYY1NZWCWvv0MsAo5XQ6iwGfgjOtpkuQ4DKYYfcsFfCx+gQ6MD1egnmLds20I6mEp/CYwUFPoY/2TnyhD1vMHzVo/YgctB6zWa1kPfkQtYSsmWVf57BXViMZQ48MJA+SuxX9PVWDnj6YywBZsy20XNz34qrG6qb4jtpNydyplz6xVBZ9T9j7BHn3+BeFcjs1p+jzArn4+kgZNe+uKl8dRVJ+qo5kaaqTXHJ91EHd6Vi0bfZIxcb+Xe9f/mXLElTh3L6y4o4rZNW4k9VaZnEMWi2zt3wh5mv3z99edeK1LTWLw39/s1NefZRv3vDIwkXaheGjj2+e//K5k77jNbb4D+F8T8tPrqeutm88Jx3648jHO3bNdQ49c+HNI6cV6vDB73vW1t53Zuidr9oOV+Czi0Zs9pYDyqWr3PHvNjtcXb577WeHTx449vDSNfOu5e55cviY68D8B16InRgJf+j4tmP3+TeGlp863Xto056duxsSKFr13G9/fd1c8eeZLmrTe6+uW37x3TZLaLj62aWVHfvLD84Dx/ck30aftezadiHsUBaseOX17c019c8772/ih758el/ud+/V5ySn89PKK5G1b13+XLoWz106jxuDIyt/fOxcZ9WOjv4PnI/OqVzw6+he/gO/H5mp6REAAA==";

    public EbaySearch(String code, String type) throws IOException, InterruptedException {
    
        if (type.equals("barcode")) {
            Root = fetchProductDetailsUsingGtin(code);
        } else if (type.equals("name")) {
            Root = fetchProductDetailsUsingName(code);
        }

    }

    private Root fetchProductDetailsUsingGtin(String gtin) throws IOException, InterruptedException {
        String fullUrl = String.format("%s?gtin=%s&limit=15", url, gtin);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("Authorization", "Bearer " + token)
                .header("X-EBAY-C-MARKETPLACE-ID", "EBAY_US")
                .header("X-EBAY-C-ENDUSERCTX", "affiliateCampaignId=<ePNCampaignId>,affiliateReferenceId=<referenceId>")
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            Root = gson.fromJson(response.body(), Root.class);
            return gson.fromJson(response.body(), Root.class);
        } else {
            System.out.println("Failed to fetch product details: " + response.statusCode());
            return null;
        }
    }

        private Root fetchProductDetailsUsingName(String name) throws IOException, InterruptedException {
        String fullUrl = String.format("%s?q=%s&limit=15", url, name);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("Authorization", "Bearer " + token)
                .header("X-EBAY-C-MARKETPLACE-ID", "EBAY_US")
                .header("X-EBAY-C-ENDUSERCTX", "affiliateCampaignId=<ePNCampaignId>,affiliateReferenceId=<referenceId>")
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            Root = gson.fromJson(response.body(), Root.class);
            return gson.fromJson(response.body(), Root.class);
        } else {
            System.out.println("Failed to fetch product details: " + response.statusCode());
            return null;
        }
    }



    public String toAppProductList() {
        List<Product> EbayProducts = new ArrayList<Product>();
        if (Root != null) {
            for (int i = 0; i < Root.itemSummaries.size(); i++) {
                Product p;
                itemSummary item = Root.itemSummaries.get(i);
                item.calculateCommission();
                Random rand = new Random();
                p = new Product("P" + rand.nextInt(), item.getTitle(), item.getPrice().getValue(), gtin, item.getPrice().getCurrency(), item.getImage().getImageUrl(), item.getCategories().get(0).getCategoryName(), item.getSeller().getUsername(), item.getCommission(), "eBay", "ebay", item.getItemHref(), item.getItemAffiliateWebUrl(), "", "", "", item.hasAvailableCoupons(), item.isTopRatedBuyingExperience(), item.isNew(), item.isPriorityListing(), false, false, item.getItemCreationDate());
                EbayProducts.add(p);
            }
            Gson gson = new Gson();
            String json = gson.toJson(EbayProducts);
            System.out.println("JSON: "+json);
            return json;
        } else {
            System.out.println("No product details found");
            return null;
        }
    }

    public List<Product> toList() {
        List<Product> EbayProducts = new ArrayList<>();
        if ((Root != null)&& (Root.itemSummaries != null)) {
            for (int i = 0; i < Root.itemSummaries.size(); i++) {
                Product p;
                itemSummary item = Root.itemSummaries.get(i);
                item.calculateCommission();
                Random rand = new Random();
                p = new Product("P" + rand.nextInt(), item.getTitle(), item.getPrice().getValue(), gtin, item.getPrice().getCurrency(), item.getImage().getImageUrl(), item.getCategories().get(0).getCategoryName(), item.getSeller().getUsername(), item.getCommission(), "eBay","ebay", item.getItemHref(), item.getItemAffiliateWebUrl(), "", "", "", item.hasAvailableCoupons(), item.isTopRatedBuyingExperience(), item.isNew(), item.isPriorityListing(), false, false, item.getItemCreationDate());
                EbayProducts.add(p);
            }
            return EbayProducts;
        } else {
            System.out.println("No product details found");
            return null;
        }
    }

}
