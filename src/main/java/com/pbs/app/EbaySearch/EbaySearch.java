package com.pbs.app.EbaySearch;

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
import com.pbs.Entities.Product;
import com.pbs.app.EbaySearch.Root.itemSummary;
import com.pbs.app.ImpactSearch.ProductResults.Result;
import com.pbs.app.Services.Data;

public class EbaySearch {

    private HttpClient client = HttpClient.newHttpClient();
    private final String url = "https://api.ebay.com/buy/browse/v1/item_summary/search";
    private Root Root;
    String gtin = "";

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
                .header("Authorization", "Bearer " + "v^1.1#i^1#I^3#r^0#f^0#p^1#t^H4sIAAAAAAAAAOVYf2wTVRxft244GfjH+CWQrN400Y27vrte2+tlLXY/gAmjcy0DFgZ5d/duu629K/eubJWgs0FiRoQEZSJLFEEX0EVNMKhETQCR+AdhGv4giAkRJSQaYggJEkz0ru1GNwksrDFL7D/Nfd/3fd/n83nf73vfO9BXUlq1Y8WOW7NsMwoP9IG+QpuNnglKS4qrZxcVLiwuADkOtgN9T/bZU0XXajCMReN8C8JxTcXI0RuLqphPG/1EQld5DWIF8yqMIcwbIh8ONq3iGQrwcV0zNFGLEo7Gej8hcLTP5XEhDxQlwAJgWtXRmBHNT7g46PZJwCXTspdjJK85jnECNarYgKrhJxjAsCTgSIaOAC/vZnmGo2jW1UY4WpGOFU01XShABNJw+fRcPQfr/aFCjJFumEGIQGNwWTgUbKxvWB2pcebECmR1CBvQSODxT3WahBytMJpA918Gp735cEIUEcaEM5BZYXxQPjgK5iHgZ6SmPW5JAjLnFYAAWTovUi7T9Bg07o/DsigSKaddeaQaipF8kKKmGkIXEo3s02ozRGO9w/p7PgGjiqwg3U801AbXB5ubiUBQ0hWoLtfJ5tow2dxST0Io0h4BiSIpe2QXLUhcdo1MoKzCExap01RJsfTCjtWaUYtMwGiiLGyOLKZTSA3pQdmwwOT6+Ublc3nbrP3MbGDC6FStLUUxUwNH+vHB4o/NNgxdERIGGoswcSCtjp+A8bgiERMH02mYzZxe7Cc6DSPOO509PT1Uj4vS9A4nAwDtXNe0Kix2ohgkRn2tWsfKgyeQSpqKiMyZWOGNZNzE0mumqQlA7SACrM/t4uis7uNhBSZa/2XI4ewcXwx5Kw6fxEEk0NZpxAHWlY/iCGTz02nhQAJMkjGodyMjHoUiIkUzzxIxpCsS73LLjIuTESl5fDLJ+mSZFNySh6RlhABCgiD6uP9JjUw2y8NI1JGRvzTPR4qv765WZHldl77W68EJWRBXerzQ19Ia6uiKwGrQWy966zVvbUNvZI1/soVwT/J1UcVUJmKun18BrFqfqggrNGwgaUr0wqIWR81aVBGT02uDXbrUDHUjGUbRqGmYEslgPN6Yx2M6H/Qmf0I8HOU830z//a10T1bYytbpxcqaj80AMK5Q1r1DiVrMqUGz4bBMm9KI0z38VHgrZqs6rVibJDNsFSnTY1JpyhTeIlI6wlpCN9trKmT1XRGtG6nmVWboWjSK9NapZYBVyrFYwoBCFE23ms5Dgitwmt2ztJdhGcbDeaZ2HInpW3TTdDuS8ngKjxrsrkn00c7xL/SBgvSPTtlOgpTt60KbDdSAp+hK8ERJ0Rp7UdlCrBiIUqBMYaVDNd9TdUR1o2QcKnphecGNg3tX1C1sCA1UbY0kRwbPFJTlfE840A4WjH1RKC2iZ+Z8XgCL744U04/Nn8WwgGNo4HWzDNcGKu+O2ul59jl9FZ/dilw8vfj6oiVh4cIflwYr+i6DWWNONltxgT1lK2Db55/Zs6B0c//1n78qGdj9y7uvvX+4wnfhyuaVw53H9vULL28bCA1/0fRq+KffTwHsO/FplXLutFe88rT99g+tK2sCHwmU+/t9duWIcy35aH/kk6MbH7lzteyFLbuW/LXr15E5507Nvll+y/Hmtxv2PSddVM8u3dg5dGjp7fKTlS3l++eOyBUvDZ493/Qj+8rQG9u7Fh0SDlYObbs6HFkyfJTdcOPjYV+dY97Q+co/Pz++5lJy7XftRwbaD29Jzbg8e+fhE/vXCV96eLlsYHus9nUa7d+6uOXQb3feOv73h46D6m7nB7dPP4vmXrzWf/Pc5bev70lVtQ0OpR5/cefQM9+8d7T6wrF3Tm7ay42omb38B6ZuPmnpEQAA")
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
                .header("Authorization", "Bearer " + "v^1.1#i^1#I^3#r^0#f^0#p^1#t^H4sIAAAAAAAAAOVYf2wTVRxft244GfjH+CWQrN400Y27vrte2+tlLXY/gAmjcy0DFgZ5d/duu629K/eubJWgs0FiRoQEZSJLFEEX0EVNMKhETQCR+AdhGv4giAkRJSQaYggJEkz0ru1GNwksrDFL7D/Nfd/3fd/n83nf73vfO9BXUlq1Y8WOW7NsMwoP9IG+QpuNnglKS4qrZxcVLiwuADkOtgN9T/bZU0XXajCMReN8C8JxTcXI0RuLqphPG/1EQld5DWIF8yqMIcwbIh8ONq3iGQrwcV0zNFGLEo7Gej8hcLTP5XEhDxQlwAJgWtXRmBHNT7g46PZJwCXTspdjJK85jnECNarYgKrhJxjAsCTgSIaOAC/vZnmGo2jW1UY4WpGOFU01XShABNJw+fRcPQfr/aFCjJFumEGIQGNwWTgUbKxvWB2pcebECmR1CBvQSODxT3WahBytMJpA918Gp735cEIUEcaEM5BZYXxQPjgK5iHgZ6SmPW5JAjLnFYAAWTovUi7T9Bg07o/DsigSKaddeaQaipF8kKKmGkIXEo3s02ozRGO9w/p7PgGjiqwg3U801AbXB5ubiUBQ0hWoLtfJ5tow2dxST0Io0h4BiSIpe2QXLUhcdo1MoKzCExap01RJsfTCjtWaUYtMwGiiLGyOLKZTSA3pQdmwwOT6+Ublc3nbrP3MbGDC6FStLUUxUwNH+vHB4o/NNgxdERIGGoswcSCtjp+A8bgiERMH02mYzZxe7Cc6DSPOO509PT1Uj4vS9A4nAwDtXNe0Kix2ohgkRn2tWsfKgyeQSpqKiMyZWOGNZNzE0mumqQlA7SACrM/t4uis7uNhBSZa/2XI4ewcXwx5Kw6fxEEk0NZpxAHWlY/iCGTz02nhQAJMkjGodyMjHoUiIkUzzxIxpCsS73LLjIuTESl5fDLJ+mSZFNySh6RlhABCgiD6uP9JjUw2y8NI1JGRvzTPR4qv765WZHldl77W68EJWRBXerzQ19Ia6uiKwGrQWy966zVvbUNvZI1/soVwT/J1UcVUJmKun18BrFqfqggrNGwgaUr0wqIWR81aVBGT02uDXbrUDHUjGUbRqGmYEslgPN6Yx2M6H/Qmf0I8HOU830z//a10T1bYytbpxcqaj80AMK5Q1r1DiVrMqUGz4bBMm9KI0z38VHgrZqs6rVibJDNsFSnTY1JpyhTeIlI6wlpCN9trKmT1XRGtG6nmVWboWjSK9NapZYBVyrFYwoBCFE23ms5Dgitwmt2ztJdhGcbDeaZ2HInpW3TTdDuS8ngKjxrsrkn00c7xL/SBgvSPTtlOgpTt60KbDdSAp+hK8ERJ0Rp7UdlCrBiIUqBMYaVDNd9TdUR1o2QcKnphecGNg3tX1C1sCA1UbY0kRwbPFJTlfE840A4WjH1RKC2iZ+Z8XgCL744U04/Nn8WwgGNo4HWzDNcGKu+O2ul59jl9FZ/dilw8vfj6oiVh4cIflwYr+i6DWWNONltxgT1lK2Db55/Zs6B0c//1n78qGdj9y7uvvX+4wnfhyuaVw53H9vULL28bCA1/0fRq+KffTwHsO/FplXLutFe88rT99g+tK2sCHwmU+/t9duWIcy35aH/kk6MbH7lzteyFLbuW/LXr15E5507Nvll+y/Hmtxv2PSddVM8u3dg5dGjp7fKTlS3l++eOyBUvDZ493/Qj+8rQG9u7Fh0SDlYObbs6HFkyfJTdcOPjYV+dY97Q+co/Pz++5lJy7XftRwbaD29Jzbg8e+fhE/vXCV96eLlsYHus9nUa7d+6uOXQb3feOv73h46D6m7nB7dPP4vmXrzWf/Pc5bev70lVtQ0OpR5/cefQM9+8d7T6wrF3Tm7ay42omb38B6ZuPmnpEQAA")
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
                p = new Product("P" + rand.nextInt(), item.getTitle(), item.getPrice().getValue(), gtin, item.getPrice().getCurrency(), item.getImage().getImageUrl(), item.getCategories().get(0).getCategoryName(), item.getSeller().getUsername(), item.getCommission(), "eBay", "ebay", item.getItemHref(), item.getItemAffiliateWebUrl(), "", "", "", item.hasAvailableCoupons(), item.isTopRatedBuyingExperience(), item.isNew(), item.isPriorityListing(), false, false);
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
                p = new Product("P" + rand.nextInt(), item.getTitle(), item.getPrice().getValue(), gtin, item.getPrice().getCurrency(), item.getImage().getImageUrl(), item.getCategories().get(0).getCategoryName(), item.getSeller().getUsername(), item.getCommission(), "eBay","ebay", item.getItemHref(), item.getItemAffiliateWebUrl(), "", "", "", item.hasAvailableCoupons(), item.isTopRatedBuyingExperience(), item.isNew(), item.isPriorityListing(), false, false);
                EbayProducts.add(p);
            }
            return EbayProducts;
        } else {
            System.out.println("No product details found");
            return null;
        }
    }

}
