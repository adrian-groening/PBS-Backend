package com.pbs.app.Search.ImpactSearch;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.pbs.app.Entities.APIKeys;
import com.pbs.app.Entities.Creator;
import com.pbs.app.Entities.Product;
import com.pbs.app.Search.ImpactSearch.ProductResults.Result;
import com.pbs.app.Services.Data;

@Service
public class ImpactSearch {

    private HttpClient client = HttpClient.newHttpClient();
    private String username = null;
    private String password = null;
    private String encodedCredentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    private String barcodeNumber = null;  
    private ProductResults pr = null;

    @Autowired
    @SuppressWarnings("unused")
    private Data data;

    public ImpactSearch() {
    }

    public ImpactSearch(Data data) {
        this.data = data;
    }
  
    public ImpactSearch(String code, String type, String email, Data data) {

        
        this.data = data;
        HttpResponse<String> response;

        //fetches creators api keys for searching
        try {
            Creator creator = data.getCreator(email);
            APIKeys keys = data.getAPIKeys(creator.getCreatorID());
            username = keys.getImpactUsername();
            password = keys.getImpactPassword();
            encodedCredentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

            //checks if the search type is barcode or name
            switch (type) {
                case "barcode" -> {
                    barcodeNumber = code;
                    try {
                        response = client.send(generateGtinQueryURL(barcodeNumber), HttpResponse.BodyHandlers.ofString());
                        String jsonResponse = response.body();
                        Gson gson = new Gson();
                        pr = gson.fromJson(jsonResponse, ProductResults.class);
                        
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Error sending request to API");
                    }
                }
                case "name" -> {
                    try {
                        response = client.send(generateNameQueryURL(code), HttpResponse.BodyHandlers.ofString());
                        String jsonResponse = response.body();
                        Gson gson = new Gson();
                        pr = gson.fromJson(jsonResponse, ProductResults.class);
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Error sending request to API");
                    }
                }
                default -> System.out.println("Invalid search type");
            }
        } catch (SQLException ex) {
            System.out.println("Error getting creator details");
        }
    }

    //generates a request to search the Impact API by barcode
    public HttpRequest generateGtinQueryURL(String barcode) {
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://products.api.impact.com/Mediapartners/IRjnYtzeHhaR116142yBQMg6n76dULQUn1/Marketplace/Products.json?Query=" + "Gtin=" + barcode))
        .header("Authorization", "Basic " + encodedCredentials)
        .build();
        return request;
    }

    //generates a request to search the Impact API by name
    public HttpRequest generateNameQueryURL(String name) {
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://products.api.impact.com/Mediapartners/IRjnYtzeHhaR116142yBQMg6n76dULQUn1/Marketplace/Products.json?Query=" + "Name=" + "%27" + name + "%27"))
        .header("Authorization", "Basic " + encodedCredentials)
        .build();
        return request;  
    }

    //returns a list of products as a string
    public String toAppProductList() {
        List<Product> ImpactProducts = new ArrayList<>();
        if (pr != null) {
            for (int i = 0; i < pr.getResults().size(); i++) {
                Product p;
                Result result = pr.getResults().get(i);
                if (result.getOffers() != null) {
                    for (int j = 0; j < result.getOffers().size(); j++) {
                        Random rand = new Random();
                        rand.nextInt();
                        p = new Product(
                            "P" + rand.nextInt(),
                            result.getName() != null ? result.getName() : "null",
                            result.getOffers().get(j).getCurrentPrice() != null ? result.getOffers().get(j).getCurrentPrice() : "null",
                            barcodeNumber != null ? barcodeNumber : "null",
                            result.getOffers().get(j).getCurrency() != null ? result.getOffers().get(j).getCurrency() : "null",
                            result.getImageUrl() != null ? result.getImageUrl() : "null",
                            result.getCategory() != null && result.getCategory().getName() != null ? result.getCategory().getName() : "null",
                            result.getManufacturer() != null && result.getManufacturer().getName() != null ? result.getManufacturer().getName() : "null",
                            result.getOffers().get(j).getMinCommissionPercentage() != null && result.getOffers().get(j).getMaxCommissionPercentage() != null ? result.getOffers().get(j).getMaxCommissionPercentage() : "null",
                            "Impact",
                            result.getOffers().get(j).getProgram().getLogoUri() != null ? result.getOffers().get(j).getProgram().getLogoUri() : "null",
                            result.getOffers().get(j).getUrl() != null ? result.getOffers().get(j).getUrl() : "null",
                            result.getOffers().get(j).getOriginalUrl() != null ? result.getOffers().get(j).getOriginalUrl() : "null",
                            result.getOffers().get(j).getDescription() != null ? result.getOffers().get(j).getDescription() : "null",
                            result.getOffers().get(j).getProgramEarningsPerClick() != null ? result.getOffers().get(j).getProgramEarningsPerClick() : "null",
                            result.getOffers().get(j).getTotalSalesVolume() != null ? result.getOffers().get(j).getTotalSalesVolume() : "null",
                            false,
                            false,
                            result.getOffers().get(j).isNew(),
                            false,
                            result.containsOfferOnSale(),
                            result.containsOfferOnPromotion(),
                            result.getOffers().get(j).getLastUpdated()
                        );
                        ImpactProducts.add(p);
                    }
                }
            }
            Gson gson = new Gson();
            String json = gson.toJson(ImpactProducts);
            System.out.println(json);
            return json;
        } else {
            return null;
        }
    }

    //returns a list of of products from the Impact API
    public List<Product> toList() {
        List<Product> ImpactProducts = new ArrayList<>();
        if (pr != null) {
            for (int i = 0; i < pr.getResults().size(); i++) {
                Product p;
                Result result = pr.getResults().get(i);
                if (result.getOffers() != null) {
                    for (int j = 0; j < result.getOffers().size(); j++) {
                        Random rand = new Random();
                        rand.nextInt();
                        p = new Product(
                            "P" + rand.nextInt(),
                            result.getName() != null ? result.getName() : "null",
                            result.getOffers().get(j).getCurrentPrice() != null ? result.getOffers().get(j).getCurrentPrice() : "null",
                            barcodeNumber != null ? barcodeNumber : "null",
                            result.getOffers().get(j).getCurrency() != null ? result.getOffers().get(j).getCurrency() : "null",
                            result.getImageUrl() != null ? result.getImageUrl() : "null",
                            result.getCategory() != null && result.getCategory().getName() != null ? result.getCategory().getName() : "null",
                            result.getManufacturer() != null && result.getManufacturer().getName() != null ? result.getManufacturer().getName() : "null",
                            result.getOffers().get(j).getMinCommissionPercentage() != null && result.getOffers().get(j).getMaxCommissionPercentage() != null ? result.getOffers().get(j).getMaxCommissionPercentage() : "null",
                            "Impact",
                            result.getOffers().get(j).getProgram().getLogoUri() != null ? result.getOffers().get(j).getProgram().getLogoUri() : "null",
                            result.getOffers().get(j).getUrl() != null ? result.getOffers().get(j).getUrl() : "null",
                            result.getOffers().get(j).getOriginalUrl() != null ? result.getOffers().get(j).getOriginalUrl() : "null",
                            result.getOffers().get(j).getDescription() != null ? result.getOffers().get(j).getDescription() : "null",
                            result.getOffers().get(j).getProgramEarningsPerClick() != null ? result.getOffers().get(j).getProgramEarningsPerClick() : "null",
                            result.getOffers().get(j).getTotalSalesVolume() != null ? result.getOffers().get(j).getTotalSalesVolume() : "null",
                            false,
                            false,
                            result.getOffers().get(j).isNew(),
                            false,
                            result.containsOfferOnSale(),
                            result.containsOfferOnPromotion(),
                            result.getOffers().get(j).getLastUpdated()
                        );
                        ImpactProducts.add(p);
                    }
                }
            }
            return ImpactProducts;
        } else {
            return null;
        }
    }
    
    
}
