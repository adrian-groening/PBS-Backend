package com.pbs.app.ImpactSearch;

//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.pbs.Entities.Product;
import com.pbs.app.ImpactSearch.ProductResults.Offer;
import com.pbs.app.ImpactSearch.ProductResults.Result;


//@RestController
public class ImpactSearch {

    HttpClient client = HttpClient.newHttpClient();
    String username = "IRjnYtzeHhaR116142yBQMg6n76dULQUn1";
    String password = "U9zf2UncgYhJsAh.hVgf~ikoQXadBzGo";
    String encodedCredentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    String barcodeNumber = "";  
    ProductResults pr = null;
      
    public ImpactSearch(String code, String type) {
        HttpResponse<String> response = null;

        if (type.equals("barcode")) {
            barcodeNumber = code;
            try {
                response = client.send(generateGtinQueryURL(barcodeNumber), HttpResponse.BodyHandlers.ofString());
                String jsonResponse = response.body();
                // Parse the JSON response into a Product object
                Gson gson = new Gson();
                pr = gson.fromJson(jsonResponse, ProductResults.class);
    
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                System.out.println("Error sending request to API"); 
            }
        } else if (type.equals("name")) {
            try {
                response = client.send(generateNameQueryURL(code), HttpResponse.BodyHandlers.ofString());
                String jsonResponse = response.body();
                // Parse the JSON response into a Product object
                Gson gson = new Gson();
                pr = gson.fromJson(jsonResponse, ProductResults.class);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                System.out.println("Error sending request to API"); 
            }
        } else {
            System.out.println("Invalid search type");
        }

    }



    public HttpRequest generateGtinQueryURL(String barcode) {
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://products.api.impact.com/Mediapartners/IRjnYtzeHhaR116142yBQMg6n76dULQUn1/Marketplace/Products.json?Query=" + "Gtin=" + barcode))
        .header("Authorization", "Basic " + encodedCredentials)
        .build();
        return request;
    }

    public HttpRequest generateNameQueryURL(String name) {
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://products.api.impact.com/Mediapartners/IRjnYtzeHhaR116142yBQMg6n76dULQUn1/Marketplace/Products.json?Query=" + "Name=" + "%27" + name + "%27"))
        .header("Authorization", "Basic " + encodedCredentials)
        .build();
        return request;  
    }


    

    public String toAppProductList() {
        List<Product> ImpactProducts = new ArrayList<Product>();
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
                            result.containsOfferOnPromotion()
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

    public List<Product> toList() {
        List<Product> ImpactProducts = new ArrayList<Product>();
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
                            result.containsOfferOnPromotion()
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
