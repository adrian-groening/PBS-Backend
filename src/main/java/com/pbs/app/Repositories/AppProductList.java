package com.pbs.app.Repositories;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pbs.app.Entities.Product;



public class AppProductList {
    List<Product> products;
    Map<Product, Integer> productTally = new HashMap<>();

    public AppProductList() {
        products = new ArrayList<>();
    }

    public AppProductList(List<Product> products) {
        this.products = products;
        for (Product product : products) {
            productTally.put(product, 0);
        }
    }

    public void sortByPrice() {
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Double.compare(Double.parseDouble(p1.getPrice()), Double.parseDouble(p2.getPrice()));
            }
        });
        for (int i = products.size() - 1; i >= 0; i--) {
            productTally.put(products.get(i), products.size() - 1 - i);
        }
    }

    public void sortByCommission() {        
        // Sorting in descending order by reversing the operands in the Double.compare method
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Double.compare(Double.parseDouble(p2.getCommissionRate()), Double.parseDouble(p1.getCommissionRate()));
            }
        });
        // Assigning points where the highest commission gets the most points
        for (int i = 0; i < products.size(); i++) {
            productTally.put(products.get(i), products.size() - 1 - i);
        }
    }

    public void synthesizePoints() {
        for (int i = products.size() - 1; i >= 0; i--) {
            sortByPrice();
            int pricePoint = productTally.get(products.get(i));
            sortByCommission();
            int commissionPoint = productTally.get(products.get(i));
            products.get(i).tallyPoints();
            productTally.put(products.get(i), pricePoint + commissionPoint + products.get(i).points);
        } 
    }

    public void sortByValue() {
        synthesizePoints();
        productTally = productTally.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(LinkedHashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), Map::putAll);
    }


    public List<Product> getProducts() {
        return products;
    }

    public Map<Product, Integer> getProductTally() {
        return productTally;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProducts(List<Product> products) {
        this.products.addAll(products);
    }


    public void convertToUSD() throws IOException, InterruptedException {
        Currencies currencies = fetchAndMapCurrencies();
    
        for (Product product : products) {
            String productCurrency = product.getCurrency();
            double conversionRate = currencies.getConversionRate(productCurrency);
    
            // Debugging output
            System.out.println("Converting " + product.getName() + " (" + productCurrency + ") to USD");
    
            // Validate conversion rate
            if (conversionRate == 0) {
                System.out.println("Warning: Conversion rate for " + productCurrency + " is zero.");
                continue;
            }
            double priceInUSD = Double.parseDouble(product.getPrice()) / conversionRate;
            product.setPrice(Double.toString(priceInUSD));
            product.setCurrency("USD");
        }
    }

    public class Currencies {
        private Map<String, Double> data = new HashMap<>();
        public Map<String, Double> getData() {
            return data;
        }
        public double getConversionRate(String currency) {
            Double rate = data.get(currency.toUpperCase());
            if (rate == null) {
                return 1.0; 
            }
            return rate;
        }
        Currencies() {
        }
    }

    public Currencies fetchAndMapCurrencies() throws IOException, InterruptedException {
        String API_KEY = "fca_live_T5oOr3bktIm1jUtnHzCdtdhlS9sXyAT5bHwGDY3t"; 
        String API_URL = "https://api.freecurrencyapi.com/v1/latest?apikey=" + API_KEY;
        Currencies currencies = new Currencies();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch currency rates: HTTP status code " + response.statusCode());
        }

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        JsonElement dataElement = jsonObject.get("data");

        if (dataElement.isJsonObject()) {
            JsonObject dataObject = dataElement.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : dataObject.entrySet()) {
                currencies.getData().put(entry.getKey(), entry.getValue().getAsDouble());
            }
        }

        System.out.println("USD: " + currencies.getData().get("USD"));
        System.out.println("AUD: " + currencies.getData().get("AUD"));
        System.out.println("BGN: " + currencies.getData().get("BGN"));
        System.out.println("BRL: " + currencies.getData().get("BRL"));
        System.out.println("CAD: " + currencies.getData().get("CAD"));
        System.out.println("CHF: " + currencies.getData().get("CHF"));
        System.out.println("CNY: " + currencies.getData().get("CNY"));
        return currencies;
    }

    

    
}
